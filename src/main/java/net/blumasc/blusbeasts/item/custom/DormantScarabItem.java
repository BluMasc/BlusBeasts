package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.entity.custom.BurryEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DormantScarabItem extends Item {
    public DormantScarabItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if(target instanceof BurryEntity burry){
            if(burry.getGrowth()<=0) {
                burry.startGrowth();
                stack.shrink(1);
                return InteractionResult.CONSUME;
            }
        }
        return super.interactLivingEntity(stack, player, target, hand);
    }
}
