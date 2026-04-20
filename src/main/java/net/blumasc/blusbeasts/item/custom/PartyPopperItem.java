package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.particle.ModParticles;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
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
            ServerLevel serverLevel = (ServerLevel) level;

            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.PARTY_POPPER.get(), SoundSource.PLAYERS,
                    1.0f, 0.8f + player.getRandom().nextFloat() * 0.4f);

            serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(),
                    ModSounds.SPARKLE_ALL.get(), SoundSource.PLAYERS,
                    0.5f, 0.8f + player.getRandom().nextFloat() * 0.4f);

            Vec3 look = player.getLookAngle();
            Vec3 pos = player.getEyePosition();


            for (int i = 0; i < 75; i++) {
                double spread = 1.3;
                double distance = 1.0 + level.random.nextDouble() * 1.5;

                double vx = look.x * distance + (level.random.nextDouble() - 0.5) * spread;
                double vy = look.y * distance + (level.random.nextDouble() - 0.5) * spread;
                double vz = look.z * distance + (level.random.nextDouble() - 0.5) * spread;

                serverLevel.sendParticles(
                        ModParticles.CONFETTI.get(),
                        pos.x, pos.y, pos.z,
                        0,
                        vx, vy, vz,
                        1
                );
            }

            if (!player.hasInfiniteMaterials()) {
                player.getItemInHand(hand).shrink(1);
            }
        }

        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }
}
