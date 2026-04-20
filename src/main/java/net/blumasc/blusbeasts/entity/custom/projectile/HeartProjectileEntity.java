package net.blumasc.blusbeasts.entity.custom.projectile;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blubasics.block.entity.VoidBlockEntity;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.checkerframework.checker.units.qual.A;

import javax.annotation.Nullable;

public class HeartProjectileEntity extends AbstractHurtingProjectile {
    public int rotation;
    public double bounce;
    public HeartProjectileEntity(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
        super.accelerationPower = 0.2;
    }

    public HeartProjectileEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Level level) {
        super(entityType, x, y, z, level);
    }

    public HeartProjectileEntity(EntityType<? extends AbstractHurtingProjectile> entityType, double x, double y, double z, Vec3 movement, Level level) {
        super(entityType, x, y, z, movement, level);
    }

    public HeartProjectileEntity(EntityType<? extends AbstractHurtingProjectile> entityType, LivingEntity owner, Vec3 movement, Level level) {
        super(entityType, owner, movement, level);
    }

    public boolean isOnFire() {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
        if(this.level().isClientSide()){
            this.rotation = (this.rotation+15)%360;
            this.bounce = Math.sin(this.tickCount/8.0)/4.0;
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        BlockPos pos = result.getBlockPos();
        if (!this.level().isClientSide()) {
            ((ServerLevel) this.level()).sendParticles(
                    ModParticles.PINK_HEART.get(),
                    pos.getX(), pos.getY(), pos.getZ(),
                    25, 0.3, 0.3, 0.3, 0.05
            );
        }
        AreaEffectCloud c = new AreaEffectCloud(this.level(), pos.getX(), pos.getY(), pos.getZ());
        c.setRadius(2.5f);
        c.setOwner(this.getLivingOwner());
        c.setDuration(100);
        c.addEffect(new MobEffectInstance(MobEffects.HEAL));
        level().playSound(null, getX(), getY(), getZ(), ModSounds.HEART_EXPLOSION.get(), SoundSource.PLAYERS, 1.0f, 0.8f+getRandom().nextFloat()*0.4f);
        this.level().addFreshEntity(c);
        this.discard();
    }

    private LivingEntity getLivingOwner(){
        return this.getOwner() instanceof LivingEntity le? le:null;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        Entity e = result.getEntity();
        if (!this.level().isClientSide()) {
            ((ServerLevel) this.level()).sendParticles(
                    ModParticles.PINK_HEART.get(),
                    e.getX(), e.getY() + e.getBbHeight() / 2, e.getZ(),
                    25, 0.3, 0.3, 0.3, 0.05
            );
        }
        if(e instanceof LivingEntity le){
            le.addEffect(new MobEffectInstance(MobEffects.HEAL, 20, 2));
        }else{
            AreaEffectCloud c = new AreaEffectCloud(this.level(), e.getX(), e.getY(), e.getZ());
            c.setRadius(2.5f);
            c.setOwner(this.getLivingOwner());
            c.setDuration(100);
            c.addEffect(new MobEffectInstance(MobEffects.HEAL));
            this.level().addFreshEntity(c);
        }
        level().playSound(null, getX(), getY(), getZ(), ModSounds.HEART_EXPLOSION.get(), SoundSource.PLAYERS, 1.0f, 0.8f+getRandom().nextFloat()*0.4f);
        this.discard();
    }

    @Nullable
    protected ParticleOptions getTrailParticle() {
        return ModParticles.PINK_HEART.get();
    }
}
