package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.INFESTED_NETHERRACK.get(),
                block -> createSilkTouchOnlyTable(ModBlocks.INFESTED_NETHERRACK.get()));
        add(ModBlocks.END_WYVERN_EGG.get(),
                block -> createSilkTouchOnlyTable(ModBlocks.END_WYVERN_EGG.get()));
        dropSelf(ModBlocks.END_WYVERN_NEST.get());
        dropSelf(ModBlocks.VOID_ORE.get());
        dropSelf(ModBlocks.VOID_ORE_MAGNET.get());
        dropSelf(ModBlocks.PLATE.get());
        dropSelf(ModBlocks.PLATE_CRYSTAL.get());
        dropSelf(ModBlocks.PLATE_DONUT.get());
        dropSelf(ModBlocks.PLATE_HORNS.get());
        dropSelf(ModBlocks.PLATE_LEACH.get());
        dropSelf(ModBlocks.PLATE_LIGHTNING.get());
        dropSelf(ModBlocks.PLATE_MIMICART.get());
        dropSelf(ModBlocks.PLATE_PHEROMONES.get());
        dropSelf(ModBlocks.PLATE_PIXIE.get());
        dropSelf(ModBlocks.PLATE_SAND.get());
        dropSelf(ModBlocks.PLATE_SAPLING.get());
        dropSelf(ModBlocks.PLATE_SCARAB.get());
        dropSelf(ModBlocks.PLATE_SHROOM.get());
        dropSelf(ModBlocks.PLATE_SQUID.get());
        dropSelf(ModBlocks.PLATE_VOID.get());
        dropSelf(ModBlocks.PLATE_WINGS.get());
        dropSelf(ModBlocks.BLUEBELLS.get());
        dropSelf(ModBlocks.RAINBOW_WOOL.get());
        dropSelf(ModBlocks.RAINBOW_CARPET.get());
        dropSelf(ModBlocks.RAINBOW_CONCRETE.get());
        dropOther(ModBlocks.PIXIE_TORCH_BLOCK.get(), ModBlocks.PIXIE_TORCH.get());
        dropOther(ModBlocks.WALL_PIXIE_TORCH.get(), ModBlocks.PIXIE_TORCH.get());
        dropOther(ModBlocks.FAIRY_FIRE_BLOCK.get(), ModItems.FAIRY_DUST.get());
        add(ModBlocks.POTTED_BLUEBELLS.get(),
                block -> {
                    return LootTable.lootTable().withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(
                                    LootItem.lootTableItem(ModBlocks.BLUEBELLS)
                            )).withPool(LootPool.lootPool()
                            .setRolls(ConstantValue.exactly(1))
                            .add(
                                    LootItem.lootTableItem(Blocks.FLOWER_POT)
                            ));
                });
        add(ModBlocks.GRAVE_SANDSTONE.get(),
                block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(this.hasSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(block)))
                        .withPool(LootPool.lootPool()
                                .when(this.doesNotHaveSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.GRAVE_GEM.get())
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                                this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                                                0.3F,
                                                0.5F,
                                                0.7F,
                                                0.9F
                                        ))))
                        .withPool(LootPool.lootPool()
                                .when(this.doesNotHaveSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Blocks.SMOOTH_SANDSTONE))
                        )
        );
        add(ModBlocks.GRAVE_RED_SANDSTONE.get(),
                block -> LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(this.hasSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(block)))
                        .withPool(LootPool.lootPool()
                                .when(this.doesNotHaveSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.GRAVE_GEM.get())
                                        .when(BonusLevelTableCondition.bonusLevelFlatChance(
                                                this.registries.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE),
                                                0.3F,
                                                0.5F,
                                                0.7F,
                                                0.9F
                                        ))))
                        .withPool(LootPool.lootPool()
                                .when(this.doesNotHaveSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(Blocks.SMOOTH_RED_SANDSTONE))
                        )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
