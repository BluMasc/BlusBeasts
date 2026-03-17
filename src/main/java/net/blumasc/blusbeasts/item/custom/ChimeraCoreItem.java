package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blubasics.entity.BaseModEntities;
import net.blumasc.blubasics.entity.custom.ChimeraEntity;
import net.blumasc.blubasics.util.BaseModTags;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.item.custom.components.ModItemDataComponents;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ChimeraCoreItem extends Item {

    public ChimeraCoreItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {

        if (player.level().isClientSide)
            return InteractionResult.SUCCESS;

        if(target.getHealth()>40)
            return InteractionResult.PASS;

        if (!target.getType().is(BaseModTags.EntityTypes.CHIMERA_LIKE))
            return InteractionResult.PASS;

        Set<EntityType<?>> collected = new HashSet<>(stack.getOrDefault(ModItemDataComponents.COLLECTED_MOBS.get(), Set.of()));

        if (checkIfAlreadyCollected(target.getType(), collected))
            return InteractionResult.PASS;

        collected.add(target.getType());
        stack.set(ModItemDataComponents.COLLECTED_MOBS.get(), collected);

        player.setItemInHand(hand, stack);

        target.discard();
        ServerLevel server = (ServerLevel) player.level();

        server.sendParticles(
                ParticleTypes.SOUL,
                target.getX(),
                target.getY() + target.getBbHeight() * 0.5,
                target.getZ(),
                20,
                (player.getX() - target.getX()) * 0.1,
                (player.getY() + 1 - target.getY()) * 0.1,
                (player.getZ() - target.getZ()) * 0.1,
                0.02
        );
        server.playSound(
                null,
                target.getX(),
                target.getY(),
                target.getZ(),
                SoundEvents.SOUL_ESCAPE,
                SoundSource.PLAYERS,
                1.0F,
                1.2F
        );

        return InteractionResult.SUCCESS;
    }

    private boolean checkIfAlreadyCollected(EntityType<?> type, Set<EntityType<?>> collected) {
        boolean canBeAddedFox = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_FOX)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_FOX)){ canBeAddedFox=false; break;}
            }
        }
        boolean canBeAddedChicken = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_CHICKEN)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_CHICKEN)){ canBeAddedChicken=false; break;}
            }
        }
        boolean canBeAddedGoat = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_GOAT)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_GOAT)){ canBeAddedGoat=false; break;}
            }
        }
        boolean canBeAddedGuardian = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_GUARDIAN)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_GUARDIAN)){ canBeAddedGuardian=false; break;}
            }
        }
        boolean canBeAddedPhantom = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_PHANTOM)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_PHANTOM)){ canBeAddedPhantom=false; break;}
            }
        }
        boolean canBeAddedRabbit = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_RABBIT)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_RABBIT)){ canBeAddedRabbit=false; break;}
            }
        }
        boolean canBeAddedHoglin = true;
        if(type.is(BaseModTags.EntityTypes.CHIMERA_LIKE_HOGLIN)){
            for(EntityType e : collected)
            {
                if(e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_HOGLIN)){ canBeAddedHoglin=false; break;}
            }
        }
        return !(canBeAddedFox || canBeAddedChicken || canBeAddedGoat || canBeAddedGuardian || canBeAddedPhantom || canBeAddedRabbit || canBeAddedHoglin);

    }

    @Override
    public InteractionResult useOn(UseOnContext context) {

        Level level = context.getLevel();
        if (level.isClientSide)
            return InteractionResult.SUCCESS;

        if (context.getClickedFace() != Direction.UP)
            return InteractionResult.FAIL;

        ItemStack stack = context.getItemInHand();
        Player player = context.getPlayer();

        Set<EntityType<?>> collected = stack.getOrDefault(ModItemDataComponents.COLLECTED_MOBS.get(), Set.of());

        if(collected.size()>=5) {

            ServerLevel server = (ServerLevel) level;

            BlockPos spawnPos = context.getClickedPos().above();

            if (!level.getBlockState(spawnPos).canBeReplaced())
                return InteractionResult.FAIL;

            ChimeraEntity chimera = BaseModEntities.CHIMERA.get().create(server);
            if (chimera == null) return InteractionResult.FAIL;

            chimera.moveTo(
                    spawnPos.getX() + 0.5,
                    spawnPos.getY(),
                    spawnPos.getZ() + 0.5,
                    player.getYRot(),
                    0
            );

            chimera.tame(player);
            server.addFreshEntity(chimera);
            server.sendParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    chimera.getX(),
                    chimera.getY(),
                    chimera.getZ(),
                    30,
                    (spawnPos.getX() + 0.5 - player.getX()) * 0.1,
                    (spawnPos.getY() - player.getY()) * 0.1,
                    (spawnPos.getZ() + 0.5 - player.getZ()) * 0.1,
                    0.02
            );
            server.playSound(
                    null,
                    chimera.getX(),
                    chimera.getY(),
                    chimera.getZ(),
                    SoundEvents.SOUL_ESCAPE,
                    SoundSource.PLAYERS,
                    1.0F,
                    1.2F
            );
            stack.shrink(1);
            player.setItemInHand(context.getHand(), stack);

            return InteractionResult.CONSUME;
        }

        return InteractionResult.PASS;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        Set<EntityType<?>> s = stack.getOrDefault(ModItemDataComponents.COLLECTED_MOBS.get(), Set.of());
        return s.size()>=5;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {

        Set<EntityType<?>> collected = stack.getOrDefault(ModItemDataComponents.COLLECTED_MOBS.get(), Set.of());

        tooltip.add(Component.translatable("item.blusbeasts.chimera_core.tooltip").withStyle(ChatFormatting.GRAY));
        if(collected.isEmpty()){
            tooltip.add(Component.translatable("item.blusbeasts.chimera_core.tooltip.empty").withStyle(ChatFormatting.RED));
        }
        for (EntityType<?> entityType : collected) {

            Component line = Component.literal((""))
                    .append(entityType.getDescription())
                    .withStyle(ChatFormatting.GREEN);

            tooltip.add(line);
        }
    }
}
