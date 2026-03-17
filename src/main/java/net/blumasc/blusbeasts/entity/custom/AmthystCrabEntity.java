package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class AmthystCrabEntity extends GemCrabEntity{
    public AmthystCrabEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()){
            this.setupAnimationStates();
        }else if (!this.mainSupportingBlockPos.isEmpty()){
            if(level().getBlockState(this.mainSupportingBlockPos.get()).is(ModTags.Blocks.AMETHYST_CRAB_SPAWNABLE))
                if(this.random.nextFloat()<0.1) {
                    int newGem = Math.min(100, this.hasGem() + 1);

                    this.entityData.set(GEM, newGem);
                }
        }
    }

    private void setupAnimationStates() {
        idleAnimationState.startIfStopped(tickCount);
    }

    @Override
    public void shear(SoundSource soundSource) {
        this.level().playSound((Player)null, this, SoundEvents.SHEEP_SHEAR, soundSource, 1.0F, 1.0F);

        if(!(this.entityData.get(GEM)>=100))return;
        this.entityData.set(GEM, 0);
        Item i = Items.AMETHYST_SHARD;
        if(this.random.nextFloat()<0.3){
            i = ModItems.EMBEDDED_CRYSTALS.get();
        }
        ItemEntity itementity = this.spawnAtLocation(i, 1);
        if (itementity != null) {
            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F), (double)(this.random.nextFloat() * 0.05F), (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)));
        }
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 10d)
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.ARMOR, 0.4F)
                .add(Attributes.FOLLOW_RANGE, 24D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Monster.class, 16.0F, 1.5D, 2.0D));
        this.goalSelector.addGoal(2, new MoveToAmethystGoal(this));
        this.goalSelector.addGoal(3, new MoveToSculkGoal(this));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean readyForShearing() {
        return (this.entityData.get(GEM)>=100);
    }

    public static boolean checkAmethystCrabSpawnRules(EntityType<AmthystCrabEntity> amethystCrabEntityEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType spawnType, BlockPos blockPos, RandomSource randomSource) {
        BlockState below = serverLevelAccessor.getBlockState(blockPos.below());
        return below.is(ModTags.Blocks.AMETHYST_CRAB_SPAWNABLE);
    }

    private class MoveToAmethystGoal extends MoveToBlockGoal {

        private final AmthystCrabEntity mob;

        public MoveToAmethystGoal(AmthystCrabEntity mob) {
            super(mob, 1.0D, 12, 6);
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            if(mob.hasGem()>=100) return false;
            if (mob.getRandom().nextInt(80) != 0) return false;
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if(mob.hasGem()>=100) return false;
            return super.canContinueToUse();
        }

        @Override
        public void tick() {
            super.tick();

            if (isReachedTarget()) {
                mob.getNavigation().stop();
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            BlockState state = levelReader.getBlockState(blockPos);
            return state.is(ModTags.Blocks.AMETHYST_CRAB_SPAWNABLE)
                    && levelReader.isEmptyBlock(blockPos.above());
        }

    }

    private class MoveToSculkGoal extends MoveToBlockGoal {

        private final AmthystCrabEntity mob;

        public MoveToSculkGoal(AmthystCrabEntity mob) {
            super(mob, 1.0D, 16, 10);
            this.mob = mob;
        }

        @Override
        public boolean canUse() {
            if(mob.hasGem()<100) return false;
            if (mob.getRandom().nextInt(120) != 0) return false;
            return super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            if(mob.hasGem()<100) return false;
            return super.canContinueToUse();
        }

        @Override
        public void tick() {
            super.tick();

            if (isReachedTarget()) {
                mob.getNavigation().stop();
            }
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            BlockState state = levelReader.getBlockState(blockPos);
            return state.is(Blocks.SCULK_CATALYST)
                    && levelReader.isEmptyBlock(blockPos.above());
        }

    }

}
