package net.blumasc.blusbeasts.block;

import net.blumasc.blubasics.effect.BaseModEffects;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.custom.*;
import net.blumasc.blusbeasts.block.custom.InfestedBlock;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {

    static {
        ModParticles.class.getName();
    }
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(BlusBeastsMod.MODID);

    public static final DeferredBlock<InfestedBlock> INFESTED_NETHERRACK = registerBlock("infested_netherrack",
            () -> new InfestedBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERRACK)));

    public static final DeferredBlock<EndWyvernEggBlock> END_WYVERN_EGG = registerBlock("end_wyvern_egg",
            () -> new EndWyvernEggBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.SNIFFER_EGG)));

    public static final DeferredBlock<Block> END_WYVERN_NEST = registerBlock("end_wyvern_nest",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

    public static final DeferredBlock<Block> VOID_ORE = registerBlock("void_ore",
            () -> new VoidOre(BlockBehaviour.Properties.of().destroyTime(0.2f).sound(SoundType.DEEPSLATE).instabreak().randomTicks()));

    public static final DeferredBlock<Block> VOID_ORE_MAGNET = registerBlock("void_ore_magnet",
            () -> new VoidOreMagnetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE).lightLevel((be)-> be.getValue(VoidOreMagnetBlock.ACTIVE)?5:2)));

    public static final DeferredBlock<Block> PLATE = registerBlock("plate",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_CRYSTAL = registerBlock("plate_crystal",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_DONUT = registerBlock("plate_donut",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_HORNS = registerBlock("plate_horns",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_LEACH = registerBlock("plate_leach",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_LIGHTNING = registerBlock("plate_lightning",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_MIMICART = registerBlock("plate_mimicart",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_PHEROMONES = registerBlock("plate_pheromones",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_PIXIE = registerBlock("plate_pixie",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_SAND = registerBlock("plate_sand",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_SAPLING = registerBlock("plate_sapling",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_SCARAB = registerBlock("plate_scarab",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_SHROOM = registerBlock("plate_shroom",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_SQUID = registerBlock("plate_squid",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_VOID = registerBlock("plate_void",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<Block> PLATE_WINGS = registerBlock("plate_wings",
            () -> new PlateBlock(BlockBehaviour.Properties.of().strength(0.1f).sound(SoundType.CALCITE).noOcclusion().mapColor(MapColor.COLOR_LIGHT_GRAY)));

    public static final DeferredBlock<BluebellsFlower> BLUEBELLS = registerBlock("bluebells",
            () -> new BluebellsFlower(BaseModEffects.HALLUCINATION, 15, BlockBehaviour.Properties.ofFullCopy(Blocks.LILY_OF_THE_VALLEY).randomTicks()));

    public static final DeferredBlock<FlowerPotBlock> POTTED_BLUEBELLS =
            BLOCKS.register("potted_bluebells", () -> new FlowerPotBlock(
                    BLUEBELLS.get(),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.POTTED_LILY_OF_THE_VALLEY)
            ));

    public static final DeferredBlock<Block> GRAVE_RED_SANDSTONE = registerBlock("grave_red_sandstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_RED_SANDSTONE)));

    public static final DeferredBlock<Block> GRAVE_SANDSTONE = registerBlock("grave_sandstone",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.SMOOTH_SANDSTONE)));

    public static final DeferredBlock<TorchBlock> PIXIE_TORCH_BLOCK = BLOCKS.register("pixie_torch",
            () -> new PixieTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH)));

    public static final DeferredBlock<WallPixieTorchBlock> WALL_PIXIE_TORCH = BLOCKS.register("wall_pixie_torch",
            () -> new WallPixieTorchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WALL_TORCH)));

    public static final DeferredItem<BlockItem> PIXIE_TORCH = ModItems.ITEMS.register("pixie_torch",
            () -> new StandingAndWallBlockItem(ModBlocks.PIXIE_TORCH_BLOCK.get(), ModBlocks.WALL_PIXIE_TORCH.get(), new Item.Properties(), Direction.DOWN));

    public static final DeferredBlock<Block> RAINBOW_WOOL= registerBlock("rainbow_wool",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)));

    public static final DeferredBlock<CarpetBlock> RAINBOW_CARPET= registerBlock("rainbow_carpet",
            () -> new CarpetBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CARPET)));

    public static final DeferredBlock<Block> RAINBOW_CONCRETE= registerBlock("rainbow_concrete",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_CONCRETE)));

    public static final DeferredBlock<Block> FAIRY_FIRE_BLOCK= registerBlock("fairy_fire_block",
            () -> new FairyFireBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TORCH).sound(SoundType.SMALL_AMETHYST_BUD)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
        FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
        pot.addPlant(
                ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "bluebells"),
                () -> POTTED_BLUEBELLS.get()
        );
    }
}
