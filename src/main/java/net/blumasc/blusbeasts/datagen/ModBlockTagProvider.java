package net.blumasc.blusbeasts.datagen;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.PACKWING_SPAWNABLE)
                .addTag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .addTag(BlockTags.AZALEA_GROWS_ON)
                .addTag(BlockTags.CONVERTABLE_TO_MUD)
                .addTag(BlockTags.DIRT)
                .addTag(BlockTags.GOATS_SPAWNABLE_ON)
                .addTag(BlockTags.LEAVES)
                .add(Blocks.STONE)
                .add(Blocks.END_STONE)
                .add(Blocks.WARPED_WART_BLOCK)
                .add(Blocks.NETHER_WART_BLOCK)
                .add(Blocks.AZALEA)
                .add(Blocks.FLOWERING_AZALEA)
                .add(Blocks.DEEPSLATE)
                .add(Blocks.CLAY)
                .add(Blocks.WARPED_NYLIUM)
                .add(Blocks.CRIMSON_NYLIUM);
        tag(ModTags.Blocks.NETHER_LEACH_SPAWNABLE)
                .add(Blocks.CRIMSON_NYLIUM);
        tag(ModTags.Blocks.AMETHYST_CRAB_SPAWNABLE)
                .add(Blocks.AMETHYST_BLOCK)
                .add(Blocks.BUDDING_AMETHYST)
                .add(Blocks.CALCITE)
                .add(Blocks.SMOOTH_BASALT);
        tag(ModTags.Blocks.SALAMANDER_SCRATCHER)
                .add(Blocks.CACTUS)
                .add(Blocks.POINTED_DRIPSTONE)
                .addTag(BlockTags.OVERWORLD_NATURAL_LOGS);
        tag(ModTags.Blocks.MUSHROOM)
                .add(Blocks.RED_MUSHROOM)
                .add(Blocks.BROWN_MUSHROOM)
                .add(Blocks.WARPED_FUNGUS)
                .add(Blocks.CRIMSON_FUNGUS)
                .add(BaseModBlocks.SPORE_MUSHROOM_BLOCK.get())
                .add(BaseModBlocks.JUMP_MUSHROOM.get());
        tag(ModTags.Blocks.MYCELIUM_TOAD_SPAWNABLE)
                .addTag(BlockTags.ANIMALS_SPAWNABLE_ON)
                .addTag(BlockTags.MOOSHROOMS_SPAWNABLE_ON)
                .addTag(BlockTags.FROGS_SPAWNABLE_ON);
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.INFESTED_NETHERRACK.get())
                .add(ModBlocks.GRAVE_RED_SANDSTONE.get())
                .add(ModBlocks.GRAVE_SANDSTONE.get());

        tag(ModTags.Blocks.ROOTLING_PLANTS)
                .addTag(BlockTags.SAPLINGS)
                .addTag(BlockTags.SMALL_FLOWERS)
                .remove(Blocks.WITHER_ROSE)
                .add(Blocks.MOSS_CARPET);

        tag(ModTags.Blocks.END_WYVERN_HATCHABLE)
                .add(Blocks.HAY_BLOCK)
                .add(ModBlocks.END_WYVERN_NEST.get());

        tag(ModTags.Blocks.BREAKABLE_GEODES)
                .add(Blocks.BUDDING_AMETHYST);

        tag(ModTags.Blocks.BURRY_SPAWNABLE)
                .addTag(BlockTags.SAND)
                .addTag(BlockTags.BADLANDS_TERRACOTTA)
                .add(Blocks.SANDSTONE)
                .add(Blocks.RED_SANDSTONE)
                .add(Blocks.COARSE_DIRT);

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.INFESTED_NETHERRACK.get())
                .add(ModBlocks.VOID_ORE_MAGNET.get())
                .add(ModBlocks.RAINBOW_CONCRETE.get());
        tag(BlockTags.FLOWERS)
                .add(ModBlocks.BLUEBELLS.get());
        tag(BlockTags.SMALL_FLOWERS)
                .add(ModBlocks.BLUEBELLS.get());
        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_BLUEBELLS.get());

        tag(ModTags.Blocks.RED_SAND_BLOCKS)
                .add(ModBlocks.GRAVE_RED_SANDSTONE.get())
                .add(Blocks.RED_SANDSTONE)
                .add(Blocks.CHISELED_RED_SANDSTONE)
                .add(Blocks.SMOOTH_RED_SANDSTONE)
                .add(Blocks.RED_SAND)
                .add(Blocks.CUT_RED_SANDSTONE);

        tag(ModTags.Blocks.SAND_BLOCKS)
                .add(ModBlocks.GRAVE_SANDSTONE.get())
                .add(Blocks.SANDSTONE)
                .add(Blocks.CHISELED_SANDSTONE)
                .add(Blocks.SMOOTH_SANDSTONE)
                .add(Blocks.SAND)
                .add(Blocks.CUT_SANDSTONE);

        tag(BlockTags.WOOL)
                .add(ModBlocks.RAINBOW_WOOL.get());

        tag(BlockTags.WOOL_CARPETS)
                .add(ModBlocks.RAINBOW_CARPET.get());

        tag(BlockTags.WALL_POST_OVERRIDE)
                .add(ModBlocks.PIXIE_TORCH_BLOCK.get())
                .add(ModBlocks.WALL_PIXIE_TORCH.get());

        tag(BlockTags.create(ResourceLocation.fromNamespaceAndPath("c","sandstone/blocks")))
                .add(ModBlocks.GRAVE_SANDSTONE.get())
                .add(ModBlocks.GRAVE_RED_SANDSTONE.get());
    }
}
