package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blubasics.entity.custom.projectile.BlockProjectileEntity;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.variants.BurryVariant;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class BurryEntity extends Monster {

    public final AnimationState idleAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> VARIANT =
            SynchedEntityData.defineId(BurryEntity.class, EntityDataSerializers.INT);

    private static final EntityDataAccessor<Integer> GROWTH =
            SynchedEntityData.defineId(BurryEntity.class, EntityDataSerializers.INT);

    public int getGrowth() {
        return this.entityData.get(GROWTH);
    }

    private void setGrowth(int growth) {
        this.entityData.set(GROWTH, growth);
    }

    public void startGrowth(){
        setGrowth(1);
    }

    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    public BurryVariant getVariant(){
        return BurryVariant.byId(this.getTypeVariant() & 255);
    }

    public void setVariant(BurryVariant variant){
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, 0);
        builder.define(GROWTH, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("burryVariant", this.getVariant().getId());
        compound.putInt("growth", this.getGrowth());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setVariant(BurryVariant.byId(compound.getInt("burryVariant")));
        setGrowth(compound.getInt("growth"));
    }

    @Override
    public void handleDamageEvent(DamageSource damageSource) {
        if(getGrowth()>0){
            return;
        }
        super.handleDamageEvent(damageSource);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, (double)6.0F)
                .add(Attributes.MOVEMENT_SPEED, (double)0.23F)
                .add(Attributes.FOLLOW_RANGE, (double)48.0F);
    }

    public BurryEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        if(getGrowth()>0 && !this.level().isClientSide()){
            setGrowth(getGrowth()+1);
            this.setNoGravity(true);
            this.setPos(this.position().add(0, 0.001*getGrowth(), 0));

            if(getGrowth()>60){
                Vec3 p = this.position();
                this.discard();
                level().explode(
                        this,
                        p.x,
                        p.y,
                        p.z,
                        2.2F,
                        false,
                        Level.ExplosionInteraction.MOB
                );
                GraveEntity ge = new GraveEntity(ModEntities.GRAVE.get(), level());
                ge.setPos(p);
                ge.setYRot(this.getYRot());
                ge.setVariant(this.getVariant());
                level().addFreshEntity(ge);
            }
            return;
        }
        super.tick();
        if(this.level().isClientSide()){
            animate();
        }else{
            if(this.level().canSeeSky(this.blockPosition()) && this.level().isDay()){
                if(this.tickCount%40==0){
                    this.hurt(damageSources().inFire(), 1.0f);
                }
            }
        }
    }

    public void animate(){
        idleAnimationState.startIfStopped(this.tickCount);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(4, new BurryAttackGoal(this));
        this.goalSelector.addGoal(5, new MoveTowardsRestrictionGoal(this, (double)1.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, (double)1.0F, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        BlockPos pos = this.blockPosition();
        Holder<Biome> biomeHolder = level.getBiome(pos);

        BurryVariant variant;

        if (biomeHolder.is(ModTags.Biomes.RED_BURRY_BIOMES)) {
            variant = BurryVariant.RED_SAND;

        } else {
            variant = BurryVariant.SAND;

        }
        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    public static boolean checkBurryRules(EntityType<BurryEntity> burryEntityEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        BlockState below = serverLevelAccessor.getBlockState(blockPos.below());
        if(below.is(ModTags.Blocks.BURRY_SPAWNABLE)) {
            return Monster.checkMonsterSpawnRules(burryEntityEntityType, serverLevelAccessor, spawnType, blockPos, randomSource);
        }
        return false;
    }

    static class BurryAttackGoal extends Goal {
        private final BurryEntity burry;
        private int attackStep;
        private int attackTime;
        private int lastSeen;

        public BurryAttackGoal(BurryEntity burry) {
            this.burry = burry;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingentity = this.burry.getTarget();
            return livingentity != null && livingentity.isAlive() && this.burry.canAttack(livingentity);
        }

        public void start() {
            this.attackStep = 0;
        }

        public void stop() {
            this.lastSeen = 0;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.burry.getTarget();
            if (livingentity != null) {
                boolean flag = this.burry.getSensing().hasLineOfSight(livingentity);
                if (flag) {
                    this.lastSeen = 0;
                } else {
                    ++this.lastSeen;
                }

                double d0 = this.burry.distanceToSqr(livingentity);
                if (d0 < (double)4.0F) {
                    if (!flag) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.burry.doHurtTarget(livingentity);
                    }

                    this.burry.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), (double)1.0F);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getX() - this.burry.getX();
                    double d2 = livingentity.getY((double)0.5F) - this.burry.getY((double)0.5F);
                    double d3 = livingentity.getZ() - this.burry.getZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                        }

                        if (this.attackStep > 1) {
                            double d4 = Math.sqrt(Math.sqrt(d0)) * (double)0.5F;
                            if (!this.burry.isSilent()) {
                                this.burry.level().playSound(null, this.burry.getX(), this.burry.getY(), this.burry.getZ(), SoundEvents.SAND_PLACE, SoundSource.HOSTILE, 1.0f, this.burry.random.nextFloat()+0.5f);
                            }

                            for(int i = 0; i < 1; ++i) {
                                Vec3 vec3 = new Vec3(this.burry.getRandom().triangle(d1, 2.297 * d4), d2, this.burry.getRandom().triangle(d3, 2.297 * d4));
                                BlockProjectileEntity block = new BlockProjectileEntity(this.burry, vec3.normalize(), this.burry.level(), this.burry.getBlock());
                                block.setPos(block.getX(), this.burry.getY((double)0.5F) + (double)0.5F, block.getZ());
                                this.burry.level().addFreshEntity(block);
                            }
                        }
                    }

                    this.burry.getLookControl().setLookAt(livingentity, 10.0F, 10.0F);
                } else if (this.lastSeen < 5) {
                    this.burry.getMoveControl().setWantedPosition(livingentity.getX(), livingentity.getY(), livingentity.getZ(), (double)1.0F);
                }

                super.tick();
            }

        }

        private double getFollowDistance() {
            return this.burry.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }

    private BlockState getBlock() {
        if(getVariant() == BurryVariant.RED_SAND){
            return Blocks.RED_SAND.defaultBlockState();
        }
        return Blocks.SAND.defaultBlockState();
    }
}
