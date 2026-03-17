package net.blumasc.blusbeasts.block.custom.blockentity.custom;

import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class FairyFireBlockEntity extends BlockEntity {
    private int lifetime;
    public FairyFireBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FAIRY_FIRE_BE.get(), pos, blockState);
        this.lifetime = 600 + (int)(Math.random() * 600);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("Lifetime", this.lifetime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.lifetime = tag.getInt("Lifetime");
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            for (int i = 0; i < 10; i++) {
                double px = blockPos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 10;
                double py = blockPos.getY() + 0.5 + (level.random.nextDouble() - 0.5) * 10;
                double pz = blockPos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 10;
                level.addParticle(ModParticles.CONFETTI.get(), px, py, pz, 0, 0, 0);
            }
            return;
        }
        AABB area = new AABB(blockPos).inflate(5);
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, area);
        for (LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 4, 0, false, false));
        }
        lifetime--;
        if (lifetime <= 0) {
            level.removeBlock(blockPos, false);
        }
    }
}
