package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.projectile.HeartProjectileEntity;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class MagicWandItem extends Item {

    public MagicWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
        Level level = entity.level();

        if (!level.isClientSide() && entity instanceof LivingEntity target) {
            List<Holder<MobEffect>> harmfulEffects = target.getActiveEffectsMap()
                    .keySet()
                    .stream()
                    .filter(effect -> effect.value().getCategory() == MobEffectCategory.HARMFUL)
                    .toList();

            if (!harmfulEffects.isEmpty()) {
                Holder<MobEffect> toRemove = harmfulEffects.get(level.random.nextInt(harmfulEffects.size()));
                target.removeEffect(toRemove);

                ((ServerLevel) level).sendParticles(
                        ModParticles.PINK_HEART.get(),
                        target.getX(),
                        target.getY() + target.getBbHeight() / 2,
                        target.getZ(),
                        12, 0.3, 0.3, 0.3, 0.05
                );

                level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.MAGIC_WAND_HEAL.get(), SoundSource.PLAYERS, 1.0f, 0.8f+player.getRandom().nextFloat()*0.4f);
                stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
                player.getCooldowns().addCooldown(this, 20*20);
            }
        }
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            Vec3 lookAngle = player.getLookAngle();

            HeartProjectileEntity projectile = new HeartProjectileEntity(
                    ModEntities.HEART.get(),
                    player,
                    lookAngle,
                    level
            );

            projectile.setPos(
                    player.getX(),
                    player.getEyeY() - 0.1,
                    player.getZ()
            );

            level.addFreshEntity(projectile);
            level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.MAGIC_WAND_HEAL.get(), SoundSource.PLAYERS, 1.0f, 0.8f+player.getRandom().nextFloat()*0.4f);
            stack.hurtAndBreak(1, player, EquipmentSlot.MAINHAND);
            player.getCooldowns().addCooldown(this, 10*20);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
