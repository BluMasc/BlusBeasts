package net.blumasc.blusbeasts.block.custom;

import net.blumasc.blusbeasts.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;

public class WallPixieTorchBlock extends WallTorchBlock {
    public WallPixieTorchBlock(Properties properties) {
        super(ParticleTypes.SMOKE, properties);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        Direction direction = (Direction)state.getValue(FACING);
        double d0 = (double)pos.getX() + (double)0.5F;
        double d1 = (double)pos.getY() + 0.7;
        double d2 = (double)pos.getZ() + (double)0.5F;
        double x0 = (level.random.nextDouble() - 0.5) * 0.3;
        double y0 = level.random.nextDouble()* 0.3;
        double z0 = (level.random.nextDouble() - 0.5) * 0.3;
        level.addParticle(ModParticles.CONFETTI.get(), d0, d1, d2, x0, y0, z0);
        Direction direction1 = direction.getOpposite();
        level.addParticle(ModParticles.CONFETTI.get(), d0 + 0.27 * (double)direction1.getStepX(), d1 + 0.22, d2 + 0.27 * (double)direction1.getStepZ(), x0, y0, z0);
    }
}
