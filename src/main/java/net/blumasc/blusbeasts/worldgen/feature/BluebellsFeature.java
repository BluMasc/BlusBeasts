package net.blumasc.blusbeasts.worldgen.feature;

import net.blumasc.blusbeasts.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BluebellsFeature extends Feature<NoneFeatureConfiguration> {

    public BluebellsFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        BlockPos pos = origin;
        while (pos.getY() > level.getMinBuildHeight() && level.getBlockState(pos).isAir()) {
            pos = pos.below();
        }
        pos = pos.above();

        BlockState flower = ModBlocks.BLUEBELLS.get().defaultBlockState();
        BlockState below = level.getBlockState(pos.below());

        if (!level.getBlockState(pos).isAir()) return false;
        if (!ModBlocks.BLUEBELLS.get().mayPlaceOn(below, level, pos.below())) return false;

        level.setBlock(pos, flower, 3);
        return true;
    }
}