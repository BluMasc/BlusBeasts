package net.blumasc.blusbeasts.item.dispensebehaviour;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

public class GravityDustDispenseBehavior extends DefaultDispenseItemBehavior {

    @Override
    protected ItemStack execute(BlockSource source, ItemStack stack) {
        Level level = source.level();
        if(!(level instanceof ServerLevel sl)) return stack;
        BlockPos pos = source.pos();
        Direction facing = source.state().getValue(net.minecraft.world.level.block.DispenserBlock.FACING);

        BlockState state = level.getBlockState(pos.relative(facing));
        if(state.is(Blocks.AIR)) return stack;
        if (!PistonBaseBlock.isPushable(state, level, pos.relative(facing), Direction.DOWN, false, Direction.DOWN)) {
            return stack;
        }

        level.removeBlock(pos.relative(facing), false);
        FallingBlockEntity falling = FallingBlockEntity.fall(
                level,
                pos.relative(facing),
                state
        );

        level.addFreshEntity(falling);

        stack.shrink(1);
        return stack;
    }
}
