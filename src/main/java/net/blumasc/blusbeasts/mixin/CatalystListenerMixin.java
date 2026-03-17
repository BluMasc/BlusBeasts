package net.blumasc.blusbeasts.mixin;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.AmthystCrabEntity;
import net.blumasc.blusbeasts.entity.custom.EchoCrabEntity;
import net.blumasc.blusbeasts.entity.custom.GemCrabEntity;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SculkCatalystBlockEntity.CatalystListener.class)
public class CatalystListenerMixin {

    @Inject(
            method = "handleGameEvent",
            at = @At("HEAD")
    )
    private void onEntityDeath(ServerLevel level, Holder<GameEvent> gameEvent, GameEvent.Context context, Vec3 pos, CallbackInfoReturnable<Boolean> cir) {
        if (gameEvent.is(GameEvent.ENTITY_DIE)) {
            Entity source = context.sourceEntity();
            if (source instanceof LivingEntity) {
                double radius = 5.0;
                List<AmthystCrabEntity> entities = level.getEntitiesOfClass(
                        AmthystCrabEntity.class,
                        new AABB(source.blockPosition()).inflate(radius)
                );

                for (AmthystCrabEntity entity : entities) {
                    EchoCrabEntity newEntity = new EchoCrabEntity(ModEntities.ECHO_CRAB.get(), level);
                    newEntity.moveTo(entity.getX(), entity.getY(), entity.getZ(), entity.getYRot(), entity.getXRot());
                    newEntity.setHealth(entity.getHealth());

                    if (entity.hasCustomName()) {
                        newEntity.setCustomName(entity.getCustomName());
                        newEntity.setCustomNameVisible(entity.isCustomNameVisible());
                    }

                    newEntity.setGem(entity.hasGem());
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    level.addFreshEntity(newEntity);
                }
            }
        }
    }
}



