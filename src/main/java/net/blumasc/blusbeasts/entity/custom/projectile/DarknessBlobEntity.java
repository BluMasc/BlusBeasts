package net.blumasc.blusbeasts.entity.custom.projectile;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blubasics.block.entity.VoidBlockEntity;
import net.blumasc.blubasics.effect.BaseModEffects;
import net.blumasc.blubasics.item.BaseModItems;
import net.blumasc.blusbeasts.entity.variants.DeepshovelerVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

import static net.blumasc.blusbeasts.entity.variants.DeepshovelerVariant.GOLD;
import static net.blumasc.blusbeasts.entity.variants.DeepshovelerVariant.IRON;

public class DarknessBlobEntity extends AbstractHurtingProjectile {
    public DarknessBlobEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
        super.accelerationPower = 0.4;
    }

    public DarknessBlobEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Level level) {
        super(entityType, x, y, z, level);
    }

    public DarknessBlobEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Vec3 movement, Level level) {
        super(entityType, x, y, z, movement, level);
    }

    public DarknessBlobEntity(EntityType<? extends AbstractHurtingProjectile> entityType, LivingEntity owner, Vec3 movement, Level level) {
        super(entityType, owner, movement, level);
    }

    protected float getInertia() {
        return 0.6F;
    }

    protected float getLiquidInertia() {
        return 0.5F;
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos pos = BlockPos.containing(this.position());
        BlockState state = this.level().getBlockState(pos);
        if (state.getLightEmission() > 0) {
            this.level().setBlock(pos, BaseModBlocks.VOID_BLOCK.get().defaultBlockState(), 3);
            BlockEntity vtile = this.level().getBlockEntity(pos);
            if (vtile instanceof VoidBlockEntity timedTile) {
                timedTile.setOriginalBlock(state, 300);
            }
            this.discard();
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        BlockPos pos = result.getBlockPos();
        BlockState state = this.level().getBlockState(pos);
        if (state.getLightEmission() > 0) {
            this.level().setBlock(pos, BaseModBlocks.VOID_BLOCK.get().defaultBlockState(), 3);
            BlockEntity vtile = this.level().getBlockEntity(pos);
            if (vtile instanceof VoidBlockEntity timedTile) {
                timedTile.setOriginalBlock(state, 20*60);
            }
        }else{
            Direction d = result.getDirection();
            BlockState oldstate = this.level().getBlockState(pos.relative(d));
            this.level().setBlock(pos.relative(d), BaseModBlocks.VOID_BLOCK.get().defaultBlockState(), 3);
            BlockEntity vtile = this.level().getBlockEntity(pos.relative(d));
            if (vtile instanceof VoidBlockEntity timedTile) {
                timedTile.setOriginalBlock(oldstate, 20*60);
            }
        }
        this.discard();
    }

    private LivingEntity getLivingOwner(){
        return this.getOwner() instanceof LivingEntity le? le:null;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity e = result.getEntity();
        e.hurt(this.level().damageSources().mobProjectile(this, this.getLivingOwner()), 1.3f);
        if(e instanceof LivingEntity entity){
            entity.addEffect(new MobEffectInstance(BaseModEffects.VOID_EFFECT, 20*10));
        }
        this.discard();
    }

    @Nullable
    protected ParticleOptions getTrailParticle() {
        return ParticleTypes.ASH;
    }
}
