package net.blumasc.blusbeasts.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class VoidOre extends Block {
    public VoidOre(Properties properties) {
        super(properties);
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        Direction direction = Direction.getRandom(random);
        BlockPos targetPos = pos.relative(direction);

        BlockState targetState = level.getBlockState(targetPos);

        if (targetState.is(BlockTags.create(ResourceLocation.fromNamespaceAndPath("c","ores")))) {

            level.setBlock(pos, targetState, 3);
            level.setBlock(targetPos, state, 3);
        }
    }
}
