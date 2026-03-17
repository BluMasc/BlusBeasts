package net.blumasc.blusbeasts.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;

public abstract class GemCrabEntity extends PathfinderMob implements Shearable {
    public final AnimationState snapAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();

    protected static final EntityDataAccessor<Integer> GEM =
            SynchedEntityData.defineId(GemCrabEntity.class, EntityDataSerializers.INT);

    protected GemCrabEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(GEM, 100);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("has_gem", this.hasGem());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(GEM, compound.getInt("has_gem"));
    }

    public int hasGem(){
        return this.entityData.get(GEM);
    }
    public void setGem(int gem){this.entityData.set(GEM, Math.min(100, Math.max(0, gem)));}
}
