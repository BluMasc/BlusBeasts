package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.entity.custom.projectile.InfestedArrowEntity;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class InfestedArrowItem extends ArrowItem {
    public InfestedArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity entity, @Nullable ItemStack weapon) {
        return new InfestedArrowEntity(entity, level, stack.copyWithCount(1), weapon);
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        InfestedArrowEntity spectralarrow = new InfestedArrowEntity(pos.x(), pos.y(), pos.z(), level, stack.copyWithCount(1), (ItemStack)null);
        spectralarrow.pickup = AbstractArrow.Pickup.ALLOWED;
        return spectralarrow;
    }
}
