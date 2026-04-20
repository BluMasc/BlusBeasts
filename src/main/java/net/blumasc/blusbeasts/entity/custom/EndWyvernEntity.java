package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.playerstate.PlayerDragonState;
import net.blumasc.blusbeasts.playerstate.PlayerDragonStateHandler;
import net.blumasc.blusbeasts.recipe.ModRecipes;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3d;

import java.util.List;

public class EndWyvernEntity extends Animal implements FlyingAnimal {
    public final AnimationState holdingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState flyingAnimationState = new AnimationState();

    protected static final EntityDataAccessor<BlockPos> HOME =
            SynchedEntityData.defineId(EndWyvernEntity.class, EntityDataSerializers.BLOCK_POS);

    private static final EntityDataAccessor<Boolean> HAS_EGG =
            SynchedEntityData.defineId(EndWyvernEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HOME, BlockPos.ZERO);
        builder.define(HAS_EGG, false);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.ENDERDRAKE_AMBIENT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return ModSounds.ENDERDRAKE_DEATH.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ENDERDRAKE_HURT.get();
    }

    public EndWyvernEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.navigation = new FlyingPathNavigation(this, level);
    }

    public BlockPos getHome(){
        return this.entityData.get(HOME);
    }

    public void setHome(BlockPos pos){
        this.entityData.set(HOME, pos);
        this.restrictTo(pos, 50);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("hasEgg", this.entityData.get(HAS_EGG));
        BlockPos home = getHome();
        if(home!=null){
            compound.putInt("homeX", home.getX());
            compound.putInt("homeY", home.getY());
            compound.putInt("homeZ", home.getZ());
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(HAS_EGG, compound.getBoolean("hasEgg"));
        if(compound.contains("homeX")){
            setHome(new BlockPos(compound.getInt("homeX"), compound.getInt("homeY"), compound.getInt("homeZ")));
        }
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 30d)
                .add(Attributes.ATTACK_DAMAGE, 2d)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.FLYING_SPEED, 0.4F)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new FlyingPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.2));
        this.goalSelector.addGoal(2, new WyvernLayEggGoal(this));
        this.goalSelector.addGoal(3, new WyvernRescueGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomFlyingGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModTags.Items.END_WYVERN_FOOD);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal partner) {
        this.entityData.set(HAS_EGG, true);
        this.setAge(6000);
        partner.setAge(6000);

        this.resetLove();
        partner.resetLove();

        level.broadcastEntityEvent(this, (byte)18);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getHome()==BlockPos.ZERO){
            this.setHome(this.getOnPos());
        }
        if(level().isClientSide()){
            animate();
        }
    }
    private void animate(){
        flyingAnimationState.startIfStopped(this.tickCount);
        idleAnimationState.startIfStopped(this.tickCount);
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
    }

    @Override
    protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
        return 0;
    }

    public class WyvernLayEggGoal extends MoveToBlockGoal {

        private final EndWyvernEntity wyvern;

        public WyvernLayEggGoal(EndWyvernEntity wyvern) {
            super(wyvern, 1.0D, 16);
            this.wyvern = wyvern;
        }

        @Override
        public boolean canUse() {
            return wyvern.entityData.get(HAS_EGG)&& super.canUse();
        }

        @Override
        public void tick() {
            super.tick();

            if (this.blockPos != null) {
                if(tickCount%random.nextInt(20)+10==0){
                    level().playSound(null, getX(), getY(), getZ(), ModSounds.ENDERDRAKE_WINGBEAT.get(), SoundSource.PLAYERS, 0.7f, 0.8f+random.nextFloat()*0.4f);
                }
                wyvern.getMoveControl().setWantedPosition(
                        blockPos.getX() + 0.5,
                        blockPos.getY() + 1,
                        blockPos.getZ() + 0.5,
                        1.0
                );

                if (wyvern.blockPosition().closerThan(blockPos, 2)) {
                    wyvern.level().setBlock(
                            blockPos.above(),
                            ModBlocks.END_WYVERN_EGG.get().defaultBlockState(),
                            3
                    );

                    wyvern.entityData.set(HAS_EGG, false);
                    this.stop();
                }
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            return blockPos.distSqr(wyvern.getHome())<25 && levelReader.getBlockState(blockPos).canBeReplaced();
        }
    }
    public class WyvernRescueGoal extends Goal {

        private final EndWyvernEntity wyvern;
        private Player target;

        public WyvernRescueGoal(EndWyvernEntity wyvern) {
            this.wyvern = wyvern;
        }

        @Override
        public boolean canUse() {
            List<Player> players = wyvern.level().getEntitiesOfClass(
                    Player.class,
                    wyvern.getBoundingBox().inflate(50),
                    p -> shouldCatch(p) && p.distanceToSqr(wyvern.getHome().getCenter()) < 2500
            );

            if (!players.isEmpty()) {
                target = players.getFirst();
                PlayerDragonState pds = PlayerDragonStateHandler.loadState(target);
                return pds.emptyBack();
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            PlayerDragonState pds = PlayerDragonStateHandler.loadState(target);
            return target!=null && !target.isDeadOrDying() && target.position().closerThan(wyvern.getHome().getCenter(), 50) && !target.onGround() && pds.emptyBack();
        }

        @Override
        public void tick() {
            Vec3 delta = target.position().subtract(wyvern.position());
            Vec3 motion = delta.normalize().scale(1.6);

            wyvern.setDeltaMovement(motion.x, motion.y, motion.z);
            wyvern.fallDistance = 0;
            wyvern.hasImpulse = true;

            wyvern.moveControl.setWantedPosition(target.getX(), target.getY(), target.getZ(), 1.0);
            lookControl.setLookAt(target);

            Vec3 wyvernPos = new Vec3(wyvern.position().x, 0, wyvern.position().z);

            Vec3 targetPos = new Vec3(target.position().x, 0, target.position().z);

            if (wyvernPos.closerThan(targetPos, 4)) {
                PlayerDragonState pds = PlayerDragonStateHandler.loadState(target);
                if(pds.emptyBack()){
                    pds.attachWyvenrn(wyvern);
                    ((ServerLevel)wyvern.level()).sendParticles(
                            ParticleTypes.PORTAL,
                            wyvern.getX(),
                            wyvern.getY(),
                            wyvern.getZ(),
                            50,
                            0,
                            0,
                            0,
                            0.3
                    );
                    wyvern.level().playSound(null, wyvern.getX(), wyvern.getY(), wyvern.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL );
                    wyvern.discard();
                    PlayerDragonStateHandler.saveState(target, pds);
                    ((ServerLevel)target.level()).sendParticles(
                            ParticleTypes.PORTAL,
                            target.getX(),
                            target.getY(),
                            target.getZ(),
                            50,
                            0,
                            0,
                            0,
                            0.3
                    );
                    target.level().playSound(null, target.getX(), target.getY(), target.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.NEUTRAL );
                }
                target.fallDistance = 0;
            }
        }

        private boolean shouldCatch(Player p){
            if(p.onGround()) return false;
            BlockPos pos = p.getOnPos().below();
            if(p.level().getBlockState(pos).canBeReplaced()){
                if(p.level().getBlockState(pos.below()).canBeReplaced()){
                    if(p.level().getBlockState(pos.below().below()).canBeReplaced()){
                        return true;
                    }
                }
            }
            return false;
        }
    }
}
