package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blubasics.effect.BaseModEffects;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.projectile.DarknessBlobEntity;
import net.blumasc.blusbeasts.entity.variants.DeepshovelerVariant;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

import static net.blumasc.blusbeasts.entity.variants.DeepshovelerVariant.*;

public class DeepshovelerEntity extends Monster {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState diggingAnimationState = new AnimationState();
    public final AnimationState shootAnimationState = new AnimationState();
    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(DeepshovelerEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> DIGGING =
            SynchedEntityData.defineId(DeepshovelerEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> SHOOTING =
            SynchedEntityData.defineId(DeepshovelerEntity.class, EntityDataSerializers.INT);

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 15)
                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .add(Attributes.FOLLOW_RANGE, (double)48.0F);
    }

    private Vec3 targetPos;
    private BlockPos targetBlock;

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(1, new DeepshovelerDigLockGoal(this));
        this.goalSelector.addGoal(2, new DeepshovelerFleeDarkenedPlayersGoal(this));
        this.goalSelector.addGoal(3, new DeepshovelerFindOreGoal(this));
        this.goalSelector.addGoal(4, new DeepshovelerAttackLightGoal(this));
        this.goalSelector.addGoal(4, new DeepshovelerAttackPlayerGoal(this));

        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public DeepshovelerVariant getVariant(){
        return DeepshovelerVariant.byId(this.getTypeVariant() & 255);
    }

    private void setVariant(DeepshovelerVariant variant){
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    public int getDigging(){
        return this.entityData.get(DIGGING);
    }

    private void setDigging(int digging){
        this.entityData.set(DIGGING, digging);
    }

    public int getShooting(){
        return this.entityData.get(SHOOTING);
    }

    private void setShooting(int shooting){
        this.entityData.set(SHOOTING, shooting);
    }

    public TagKey<Block> getTargetOre() {
        return switch (this.getVariant()) {
            case STONE -> BlockTags.IRON_ORES;
            case IRON -> BlockTags.GOLD_ORES;
            case GOLD -> BlockTags.DIAMOND_ORES;
            default -> null;
        };
    }

    public Item getTargetOreParticle() {
        return switch (this.getVariant()) {
            case STONE -> Items.RAW_IRON;
            case IRON -> Items.RAW_GOLD;
            case GOLD -> Items.DIAMOND;
            default -> null;
        };
    }

    public void upgradeVariant() {
        if (this.getVariant() == STONE) {
            setVariant(IRON);
        } else if (this.getVariant() == IRON) {
            setVariant(GOLD);
        } else if (this.getVariant() == GOLD) {
            setVariant(DeepshovelerVariant.DIAMOND);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, STONE.getId());
        builder.define(DIGGING, 0);
        builder.define(SHOOTING, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getTypeVariant());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(VARIANT, compound.getInt("Variant"));
    }

    public DeepshovelerEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.METAL_STEP, 0.15F, 1.0F);
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.METAL_HIT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.METAL_BREAK;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            animationStarting();
        }else{
            if(getShooting()>0){
                setShooting(getShooting()-1);
                if(getShooting()==5){
                    if(targetPos!=null) {
                        shootAt(targetPos);
                        targetPos = null;
                    }
                }
            }
            if(getDigging()>0){
                if(targetBlock == null){
                    setDigging(0);
                    return;
                }
                if(!this.position().closerThan(targetBlock.getCenter(), 2)){
                    setDigging(0);
                    return;
                }
                BlockState target = level().getBlockState(targetBlock);
                if(getDigging()%15==5){
                    Vec3 blockCenter = Vec3.atCenterOf(targetBlock);
                    Vec3 mobCenter = getEyePosition();

                    Vec3 spawnPos = mobCenter.add(blockCenter).scale(0.5);

                    int count = 5 + random.nextInt(3);

                    for (int i = 0; i < count; i++) {
                        double offsetX = (random.nextDouble() - 0.5) * 0.2;
                        double offsetY = (random.nextDouble() - 0.5) * 0.2;
                        double offsetZ = (random.nextDouble() - 0.5) * 0.2;
                        Vec3 outward = spawnPos.subtract(mobCenter).normalize();
                        double speed = 0.1 + random.nextDouble() * 0.1;

                        ((ServerLevel)level()).sendParticles(
                                new ItemParticleOption(
                                        ParticleTypes.ITEM,
                                        getTargetOreParticle().getDefaultInstance()
                                ),
                                spawnPos.x + offsetX,
                                spawnPos.y + offsetY,
                                spawnPos.z + offsetZ,
                                3,
                                outward.x ,
                                outward.y,
                                outward.z,
                                speed*0.3
                        );
                    }
                }
                if(target.is(getTargetOre())){
                    setDigging(getDigging()+1);
                    float hardness = target.getDestroySpeed(level(), targetBlock);
                    int requiredTime = (int)(hardness * 40);
                    if(getDigging()>requiredTime){
                        target.getBlock().destroy(level(), targetBlock, target);
                        level().setBlock(targetBlock, ModBlocks.VOID_ORE.get().defaultBlockState(), 3);
                        upgradeVariant();
                        setDigging(0);
                        targetBlock=null;
                    }
                }else{
                    setDigging(0);
                    targetBlock=null;
                }
            }
        }
    }

    private void prepareShootAt(Vec3 targetPos) {
        this.targetPos = targetPos;
        this.setShooting(15);
    }

    private void shootAt(Vec3 target) {
        DarknessBlobEntity projectile = new DarknessBlobEntity(
                ModEntities.DARKNESS_BLOCK.get(),
                level()
        );

        projectile.setPos(getX(), getEyeY(), getZ());
        projectile.shoot(
                target.x - getX(),
                target.y - getEyeY(),
                target.z - getZ(),
                1.2F,
                0
        );
        level().playSound(null, getX(), getY(), getZ(), ModSounds.DEEPSHOVELER_SHOOT.get(), SoundSource.PLAYERS, 1.0f, 0.8f+getRandom().nextFloat()*0.4f);
        level().addFreshEntity(projectile);
    }

    private void animationStarting(){
        if(this.getShooting()>0){
            this.shootAnimationState.startIfStopped(this.tickCount);
        }else{
            this.shootAnimationState.stop();
        }
        if(this.getDigging()>0){
            this.idleAnimationState.stop();
            this.diggingAnimationState.startIfStopped(this.tickCount);
        }else{
            this.diggingAnimationState.stop();
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    public static boolean checkDeepshovelerSpawnRules(EntityType<? extends Monster> deepShoveler, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        return pos.getY() <=  10 && level.getRawBrightness(pos, 0) == 0;
    }

    public class DeepshovelerAttackLightGoal extends Goal {

        private final DeepshovelerEntity mob;
        private BlockPos targetPos;
        private boolean hasShot;
        private int cooldown = 0;

        public DeepshovelerAttackLightGoal(DeepshovelerEntity mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            if (mob.getDigging() > 0) return false;
            if (mob.getShooting() > 0) return false;

            if (cooldown > 0) {
                cooldown--;
                return false;
            }

            if (mob.level().getBrightness(LightLayer.BLOCK, mob.blockPosition()) < 5)
                return false;

            BlockPos mobPos = mob.blockPosition();
            Vec3 eyePos = mob.getEyePosition();

            int bestLight = 0;
            double bestDistance = Double.MAX_VALUE;
            BlockPos bestPos = null;

            for (BlockPos pos : BlockPos.betweenClosed(
                    mobPos.offset(-8, -4, -8),
                    mobPos.offset(8, 4, 8))) {

                BlockState state = mob.level().getBlockState(pos);
                if (!state.getFluidState().isEmpty() && !state.getFluidState().isSource()) {
                    continue;
                }
                int light = state.getLightEmission();

                if (light > 3) {

                    Vec3 targetCenter = Vec3.atCenterOf(pos);

                    ClipContext context = new ClipContext(
                            eyePos,
                            targetCenter,
                            ClipContext.Block.COLLIDER,
                            ClipContext.Fluid.NONE,
                            mob
                    );

                    BlockHitResult result = mob.level().clip(context);

                    boolean clearSight = result.getType() == HitResult.Type.MISS
                            || result.getBlockPos().equals(pos);

                    if (!clearSight) continue;

                    double distance = eyePos.distanceToSqr(targetCenter);
                    if (light > bestLight ||
                            (light == bestLight && distance < bestDistance)) {

                        bestLight = light;
                        bestDistance = distance;
                        bestPos = pos.immutable();
                    }
                }
            }

            if (bestPos != null) {
                targetPos = bestPos;
                return true;
            }

            return false;
        }

        @Override
        public void start() {
            hasShot = false;
        }

        @Override
        public void tick() {
            if (!hasShot && targetPos != null) {
                mob.prepareShootAt(targetPos.getCenter());
                hasShot = true;
                cooldown = 40;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return !hasShot;
        }
    }

    public class DeepshovelerAttackPlayerGoal extends Goal {

        private final DeepshovelerEntity mob;
        private Player target;
        private boolean hasShot;
        private int cooldown = 0;

        public DeepshovelerAttackPlayerGoal(DeepshovelerEntity mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            if (mob.getDigging() > 0) return false;
            if(mob.getShooting()>0){
                return false;
            }
            if (cooldown > 0) {
                cooldown--;
                return false;
            }

            List<Player> players = mob.level().getEntitiesOfClass(
                    Player.class,
                    mob.getBoundingBox().inflate(12),
                    p -> !p.hasEffect(BaseModEffects.VOID_EFFECT) && !p.isCreative() && !p.isSpectator()
            );

            if (!players.isEmpty()) {
                target = players.get(0);
                return true;
            }

            return false;
        }

        @Override
        public void start() {
            hasShot = false;
        }

        @Override
        public void tick() {
            if (!hasShot && target != null) {
                mob.prepareShootAt(target.position());
                hasShot = true;
                cooldown = 40;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return !hasShot;
        }
    }
    public class DeepshovelerDigLockGoal extends Goal {

        private final DeepshovelerEntity mob;

        public DeepshovelerDigLockGoal(DeepshovelerEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            return mob.getDigging() > 0;
        }

        @Override
        public boolean canContinueToUse() {
            return mob.getDigging() > 0;
        }
    }
    public class DeepshovelerFindOreGoal extends Goal {

        private final DeepshovelerEntity mob;
        private BlockPos foundOre;
        private int searchCooldown = 0;

        public DeepshovelerFindOreGoal(DeepshovelerEntity mob) {
            this.mob = mob;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }

        @Override
        public boolean canUse() {
            if (mob.getDigging() > 0) return false;
            if (mob.targetBlock != null) return false;
            if (searchCooldown > 0) {
                searchCooldown--;
                return false;
            }
            TagKey<Block> targetOre = mob.getTargetOre();
            if (targetOre == null) return false;

            BlockPos mobPos = mob.blockPosition();

            foundOre = null;

            for (BlockPos pos : BlockPos.betweenClosed(
                    mobPos.offset(-12, -6, -12),
                    mobPos.offset(12, 6, 12))) {

                if (mob.level().getBlockState(pos).is(targetOre)) {
                    for (Direction dir : Direction.values()) {
                        BlockPos adjacent = pos.relative(dir);

                        if (mob.level().getBlockState(adjacent).isAir()
                                && mob.level().getBlockState(adjacent.below()).isSolid()) {

                            Path path = mob.getNavigation().createPath(adjacent, 0);

                            if (path != null) {
                                Node end = path.getEndNode();
                                if (end != null &&
                                        end.x == adjacent.getX() &&
                                        end.y == adjacent.getY() &&
                                        end.z == adjacent.getZ()) {
                                    if (foundOre == null ||
                                            pos.distSqr(mob.blockPosition()) <
                                                    foundOre.distSqr(mob.blockPosition())) {
                                        foundOre = pos.immutable();
                                    }
                                }
                            }
                        }
                    }
                }
            }
            searchCooldown = 40;
            if(foundOre!=null){
                return true;
            }

            return false;
        }

        @Override
        public void start() {
            mob.getNavigation().moveTo(
                    foundOre.getX(),
                    foundOre.getY(),
                    foundOre.getZ(),
                    1.0D
            );
        }

        @Override
        public void tick() {
            if (foundOre == null) return;

            double distance = mob.distanceToSqr(
                    foundOre.getX() + 0.5,
                    foundOre.getY() + 0.5,
                    foundOre.getZ() + 0.5
            );

            if (distance < 4.0D) {
                mob.getNavigation().stop();
                mob.targetBlock = foundOre;
                mob.setDigging(1);
                foundOre = null;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return foundOre != null && mob.getDigging() == 0;
        }
    }
    public class DeepshovelerFleeDarkenedPlayersGoal extends Goal {

        private final DeepshovelerEntity mob;

        public DeepshovelerFleeDarkenedPlayersGoal(DeepshovelerEntity mob) {
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            if (mob.getDigging() > 0) return false;
            List<Player> players = mob.level().getEntitiesOfClass(
                    Player.class,
                    mob.getBoundingBox().inflate(12)
            );

            if (players.isEmpty()) return false;

            boolean allDark = players.stream()
                    .allMatch(p -> p.hasEffect(BaseModEffects.VOID_EFFECT));

            return allDark;
        }

        @Override
        public void start() {
            Player nearest = mob.level().getNearestPlayer(mob, 12);
            if (nearest != null) {
                Vec3 fleePos = DefaultRandomPos.getPosAway(mob, 16, 7, nearest.position());
                if (fleePos != null) {
                    mob.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.2D);
                }
            }
        }
    }
}
