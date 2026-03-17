package net.blumasc.blusbeasts.mixin;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.NetherLeachEntity;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin extends Entity {

    @Shadow
    public abstract ItemStack getItem();

    public ItemEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(
            method = "tick",
            at = @At("HEAD")
    )
    public void turnToMob(CallbackInfo ci){
        if(this.getItem().is(ModItems.NETHER_LEACH.get())){
            if(this.onGround()){
                NetherLeachEntity ne = new NetherLeachEntity(ModEntities.NETHER_LEACH.get(), this.level());
                ne.moveTo(this.position());
                this.level().addFreshEntity(ne);
                this.discard();
            }
        }
    }
}
