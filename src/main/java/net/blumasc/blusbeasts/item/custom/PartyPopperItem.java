package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.particle.ModParticles;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PartyPopperItem extends Item {
    public PartyPopperItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (!level.isClientSide) {
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        }

        Vec3 look = player.getLookAngle();
        Vec3 pos = player.getEyePosition();

        for (int i = 0; i < 75; i++) {
            double spread = 1.3;
            double distance = 1.0 + level.random.nextDouble() * 1.5;
            double dx = look.x*distance + (level.random.nextDouble() - 0.5) * spread;
            double dy = look.y*distance + (level.random.nextDouble() - 0.5) * spread;
            double dz = look.z*distance + (level.random.nextDouble() - 0.5) * spread;

            level.addParticle(
                    ModParticles.CONFETTI.get(),
                    pos.x, pos.y, pos.z,
                    dx, dy, dz
            );
        }

        if(!player.hasInfiniteMaterials()) {
            player.getItemInHand(hand).shrink(1);
        }
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
