package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blubasics.entity.custom.projectile.PickaxeBoomerangEntity;
import net.blumasc.blubasics.sound.BaseModSounds;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

public class PickerangItem extends PickaxeItem {
    public PickerangItem(Tier p_42961_, Properties p_42964_) {
        super(p_42961_, p_42964_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {

            PickaxeBoomerangEntity boomerang =
                    new PickaxeBoomerangEntity(level, player, stack.copy(), 25);

            boomerang.setPos(
                    player.getX(),
                    player.getY() + player.getEyeHeight(),
                    player.getZ()
            );

            level.addFreshEntity(boomerang);

            stack.shrink(1);

            ((ServerLevel) level).playSound(
                    null,
                    player.blockPosition(),
                    BaseModSounds.PICKERANG.get(),
                    SoundSource.PLAYERS,
                    1.0F,
                    player.getRandom().nextFloat() + 0.5f
            );
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        System.out.println("Repair check triggered");
        return repair.is(ModItems.BURROW_GEM.get());
    }
}
