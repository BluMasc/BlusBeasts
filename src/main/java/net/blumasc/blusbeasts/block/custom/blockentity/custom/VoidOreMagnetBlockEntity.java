package net.blumasc.blusbeasts.block.custom.blockentity.custom;

import net.blumasc.blusbeasts.block.custom.VoidOreMagnetBlock;
import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VoidOreMagnetBlockEntity extends BlockEntity {
    private int activeTicks = 0;
    private static final int ACTIVE_DURATION = 100;
    private static final int BREAK_INTERVAL = 10;
    private static final int RADIUS = 5;

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt("activeTicks", activeTicks);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        activeTicks = tag.getInt("activeTicks");
    }

    public VoidOreMagnetBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_ORE_MAGNET_BE.get(), pos, state);
    }

    public void activate() {
        this.activeTicks = ACTIVE_DURATION;
        setActiveState(true);
    }

    public static void tick(Level level, BlockPos pos, BlockState state,
                            VoidOreMagnetBlockEntity be) {

        if (level.isClientSide)  {
            if (state.getValue(VoidOreMagnetBlock.ACTIVE)) {
                be.spawnActiveParticles(level, pos);
            }
            return;
        }
        if (be.activeTicks > 0) {
            if (be.activeTicks % BREAK_INTERVAL == 0) {
                be.breakRandomOres((ServerLevel) level, be);
            }

            be.activeTicks--;

            if (be.activeTicks <= 0) {
                be.setActiveState(false);
            }
        }
    }

    private void setActiveState(boolean active) {
        if (level != null) {
            level.setBlock(worldPosition,
                    getBlockState().setValue(VoidOreMagnetBlock.ACTIVE, active),
                    3);
        }
    }

    private void breakRandomOres(ServerLevel level, VoidOreMagnetBlockEntity be) {

        List<BlockPos> ores = BlockPos.betweenClosedStream(
                        worldPosition.offset(-RADIUS, -RADIUS, -RADIUS),
                        worldPosition.offset(RADIUS, RADIUS, RADIUS)
                )
                .map(BlockPos::immutable)
                .filter(pos -> !pos.equals(worldPosition))
                .filter(pos -> level.getBlockState(pos).is(BlockTags.create(ResourceLocation.fromNamespaceAndPath("c","ores"))))
                .toList();

        if (ores.isEmpty()){
            if(be.activeTicks<=80) {
                be.activeTicks = 0;
            }
            return;
        }

        List<BlockPos> mutableList = new ArrayList<>(ores);
        Collections.shuffle(mutableList);

        int breaks = Math.min(3, mutableList.size());

        for (int i = 0; i < breaks; i++) {

            BlockPos targetPos = mutableList.get(i);
            BlockState targetState = level.getBlockState(targetPos);

            List<ItemStack> drops = Block.getDrops(
                    targetState,
                    level,
                    targetPos,
                    level.getBlockEntity(targetPos)
            );

            level.removeBlock(targetPos, false);
            BlockPos dropPos = worldPosition.above();

            for (ItemStack stack : drops) {
                Block.popResource(level, dropPos, stack);
                level.playSound(null, dropPos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8f, level.random.nextFloat()+0.5f);
            }

            level.levelEvent(2001, targetPos, Block.getId(targetState));
            level.playSound(null, targetPos, targetState.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }
    private void spawnActiveParticles(Level level, BlockPos pos) {

        double centerX = pos.getX() + 0.5;
        double topY = pos.getY() + 0.15;
        double centerZ = pos.getZ() + 0.5;

        for (int i = 0; i < 4; i++) {

            double offsetX = (level.random.nextDouble() - 0.5) * 0.6;
            double offsetZ = (level.random.nextDouble() - 0.5) * 0.6;

            level.addParticle(
                    ParticleTypes.PORTAL,
                    centerX + offsetX,
                    topY,
                    centerZ + offsetZ,
                    0.0,
                    0.05,
                    0.0
            );
        }
    }
}
