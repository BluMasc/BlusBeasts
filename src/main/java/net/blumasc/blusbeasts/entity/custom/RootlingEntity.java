package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blubasics.item.BaseModItems;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RootlingEntity extends PathfinderMob {

    public final AnimationState diggingAnimationState = new AnimationState();
    public final AnimationState happyAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    private static final EntityDataAccessor<String> HEAD_BLOCK_ID =
            SynchedEntityData.defineId(RootlingEntity.class, EntityDataSerializers.STRING);

    private static final EntityDataAccessor<Integer> HAPPY_TIMER =
            SynchedEntityData.defineId(RootlingEntity.class, EntityDataSerializers.INT);


    private static final EntityDataAccessor<Integer> PLANTING_ANIMATION_TIMER =
            SynchedEntityData.defineId(RootlingEntity.class, EntityDataSerializers.INT);

    private int numPlants;
    private int plantingTimer;
    private BlockPos plantingLocation;


    public RootlingEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    private int getHappyTimer() {
        return this.entityData.get(HAPPY_TIMER);
    }

    private void setHappyTimer(int i) {
        this.entityData.set(HAPPY_TIMER, i);
    }

    private int getPlantingAnimationTimer() {
        return this.entityData.get(PLANTING_ANIMATION_TIMER);
    }

    private void setPlantingAnimationTimer(int i) {
        this.entityData.set(PLANTING_ANIMATION_TIMER, i);
    }

    public BlockState getHeadBlock() {
        return stringToBlockState(this.entityData.get(HEAD_BLOCK_ID));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new RootlingFindPlantSpotGoal(this));
        this.goalSelector.addGoal(2, new RootlingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            animate();
        } else {
            if (numPlants <= 0) {
                ((ServerLevel) this.level()).sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        this.getX(), this.getY(), this.getZ(),
                        24, 0.3, 0.3, 0.3, 0.02
                );
                level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WOOD_BREAK, SoundSource.NEUTRAL, 1.0f, random.nextFloat() + 0.5f);
                this.discard();
            }
            if (getHappyTimer() > 0) {
                setHappyTimer(getHappyTimer() - 1);
                if (getHappyTimer() <= 0) {
                    ((ServerLevel) this.level()).sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            this.getX(), this.getY(), this.getZ(),
                            24, 0.3, 0.3, 0.3, 0.02
                    );
                    level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.WOOD_BREAK, SoundSource.NEUTRAL, 1.0f, random.nextFloat() + 0.5f);
                    if (this.numPlants >= 3) {
                        ItemEntity itementity = this.spawnAtLocation(BaseModItems.SPINE_TREE.get(), 1);
                        if (itementity != null) {
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double) (this.random.nextFloat() * 0.05F), (double) ((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
                        }
                    }
                    this.discard();
                }
            }
            if (plantingTimer > 0) {
                plantingTimer--;
            }
            if(plantingLocation != null){
            BlockState at = level().getBlockState(plantingLocation);
            if (!at.canBeReplaced()){
                plantingLocation=null;
            }
            if (!getHeadBlock().canSurvive(level(), plantingLocation)){
                plantingLocation=null;
            }
            }
            if (plantingLocation == null || distanceToSqr(plantingLocation.getBottomCenter()) > 2.5) {
                setPlantingAnimationTimer(0);
            } else if (getPlantingAnimationTimer() == 0) {
                setPlantingAnimationTimer(100);
            }
            if (getPlantingAnimationTimer() > 0) {
                setPlantingAnimationTimer(getPlantingAnimationTimer() - 1);
                if (getPlantingAnimationTimer() <= 0) {
                    level().setBlock(plantingLocation, getHeadBlock(), 3);
                    plantingLocation = null;
                    plantingTimer = 600 + random.nextInt(400);
                    numPlants--;
                }
            }
        }
    }

    private void animate() {
        if (getHappyTimer() > 0) {
            diggingAnimationState.stop();
            idleAnimationState.stop();
            happyAnimationState.startIfStopped(this.tickCount);
            return;
        }
        if (getPlantingAnimationTimer() > 0) {
            happyAnimationState.stop();
            idleAnimationState.stop();
            diggingAnimationState.startIfStopped(this.tickCount);
            return;
        }
        happyAnimationState.stop();
        diggingAnimationState.stop();
        idleAnimationState.startIfStopped(this.tickCount);
    }

    public boolean plantedCloseBy(BlockState b) {
        if (b.is(this.getHeadBlock().getBlock())) {
            setHappyTimer(25);
            return true;
        }
        return false;
    }

    public boolean setBlock(BlockState b) {
        if (this.getHeadBlock().is(ModTags.Blocks.ROOTLING_PLANTS)) return false;
        if (!b.is(ModTags.Blocks.ROOTLING_PLANTS)) return false;
        this.entityData.set(HEAD_BLOCK_ID, blockStateToString(b));
        numPlants = random.nextInt(4) + 2;
        plantingTimer = random.nextInt(600) + 200;
        return true;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.1f)
                .add(Attributes.FOLLOW_RANGE, 12D);
    }

    private static String blockStateToString(BlockState state) {
        return BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
    }

    private static BlockState stringToBlockState(String id) {
        Block block = BuiltInRegistries.BLOCK.get(ResourceLocation.parse(id));
        return block.defaultBlockState();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putString("HeadBlock", this.entityData.get(HEAD_BLOCK_ID));
        tag.putInt("HappyTimer", getHappyTimer());
        tag.putInt("PlantingAnimationTimer", getPlantingAnimationTimer());
        tag.putInt("PlantingTimer", plantingTimer);
        tag.putInt("PlantCount", numPlants);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(HEAD_BLOCK_ID, tag.getString("HeadBlock"));
        setHappyTimer(tag.getInt("HappyTimer"));
        setPlantingAnimationTimer(tag.getInt("PlantingAnimationTimer"));
        plantingTimer = tag.getInt("PlantingTimer");
        numPlants = tag.getInt("PlantCount");
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(HEAD_BLOCK_ID, blockStateToString(Blocks.AIR.defaultBlockState()));
        builder.define(HAPPY_TIMER, 0);
        builder.define(PLANTING_ANIMATION_TIMER, 0);
    }

    public class RootlingFindPlantSpotGoal extends Goal {

        private final RootlingEntity rootling;
        private BlockPos target;

        public RootlingFindPlantSpotGoal(RootlingEntity rootling) {
            this.rootling = rootling;
        }

        @Override
        public boolean canUse() {
            if (rootling.getHappyTimer() > 0) return false;
            if (rootling.getPlantingAnimationTimer() > 0) return false;
            if (rootling.plantingTimer > 0) return false;

            target = findSpot();
            return target != null;
        }

        @Override
        public boolean canContinueToUse() {
            if (rootling.getHappyTimer() > 0) return false;
            if (rootling.getPlantingAnimationTimer() > 0) return false;
            if (rootling.plantingTimer > 0) return false;
            if (rootling.plantingLocation == null) return false;
            return target != null;
        }

        @Override
        public void start() {
            rootling.plantingLocation = target;
        }

        @Override
        public void tick() {
            super.tick();
            rootling.getNavigation().moveTo(
                    target.getX() + 0.5,
                    target.getY(),
                    target.getZ() + 0.5,
                    1.0
            );

        }

        @Override
        public void stop() {
            rootling.getNavigation().stop();
            target = null;
        }

        private BlockPos findSpot() {
            BlockPos origin = rootling.blockPosition();

            for (int i = 0; i < 20; i++) {

                BlockPos pos = origin.offset(
                        rootling.getRandom().nextInt(9) - 4,
                        rootling.getRandom().nextInt(3) - 1,
                        rootling.getRandom().nextInt(9) - 4
                );
                BlockState at = rootling.level().getBlockState(pos);

                if (!at.canBeReplaced()) continue;

                if (rootling.getHeadBlock().canSurvive(rootling.level(), pos))
                    return pos;
            }

            return null;
        }

    }

    public class RootlingRandomStrollGoal extends RandomStrollGoal{
        private final RootlingEntity rootling;

        public RootlingRandomStrollGoal(RootlingEntity mob, double speedModifier) {
            super(mob, speedModifier);
            this.rootling = mob;
        }

        @Override
        public boolean canUse() {
            if (rootling.getHappyTimer() > 0) return false;
            if (rootling.getPlantingAnimationTimer() > 0) return false;
            if (rootling.plantingLocation!=null) return false;
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if (rootling.getHappyTimer() > 0) return false;
            if (rootling.getPlantingAnimationTimer() > 0) return false;
            if (rootling.plantingLocation!=null) return false;
            return super.canContinueToUse();
        }
    }
}