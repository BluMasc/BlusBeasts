package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends BiomeTagsProvider {
    public ModBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Biomes.PACKWING_SPAWNABLE)
                .addTag(BiomeTags.HAS_END_CITY)
                .add(Biomes.LUSH_CAVES)
                .addTag(BiomeTags.IS_TAIGA)
                .add(Biomes.SWAMP)
                .addTag(BiomeTags.IS_JUNGLE)
                .add(Biomes.WARPED_FOREST)
                .add(Biomes.CRIMSON_FOREST);
        tag(ModTags.Biomes.SALAMANDER_SPAWNABLE)
                .addTag(BiomeTags.IS_MOUNTAIN)
                .addTag(BiomeTags.HAS_DESERT_PYRAMID)
                .addTag(BiomeTags.IS_SAVANNA);
        tag(ModTags.Biomes.END_SQUID_SPAWNABLE)
                .add(Biomes.SMALL_END_ISLANDS)
                .add(Biomes.END_BARRENS);
        tag(ModTags.Biomes.LIVING_LIGHTNING_SPAWNABLE)
                .addTag(BiomeTags.IS_OVERWORLD);
        tag(ModTags.Biomes.MYCELIUM_TOAD_SPAWNABLE)
                .add(Biomes.MUSHROOM_FIELDS);
        tag(ModTags.Biomes.MYCELIUM_TOAD_SPAWNABLE_RARE)
                .addTag(BiomeTags.HAS_SWAMP_HUT)
                .add(Biomes.MANGROVE_SWAMP)
                .add(Biomes.SWAMP)
                .add(Biomes.SPARSE_JUNGLE)
                .add(Biomes.LUSH_CAVES);
        tag(ModTags.Biomes.NETHER_LEACH_SPAWNABLE)
                .add(Biomes.CRIMSON_FOREST);
        tag(ModTags.Biomes.WYVERN_NEST_BIOMES)
                .add(Biomes.SMALL_END_ISLANDS)
                .add(Biomes.END_MIDLANDS)
                .add(Biomes.END_HIGHLANDS)
                .add(Biomes.END_BARRENS);
        tag(ModTags.Biomes.BURRY_SPAWNABLE)
                .addTag(BiomeTags.HAS_DESERT_PYRAMID)
                .addTag(BiomeTags.IS_BADLANDS)
                .addTag(BiomeTags.IS_SAVANNA);
        tag(ModTags.Biomes.RED_BURRY_BIOMES)
                .addTag(BiomeTags.IS_BADLANDS);
        tag(ModTags.Biomes.DEEPSHOVELER_BIOMES)
                .addTag(BiomeTags.IS_OVERWORLD);
        tag(ModTags.Biomes.MINERS_SNACK_BIOMES)
                .addTag(BiomeTags.IS_OVERWORLD);
        tag(ModTags.Biomes.BLUEBELLS_BIOMES)
                .addTag(BiomeTags.IS_FOREST)
                .add(Biomes.PLAINS)
                .add(Biomes.SWAMP);
    }
}
