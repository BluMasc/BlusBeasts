package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.Config;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class SalamanderEntity extends Animal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState infusionAnimationState = new AnimationState();
    public final AnimationState danceAnimation = new AnimationState();
    public final AnimationState scratchRightAnimation = new AnimationState();
    public final AnimationState scratchLeftAnimation = new AnimationState();
    private static final EntityDataAccessor<Integer> COOKING_TIME =
            SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COOK_TIME =
            SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DANCE_TIME =
            SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> IS_SCRATCHING =
            SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SCRATCH_LEFT =
            SynchedEntityData.defineId(SalamanderEntity.class, EntityDataSerializers.BOOLEAN);
    private int scaleLayTime = random.nextInt(6000) + 6000;
    private BlockPos scratchBlockPos = null;
    private int scratchTimer = 0;

    public SalamanderEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 12d)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 12D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COOK_TIME, 200);
        builder.define(COOKING_TIME, 0);
        builder.define(DANCE_TIME, 0);
        builder.define(IS_SCRATCHING, false);
        builder.define(SCRATCH_LEFT, false);
    }

    @Override
    public void addAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("ScaleTimer", this.scaleLayTime);
    }

    @Override
    public void readAdditionalSaveData(net.minecraft.nbt.CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.scaleLayTime = compound.getInt("ScaleTimer");
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (this.getDanceTime() > 0) {
            if (this.isInWater()) {
                super.travel(new Vec3(0.0, travelVector.y, 0.0));
            } else {
                super.travel(Vec3.ZERO);
            }
            this.setYRot(this.getYRot() + this.yRotO - this.getYRot());
            this.setXRot(this.getXRot() + this.xRotO - this.getXRot());
            this.yBodyRot = this.getYRot();
            this.yHeadRot = this.getYRot();
            return;
        }
        super.travel(travelVector);
    }

    public int getCookingTime() {
        return this.entityData.get(COOKING_TIME);
    }

    public void setCookingTime(int delivering) {
        this.entityData.set(COOKING_TIME, delivering);
    }

    public int getDanceTime() {
        return this.entityData.get(DANCE_TIME);
    }

    public void setDanceTime(int delivering) {
        this.entityData.set(DANCE_TIME, delivering);
    }

    public int getCookTime() {
        return this.entityData.get(COOK_TIME);
    }

    public void setCookTime(int delivering) {
        this.entityData.set(COOK_TIME, delivering);
    }

    public boolean isCooking() {
        return getCookingTime()>0;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, (double)1.25F));
        this.goalSelector.addGoal(3, new BreedGoal(this, (double)1.0F));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2, (p_336182_) -> p_336182_.is(ModTags.Items.SALAMANDER_FOOD), false));
        this.goalSelector.addGoal(5,new RunToDroppedSmeltableGoal(this, 1.5));
        this.goalSelector.addGoal(6, new ScratchBlockGoal(this, 1.0));
        this.goalSelector.addGoal(7, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, (double)1.0F));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.SALAMANDER.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.SALAMANDER_HURT.get();
    }



    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.SALAMANDER_DEATH.get();
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.SALAMANDER_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.SALAMANDER.get().create(serverLevel);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.GLASS_BOTTLE)) {
            itemstack.consume(1, player);
            boolean inserted = player.getInventory().add(new ItemStack(ModItems.SALAMANDER_GOO.get()));
            if (!inserted) {
                ItemEntity drop = new ItemEntity(
                        player.level(),
                        player.getX(),
                        player.getY() + 0.5,
                        player.getZ(),
                        player.getMainHandItem().copy()
                );
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BOTTLE_FILL, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                }
                drop.setPickUpDelay(0);
                player.level().addFreshEntity(drop);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide())
        {
            this.setupAnimationState();
        }
        if(getDanceTime()>0)
        {
            setDanceTime(getDanceTime()-1);
        }
        if (!this.level().isClientSide && !this.isBaby()) {

            if (this.scaleLayTime > 0) {
                this.scaleLayTime--;
            }
        }
    }

    private void setupAnimationState() {
        if(getDanceTime()>0)
        {
            this.danceAnimation.startIfStopped(this.tickCount);
            return;
        }else{
            this.danceAnimation.stop();
        }
        if(getCookingTime()>0){
            this.idleAnimationState.stop();
            this.scratchLeftAnimation.stop();
            this.scratchRightAnimation.stop();
            this.infusionAnimationState.startIfStopped(this.tickCount);
        }else if(entityData.get(IS_SCRATCHING)){
            if(entityData.get(SCRATCH_LEFT))
            {
                this.idleAnimationState.stop();
                this.scratchRightAnimation.stop();
                this.infusionAnimationState.stop();
                this.scratchLeftAnimation.startIfStopped(this.tickCount);
            }else{
                this.idleAnimationState.stop();
                this.scratchLeftAnimation.stop();
                this.infusionAnimationState.stop();
                this.scratchRightAnimation.startIfStopped(this.tickCount);
            }

        }else {
            this.infusionAnimationState.stop();
            this.scratchLeftAnimation.stop();
            this.scratchRightAnimation.stop();
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    public class RunToDroppedSmeltableGoal extends Goal {

        private final SalamanderEntity lizard;
        private final double speed;
        private ItemEntity target;

        public RunToDroppedSmeltableGoal(SalamanderEntity lizard, double speed) {
            this.lizard = lizard;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if(lizard.isBaby()) return false;
            List<ItemEntity> items = lizard.level().getEntitiesOfClass(
                    ItemEntity.class,
                    lizard.getBoundingBox().inflate(16.0),
                    e -> getSmeltingResult(e.getItem()) != null
            );

            ItemEntity closest = null;
            double closestDist = Double.MAX_VALUE;

            for (ItemEntity item : items) {
                Path path = lizard.getNavigation().createPath(item, 1);
                if (path != null && path.canReach()) {
                    double dist = lizard.distanceToSqr(item);
                    if (dist < closestDist) {
                        closestDist = dist;
                        closest = item;
                    }
                }
            }

            this.target = closest;
            return closest != null;
        }

        @Override
        public boolean canContinueToUse() {
            if (target == null || !target.isAlive()) return false;
            if (getSmeltingResult(target.getItem()) == null) return false;
            if (lizard.distanceToSqr(target) > 256.0) return false;
            Path path = lizard.getNavigation().createPath(target, 1);
            return path != null && path.canReach();
        }

        @Override
        public void stop() {
            lizard.setCookingTime(0);
            if (!lizard.isSilent()) {
                lizard.level().playSound(null, lizard.getX(), lizard.getY(), lizard.getZ(), ModSounds.SALAMANDER_BURN_END.get(), lizard.getSoundSource(), 0.3F, 1.0F + (lizard.random.nextFloat() - lizard.random.nextFloat()) * 0.2F);
            }
            super.stop();
        }

        @Override
        public void start() {
            if (!lizard.isSilent()) {
                lizard.level().playSound(null, target.getX(), target.getY(), target.getZ(), ModSounds.SALAMANDER_BURN_START.get(), lizard.getSoundSource(), 0.3F, 1.0F + (lizard.random.nextFloat() - lizard.random.nextFloat()) * 0.2F);
            }
            super.start();
        }

        @Override
        public void tick() {
            if(target != null) {
                lizard.getNavigation().moveTo(target, speed);

                if (lizard.distanceTo(target) < 2.0) {
                    ItemStack result = getSmeltingResult(target.getItem());
                    if (result != null) {
                        lizard.getNavigation().stop();
                        lizard.getLookControl().setLookAt(
                                target.getX(),
                                target.getY() + 0.1,
                                target.getZ(),
                                30.0F,
                                30.0F
                        );
                        ((ServerLevel) lizard.level()).sendParticles(
                                ParticleTypes.FLAME,
                                target.getX(), target.getY() + 0.25, target.getZ(),
                                5,
                                0.2, 0.2, 0.2,
                                0.01
                        );
                        if (lizard.getCookingTime()>5) {
                            if (!lizard.isSilent()) {
                                lizard.level().playSound(null, target.getX(), target.getY(), target.getZ(), ModSounds.SALAMANDER_BURN.get(), lizard.getSoundSource(), 0.3F, 1.0F + (lizard.random.nextFloat() - lizard.random.nextFloat()) * 0.2F);
                            }
                        }
                        if (lizard.getCookingTime() < lizard.getCookTime()) {
                            lizard.setCookingTime(lizard.getCookingTime() + 1);
                        } else {
                            if(Config.SALAMANDER_STACK_SMELT.get()) {
                                target.setItem(result.copyWithCount(target.getItem().getCount()*result.getCount()));
                                target = null;
                            }
                            else{
                                ItemEntity itementity = target.spawnAtLocation(result.copy(), 1);
                                ItemStack i = target.getItem().copy();
                                i.shrink(1);
                                if(i.isEmpty()){
                                    target.discard();
                                    target=null;
                                }else {
                                    target.setItem(i);
                                }
                                if (itementity != null) {
                                    itementity.setDeltaMovement(itementity.getDeltaMovement().add((double)((lizard.random.nextFloat() - lizard.random.nextFloat()) * 0.1F), (double)(lizard.random.nextFloat() * 0.05F), (double)((lizard.random.nextFloat() - lizard.random.nextFloat()) * 0.1F)));
                                }
                                lizard.setCookingTime(0);

                            }
                        }
                    }
                }
            }
        }

        private @Nullable ItemStack getSmeltingResult(ItemStack input) {
            RecipeManager rm = lizard.level().getRecipeManager();
            Optional<RecipeHolder<SmeltingRecipe>> recipe =
                    rm.getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(input), lizard.level());
            if(recipe.isEmpty()) return null;
            lizard.setCookTime(recipe.get().value().getCookingTime());

            return recipe.get().value().getResultItem(lizard.level().registryAccess());
        }
    }

    public class ScratchBlockGoal extends Goal {

        private final SalamanderEntity salamander;
        private final double speed;

        public ScratchBlockGoal(SalamanderEntity salamander, double speed) {
            this.salamander = salamander;
            this.speed = speed;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (salamander.isBaby()) return false;
            if (salamander.scaleLayTime > 0) return false;

            BlockPos salamanderPos = salamander.blockPosition();
            BlockPos bestPos = null;
            double closestDistance = Double.MAX_VALUE;
            for (BlockPos pos : BlockPos.betweenClosed(
                    salamanderPos.offset(-5, -2, -5),
                    salamanderPos.offset(5, 2, 5))) {
                if (salamander.level().getBlockState(pos).is(ModTags.Blocks.SALAMANDER_SCRATCHER)) {
                    for (Direction dir : Direction.Plane.HORIZONTAL) {
                        BlockPos adjacent = pos.relative(dir);
                        if (!salamander.level().getBlockState(adjacent).getCollisionShape(salamander.level(), adjacent).isEmpty()) continue;
                        Path path = salamander.getNavigation().createPath(adjacent, 99);
                        if (path != null && path.canReach()) {
                            double dist = salamander.distanceToSqr(adjacent.getX() + 0.5, adjacent.getY(), adjacent.getZ() + 0.5);
                            if (dist < closestDistance) {
                                closestDistance = dist;
                                bestPos = adjacent;
                            }
                        }
                    }
                }
            }
            if (bestPos != null) {
                salamander.scratchBlockPos = bestPos;
                return true;
            }
            salamander.scratchBlockPos = null;
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return salamander.scratchBlockPos != null;
        }

        @Override
        public void start() {
            salamander.scratchTimer = 0;
        }

        @Override
        public void stop() {
            salamander.entityData.set(IS_SCRATCHING, false);
            salamander.scratchBlockPos = null;
            salamander.scratchTimer = 0;
            salamander.scaleLayTime = salamander.random.nextInt(6000) + 6000;
        }

        @Override
        public void tick() {
            if (salamander.scratchBlockPos == null) return;

            BlockPos pos = salamander.scratchBlockPos;
            Vec3 targetVec = new Vec3(pos.getX() + 0.5, salamander.getY(), pos.getZ() + 0.5);

            if (salamander.distanceToSqr(targetVec) > 1.0) {
                salamander.getNavigation().moveTo(targetVec.x, targetVec.y, targetVec.z, speed);
            } else {
                Direction bestDir = null;
                double minDistance = Double.MAX_VALUE;

                for (Direction dir : Direction.Plane.HORIZONTAL) {
                    BlockPos adjacent = salamander.blockPosition().relative(dir);
                    if (!salamander.level().getBlockState(adjacent).is(ModTags.Blocks.SALAMANDER_SCRATCHER)) continue;
                    Vec3 faceVec = Vec3.atCenterOf(pos).subtract(salamander.position());
                    float yaw;
                    if (dir == Direction.NORTH || dir == Direction.SOUTH) {
                        yaw = faceVec.x > 0 ? 90 : -90;
                    } else {
                        yaw = faceVec.z > 0 ? 180 : 0;
                    }
                    double angleDiff = Math.abs(yaw - salamander.getYRot()) % 360;
                    if (angleDiff < minDistance) {
                        minDistance = angleDiff;
                        bestDir = dir;
                    }
                }
                if (bestDir != null) {
                    float yaw;
                    if (bestDir == Direction.NORTH || bestDir == Direction.SOUTH) {
                        yaw = pos.getX() + 0.5 - salamander.getX() > 0 ? 90 : -90;
                    } else {
                        yaw = pos.getZ() + 0.5 - salamander.getZ() > 0 ? 180 : 0;
                    }

                    salamander.setYRot(yaw);
                    salamander.yHeadRot = yaw;
                    salamander.yBodyRot = yaw;

                    salamander.entityData.set(SCRATCH_LEFT, yaw < 0 || yaw == 180);
                }

                salamander.entityData.set(IS_SCRATCHING, true);
                salamander.getNavigation().stop();
                salamander.scratchTimer++;

                if (salamander.scratchTimer >= 20) {
                    int count = 1 + salamander.random.nextInt(2);
                    for (int i = 0; i < count; i++) {
                        salamander.spawnAtLocation(ModItems.SALAMANDER_SCALES.get());
                    }
                    salamander.level().playSound(
                            null,
                            salamander.getX(), salamander.getY(), salamander.getZ(),
                            SoundEvents.DRIPSTONE_BLOCK_BREAK,
                            salamander.getSoundSource(),
                            1.0F,
                            (salamander.random.nextFloat() * 0.2F) + 0.9F
                    );
                    this.stop();
                }
            }
        }
    }



}
