package net.blumasc.blusbeasts.entity.custom.projectile;

import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class GlitterBombEntity extends ThrowableItemProjectile {
    private static final EntityDimensions ZERO_SIZED_DIMENSIONS = EntityDimensions.fixed(0.0F, 0.0F);

    public GlitterBombEntity(EntityType<? extends GlitterBombEntity> entityType, Level level) {
        super(entityType, level);
    }

    public GlitterBombEntity(Level level, LivingEntity shooter) {
        super(ModEntities.GLITTER_BOMB.get(), shooter, level);
    }

    public GlitterBombEntity(Level level, double x, double y, double z) {
        super(ModEntities.GLITTER_BOMB.get(), x, y, z, level);
    }

    public void handleEntityEvent(byte id) {
        if (id == 3) {
            double d0 = 0.08;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08, ((double)this.random.nextFloat() - (double)0.5F) * 0.08);
            }
        }

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);

        if (!this.level().isClientSide) {
            BlockPos pos = result.getEntity().getOnPos().above();
            if (this.level().getBlockState(pos).canBeReplaced()) {
                this.level().setBlockAndUpdate(pos, ModBlocks.FAIRY_FIRE_BLOCK.get().defaultBlockState());
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        if (!this.level().isClientSide) {
            BlockPos pos = result.getBlockPos().relative(result.getDirection());

            if (this.level().getBlockState(pos).canBeReplaced()) {
                this.level().setBlockAndUpdate(pos, ModBlocks.FAIRY_FIRE_BLOCK.get().defaultBlockState());
            }
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);

        if (!this.level().isClientSide) {
            this.level().explode(
                    this,
                    this.getX(), this.getY(), this.getZ(),
                    1.5f,
                    false,
                    Level.ExplosionInteraction.NONE
            );
                double dx = (this.random.nextDouble() - 0.5) * 4.0;
                double dy = (this.random.nextDouble() - 0.5) * 4.0;
                double dz = (this.random.nextDouble() - 0.5) * 4.0;
                ((ServerLevel) this.level()).sendParticles(ModParticles.CONFETTI.get(),
                        this.getX(), this.getY(), this.getZ(),
                        100, dx, dy, dz, 2.0);
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }


    protected Item getDefaultItem() {
        return ModItems.GLITTER_BOMB.get();
    }
}
