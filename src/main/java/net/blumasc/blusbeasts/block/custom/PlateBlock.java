package net.blumasc.blusbeasts.block.custom;

import com.mojang.serialization.MapCodec;
import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.PlateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public static final MapCodec<PlateBlock> CODEC = simpleCodec(PlateBlock::new);


    public PlateBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return level.getBlockState(pos.below()).isSolid();
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    ) {
        if (direction == Direction.DOWN && !state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PlateBlockEntity(blockPos,blockState);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if(state.getBlock() != newState.getBlock()){
            if(level.getBlockEntity(pos) instanceof PlateBlockEntity pedestalBlockEntity){
                pedestalBlockEntity.drops();
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return  Block.box((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)2.0F, (double)15.0F);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hitResult) {

        if (!(level.getBlockEntity(pos) instanceof PlateBlockEntity plate)) {
            return ItemInteractionResult.FAIL;
        }

        if (level.isClientSide) {
            return ItemInteractionResult.SUCCESS;
        }

            if (!stack.isEmpty())
            {
                if(plate.inventory.getStackInSlot(0).isEmpty())
                {
                    plate.inventory.setStackInSlot(0, stack.copyWithCount(1));
                    stack.shrink(1);
                } else if(plate.inventory.getStackInSlot(1).isEmpty())
                {
                    plate.inventory.setStackInSlot(1, stack.copyWithCount(1));
                    stack.shrink(1);
                } else if(plate.inventory.getStackInSlot(2).isEmpty())
                {
                    plate.inventory.setStackInSlot(2, stack.copyWithCount(1));
                    stack.shrink(1);
                }
            }
        else{
            if(!plate.inventory.getStackInSlot(0).isEmpty())
            {
                ItemStack extracted = plate.inventory.extractItem(0, 1, false);
                giveItemToPlayer(player,extracted);
            } else if(!plate.inventory.getStackInSlot(1).isEmpty())
            {
                ItemStack extracted = plate.inventory.extractItem(1, 1, false);
                giveItemToPlayer(player,extracted);
            } else if(!plate.inventory.getStackInSlot(2).isEmpty())
            {
                ItemStack extracted = plate.inventory.extractItem(2, 1, false);
                giveItemToPlayer(player,extracted);
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    private void giveItemToPlayer(Player player, ItemStack stack){
        boolean inserted = player.getInventory().add(stack);
        if (!inserted) {
            ItemEntity drop = new ItemEntity(
                    player.level(),
                    player.getX(),
                    player.getY() + 0.5,
                    player.getZ(),
                    stack.copy()
            );
            drop.setPickUpDelay(0);
            player.level().addFreshEntity(drop);
        }
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ModBlockEntities.PLATE_BE.get(),
                (nlevel, blockPos, blockState, blockEntity) -> blockEntity.tick(nlevel, blockPos, blockState));
    }
}
