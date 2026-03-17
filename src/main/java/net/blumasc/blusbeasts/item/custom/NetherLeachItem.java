package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.damage.ModDamageTypes;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NetherLeachItem extends Item implements Equipable {
    public NetherLeachItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        super.inventoryTick(stack, level, entity, slotId, isSelected);
        if(entity.tickCount % 40==0) {
            if (entity instanceof LivingEntity le){
                le.hurt(ModDamageTypes.leachDamage(le.level()), 1);
                le.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 60));
                if(entity.getRandom().nextFloat()<0.05) {
                    level.playSound(null, entity.getX(), entity.getY(), entity.getZ(), ModSounds.LEACH.get(), SoundSource.HOSTILE, 0.3f, entity.getRandom().nextFloat() + 0.5f);
                }
            }
        }
    }


    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
