package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blubasics.entity.custom.projectile.MeteoriteEntity;
import net.blumasc.blubasics.entity.custom.projectile.SpikeEntity;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.variants.BurryVariant;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.BossEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.List;

public class GraveEntity extends Monster {

    // ── Animation states ──────────────────────────────────────────────────────
    public final AnimationState idleAnimationState     = new AnimationState();
    public final AnimationState whirlingAnimationState = new AnimationState();
    public final AnimationState throwAnimationState    = new AnimationState();
    public final AnimationState hitAnimationState      = new AnimationState();
    public final AnimationState screamAnimationState   = new AnimationState();

    // ── Synced data ───────────────────────────────────────────────────────────
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(GraveEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> ATTACK_PHASE =
            SynchedEntityData.defineId(GraveEntity.class, EntityDataSerializers.INT);

    // ── Attack state machine ──────────────────────────────────────────────────
    private enum AttackPhase { NONE, SPIKE, METEOR, SUMMON }

    private AttackPhase currentPhase   = AttackPhase.NONE;
    private int         attackTick     = 0;      // ticks since phase started
    private int         retargetTimer  = 0;

    private void setPhase(AttackPhase phase) {
        currentPhase = phase;
        this.entityData.set(ATTACK_PHASE, phase.ordinal());
    }

    // Meteor held above head before throwing
    private MeteoriteEntity chargedMeteor = null;

    // ── Boss bar ──────────────────────────────────────────────────────────────
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.translatable("entity.blusbeasts.grave"),
            BossEvent.BossBarColor.YELLOW,
            BossEvent.BossBarOverlay.NOTCHED_10);

    // ═════════════════════════════════════════════════════════════════════════
    //  Construction & registration
    // ═════════════════════════════════════════════════════════════════════════

    public GraveEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH,      150)
                .add(Attributes.ATTACK_DAMAGE,     8.0)
                .add(Attributes.MOVEMENT_SPEED,    0.32)
                .add(Attributes.FOLLOW_RANGE,     48.0)
                .add(Attributes.ARMOR,             4.0);
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Variant (unchanged from your original)
    // ═════════════════════════════════════════════════════════════════════════

    private int getTypeVariant()              { return this.entityData.get(VARIANT); }
    public  BurryVariant getVariant()         { return BurryVariant.byId(getTypeVariant() & 255); }
    public  void setVariant(BurryVariant v)   { this.entityData.set(VARIANT, v.getId() & 255); }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(ATTACK_PHASE, 0);
    }

    @Override public void addAdditionalSaveData(CompoundTag c) {
        super.addAdditionalSaveData(c);
        c.putInt("burryVariant", getVariant().getId());
    }

    @Override public void readAdditionalSaveData(CompoundTag c) {
        super.readAdditionalSaveData(c);
        setVariant(BurryVariant.byId(c.getInt("burryVariant")));
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Boss bar
    // ═════════════════════════════════════════════════════════════════════════

    @Override public void startSeenByPlayer(ServerPlayer p) { super.startSeenByPlayer(p); bossEvent.addPlayer(p); }
    @Override public void stopSeenByPlayer(ServerPlayer p)  { super.stopSeenByPlayer(p);  bossEvent.removePlayer(p); }

    @Override
    public void aiStep() {
        super.aiStep();
        bossEvent.setProgress(getHealth() / getMaxHealth());
    }

    private boolean deathSandSpawned = false;

    @Override
    public void die(DamageSource source) {
        super.die(source);
        if (!level().isClientSide() && !deathSandSpawned) {
            deathSandSpawned = true;
            spawnDeathSand();
        }
    }

    private void spawnDeathSand() {
        Block sandBlock = switch (getVariant()) {
            case RED_SAND -> Blocks.RED_SAND;
            default  -> Blocks.SAND;
        };

        int count = 10 + random.nextInt(5);

        for (int i = 0; i < count; i++) {
            double ox = (random.nextDouble() - 0.5) * getBbWidth() * 3;
            double oy = random.nextDouble() * getBbHeight();
            double oz = (random.nextDouble() - 0.5) * getBbWidth() * 3;

            FallingBlockEntity sand = new FallingBlockEntity(
                    level(),
                    getX() + ox,
                    getY() + oy,
                    getZ() + oz,
                    sandBlock.defaultBlockState()
            );

            sand.setDeltaMovement(
                    (random.nextDouble() - 0.5) * 0.6,
                    0.3 + random.nextDouble() * 0.5,
                    (random.nextDouble() - 0.5) * 0.6
            );

            sand.time = 1;
            sand.dropItem = false;

            level().addFreshEntity(sand);
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Goal registration
    // ═════════════════════════════════════════════════════════════════════════

    @Override
    protected void registerGoals() {
        // Movement
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(3, new GraveAttackGoal(this));
        this.goalSelector.addGoal(4, new GraveRepositionGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        // Targeting — anything alive that isn't a Grave or Burry
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, 0, true, false,
                e -> (e instanceof Player p &&!p.isCreative() && !p.isSpectator())));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 0, true, false,
                e -> !(e instanceof GraveEntity) && !(e instanceof BurryEntity)));
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Main tick
    // ═════════════════════════════════════════════════════════════════════════

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide()) {
            animate();
            return;
        }

        // Retarget periodically
        retargetTimer++;
        if (retargetTimer >= 200 && currentPhase==AttackPhase.NONE) {
            retargetTimer = 0;
            switchTarget();
        }

        // Phase state machine
        if (currentPhase != AttackPhase.NONE) {
            attackTick++;
            switch (currentPhase) {
                case SPIKE  -> tickSpikePhase();
                case METEOR -> tickMeteorPhase();
                case SUMMON -> tickSummonPhase();
                default     -> {}
            }
        }
        if (chargedMeteor != null && !chargedMeteor.isRemoved()) {
            chargedMeteor.setPos(getX(), getY() + getBbHeight() + 1.5, getZ());
            chargedMeteor.setDeltaMovement(Vec3.ZERO);
        }
        if (tickCount % 20 == 0) {
            clearBlocksAround();
        }
    }

    private void clearBlocksAround() {
        BlockPos center = blockPosition().above((int) getBbHeight() / 2);
        int range = (int) getBbWidth() + 2;

        for (int dx = -range; dx <= range; dx++) {
            for (int dy = -1; dy <= (int) getBbHeight() + 2; dy++) {
                for (int dz = -range; dz <= range; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = level().getBlockState(pos);
                    if (!state.isAir()
                            && !state.is(BlockTags.WITHER_IMMUNE)
                            && level().getBlockEntity(pos) == null) {
                        level().destroyBlock(pos, true);
                    }
                }
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Client-side animations
    // ═════════════════════════════════════════════════════════════════════════

    private void animate() {
        AttackPhase phase = AttackPhase.values()[this.entityData.get(ATTACK_PHASE)];
        whirlingAnimationState.startIfStopped(tickCount);
        switch (phase) {
            case SPIKE  -> { hitAnimationState.startIfStopped(tickCount); idleAnimationState.stop(); }
            case METEOR -> { throwAnimationState.startIfStopped(tickCount); idleAnimationState.stop(); }
            case SUMMON -> { screamAnimationState.startIfStopped(tickCount); }
            default     -> {
                hitAnimationState.stop();
                throwAnimationState.stop();
                screamAnimationState.stop();
                idleAnimationState.startIfStopped(tickCount);
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Attack initiation (called from GraveAttackGoal)
    // ═════════════════════════════════════════════════════════════════════════

    public boolean isAttacking() { return currentPhase != AttackPhase.NONE; }

    public void beginAttack(LivingEntity target) {
        if (currentPhase != AttackPhase.NONE) return;

        double distSq = distanceToSqr(target);
        boolean targetOnGround = target.onGround();

        if (!targetOnGround || distSq > 20 * 20) {
            if (distSq > 8 * 8) {
                startMeteorPhase();
            } else {
                startSpikePhase();
            }
        } else {
            List<BurryEntity> nearby = level().getEntitiesOfClass(BurryEntity.class,
                    getBoundingBox().inflate(48),
                    e ->  e.isAlive());
            if (random.nextBoolean() || nearby.size()>=6) startSpikePhase();
            else startSummonPhase();
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  SPIKE PHASE  (hit animation, 20 ticks = 1 second; spike at tick 10)
    // ═════════════════════════════════════════════════════════════════════════

    private void startSpikePhase() {
        setPhase(AttackPhase.SPIKE);
        attackTick   = 0;
        spikeTargetPos = null;
    }

    private BlockPos spikeTargetPos = null;

    private void tickSpikePhase() {
        LivingEntity target = getTarget();

        // Ripple effect: ticks 1-9, radiating out toward target
        if (attackTick == 15 && target != null) {
            spikeTargetPos = target.blockPosition();
        }
        if (attackTick >= 15  && attackTick <= 55 && target != null) {
            spawnRippleParticle(attackTick, spikeTargetPos);
        }

        // Spike spawns at midpoint — tick 10
        if (attackTick == 55) {
            BlockPos groundPos = findGround(spikeTargetPos);
            if (groundPos != null) {
                SpikeEntity spike = new SpikeEntity(
                        level(),
                        groundPos,
                        3,
                        60,
                        null
                );
                level().addFreshEntity(spike);
            }
        }


        if (attackTick >= 60) endPhase();
    }

    private BlockPos findGround(BlockPos startPos) {
        if (startPos == null) return null;

        for (int i = 0; i <= 10; i++) {
            BlockPos check = startPos.below(i);
            if (level().getBlockState(check).isSolidRender(level(), check)
                    && level().getBlockState(check.above()).canBeReplaced()) {
                return check;
            }
        }

        for (int i = 1; i <= 10; i++) {
            BlockPos check = startPos.above(i);
            if (level().getBlockState(check).isSolidRender(level(), check)
                    && level().getBlockState(check.above()).canBeReplaced()) {
                return check;
            }
        }

        return null;
    }

    /**
     * Spawns a particle ring at a fraction t (0→1) of the way between
     * the boss and the target, creating a ground-ripple feel.
     */
    private void spawnRippleParticle(int step, BlockPos targetPos) {
        double t = (step-15) / 40.0;
        double px = getX() + (targetPos.getX() - getX()) * t;
        double pz = getZ() + (targetPos.getZ() - getZ()) * t;
        BlockPos ground = findGround(new BlockPos((int) px, (int) getY(), (int) pz));
        if(ground==null)return;
        double py = ground.above().getBottomCenter().y;

        if (level() instanceof ServerLevel sl) {
            for (int i = 0; i < 6; i++) {
                double angle  = (Math.PI * 2 / 6) * i;
                double radius = 0.8;
                sl.sendParticles(ParticleTypes.CRIT,
                        px + Math.cos(angle) * radius, py + 0.1, pz + Math.sin(angle) * radius,
                        1, 0, 0, 0, 0.05);
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  METEOR PHASE  (throw animation, 20 ticks = 1 second)
    //    tick 5  (0.25s) → spawn meteor above head (starts small, grows)
    //    tick 13 (0.65s) → throw meteor at target
    // ═════════════════════════════════════════════════════════════════════════

    private void startMeteorPhase() {
        setPhase(AttackPhase.METEOR);
        attackTick   = 0;
        chargedMeteor = null;
    }

    private void tickMeteorPhase() {
        if (attackTick == 10) {
            chargedMeteor = new MeteoriteEntity(level(), this);
            chargedMeteor.setExplodes(false);
            chargedMeteor.setReplaces(false);
            chargedMeteor.setRenderBlock(Blocks.DRIPSTONE_BLOCK.defaultBlockState());
            chargedMeteor.setShellBlock(Blocks.DRIPSTONE_BLOCK.defaultBlockState());
            chargedMeteor.setCoreBlockTag(getVariant()==BurryVariant.RED_SAND? ModTags.Blocks.RED_SAND_BLOCKS : ModTags.Blocks.SAND_BLOCKS);
            chargedMeteor.setPos(getX(), getY() + getBbHeight() + 1.5, getZ());
            chargedMeteor.setRadius(0); // start small
            chargedMeteor.setNoGravity(true);
            chargedMeteor.setDeltaMovement(Vec3.ZERO);
            level().addFreshEntity(chargedMeteor);
        }

        if (attackTick > 10 && attackTick < 26 && chargedMeteor != null && !chargedMeteor.isRemoved()) {
            int growRadius = 1 + (attackTick - 5) / 4; // 1 → up to 4
            chargedMeteor.setRadius(Math.min(growRadius, 2));
        }

        if (attackTick == 26) {
            LivingEntity target = getTarget();
            System.out.println(target);
            if (chargedMeteor != null && !chargedMeteor.isRemoved()) {
                if (target != null && distanceToSqr(target) > 8 * 8) {
                    //chargedMeteor.setNoGravity(false);
                    chargedMeteor.setRadius(2);
                    Vec3 toTarget = target.position()
                            .add(0, target.getBbHeight() * 0.5, 0)
                            .subtract(chargedMeteor.position())
                            .normalize()
                            .scale(1.8);
                    chargedMeteor.setDeltaMovement(toTarget);
                } else {
                    chargedMeteor.discard();
                }
                chargedMeteor = null;
            }
        }

        if (attackTick >= 40) endPhase();
    }

    @Override
    public void onRemovedFromLevel() {
        super.onRemovedFromLevel();
        if (chargedMeteor != null && !chargedMeteor.isRemoved()) {
            chargedMeteor.discard();
        }
        chargedMeteor = null;
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  SUMMON PHASE  (scream animation, ~40 ticks; minions appear at peak)
    // ═════════════════════════════════════════════════════════════════════════

    private void startSummonPhase() {
        setPhase(AttackPhase.SUMMON);
        attackTick   = 0;
    }

    private void tickSummonPhase() {
        // Summon minions midway through scream (tick 20)
        if (attackTick == 20) {
            int count = 2 + random.nextInt(3); // 2–4 Burrys
            for (int i = 0; i < count; i++) {
                double angle  = (Math.PI * 2 / count) * i;
                double radius = 3.0;
                double sx     = getX() + Math.cos(angle) * radius;
                double sz     = getZ() + Math.sin(angle) * radius;
                BlockPos spawnPos = BlockPos.containing(sx, getY(), sz);

                BurryEntity burry = new BurryEntity(ModEntities.BURRY.get(), level());
                burry.setPos(sx, getY(), sz);
                burry.setVariant(this.getVariant());

                LivingEntity target = getTarget();
                if (target != null) burry.setTarget(target);

                level().addFreshEntity(burry);
            }
        }

        if (attackTick >= 40) endPhase();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Helpers
    // ═════════════════════════════════════════════════════════════════════════

    private void endPhase() {
        setPhase(AttackPhase.NONE);
        attackTick    = 0;
        spikeTargetPos = null;
        if (chargedMeteor != null && !chargedMeteor.isRemoved()) {
            chargedMeteor.discard();
        }
        chargedMeteor = null;
    }

    /** Pick a random nearby living entity that is not a Grave or Burry. */
    private void switchTarget() {
        List<LivingEntity> nearby = level().getEntitiesOfClass(LivingEntity.class,
                getBoundingBox().inflate(48),
                e -> e != this
                        && !(e instanceof GraveEntity)
                        && !(e instanceof BurryEntity)
                        && e.isAlive());

        if (!nearby.isEmpty()) {
            setTarget(nearby.get(random.nextInt(nearby.size())));
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  Inner goal class
    // ═════════════════════════════════════════════════════════════════════════

    private static class GraveAttackGoal extends Goal {
        private final GraveEntity grave;
        private int cooldown = 0;

        GraveAttackGoal(GraveEntity grave) {
            this.grave = grave;
            setFlags(java.util.EnumSet.of(Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            return grave.getTarget() != null
                    && grave.getTarget().isAlive()
                    && !grave.isAttacking();
        }

        @Override
        public boolean canContinueToUse() {
            return grave.isAttacking() || (grave.getTarget() != null && grave.getTarget().isAlive());
        }

        @Override
        public void tick() {
            LivingEntity target = grave.getTarget();
            if (target == null) return;

            grave.getLookControl().setLookAt(target, 30f, 30f);

            if (grave.isAttacking()) return; // mid-animation, do nothing

            cooldown--;
            if (cooldown <= 0) {
                grave.beginAttack(target);
                // cooldown between attacks: 40-80 ticks (2-4 seconds)
                cooldown = 40 + grave.random.nextInt(40);
            }
        }
    }
    private static class GraveRepositionGoal extends Goal {
        private final GraveEntity grave;
        private static final double PREFERRED_MIN = 7.0;
        private static final double PREFERRED_MAX = 18.0;
        private Vec3 moveTarget = null;

        GraveRepositionGoal(GraveEntity grave) {
            this.grave = grave;
            setFlags(java.util.EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return grave.getTarget() != null && grave.getTarget().isAlive();
        }

        @Override
        public boolean canContinueToUse() {
            return canUse() && moveTarget != null;
        }

        @Override
        public void start() {
            recalculate();
        }

        @Override
        public void tick() {
            // Recalculate every 20 ticks or when we've arrived
            if (grave.tickCount % 20 == 0 || hasArrived()) {
                recalculate();
            }
            if (moveTarget != null) {
                grave.getNavigation().moveTo(moveTarget.x, moveTarget.y, moveTarget.z, 1.0);
            }
        }

        private boolean hasArrived() {
            if (moveTarget == null) return true;
            return grave.position().distanceToSqr(moveTarget) < 2.0;
        }

        private void recalculate() {
            LivingEntity target = grave.getTarget();
            if (target == null) { moveTarget = null; return; }

            // Gather all threats: the target + any nearby players
            List<LivingEntity> threats = new java.util.ArrayList<>();
            threats.add(target);
            grave.level().getEntitiesOfClass(Player.class,
                    grave.getBoundingBox().inflate(20),
                    p -> p != target && !p.isCreative() && !p.isSpectator()
            ).forEach(threats::add);

            // Compute average threat position
            double ax = threats.stream().mapToDouble(Entity::getX).average().orElse(target.getX());
            double az = threats.stream().mapToDouble(Entity::getZ).average().orElse(target.getZ());

            Vec3 toThreat = new Vec3(ax - grave.getX(), 0, az - grave.getZ());
            double dist = toThreat.length();

            if (dist < 0.001) {
                // Threat is on top of us — flee in a random direction
                double angle = grave.random.nextDouble() * Math.PI * 2;
                toThreat = new Vec3(Math.cos(angle), 0, Math.sin(angle));
                dist = 1.0;
            }

            Vec3 dir = toThreat.normalize();
            double desiredDist;

            if (dist < PREFERRED_MIN) {
                // Too close — move directly away
                desiredDist = PREFERRED_MIN + 2.0;
                moveTarget = grave.position().add(dir.scale(-(desiredDist - dist)));
            } else if (dist > PREFERRED_MAX) {
                // Too far — close in
                desiredDist = PREFERRED_MAX - 2.0;
                moveTarget = grave.position().add(dir.scale(dist - desiredDist));
            } else {
                // In the sweet spot — strafe sideways slightly so it doesn't stand still
                Vec3 strafe = new Vec3(-dir.z, 0, dir.x)
                        .scale(grave.random.nextBoolean() ? 3.0 : -3.0);
                moveTarget = grave.position().add(strafe);
            }
        }
    }
}