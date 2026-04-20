package net.blumasc.blusbeasts.block.custom.blockentity.custom;

import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.DreamPixie;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class PlateBlockEntity extends BlockEntity {

    public final ItemStackHandler inventory = new ItemStackHandler(3){
        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()){
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public boolean genLoot = false;
    private long lastSpawnDay = -1;



    public PlateBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.PLATE_BE.get(), pos, blockState);
    }

    public void clearContents(){
        inventory.setStackInSlot(0, ItemStack.EMPTY);
        inventory.setStackInSlot(1, ItemStack.EMPTY);
        inventory.setStackInSlot(2, ItemStack.EMPTY);
    }

    public void clearFoodContents(){
        if(inventory.getStackInSlot(0).has(DataComponents.FOOD)) {
            inventory.setStackInSlot(0, ItemStack.EMPTY);
        }
        if(inventory.getStackInSlot(1).has(DataComponents.FOOD)) {
            inventory.setStackInSlot(1, ItemStack.EMPTY);
        }
        if(inventory.getStackInSlot(2).has(DataComponents.FOOD)) {
            inventory.setStackInSlot(2, ItemStack.EMPTY);
        }
    }

    public void drops(){
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for(int i =0; i<inventory.getSlots(); i++){
            inv.setItem(i, inventory.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState){
        if (level.isClientSide()) return;
        if (level.getGameTime() % 10 != 0) return;
        long currentDay = level.getGameTime() / 24000L;
        if (currentDay == lastSpawnDay) return;
        boolean hasFood = false;
        for (int i = 0; i < inventory.getSlots(); i++) {
            if (!inventory.getStackInSlot(i).isEmpty() && inventory.getStackInSlot(i).has(DataComponents.FOOD)) {
                hasFood = true;
                break;
            }
        }
        if (!hasFood) return;
        java.util.List<net.minecraft.world.entity.LivingEntity> nearby = level.getEntitiesOfClass(
                net.minecraft.world.entity.LivingEntity.class,
                new net.minecraft.world.phys.AABB(blockPos).inflate(8),
                entity -> {
                    if (entity instanceof net.minecraft.world.entity.player.Player player) {
                        return !player.isCreative() && !player.isSpectator();
                    }
                    return true;
                }
        );
        if (nearby.isEmpty()) return;
        boolean allSleeping = nearby.stream().allMatch(net.minecraft.world.entity.LivingEntity::isSleeping);
        if (!allSleeping) return;

        double x = blockPos.getX() + 0.5 + (level.random.nextDouble() - 0.5) * 6;
        double y = blockPos.getY() + 1 + level.random.nextDouble() * 2;
        double z = blockPos.getZ() + 0.5 + (level.random.nextDouble() - 0.5) * 6;

        DreamPixie pixie =
                ModEntities.DREAM_PIXIE.get().create(level);

        if (pixie != null) {
            pixie.moveTo(x, y, z, level.random.nextFloat() * 360, 0);
            level.addFreshEntity(pixie);
            level.playSound(null, blockPos, ModSounds.SPARKLE_ALL.get(), SoundSource.PLAYERS, 0.7f, 0.8f+level.random.nextFloat()*0.4f);
            lastSpawnDay = currentDay;
        }
    }

}
