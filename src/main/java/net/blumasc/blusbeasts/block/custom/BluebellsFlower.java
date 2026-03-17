package net.blumasc.blusbeasts.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BluebellsFlower extends FlowerBlock {

    public BluebellsFlower(Holder<MobEffect> effect, float seconds, Properties properties) {
        super(effect, seconds, properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (level.isClientSide) return;
        if (!level.isDay())return;
        if(!hasOpenSky(level, pos)) return;

        int chunkOriginX = (pos.getX() >> 4) << 4;
        int chunkOriginZ = (pos.getZ() >> 4) << 4;
        int centerX = chunkOriginX + 8;
        int centerZ = chunkOriginZ + 8;

        int radius = (int) Math.round(Math.sqrt(
                Math.pow(pos.getX() - centerX, 2) +
                        Math.pow(pos.getZ() - centerZ, 2)
        ));

        if (radius < 1 || radius > 7) return;

        for (int attempt = 0; attempt < 10; attempt++) {
            double angle = random.nextDouble() * 2 * Math.PI;
            int targetX = centerX + (int) Math.round(Math.cos(angle) * radius);
            int targetZ = centerZ + (int) Math.round(Math.sin(angle) * radius);

            int actualRadius = (int) Math.round(Math.sqrt(
                    Math.pow(targetX - centerX, 2) +
                            Math.pow(targetZ - centerZ, 2)
            ));
            if (actualRadius != radius) continue;

            BlockPos targetPos = findGroundPos(level, new BlockPos(targetX, pos.getY(), targetZ));
            if (targetPos == null) continue;

            if (level.getBlockState(targetPos).isAir() &&
                    mayPlaceOn(level.getBlockState(targetPos.below()), level, targetPos.below())) {
                level.setBlock(targetPos, defaultBlockState(), 3);
                break;
            }
        }
    }

    private BlockPos findGroundPos(Level level, BlockPos pos) {
        for (int dy = 2; dy >= -2; dy--) {
            BlockPos candidate = pos.offset(0, dy, 0);
            BlockState below = level.getBlockState(candidate.below());
            if (level.getBlockState(candidate).isAir() && mayPlaceOn(below, level, candidate.below())) {
                if (hasOpenSky(level, candidate)) {
                    return candidate;
                }
            }
        }
        return null;
    }

    private boolean hasOpenSky(Level level, BlockPos pos) {
        int topY = level.getMaxBuildHeight();
        for (int y = pos.getY() + 1; y < topY; y++) {
            BlockState above = level.getBlockState(new BlockPos(pos.getX(), y, pos.getZ()));
            if (above.isAir()) continue;
            if (above.is(BlockTags.LEAVES)) continue;
            if (above.propagatesSkylightDown(level, new BlockPos(pos.getX(), y, pos.getZ()))) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        return super.mayPlaceOn(state, level, pos);
    }
}
