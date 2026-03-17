package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.effect.ModEffects;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class PrayfinderEntity extends TamableAnimal implements FlyingAnimal {

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState perchAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> PERCHED =
            SynchedEntityData.defineId(PrayfinderEntity.class, EntityDataSerializers.BOOLEAN);

    public boolean isPerched() {
        return this.entityData.get(PERCHED);
    }

    public void setPerched(boolean perched) {
        this.entityData.set(PERCHED, perched);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PERCHED, false);
    }

    public PrayfinderEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.navigation = new FlyingPathNavigation(this, level);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new DayFleeAndDespawnGoal(this, 1.6));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new FleeCreeperGoal(this));
        this.goalSelector.addGoal(3,new PerchWhenOrderedToSitGoal(this,1.0));
        this.goalSelector.addGoal(4, new PickupFoodGoal(this, 1.2));
        this.goalSelector.addGoal(5, new AerialAttackGoal(this, 1.2));
        this.goalSelector.addGoal(6,new FollowOwnerGoal(this,1.2,6,1));
        this.goalSelector.addGoal(7, new PrayfinderTemptGoal(this, 1.5,stack -> stack.is(ModTags.Items.PRAYFINDER_FOOD)));
        this.goalSelector.addGoal(8, new PrayfinderWanderGoal(this, 1.0));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));

        this.targetSelector.addGoal(0, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new TargetDislikedPlayerGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6d)
                .add(Attributes.ATTACK_DAMAGE, 2d)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.FLYING_SPEED, 0.4F)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    public void tick(){
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }else{
            if (!this.isTame() && this.level().isDay()) {
                Player player = this.level().getNearestPlayer(this, 32);

                if (player == null) {
                        ((ServerLevel)this.level()).sendParticles(
                                net.minecraft.core.particles.ParticleTypes.CLOUD,
                                this.getX(), this.getY(), this.getZ(),
                                12, 0.3, 0.3, 0.3, 0.02
                        );
                        this.discard();
                }
            }
        }
    }

    private void setupAnimationStates() {
        if (this.isFlying()) {
            this.idleAnimationState.stop();
            this.perchAnimationState.stop();
            this.flyAnimationState.startIfStopped(this.tickCount);
            return;
        }

        if(this.isPerched())
        {
            this.idleAnimationState.stop();
            this.flyAnimationState.stop();
            this.perchAnimationState.startIfStopped(this.tickCount);
            return;
        }

        this.flyAnimationState.stop();
        this.perchAnimationState.stop();
        this.idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public void travel(Vec3 vec3) {
        if (this.isFlying()) {
            this.moveRelative(0.1F, vec3);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9));
        } else {
            super.travel(vec3);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.is(ModTags.Items.PRAYFINDER_FOOD)) {
            if (!this.isTame() && this.likesPlayer(player)) {
                if (!player.level().isClientSide) {
                    if (this.random.nextInt(3) == 0) {
                        this.tame(player);
                        this.navigation.stop();
                        this.setOrderedToSit(true);
                        this.level().broadcastEntityEvent(this, (byte)7);
                    } else {
                        this.level().broadcastEntityEvent(this, (byte)6);
                    }
                }
                stack.shrink(1);
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
            if (this.isTame() && this.isOwnedBy(player)) {
                if (!player.level().isClientSide) {
                    if(this.getHealth()<this.getMaxHealth()) {
                        this.heal(2.0F);
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                                SoundEvents.PARROT_EAT, this.getSoundSource(), 1.0F, 1.0F);
                    }
                }
                stack.shrink(1);
                return InteractionResult.sidedSuccess(player.level().isClientSide);
            }
            return InteractionResult.PASS;
        }
        if (this.isTame() && this.isOwnedBy(player)) {
            if (!player.level().isClientSide) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.navigation.stop();
            }
            return InteractionResult.sidedSuccess(player.level().isClientSide);
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public boolean isFlying() {
        return getY()%1.0>0.1 || level().getBlockState(getOnPos()).canBeReplaced();
    }

    private boolean likesPlayer(Player p)
    {
        Inventory i = p.getInventory();
        for(ItemStack s :i.armor){
            if(s.is(ModTags.Items.PRAYFINDER_LIKED_ATTIRE)) return true;
        }
        Optional<ICuriosItemHandler> curiosInventoryOpt = CuriosApi.getCuriosInventory(p);
        if (curiosInventoryOpt.isPresent()) {
            Map<String, ICurioStacksHandler> curios = curiosInventoryOpt.get().getCurios();
            for (ICurioStacksHandler slotInventory : curios.values()) {
                for (int slot = 0; slot < slotInventory.getSlots(); slot++) {
                    ItemStack stack = slotInventory.getStacks().getStackInSlot(slot);
                    if (!stack.isEmpty() && stack.is(ModTags.Items.PRAYFINDER_LIKED_ATTIRE)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.PRAYFINDER_CHIRP.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PRAYFINDER_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.PRAYFINDER_DEATH.get();
    }

    public class PickupFoodGoal extends Goal {
        private final PrayfinderEntity prayfinder;
        private final double speed;

        public PickupFoodGoal(PrayfinderEntity entity, double speed) {
            this.prayfinder = entity;
            this.speed = speed;
        }

        @Override
        public boolean canUse() {
            if (prayfinder.isTame()) return false;

            return !prayfinder.level().getEntitiesOfClass(
                    ItemEntity.class,
                    prayfinder.getBoundingBox().inflate(8),
                    item -> item.getItem().is(ModTags.Items.PRAYFINDER_FOOD)
            ).isEmpty();
        }

        @Override
        public void tick() {
            List<ItemEntity> items = prayfinder.level().getEntitiesOfClass(
                    ItemEntity.class,
                    prayfinder.getBoundingBox().inflate(8),
                    item -> item.getItem().is(ModTags.Items.PRAYFINDER_FOOD)
            );

            if (!items.isEmpty()) {
                ItemEntity target = items.get(0);
                prayfinder.getNavigation().moveTo(target, speed);

                if (prayfinder.distanceTo(target) < 1.5) {
                    ItemStack stack = target.getItem();
                    prayfinder.heal(2);
                    stack.shrink(1);
                    if (!prayfinder.isSilent()) {
                        prayfinder.level().playSound(null, prayfinder.getX(), prayfinder.getY(), prayfinder.getZ(), SoundEvents.PARROT_EAT, prayfinder.getSoundSource(), 1.0F, 1.0F + (prayfinder.random.nextFloat() - prayfinder.random.nextFloat()) * 0.2F);
                    }
                    if (stack.isEmpty()) target.discard();
                }
            }
        }
    }
    public class PrayfinderWanderGoal extends WaterAvoidingRandomFlyingGoal {

        public PrayfinderWanderGoal(PathfinderMob p_25981_, double p_25982_) {
            super(p_25981_, p_25982_);
        }

        @Override
        public boolean canUse() {
            if(((PrayfinderEntity)mob).isOrderedToSit()) return false;
            return super.canUse();
        }
    }
    public class PerchWhenOrderedToSitGoal extends Goal {
        private final PrayfinderEntity prayfinder;
        private final double speed;
        private BlockPos perchPos;

        public PerchWhenOrderedToSitGoal(PrayfinderEntity prayfinderEntity, double speed) {
            this.prayfinder = prayfinderEntity;
            this.speed = speed;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!prayfinder.isOrderedToSit())
                return false;

            if (perchPos != null)
                return true;

            perchPos = findNearestPerch();
            return perchPos != null;
        }

        @Override
        public void stop() {
            perchPos = null;
            prayfinder.setPerched(false);
        }

        @Override
        public boolean canContinueToUse() {
            return prayfinder.isOrderedToSit();
        }

        @Override
        public void start() {
            if (perchPos != null) {
                prayfinder.getNavigation().moveTo(
                        perchPos.getX() + 0.5,
                        perchPos.getY() + 0.2,
                        perchPos.getZ() + 0.5,
                        speed
                );
            }
        }

        @Override
        public void tick() {
            if (perchPos == null)
                return;

            double dist = prayfinder.position().distanceTo(Vec3.atCenterOf(perchPos));

            if (dist < 0.6) {
                prayfinder.getNavigation().stop();
                prayfinder.setDeltaMovement(Vec3.ZERO);

                prayfinder.setOnGround(true);
                prayfinder.setPerched(true);
            }

        }

        private BlockPos findNearestPerch() {
            BlockPos origin = prayfinder.blockPosition();
            Level level = prayfinder.level();
            int radius = 8;

            BlockPos best = null;
            double bestDist = Double.MAX_VALUE;

            for (BlockPos pos : BlockPos.betweenClosed(
                    origin.offset(-radius, -4, -radius),
                    origin.offset(radius, 2, radius))) {

                if (!level.getBlockState(pos).isSolid())
                    continue;

                if (!level.getBlockState(pos.above()).isAir())
                    continue;

                double dist = pos.distSqr(origin);

                if (dist < bestDist) {
                    bestDist = dist;
                    best = pos.immutable();
                }
            }

            return best;
        }
    }
    public class PrayfinderTemptGoal extends Goal {
        private static final TargetingConditions TEMP_TARGETING = TargetingConditions.forNonCombat().range((double)10.0F).ignoreLineOfSight();
        private final TargetingConditions targetingConditions;
        protected final PrayfinderEntity mob;
        private final double speedModifier;
        private double px;
        private double py;
        private double pz;
        private double pRotX;
        private double pRotY;
        @javax.annotation.Nullable
        protected Player player;
        private int calmDown;
        private boolean isRunning;
        private final Predicate<ItemStack> items;

        public PrayfinderTemptGoal(PrayfinderEntity mob, double speedModifier, Predicate<ItemStack> items) {
            this.mob = mob;
            this.speedModifier = speedModifier;
            this.items = items;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            this.targetingConditions = TEMP_TARGETING.copy().selector(this::shouldFollow);
        }

        public boolean canUse() {
            if (this.calmDown > 0) {
                --this.calmDown;
                return false;
            } else {
                this.player = this.mob.level().getNearestPlayer(this.targetingConditions, this.mob);
                return this.player != null;
            }
        }

        private boolean shouldFollow(LivingEntity entity) {
            if(entity instanceof Player p && mob.likesPlayer(p)) {
                return this.items.test(entity.getMainHandItem()) || this.items.test(entity.getOffhandItem());
            }
            return false;
        }

        public boolean canContinueToUse() {
            return this.canUse();
        }

        public void start() {
            this.px = this.player.getX();
            this.py = this.player.getY();
            this.pz = this.player.getZ();
            this.isRunning = true;
        }

        public void stop() {
            this.player = null;
            this.mob.getNavigation().stop();
            this.calmDown = reducedTickDelay(100);
            this.isRunning = false;
        }

        public void tick() {
            this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadYRot() + 20), (float)this.mob.getMaxHeadXRot());
            if (this.mob.distanceToSqr(this.player) < (double)6.25F) {
                this.mob.getNavigation().stop();
            } else {
                this.mob.getNavigation().moveTo(this.player, this.speedModifier);
            }

        }

        public boolean isRunning() {
            return this.isRunning;
        }
    }
    public class TargetDislikedPlayerGoal extends TargetGoal {

        private final PrayfinderEntity prayfinder;
        private Player targetPlayer;

        public TargetDislikedPlayerGoal(PrayfinderEntity prayfinder) {
            super(prayfinder, false);
            this.prayfinder = prayfinder;
        }

        @Override
        public boolean canUse() {
            if (prayfinder.isTame()) return false;

            double searchRadius = 16.0D;
            List<Player> players = prayfinder.level().getEntitiesOfClass(
                    Player.class,
                    prayfinder.getBoundingBox().inflate(searchRadius),
                    player -> !(prayfinder.likesPlayer(player) || player.isCreative() || player.isSpectator())
            );

            if (players.isEmpty()) return false;

            double closestDistSqr = Double.MAX_VALUE;
            Player closest = null;
            for (Player p : players) {
                double distSqr = prayfinder.distanceToSqr(p);
                if (distSqr < closestDistSqr) {
                    closestDistSqr = distSqr;
                    closest = p;
                }
            }

            if (closest == null) return false;

            targetPlayer = closest;
            return true;
        }

        @Override
        public void start() {
            if (targetPlayer != null) {
                prayfinder.setTarget(targetPlayer);
            }
            super.start();
        }

        @Override
        public boolean canContinueToUse() {
            return targetPlayer != null && targetPlayer.isAlive() && !prayfinder.isTame() && !prayfinder.likesPlayer(targetPlayer) && prayfinder.distanceToSqr(targetPlayer) < 30*30;
        }

        @Override
        public void stop() {
            targetPlayer = null;
            prayfinder.setTarget(null);
            super.stop();
        }
    }

    public class AerialAttackGoal extends Goal {

        private final PrayfinderEntity prayfinder;
        private LivingEntity target;
        private final double speed;
        private int effectCooldown;

        public AerialAttackGoal(PrayfinderEntity prayfinder, double speed) {
            this.prayfinder = prayfinder;
            this.speed = speed;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            target = prayfinder.getTarget();
            if (target == null || !target.isAlive() || prayfinder.isOrderedToSit()) return false;

            if (target == prayfinder.getOwner()) return false;

            if (prayfinder.distanceToSqr(target) > 30 * 30) return false;
            if (prayfinder.getOwner() != null && prayfinder.getOwner().distanceToSqr(target) > 30 * 30) return false;

            return true;
        }

        @Override
        public void start() {
            effectCooldown = 0;
        }

        @Override
        public void tick() {
            if (target == null || !target.isAlive()) return;

            Vec3 targetPos = target.position().add(0, target.getBbHeight() + 1.5, 0);
            prayfinder.navigation.moveTo(targetPos.x, targetPos.y, targetPos.z, this.speed);

            double distanceSqr = prayfinder.position().distanceToSqr(targetPos);
            if (distanceSqr < 1.0) {
                if (prayfinder.level().random.nextInt(6) == 0) {
                    prayfinder.level().playSound(null,
                            prayfinder.getX(), prayfinder.getY(), prayfinder.getZ(),
                            ModSounds.PRAYFINDER_CALL.get(), prayfinder.getSoundSource(),
                            1.0F, 1.0F + (prayfinder.random.nextFloat() - prayfinder.random.nextFloat()) * 0.2F);
                }
                if (effectCooldown <= 0) {
                    if (target instanceof LivingEntity livingTarget) {
                        livingTarget.addEffect(new MobEffectInstance(ModEffects.PHEROMONES, 15 * 20, 3));
                    }
                    effectCooldown = 40;
                } else {
                    effectCooldown--;
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            return canUse();
        }
    }

    public class FleeCreeperGoal extends AvoidEntityGoal<Creeper> {

        public FleeCreeperGoal(PrayfinderEntity prayfinder) {
            super(prayfinder, Creeper.class, 6.0F, 1.5, 1.8, entity -> {
                return ((Creeper) entity).isIgnited();
            });
        }
    }

    public class DayFleeAndDespawnGoal extends Goal {

        private final PrayfinderEntity prayfinder;
        private Player fleeTarget;
        private final double speed;

        public DayFleeAndDespawnGoal(PrayfinderEntity prayfinder, double speed) {
            this.prayfinder = prayfinder;
            this.speed = speed;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (prayfinder.isTame()) return false;
            if (!prayfinder.level().isDay()) return false;

            fleeTarget = prayfinder.level().getNearestPlayer(prayfinder, 16);

            return fleeTarget!=null;
        }

        @Override
        public boolean canContinueToUse() {
            return !prayfinder.isTame()
                    && prayfinder.level().isDay()
                    && fleeTarget != null
                    && fleeTarget.isAlive()
                    && prayfinder.distanceToSqr(fleeTarget) < 40 * 40;
        }

        @Override
        public void tick() {
            fleeTarget = prayfinder.level().getNearestPlayer(prayfinder, 16);
            if (fleeTarget == null) return;

            Vec3 away = prayfinder.position().subtract(fleeTarget.position()).normalize();
            Vec3 fleePos = prayfinder.position().add(away.scale(10));

            prayfinder.getNavigation().moveTo(
                    fleePos.x,
                    fleePos.y + 1.5,
                    fleePos.z,
                    speed
            );
        }

        @Override
        public void stop() {
        }
    }





}
