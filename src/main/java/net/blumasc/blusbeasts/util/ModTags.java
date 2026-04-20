package net.blumasc.blusbeasts.util;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.storage.loot.LootTable;

public class ModTags {
    public static class Blocks{
        public static final TagKey<Block> PACKWING_SPAWNABLE = createTag("packwing_spawnable");
        public static final TagKey<Block> AMETHYST_CRAB_SPAWNABLE = createTag("amethyst_crab_spawnable");
        public static final TagKey<Block> SALAMANDER_SCRATCHER = createTag("salamander_scratcher");
        public static final TagKey<Block> MUSHROOM = createTag("toad_mushrooms");
        public static final TagKey<Block> MYCELIUM_TOAD_SPAWNABLE = createTag("mycelium_toad_spawnable");
        public static final TagKey<Block> NETHER_LEACH_SPAWNABLE = createTag("nether_leach_spawnable");
        public static final TagKey<Block> ROOTLING_PLANTS = createTag("rootling_plants");
        public static final TagKey<Block> END_WYVERN_HATCHABLE = createTag("end_wyvern_hatchable");
        public static final TagKey<Block> BREAKABLE_GEODES = createTag("breakable_geodes");
        public static final TagKey<Block> BURRY_SPAWNABLE = createTag("burry_spawnable");
        public static final TagKey<Block> RED_SAND_BLOCKS = createTag("red_sand_blocks");
        public static final TagKey<Block> SAND_BLOCKS = createTag("sand_blocks");

        private static TagKey<Block> createTag(String name){
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name));
        }

    }
    public static class Items{
        public static final TagKey<Item> PACKWING_TAMING_ITEM = createTag("packwing_taming_item");
        public static final TagKey<Item> PACKWING_BREEDING_ITEM = createTag("packwing_breeding_item");
        public static final TagKey<Item> SALAMANDER_FOOD = createTag("salamander_food");
        public static final TagKey<Item> TOAD_FOOD = createTag("toad_food");
        public static final TagKey<Item> PRAYFINDER_FOOD = createTag("prayfinder_food");
        public static final TagKey<Item> PRAYFINDER_LIKED_ATTIRE = createTag("prayfinder_liked_attire");
        public static final TagKey<Item> END_WYVERN_FOOD = createTag("end_wyvern_food");
        public static final TagKey<Item> PLATE = createTag("plate");

        private static TagKey<Item> createTag(String name){
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name));
        }
    }
    public static class EntityTypes{
        public static final TagKey<EntityType<?>> PRAYFINDER_FRIENDS = createTag("prayfinder_friends");
        public static final TagKey<EntityType<?>> IGNORES_PHEROMONES = createTag("ignores_pheromones");
        public static final TagKey<EntityType<?>> INFESTATION_MOBS = createTag("infestation_mobs");
        public static final TagKey<EntityType<?>> LIGHTNING_TARGETS = createTag("lightning_targets");
        private static TagKey<EntityType<?>> createTag(String name){
            return TagKey.create(
                    Registries.ENTITY_TYPE,
                    ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name)
            );
        }
    }
    public static class Biomes{
        public static final TagKey<Biome> PACKWING_SPAWNABLE = createTag("packwing_spawnable");
        public static final TagKey<Biome> SALAMANDER_SPAWNABLE = createTag("salamander_spawnable");
        public static final TagKey<Biome> END_SQUID_SPAWNABLE = createTag("end_squid_spawnable");
        public static final TagKey<Biome> MYCELIUM_TOAD_SPAWNABLE = createTag("mycelium_toad_spawnable");
        public static final TagKey<Biome> MYCELIUM_TOAD_SPAWNABLE_RARE = createTag("mycelium_toad_spawnable_rare");
        public static final TagKey<Biome> LIVING_LIGHTNING_SPAWNABLE = createTag("living_lightning_spawnable");
        public static final TagKey<Biome> NETHER_LEACH_SPAWNABLE = createTag("nether_leach_spawnable");
        public static final TagKey<Biome> WYVERN_NEST_BIOMES = createTag("wyvern_nest_biomes");
        public static final TagKey<Biome> BURRY_SPAWNABLE = createTag("burry_spawnable");
        public static final TagKey<Biome> RED_BURRY_BIOMES = createTag("red_burry_biomes");
        public static final TagKey<Biome> DEEPSHOVELER_BIOMES = createTag("deepshoveler_biomes");
        public static final TagKey<Biome> MINERS_SNACK_BIOMES = createTag("miners_snack_biomes");
        public static final TagKey<Biome> BLUEBELLS_BIOMES = createTag("bluebells_biomes");
        private static TagKey<Biome> createTag(String name){
            return TagKey.create(
                    Registries.BIOME,
                    ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name)
            );
        }
    }
    public static class Structures{
        public static final TagKey<Structure> MIMICART_SPAWNABLE = createTag("mimicart_spawnable");
        private static TagKey<Structure> createTag(String name){
            return TagKey.create(
                    Registries.STRUCTURE,
                    ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, name)
            );
        }
    }
}