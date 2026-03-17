package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.recipe.ModRecipes;
import net.blumasc.blusbeasts.recipe.NetherLeachRecipe;
import net.blumasc.blusbeasts.recipe.NetherLeachRecipeInput;
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
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

public class NetherLeachEntity extends Monster {

    private static final EntityDataAccessor<Boolean> SUCKING =
            SynchedEntityData.defineId(NetherLeachEntity.class, EntityDataSerializers.BOOLEAN);

    private int sucking_time = 0;
    private BlockPos targetPos = null;

    public boolean isSucking() {
        return this.entityData.get(SUCKING);
    }

    public void setSucking(boolean perched) {
        this.entityData.set(SUCKING, perched);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SUCKING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        sucking_time = compound.getInt("sucking_time");
        if(compound.getBoolean("target_block_set"))
        {
            targetPos = new BlockPos(compound.getInt("target_x"),compound.getInt("target_y"),compound.getInt("target_z"));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putInt("sucking_time", sucking_time);
        compound.putBoolean("target_block_set", targetPos != null);
        if(targetPos != null) {
            compound.putInt("target_x", targetPos.getX());
            compound.putInt("target_y", targetPos.getY());
            compound.putInt("target_z", targetPos.getZ());
        }
    }

    public final AnimationState suckingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    public NetherLeachEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    public static AttributeSupplier.Builder createAttributes(){
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 8d)
                .add(Attributes.MOVEMENT_SPEED, 0.25f)
                .add(Attributes.FOLLOW_RANGE, 24D)
                .add(Attributes.ATTACK_DAMAGE, 1);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BlockSuckGoal(this));
        this.goalSelector.addGoal(2, new PlayerGiveGoal());
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8){
            @Override
            public boolean canUse() {
                return (targetPos==null && super.canUse());
            }
        });
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 2.0f));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH_SMALL;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.SLIME_HURT_SMALL;
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return ModSounds.LEACH.get();
    }

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide)
        {
            setupClientAnimation();
        }else{
            if(this.level().dimension() != Level.NETHER){
                ((ServerLevel)this.level()).sendParticles(
                        net.minecraft.core.particles.ParticleTypes.CLOUD,
                        this.getX(), this.getY(), this.getZ(),
                        12, 0.3, 0.3, 0.3, 0.02
                );
                level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.SLIME_DEATH_SMALL, SoundSource.HOSTILE);
                this.remove(RemovalReason.KILLED);
            }
            if(targetPos != null) {
                BlockState bs = level().getBlockState(targetPos);
                BlockState goal = getSuckingResult(bs);
                if(goal == null){
                    sucking_time=0;
                    targetPos=null;
                }else{
                    if(this.position().closerThan(targetPos.getBottomCenter().add(0,1,0), 1.5)){
                        this.setPos(targetPos.getBottomCenter().add(0,1,0));
                        this.sucking_time--;
                        ((ServerLevel)level()).sendParticles(
                                ParticleTypes.FLAME,
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                4,
                                0,
                                0,
                                0,
                                0.02
                        );
                        if(random.nextFloat()<0.15){
                            level().playSound(null, getX(), getY(), getZ(), ModSounds.LEACH.get(), SoundSource.HOSTILE, 1.0f, getRandom().nextFloat()+0.5f);
                        }
                        if(sucking_time<=0){
                            level().setBlock(targetPos, goal, 3);
                            targetPos=null;
                        }
                    }else{
                        sucking_time=301;
                    }
                }
            }else{
                sucking_time=0;
            }
            setSucking(sucking_time>0 && sucking_time<301);
        }
    }

    private void setupClientAnimation() {
        idleAnimationState.startIfStopped(this.tickCount);
        if(isSucking()){
            suckingAnimationState.startIfStopped(this.tickCount);
        }else{
            suckingAnimationState.stop();
        }
    }

    private @Nullable BlockState getSuckingResult(BlockState input) {
        RecipeManager rm = level().getRecipeManager();
        Optional<RecipeHolder<NetherLeachRecipe>> recipe =
                rm.getRecipeFor(ModRecipes.NETHER_LEACH_TYPE.get(), new NetherLeachRecipeInput(input.getBlock().asItem().getDefaultInstance()), level());
        if(recipe.isEmpty()) return null;
        ItemStack i = recipe.get().value().getResultItem(level().registryAccess());
        if(i.getItem() instanceof BlockItem bi){
            return bi.getBlock().defaultBlockState();
        }
        return null;
    }

    public static boolean checkNetherLeachSpawnRules(EntityType<NetherLeachEntity> netherLeachEntityEntityType, ServerLevelAccessor level, MobSpawnType mobSpawnType, BlockPos pos, RandomSource randomSource) {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.NETHER_LEACH_SPAWNABLE);
    }


    private class BlockSuckGoal extends Goal {
        private NetherLeachEntity leach;
        public BlockSuckGoal(NetherLeachEntity leach) {
            this.leach=leach;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if(targetPos != null) return true;
            BlockPos mobPos = leach.blockPosition();
            List<BlockPos> possibleTargets = new ArrayList<BlockPos>();
            for(int dx = -3; dx <= 3; dx++) {
                for(int dy = -3; dy <= 3; dy++) {
                    for(int dz = -3; dz <= 3; dz++) {
                        BlockPos checkPos = mobPos.offset(dx, dy, dz);
                        BlockState bs = level().getBlockState(checkPos);
                        if(bs != null && getSuckingResult(bs) != null && level().getBlockState(checkPos.above()).getCollisionShape(level(), checkPos.above()).isEmpty()) {
                            possibleTargets.add(checkPos);
                        }
                    }
                }
            }
            if(possibleTargets.isEmpty()) {
                return false;
            }
            else{
                leach.targetPos = possibleTargets.get(leach.random.nextInt(possibleTargets.size()));
                return true;
            }
        }

        @Override
        public boolean canContinueToUse() {
            return leach.targetPos != null;
        }

        @Override
        public void start() {
            if(leach.targetPos != null) {
                getNavigation().moveTo(leach.targetPos.getX() + 0.5, leach.targetPos.getY() + 1, leach.targetPos.getZ() + 0.5, 1.0);
                leach.sucking_time = 301;
            }
        }

        @Override
        public void tick() {
            if(leach.targetPos != null) {
                getNavigation().moveTo(leach.targetPos.getX() + 0.5, leach.targetPos.getY() + 1, leach.targetPos.getZ() + 0.5, 1.0);
            }
        }

        @Override
        public void stop() {
            leach.targetPos = null;
            leach.sucking_time = 0;
        }
    }
    private class PlayerGiveGoal extends Goal {
        public PlayerGiveGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if(targetPos!=null) return false;
            AABB damageBox = NetherLeachEntity.this.getBoundingBox().inflate(10, 10, 10);

            for (Entity entity : level().getEntities(NetherLeachEntity.this, damageBox)) {
                if (entity instanceof Player p){
                    if(p.isCreative() || p.isSpectator()) continue;
                    if(hasRoom(p))
                    {
                        targetPlayer=p;
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public boolean canContinueToUse() {
            return targetPlayer != null && !targetPlayer.isDeadOrDying() && hasRoom(targetPlayer);
        }

        @Override
        public void start() {
            if(targetPlayer != null) {
                getNavigation().moveTo(targetPlayer, 1.0);
            }
        }

        @Override
        public void tick() {
            if(targetPlayer != null) {
                getNavigation().moveTo(targetPlayer, 1.0);
                if(NetherLeachEntity.this.distanceTo(targetPlayer) < 1.5) {
                    ItemStack toGive = new ItemStack(ModItems.NETHER_LEACH.get());
                    targetPlayer.addItem(toGive);
                    NetherLeachEntity.this.remove(RemovalReason.DISCARDED);
                }
            }
        }

        @Override
        public void stop() {
            targetPlayer = null;
        }

        private Player targetPlayer;

        public static boolean hasRoom(Player player) {

            for (ItemStack slot : player.getInventory().items) {
                if (slot.isEmpty()) {
                    return true;
                }
            }
            return false;
        }

    }

}
