package net.blumasc.blusbeasts.worldgen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.neoforge.common.world.StructureModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModStructureModifiers {
    public static final ResourceKey<StructureModifier> SPAWN_MIMICART = registerKey("spawn_mimicart");

    public static void bootstrap(BootstrapContext<StructureModifier> context) {
        var structures = context.lookup(Registries.STRUCTURE);
        context.register(SPAWN_MIMICART,
                new StructureModifiers.AddSpawnsStructureModifier(
                        structures.getOrThrow(ModTags.Structures.MIMICART_SPAWNABLE),
                        List.of(new MobSpawnSettings.SpawnerData(ModEntities.MIMICART.get(), 50, 1, 5))));
    }

    private static ResourceKey<StructureModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIERS, ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name));
    }
}
