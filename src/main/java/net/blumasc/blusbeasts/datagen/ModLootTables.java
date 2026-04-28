package net.blumasc.blusbeasts.datagen;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LocationCheck;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModLootTables implements LootTableSubProvider {

    public static final ResourceKey<LootTable> MYCELIUM_TOAD_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "mycelium_toad_loot"));
    public static final ResourceKey<LootTable> PIXIE_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "pixie_loot"));



    public ModLootTables(HolderLookup.Provider provider) {

    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> consumer) {
        consumer.accept(
               MYCELIUM_TOAD_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0F, 1.0F))
                                .add(LootItem.lootTableItem(Items.BROWN_MUSHROOM))
                                .add(LootItem.lootTableItem(Items.RED_MUSHROOM))
                                .add(LootItem.lootTableItem(Items.CRIMSON_FUNGUS))
                                .add(LootItem.lootTableItem(Items.WARPED_FUNGUS))
                                .add(LootItem.lootTableItem(BaseModBlocks.JUMP_MUSHROOM))
                                .add(LootItem.lootTableItem(BaseModBlocks.SPORE_MUSHROOM_BLOCK))
                        )
        );
        consumer.accept(
                PIXIE_LOOT_TABLE,
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0F, 3.0F))
                                .add(LootItem.lootTableItem(ModItems.FAIRY_DUST).setWeight(50))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.SIMPLE_DUNGEON)
                                        .setWeight(70))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.STRONGHOLD_CORRIDOR)
                                        .setWeight(10)
                                .when(LocationCheck.checkLocation(
                                        LocationPredicate.Builder.location()
                                                .setDimension(Level.OVERWORLD)
                                )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.STRONGHOLD_CROSSING)
                                        .setWeight(10)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.OVERWORLD)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.STRONGHOLD_LIBRARY)
                                        .setWeight(5)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.OVERWORLD)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.ABANDONED_MINESHAFT)
                                        .setWeight(15)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.OVERWORLD)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.BURIED_TREASURE)
                                        .setWeight(15)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.OVERWORLD)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.DESERT_PYRAMID)
                                        .setWeight(8)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.OVERWORLD)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.JUNGLE_TEMPLE)
                                        .setWeight(7)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.OVERWORLD)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.BASTION_HOGLIN_STABLE)
                                        .setWeight(10)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.NETHER)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.BASTION_BRIDGE)
                                        .setWeight(10)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.NETHER)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.BASTION_OTHER)
                                        .setWeight(10)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.NETHER)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.BASTION_TREASURE)
                                        .setWeight(5)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.NETHER)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.NETHER_BRIDGE)
                                        .setWeight(35)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.NETHER)
                                        )))
                                .add(NestedLootTable.lootTableReference(BuiltInLootTables.END_CITY_TREASURE)
                                        .setWeight(35)
                                        .when(LocationCheck.checkLocation(
                                                LocationPredicate.Builder.location()
                                                        .setDimension(Level.END)
                                        )))
                        )
        );
    }
}