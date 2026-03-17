package net.blumasc.blusbeasts.block.custom;

import com.mojang.serialization.MapCodec;
import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.VoidOreMagnetBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.Nullable;

public class VoidOreMagnetBlock extends BaseEntityBlock {
    public static final MapCodec<VoidOreMagnetBlock> CODEC = simpleCodec(VoidOreMagnetBlock::new);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");
    public VoidOreMagnetBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ACTIVE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidOreMagnetBlockEntity(pos, state);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos,
                                Block block, BlockPos fromPos, boolean isMoving) {

        if (!level.isClientSide) {
            boolean powered = level.hasNeighborSignal(pos);

            if (powered && !state.getValue(ACTIVE)) {
                BlockEntity be = level.getBlockEntity(pos);
                if (be instanceof VoidOreMagnetBlockEntity magnet) {
                    magnet.activate();
                }
            }
        }
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> type) {

        return createTickerHelper(type,
                ModBlockEntities.VOID_ORE_MAGNET_BE.get(),
                VoidOreMagnetBlockEntity::tick);
    }
}
