package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Minecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.Nullable;

public class MimicartEntity extends PathfinderMob {

    public final AnimationState attackAnimationState = new AnimationState();

    public final AnimationState lickAnimationState = new AnimationState();
    public final AnimationState wiggleAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> REVEALED =
            SynchedEntityData.defineId(MimicartEntity.class, EntityDataSerializers.BOOLEAN);


    public boolean isRevealed() {
        return this.entityData.get(REVEALED);
    }

    public void setRevealed(boolean perched) {
        this.entityData.set(REVEALED, perched);
    }

    private boolean onRails;

    public static AttributeSupplier.Builder createAttributes(){
        return PathfinderMob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20d)
                .add(Attributes.ATTACK_DAMAGE, 2d)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ARMOR, 8.0);
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(REVEALED, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("Revealed", isRevealed());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setRevealed(compound.getBoolean("Revealed"));
    }

    public MimicartEntity(EntityType<? extends PathfinderMob> type, Level world) {
        super(type, world);
        this.blocksBuilding = true;
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
    }

    @Override
    public boolean isPickable() {
        return !this.isRemoved();
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        if(damageContainer.getSource().getEntity() instanceof  LivingEntity le){
            this.reveal(le);
        }
    }

    @Override
    protected float getKnockback(Entity attacker, DamageSource damageSource) {
        return 0.0f;
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide) {
            player.startRiding(this);
            this.reveal(player);
        }
        return InteractionResult.sidedSuccess(this.level().isClientSide);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        double yOffset = 0.3125;
        return new Vec3(this.getX(), this.getY()+yOffset, this.getZ());
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide)
        {
            setupClientAnimation();
        }

        BlockPos below = this.blockPosition();
        BlockState state = level().getBlockState(below);
        onRails = state.is(BlockTags.RAILS);

        if(onRails)snapToRail(below, state);
        damageEntitiesAbove();

        checkCollisions();
        if(isRevealed()){
            if(this.tickCount%10==0){
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.MIMICART_SNAP.get(), SoundSource.HOSTILE, 0.7f, random.nextFloat()+0.5f);
            }
        }
    }

    private void damageEntitiesAbove() {
        if (!isRevealed())return;
        if(this.getHealth()<=0)return;

        AABB damageBox = this.getBoundingBox().inflate(0.3, 0.5, 0.3);

        for (Entity entity : this.level().getEntities(this, damageBox)) {
            if (entity == this) continue;
            if (!(entity instanceof LivingEntity)) continue;

            entity.hurt(level().damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
        for (Entity entity : this.getPassengers()) {
            if (entity == this) continue;

            entity.hurt(level().damageSources().mobAttack(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }

    }


    private void setupClientAnimation() {
        attackAnimationState.startIfStopped(this.tickCount);
        if(hasPassenger(e -> true)){
            wiggleAnimationState.stop();
            lickAnimationState.startIfStopped(this.tickCount);
        }else{
            lickAnimationState.stop();
            wiggleAnimationState.startIfStopped(this.tickCount);
        }
    }

    private Vec3 getRailDirectionVector(BlockState state) {
        RailShape shape = ((BaseRailBlock) state.getBlock()).getRailDirection(state, level(), this.blockPosition(), null);
        return switch (shape) {
            case NORTH_SOUTH -> new Vec3(0, 0, 1);
            case EAST_WEST -> new Vec3(1, 0, 0);
            case ASCENDING_EAST -> new Vec3(1, 0.5, 0);
            case ASCENDING_WEST -> new Vec3(-1, 0.5, 0);
            case ASCENDING_NORTH -> new Vec3(0, 0.5, -1);
            case ASCENDING_SOUTH -> new Vec3(0, 0.5, 1);
            default -> Vec3.ZERO;
        };
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.IRON_GOLEM_HURT;
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return SoundEvents.SLIME_BLOCK_BREAK;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if(isRevealed()) return  ModSounds.MIMICART_LICK.get();
        return null;
    }

    public void reveal(LivingEntity newTarget) {
        setRevealed(true);
        this.setTarget(newTarget);
    }

    private void checkCollisions() {
        AABB box = this.getBoundingBox().inflate(0.5, 0.2, 0.5);
        for (Entity entity : level().getEntities(this, box)) {
            if (entity instanceof AbstractMinecart minecart) {
                double speed = minecart.getDeltaMovement().length();
                if (speed > 0.3) {
                    ((ServerLevel)this.level()).sendParticles(
                            net.minecraft.core.particles.ParticleTypes.CLOUD,
                            this.getX(), this.getY(), this.getZ(),
                            12, 0.3, 0.3, 0.3, 0.02
                    );
                    this.remove(RemovalReason.KILLED);
                }
            }
        }
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    private void snapToRail(BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof BaseRailBlock railBlock)) return;

        RailShape shape = railBlock.getRailDirection(state, level(), pos, new Minecart(this.level(), this.getX(), this.getY(), this.getZ()));

        double railY = pos.getY() + 0.1;
        switch (shape) {
            case ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH -> railY += 0.5;
            default -> {}
        }

        this.setPos(pos.getBottomCenter().x, railY, pos.getBottomCenter().z);

        float yaw = switch (shape) {
            case NORTH_SOUTH -> 0f;
            case EAST_WEST -> 90f;
            case ASCENDING_EAST -> 90f;
            case ASCENDING_WEST -> -90f;
            case ASCENDING_NORTH -> 180f;
            case ASCENDING_SOUTH -> 0f;
            default -> this.getYRot();
        };
        this.setYRot(yaw);
    }

    public static boolean checkMimicartSpawnRules(
            EntityType<MimicartEntity> type,
            ServerLevelAccessor level,
            MobSpawnType reason,
            BlockPos pos,
            RandomSource random
    ) {
        if (level.getDifficulty() == Difficulty.PEACEFUL) return false;
        BlockState state = level.getBlockState(pos.below());
        if (!state.is(BlockTags.RAILS)) return false;
        if (!level.getBlockState(pos).isEmpty()) return false;

        return true;
    }

    @Override
    protected void dropAllDeathLoot(ServerLevel p_level, DamageSource damageSource) {
        double oldY = this.getY();
        this.setPos(this.getX(), oldY+0.5, this.getZ());
        super.dropAllDeathLoot(p_level, damageSource);
        this.setPos(this.getX(), oldY, this.getZ());
    }
}

