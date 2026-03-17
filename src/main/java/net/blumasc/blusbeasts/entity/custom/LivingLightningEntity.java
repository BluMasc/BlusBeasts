package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blubasics.item.BaseModItems;
import net.blumasc.blubasics.item.custom.LightningInABottleItem;
import net.blumasc.blubasics.sound.BaseModSounds;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

public class LivingLightningEntity extends Monster implements FlyingAnimal {
    public final AnimationState angryAnimationState = new AnimationState();
    public final AnimationState happyAnimationState = new AnimationState();

    private static final EntityDataAccessor<Boolean> ANGRY =
            SynchedEntityData.defineId(LivingLightningEntity.class, EntityDataSerializers.BOOLEAN);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ANGRY, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LightningRageGoal(this));
        this.goalSelector.addGoal(1, new RageChaseGoal(this));
        this.goalSelector.addGoal(2, new SeekTransformTargetsGoal(this));
        this.goalSelector.addGoal(3, new FloatAroundGoal(this));
    }

    @Override
    public void onDamageTaken(DamageContainer damageContainer) {
        if(damageContainer.getSource().getEntity() instanceof LivingEntity){
            this.setAngry(true);
        }
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return BaseModSounds.ELECTRIC.get();
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setAngry(boolean perched) {
        this.entityData.set(ANGRY, perched);
    }

    public LivingLightningEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float damageMultiplier, DamageSource source) {
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8)
                .add(Attributes.FLYING_SPEED, 0.6f)
                .add(Attributes.MOVEMENT_SPEED, 0.3f)
                .add(Attributes.FOLLOW_RANGE, 20);
    }


    public static boolean checkLivingLightningSpawnRules(
            EntityType<LivingLightningEntity> type,
            ServerLevelAccessor level,
            MobSpawnType reason,
            BlockPos pos,
            RandomSource random
    ) {
        if (!Monster.isDarkEnoughToSpawn(level, pos, random)) return false;
        if (level.getDifficulty() == Difficulty.PEACEFUL) return false;
        if (!level.canSeeSky(pos)) return false;
        if (!level.getLevel().isThundering()) return false;
        if (level.getBlockState(pos.below().below()).isEmpty()) return false;

        return true;
    }

    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            setupClientAnimation();
        }else{
            if(!this.level().isThundering())
            {
                LightningBolt b = new LightningBolt(EntityType.LIGHTNING_BOLT, level());
                b.moveTo(this.position());
                b.setDamage(0);
                level().addFreshEntity(b);
                ((ServerLevel)this.level()).sendParticles(
                        net.minecraft.core.particles.ParticleTypes.CLOUD,
                        this.getX(), this.getY(), this.getZ(),
                        12, 0.3, 0.3, 0.3, 0.02
                );
                this.discard();
            }
            if (this.isAngry()) {
                List<LivingEntity> nearby = level().getEntitiesOfClass(
                        LivingEntity.class,
                        getBoundingBox().inflate(20),
                        e -> e != this
                );

                if (nearby.isEmpty()) {
                    setAngry(false);
                }
            }
            List<LivingEntity> nearby = this.level().getEntitiesOfClass(
                    LivingEntity.class,
                    this.getBoundingBox().inflate(1.5),
                    e -> e != this
            );

            for (LivingEntity target : nearby) {
                this.transformMob(target);
            }
            if (this.tickCount % 20 == 0) {
                level().playSound(null, this.position().x, this.position().y, this.position().z, BaseModSounds.ELECTRIC.get(), SoundSource.NEUTRAL, (random.nextFloat()*0.4f)+0.8f, random.nextFloat()+0.5f);
            }

        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (itemstack.is(Items.GLASS_BOTTLE)) {
            itemstack.consume(1, player);
            boolean inserted = player.getInventory().add(new ItemStack(BaseModItems.LIGHTNING_IN_A_BOTTLE.get()));
            if (!inserted) {
                ItemEntity drop = new ItemEntity(
                        player.level(),
                        player.getX(),
                        player.getY() + 0.5,
                        player.getZ(),
                        player.getMainHandItem().copy()
                );
                if (!this.isSilent()) {
                    this.level().playSound(null, this.getX(), this.getY(), this.getZ(), BaseModSounds.ELECTRIC.get(), this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                }
                drop.setPickUpDelay(0);
                player.level().addFreshEntity(drop);
            }
            this.setAngry(true);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    private void transformMob(LivingEntity target) {
        if (!(level() instanceof ServerLevel server)) return;

        LightningBolt bolt = EntityType.LIGHTNING_BOLT.create(level());
        bolt.moveTo(target.position());
        bolt.setDamage(0);

        if (target instanceof Pig pig) {
            pig.convertTo(EntityType.ZOMBIFIED_PIGLIN, true);
        }
        else if (target instanceof Villager villager) {
            villager.convertTo(EntityType.WITCH, false);
        }
        else if (target instanceof Creeper creeper && !creeper.isPowered()) {
            creeper.thunderHit(server, bolt);
            creeper.setRemainingFireTicks(0);
        }
    }

    private void setupClientAnimation() {
        this.setDeltaMovement(this.getDeltaMovement().add(0, Math.sin(tickCount * 0.15) * 0.002, 0));
        if(isAngry()){
            happyAnimationState.stop();
            angryAnimationState.startIfStopped(this.tickCount);
        }else{
            angryAnimationState.stop();
            happyAnimationState.startIfStopped(this.tickCount);
        }
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    class FloatAroundGoal extends Goal {
        private final LivingLightningEntity entity;
        private int cooldown = 0;

        public FloatAroundGoal(LivingLightningEntity e) {
            this.entity = e;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return !entity.isAngry();
        }

        @Override
        public void tick() {
            if (--cooldown > 0) return;
            cooldown = 40 + entity.getRandom().nextInt(40);

            Vec3 pos = entity.position().add(
                    entity.getRandom().nextGaussian() * 6,
                    entity.getRandom().nextGaussian() * 2,
                    entity.getRandom().nextGaussian() * 6
            );

            entity.getNavigation().moveTo(pos.x, pos.y, pos.z, 0.8);
        }
    }

    class LightningRageGoal extends Goal {
        private final LivingLightningEntity entity;
        private int attackCooldown = 0;

        public LightningRageGoal(LivingLightningEntity e) {
            this.entity = e;
        }

        @Override
        public boolean canUse() {
            return entity.isAngry();
        }

        @Override
        public void tick() {
            if (!(entity.level() instanceof ServerLevel)) return;

            List<LivingEntity> nearby = entity.level().getEntitiesOfClass(
                    LivingEntity.class,
                    entity.getBoundingBox().inflate(20),
                    e -> e != entity
            );

            if (nearby.isEmpty()) {
                entity.setAngry(false);
                return;
            }

            if (--attackCooldown > 0) return;
            attackCooldown = 20;

            LivingEntity closest = nearby.stream()
                    .filter(e -> e.distanceTo(entity) <= 5)
                    .min(Comparator.comparingDouble(e -> e.distanceTo(entity)))
                    .orElse(null);

            if (closest == null) return;

            LightningInABottleItem.castChainLightning(
                    entity,
                    6,
                    3f,
                    3,
                    4
            );
        }
    }

    class SeekTransformTargetsGoal extends Goal {
        private final LivingLightningEntity entity;
        private LivingEntity target;

        public SeekTransformTargetsGoal(LivingLightningEntity e) {
            this.entity = e;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (entity.isAngry()) return false;

            List<LivingEntity> list = entity.level().getEntitiesOfClass(
                    LivingEntity.class,
                    entity.getBoundingBox().inflate(12),
                    e -> e instanceof Pig || (e instanceof Creeper c && !c.isPowered())
            );

            target = list.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceTo(entity)))
                    .orElse(null);

            return target != null;
        }

        @Override
        public boolean canContinueToUse() {
            return target != null && target.isAlive() && !entity.isAngry() && !(target instanceof Creeper c && c.isPowered());
        }

        @Override
        public void tick() {
            Vec3 pos = target.position().add(0, 1, 0);
            entity.getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, 0.9f);
        }
    }

    class RageChaseGoal extends Goal {
        private final LivingLightningEntity entity;
        private LivingEntity target;

        public RageChaseGoal(LivingLightningEntity e) {
            this.entity = e;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (!entity.isAngry()) return false;

            List<LivingEntity> nearby = entity.level().getEntitiesOfClass(
                    LivingEntity.class,
                    entity.getBoundingBox().inflate(20),
                    e -> e != entity
            );

            target = nearby.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceTo(entity)))
                    .orElse(null);

            return target != null;
        }

        @Override
        public boolean canContinueToUse() {
            return entity.isAngry() && target != null && target.isAlive();
        }

        @Override
        public void tick() {
            double dist = entity.distanceTo(target);

            Vec3 pos = target.position().add(0, 1, 0);

            if (dist > 4)
                entity.getMoveControl().setWantedPosition(pos.x, pos.y, pos.z, 1.2f);
            else
                entity.getMoveControl().setWantedPosition(
                        entity.getX(),
                        entity.getY(),
                        entity.getZ(),
                        0
                );
        }
    }





}
