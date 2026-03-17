package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.InvertedLootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class ModEntityLoot extends EntityLootSubProvider {
    protected ModEntityLoot(HolderLookup.Provider registries) {
        super(FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    public void generate() {
        HolderLookup.RegistryLookup<Enchantment> registrylookup =
                this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        add(ModEntities.PRAYFINDER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(ModItems.PRAYFINDER_FEATHER)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(1.0F, 3.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
        );

        add(ModEntities.MINER_SNACK.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(ModItems.FISH_BONE_SKEWER)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
        );

        add(ModEntities.MIMICART.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.IRON_INGOT)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(1.0F, 3.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ModItems.LIVING_WHEELS.get()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                        this.registries,
                                        0.5F,
                                        0.1F
                                )
                                ))
                        )
        );

        add(ModEntities.BURRY.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ModItems.BURROW_ROD).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries,
                                                0.7F,
                                                0.1F
                                        )
                                ))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0F, 3.0F))
                                .add(LootItem.lootTableItem(ModItems.GRAVITY_DUST.get()))
                                .when(InvertedLootItemCondition.invert(LootItemKilledByPlayerCondition.killedByPlayer()))
                        )
                        .withPool(LootPool.lootPool()
                                .add(LootItem.lootTableItem(ModItems.BURROW_GEM.get()).when(LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(
                                                this.registries,
                                                0.01F,
                                                0.03F
                                        )
                                ))
                                .when(LootItemKilledByPlayerCondition.killedByPlayer())
                        )
        );
        add(ModEntities.GRAVE.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.SCARAB.get()))
                        )
        );

        add(ModEntities.DEEPSHOVELER.get(),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .when(LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .nbt(new NbtPredicate(createVariantTag(0)))
                                ))
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.COBBLESTONE)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(1.0F, 3.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
                        .withPool(LootPool.lootPool()
                                .when(LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .nbt(new NbtPredicate(createVariantTag(1)))
                                ))
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.RAW_IRON)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(1.0F, 3.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
                        .withPool(LootPool.lootPool()
                                .when(LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .nbt(new NbtPredicate(createVariantTag(2)))
                                ))
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.RAW_GOLD)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(1.0F, 2.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
                        .withPool(LootPool.lootPool()
                                .when(LootItemEntityPropertyCondition.hasProperties(
                                        LootContext.EntityTarget.THIS,
                                        EntityPredicate.Builder.entity()
                                                .nbt(new NbtPredicate(createVariantTag(3)))
                                ))
                                .setRolls(ConstantValue.exactly(1))
                                .add(
                                        LootItem.lootTableItem(Items.DIAMOND)
                                                .apply(SetItemCountFunction.setCount(
                                                        UniformGenerator.between(1.0F, 1.0F)
                                                ))
                                                .apply(EnchantedCountIncreaseFunction.lootingMultiplier(
                                                        this.registries,
                                                        UniformGenerator.between(0.0F, 1.0F)
                                                ))
                                )
                        )
        );
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                ModEntities.PRAYFINDER.get(),
                ModEntities.MIMICART.get(),
                ModEntities.BURRY.get(),
                ModEntities.GRAVE.get(),
                ModEntities.DEEPSHOVELER.get(),
                ModEntities.MINER_SNACK.get()
        );
    }

    private static CompoundTag createVariantTag(int variantId) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Variant", variantId);
        return tag;
    }

}