package net.blumasc.blusbeasts.item.custom;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.RootlingEntity;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class RootlingSpawnEggItem extends Item {

    private static List<Block> CACHED_PLANTS;

    public RootlingSpawnEggItem(Properties properties) {
        super(properties);
    }

    public static void cachePlants(RegistryAccess access) {
        if (CACHED_PLANTS != null) return;

        var tag = access.registryOrThrow(Registries.BLOCK).getTag(ModTags.Blocks.ROOTLING_PLANTS);
        CACHED_PLANTS = tag.orElseThrow().stream().map(h -> h.value()).toList();
    }

    public BlockState getRandomBlockFromTag(Level level) {
        cachePlants(level.registryAccess());
        return CACHED_PLANTS.get(level.random.nextInt(CACHED_PLANTS.size())).defaultBlockState();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();

        if (!(level instanceof ServerLevel serverLevel)) {
            return InteractionResult.SUCCESS;
        }

        BlockPos pos = context.getClickedPos().relative(context.getClickedFace());
        Player player = context.getPlayer();

        RootlingEntity rootling = ModEntities.ROOTLING.get().create(serverLevel);

        if (rootling == null) {
            return InteractionResult.FAIL;
        }

        rootling.moveTo(
                pos.getX() + 0.5,
                pos.getY(),
                pos.getZ() + 0.5,
                context.getRotation(),
                0
        );

        rootling.finalizeSpawn(
                serverLevel,
                serverLevel.getCurrentDifficultyAt(pos),
                MobSpawnType.SPAWN_EGG,
                null
        );

        rootling.setBlock(getRandomBlockFromTag(serverLevel));

        serverLevel.addFreshEntity(rootling);

        if (player != null && !player.getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.CONSUME;
    }
}
