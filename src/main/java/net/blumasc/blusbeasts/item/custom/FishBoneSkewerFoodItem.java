package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FishBoneSkewerFoodItem extends Item {
    public FishBoneSkewerFoodItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity user) {
        super.finishUsingItem(stack, level, user);

        ItemStack bowl = new ItemStack(ModItems.FISH_BONE_SKEWER.get());
        if (user instanceof Player player) {
            if (!player.getInventory().add(bowl)) player.drop(bowl, false);
        }

        return stack.isEmpty() ? bowl : stack;
    }
}
