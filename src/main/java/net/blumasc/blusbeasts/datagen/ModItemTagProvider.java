package net.blumasc.blusbeasts.datagen;

import net.blumasc.blubasics.item.BaseModItems;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.PACKWING_TAMING_ITEM)
                .add(Items.GLOW_BERRIES)
                .add(Items.GLOW_INK_SAC);

        tag(ModTags.Items.PACKWING_BREEDING_ITEM)
                .add(Items.SWEET_BERRIES)
                .add(Items.MELON_SLICE)
                .add(Items.APPLE);

        tag(ModTags.Items.SALAMANDER_FOOD)
                .add(Items.FIRE_CHARGE)
                .add(Items.MAGMA_BLOCK)
                .add(Items.BLAZE_POWDER)
                .add(Items.BLAZE_ROD)
                .add(Items.MAGMA_CREAM);

        tag(ModTags.Items.TOAD_FOOD)
                .addTag(ItemTags.WOLF_FOOD);

        tag(ModTags.Items.PRAYFINDER_FOOD)
                .add(Items.ROTTEN_FLESH)
                .add(Items.SPIDER_EYE)
                .add(Items.FERMENTED_SPIDER_EYE)
                .add(Items.EGG)
                .addTag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","eggs")))
                .add(Items.BONE_MEAL)
                .add(Items.NETHER_WART);

        tag(ModTags.Items.PRAYFINDER_LIKED_ATTIRE)
                .addTag(ItemTags.SKULLS)
                .add(ModItems.GOLDEN_ANTLERS.get())
                .add(ModItems.ANTLERS.get())
                .add(Items.CARVED_PUMPKIN);

        tag(ModTags.Items.END_WYVERN_FOOD)
                .add(ModItems.END_TAKOYAKI.get());

        tag(ModTags.Items.PLATE)
                .add(ModBlocks.PLATE.asItem())
                .add(ModBlocks.PLATE_CRYSTAL.asItem())
                .add(ModBlocks.PLATE_DONUT.asItem())
                .add(ModBlocks.PLATE_HORNS.asItem())
                .add(ModBlocks.PLATE_LEACH.asItem())
                .add(ModBlocks.PLATE_LIGHTNING.asItem())
                .add(ModBlocks.PLATE_MIMICART.asItem())
                .add(ModBlocks.PLATE_PHEROMONES.asItem())
                .add(ModBlocks.PLATE_PIXIE.asItem())
                .add(ModBlocks.PLATE_SAND.asItem())
                .add(ModBlocks.PLATE_SAPLING.asItem())
                .add(ModBlocks.PLATE_SCARAB.asItem())
                .add(ModBlocks.PLATE_SHROOM.asItem())
                .add(ModBlocks.PLATE_SQUID.asItem())
                .add(ModBlocks.PLATE_VOID.asItem())
                .add(ModBlocks.PLATE_WINGS.asItem());

        tag(ItemTags.PICKAXES)
                .add(ModItems.PICKERANG.get());

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.PICKERANG.get());

        tag(ItemTags.MINING_ENCHANTABLE)
                .add(ModItems.PICKERANG.get());

        tag(ItemTags.MINING_LOOT_ENCHANTABLE)
                .add(ModItems.PICKERANG.get());

        tag(ItemTags.ARROWS)
                .add(ModItems.INFESTED_ARROW.get());

        tag(ItemTags.CLUSTER_MAX_HARVESTABLES)
                .add(ModItems.PICKERANG.get());

        tag(ItemTags.STONE_TOOL_MATERIALS)
                .add(ModBlocks.VOID_ORE.asItem());

        tag(ItemTags.WOOL)
                .add(ModBlocks.RAINBOW_WOOL.asItem());

        tag(ItemTags.WOOL_CARPETS)
                .add(ModBlocks.RAINBOW_CARPET.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","feathers")))
                .add(ModItems.PRAYFINDER_FEATHER.get());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","concretes")))
                .add(ModBlocks.RAINBOW_CONCRETE.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","eggs")))
                .add(ModBlocks.END_WYVERN_EGG.asItem());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","tools/mining_tool")))
                .add(ModItems.PICKERANG.get());

        tag(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","rods/wooden")))
                .add(ModItems.FISH_BONE_SKEWER.get());

        tag(CuriosTags.BODY)
                .add(ModItems.EMBEDDED_CRYSTALS.get())
                .add(ModItems.PERSONAL_MINECART.get())
                .replace(false);
        tag(CuriosTags.HANDS)
                .add(ModItems.EMBEDDED_CRYSTALS.get())
                .replace(false);
        tag(CuriosTags.HEAD)
                .add(ModItems.ANTLERS.get())
                .add(ModItems.GOLDEN_ANTLERS.get())
                .replace(false);


    }
}
