package net.blumasc.blusbeasts.block.custom.blockentity;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.FairyFireBlockEntity;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.PlateBlockEntity;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.VoidOreMagnetBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BlusBeastsMod.MODID);
    public static final Supplier<BlockEntityType<VoidOreMagnetBlockEntity>> VOID_ORE_MAGNET_BE=
            BLOCK_ENTITIES.register("void_ore_magnet_be", () -> BlockEntityType.Builder.of(
                    VoidOreMagnetBlockEntity::new, ModBlocks.VOID_ORE_MAGNET.get()
            ).build(null));

    public static final Supplier<BlockEntityType<PlateBlockEntity>> PLATE_BE=
            BLOCK_ENTITIES.register("plate_be", () -> BlockEntityType.Builder.of(
                    PlateBlockEntity::new, ModBlocks.PLATE.get(), ModBlocks.PLATE_CRYSTAL.get(), ModBlocks.PLATE_DONUT.get(), ModBlocks.PLATE_HORNS.get(), ModBlocks.PLATE_LIGHTNING.get(), ModBlocks.PLATE_LEACH.get(), ModBlocks.PLATE_MIMICART.get(), ModBlocks.PLATE_PHEROMONES.get(), ModBlocks.PLATE_PIXIE.get(), ModBlocks.PLATE_SAND.get(), ModBlocks.PLATE_SAPLING.get(), ModBlocks.PLATE_SCARAB.get(), ModBlocks.PLATE_SHROOM.get(), ModBlocks.PLATE_SQUID.get(), ModBlocks.PLATE_VOID.get(), ModBlocks.PLATE_WINGS.get()
            ).build(null));

    public static final Supplier<BlockEntityType<FairyFireBlockEntity>> FAIRY_FIRE_BE=
            BLOCK_ENTITIES.register("fairy_fire_be", () -> BlockEntityType.Builder.of(
                    FairyFireBlockEntity::new, ModBlocks.FAIRY_FIRE_BLOCK.get()
            ).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }
}
