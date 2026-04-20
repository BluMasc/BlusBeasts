package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.block.custom.blockentity.custom.PlateBlockEntity;
import net.blumasc.blusbeasts.datagen.ModLootTables;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;import net.minecraft.sounds.SoundSource;import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;import net.minecraft.world.entity.player.Player;import net.minecraft.world.item.ItemStack;import net.minecraft.world.level.Level;import net.minecraft.world.level.storage.loot.BuiltInLootTables;import net.minecraft.world.level.storage.loot.LootParams;import net.minecraft.world.level.storage.loot.LootTable;import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointAnnotationContext;import java.util.EnumSet;import java.util.List;import java.util.Optional;

public class DreamPixie extends PathfinderMob implements FlyingAnimal {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState armSwingAnimationState = new AnimationState();
    public final AnimationState eatingAnimationState = new AnimationState();
    public final AnimationState scaleAnimationState = new AnimationState();

    private static final EntityDataAccessor<Integer> EATING =
            SynchedEntityData.defineId(DreamPixie.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> PANICING =
            SynchedEntityData.defineId(DreamPixie.class, EntityDataSerializers.INT);

    public int getEating() { return this.entityData.get(EATING); }
    public void setEating(int eating) { this.entityData.set(EATING, eating); }

    public int getPanicing() { return this.entityData.get(PANICING); }
    public void setPanicing(int panicing) {
        this.entityData.set(PANICING, panicing);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(EATING, 0);
        builder.define(PANICING, 0);
    }

    public DreamPixie(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicAndDisappearGoal(this, 8.0));
        this.goalSelector.addGoal(2, new FlyToPlateGoal(this));
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.PIXIE.get();
    }

    @Override
    public boolean isFlying() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            animateTick();
        } else {
            if (getPanicing() > 0) {
                setEating(0);
                setPanicing(getPanicing() + 1);
            }
            if (getEating() > 0) {
                setEating(getEating() - 1);
                if(getEating()==15 || getEating()==12){
                    level().playSound(null, getX(), getEyeY(), getZ(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 0.8f, random.nextFloat()*0.6f+1.2f);
                    ((ServerLevel) this.level()).sendParticles(ModParticles.PINK_HEART.get(), getX(), getEyeY(), getZ(), 5, 0f, 0f, 0f, 1.0f);
                }
            }
        }
    }

    private void animateTick() {
        idleAnimationState.startIfStopped(this.tickCount);
        scaleAnimationState.startIfStopped(this.tickCount);
        if (getEating() > 0) {
            armSwingAnimationState.stop();
            eatingAnimationState.startIfStopped(this.tickCount);
        } else {
            eatingAnimationState.stop();
            armSwingAnimationState.startIfStopped(this.tickCount);
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4d)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.FLYING_SPEED, 0.3f)
                .add(Attributes.FOLLOW_RANGE, 24f);
    }
    public class FlyToPlateGoal extends Goal {

        private final DreamPixie pixie;
        private BlockPos targetPos = null;
        private int eatTimer = 0;
        private static final int EAT_DURATION = 25;
        private int foodCount = 0;

        public FlyToPlateGoal(DreamPixie pixie) {
            this.pixie = pixie;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (pixie.getPanicing() > 0) return false;

            targetPos = findNearestPlateWithFood();
            return targetPos != null;
        }

        @Override
        public boolean canContinueToUse() {
            if (pixie.getPanicing() > 0) return false;
            if (targetPos == null) return false;

            if (!(pixie.level().getBlockEntity(targetPos) instanceof PlateBlockEntity plate)) return false;
            return hasFood(plate) || eatTimer > 0;
        }

        @Override
        public void start() {
            eatTimer = 0;
            if (targetPos != null) {
                flyToTarget();
            }
        }

        @Override
        public void stop() {
            pixie.getMoveControl().setWantedPosition(pixie.getX(), pixie.getY(), pixie.getZ(), 0);
            eatTimer = 0;
            targetPos = null;
        }

        @Override
        public void tick() {
            if (targetPos == null) return;

            if (!(pixie.level().getBlockEntity(targetPos) instanceof PlateBlockEntity plate)) {
                targetPos = null;
                return;
            }

            double distSq = pixie.distanceToSqr(
                    targetPos.getX() + 0.5,
                    targetPos.getY() + 1.0,
                    targetPos.getZ() + 0.5
            );

            if (distSq > 3.0) {
                flyToTarget();
            } else {
                if (eatTimer == 0 && hasFood(plate)) {
                    startEating(plate);
                }

                if (eatTimer > 0) {
                    eatTimer--;
                    if (eatTimer == 0) {
                        finishEatingAndDisappear(plate);
                    }
                }
            }
        }

        private void startEating(PlateBlockEntity plate) {
            foodCount = countFood(plate);
            if (foodCount == 0) return;
            plate.clearFoodContents();

            pixie.setEating(EAT_DURATION);
            eatTimer = EAT_DURATION;
        }

        private void finishEatingAndDisappear(PlateBlockEntity plate) {
            if (pixie.level().isClientSide()) return;
            ServerLevel serverLevel = (ServerLevel) pixie.level();

            for (int i = 0; i < foodCount; i++) {
                ItemStack reward = generateReward(serverLevel);
                if (!reward.isEmpty()) {
                    if(plate.inventory.getStackInSlot(0).isEmpty())
                    {
                        plate.inventory.setStackInSlot(0, reward.copyWithCount(1));
                    } else if(plate.inventory.getStackInSlot(1).isEmpty())
                    {
                        plate.inventory.setStackInSlot(1, reward.copyWithCount(1));
                    } else if(plate.inventory.getStackInSlot(2).isEmpty())
                    {
                        plate.inventory.setStackInSlot(2, reward.copyWithCount(1));
                    }
                }
            }
            pixie.discard();
        }

        private ItemStack generateReward(net.minecraft.server.level.ServerLevel level) {
            List<ResourceKey> lootTables = List.of(BuiltInLootTables.SIMPLE_DUNGEON, BuiltInLootTables.STRONGHOLD_CROSSING,BuiltInLootTables.STRONGHOLD_CORRIDOR, BuiltInLootTables.DESERT_PYRAMID, BuiltInLootTables.JUNGLE_TEMPLE);
            if (level.random.nextFloat() < 0.70f) {
                LootTable table = level().getServer()
                        .reloadableRegistries()
                        .getLootTable(lootTables.get(random.nextInt(lootTables.size())));

                LootParams params = new LootParams.Builder((ServerLevel) level())
                        .withParameter(LootContextParams.ORIGIN, pixie.position())
                        .create(LootContextParamSets.CHEST);

                List<ItemStack> items = table.getRandomItems(params).stream()
                        .filter(stack -> !stack.has(DataComponents.FOOD))
                        .collect(java.util.stream.Collectors.toList());;

                if (!items.isEmpty()) {
                    return items.get(level.random.nextInt(items.size()));
                }
            }

            return new ItemStack(ModItems.FAIRY_DUST.get());
        }

        private void flyToTarget() {
            if (targetPos == null) return;
            pixie.getMoveControl().setWantedPosition(
                    targetPos.getX() + 0.5,
                    targetPos.getY() + 1.5,
                    targetPos.getZ() + 0.5,
                    1.0
            );
        }

        private BlockPos findNearestPlateWithFood() {
            BlockPos pixiePos = pixie.blockPosition();
            int range = (int) pixie.getAttributeValue(net.minecraft.world.entity.ai.attributes.Attributes.FOLLOW_RANGE);

            BlockPos nearest = null;
            double nearestDist = Double.MAX_VALUE;

            for (BlockPos pos : BlockPos.betweenClosed(
                    pixiePos.offset(-range, -range / 2, -range),
                    pixiePos.offset(range, range / 2, range)
            )) {
                if (pixie.level().getBlockEntity(pos) instanceof PlateBlockEntity plate) {
                    if (hasFood(plate)) {
                        double dist = pixiePos.distSqr(pos);
                        if (dist < nearestDist) {
                            nearestDist = dist;
                            nearest = pos.immutable();
                        }
                    }
                }
            }

            return nearest;
        }

        private boolean hasFood(PlateBlockEntity plate) {
            return countFood(plate) > 0;
        }

        private int countFood(PlateBlockEntity plate) {
            int count = 0;
            for (int i = 0; i < plate.inventory.getSlots(); i++) {
                ItemStack stack = plate.inventory.getStackInSlot(i);
                if (!stack.isEmpty() && stack.has(DataComponents.FOOD)) {
                    count++;
                }
            }
            return count;
        }
    }
    public class PanicAndDisappearGoal extends Goal {

        private final DreamPixie pixie;
        private final double detectionRange;
        private static final int PANIC_DURATION = 60;

        public PanicAndDisappearGoal(DreamPixie pixie, double detectionRange) {
            this.pixie = pixie;
            this.detectionRange = detectionRange;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            if (pixie.getPanicing() > 0) return false;
            return isNearWakingEntity();
        }

        @Override
        public boolean canContinueToUse() {
            return pixie.getPanicing() > 0;
        }

        @Override
        public void start() {
            pixie.setPanicing(1);
        }

        @Override
        public void tick() {
            int panic = pixie.getPanicing();

            if (panic >= PANIC_DURATION) {
                disappear();
            }
        }

        private void disappear() {
            if (pixie.level().isClientSide()) return;

            ServerLevel serverLevel = (ServerLevel) pixie.level();

            ItemStack dust = new ItemStack(ModItems.FAIRY_DUST.get());
            ItemEntity dustEntity = new ItemEntity(
                    serverLevel,
                    pixie.getX(), pixie.getY(), pixie.getZ(),
                    dust
            );
            dustEntity.setDefaultPickUpDelay();
            serverLevel.addFreshEntity(dustEntity);

            pixie.discard();
        }

        private boolean isNearWakingEntity() {
            List<LivingEntity> nearby = pixie.level().getEntitiesOfClass(
                    LivingEntity.class,
                    pixie.getBoundingBox().inflate(detectionRange),
                    entity -> entity != pixie && !entity.isSleeping() && entity.isAlive() && !((entity instanceof Player p) && (p.isSpectator() || p.isCreative()))
            );
            return !nearby.isEmpty();
        }
    }
}
