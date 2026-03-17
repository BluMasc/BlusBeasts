package net.blumasc.blusbeasts.worldgen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> SPAWN_PACKWING = registerKey("spawn_packwing");
    public static final ResourceKey<BiomeModifier> SPAWN_SALAMANDER = registerKey("spawn_salamander");
    public static final ResourceKey<BiomeModifier> SPAWN_END_SQUID = registerKey("spawn_end_squid");
    public static final ResourceKey<BiomeModifier> SPAWN_MYCELIUM_TOAD = registerKey("spawn_mycelium_toad");
    public static final ResourceKey<BiomeModifier> SPAWN_MYCELIUM_TOAD_RARE = registerKey("spawn_mycelium_toad_rare");
    public static final ResourceKey<BiomeModifier> SPAWN_LIVING_LIGHTNING = registerKey("spawn_living_lightning");
    public static final ResourceKey<BiomeModifier> SPAWN_NETHER_LEACH = registerKey("spawn_nether_leach");
    public static final ResourceKey<BiomeModifier> SPAWN_BURRY = registerKey("spawn_burry");
    public static final ResourceKey<BiomeModifier> SPAWN_DEEPSHOVELER = registerKey("spawn_deepshoveler");
    public static final ResourceKey<BiomeModifier> SPAWN_MINERS_SNACK = registerKey("spawn_miners_snack");
    public static final ResourceKey<BiomeModifier> ADD_BLUEBELLS = registerKey("add_bluebells");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        context.register(SPAWN_PACKWING, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.PACKWING_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.PACKWING.get(), 20, 1, 1))
        ));

        context.register(SPAWN_SALAMANDER, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.SALAMANDER_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.SALAMANDER.get(), 20, 1, 1))
        ));

        context.register(SPAWN_END_SQUID, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.END_SQUID_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.END_SQUID.get(), 20, 4, 6))
        ));

        context.register(SPAWN_MYCELIUM_TOAD, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.MYCELIUM_TOAD_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.MYCELIUM_TOAD.get(), 20, 1, 3))
        ));

        context.register(SPAWN_MYCELIUM_TOAD_RARE, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.MYCELIUM_TOAD_SPAWNABLE_RARE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.MYCELIUM_TOAD.get(), 3, 1, 1))
        ));

        context.register(SPAWN_LIVING_LIGHTNING, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.LIVING_LIGHTNING_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.LIVING_LIGHTNING.get(), 40, 1, 1))
        ));

        context.register(SPAWN_NETHER_LEACH, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.NETHER_LEACH_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.NETHER_LEACH.get(), 10, 2, 5))
        ));

        context.register(SPAWN_BURRY, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.BURRY_SPAWNABLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BURRY.get(), 50, 1, 3))
        ));

        context.register(SPAWN_DEEPSHOVELER, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.DEEPSHOVELER_BIOMES),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.DEEPSHOVELER.get(), 30, 1, 1))
        ));

        context.register(SPAWN_MINERS_SNACK, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.MINERS_SNACK_BIOMES),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.MINER_SNACK.get(), 20, 3, 6))
        ));

        context.register(ADD_BLUEBELLS, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ModTags.Biomes.BLUEBELLS_BIOMES),
                HolderSet.direct(placedFeatures.getOrThrow(ModPlacedFeatures.BLUEBELLS_PLACE_KEY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name));
    }
}
