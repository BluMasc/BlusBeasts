package net.blumasc.blusbeasts.datagen;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public class ModLootTables implements LootTableSubProvider {

    public static final ResourceKey<LootTable> MYCELIUM_TOAD_LOOT_TABLE = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "mycelium_toad_loot"));



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
    }
}