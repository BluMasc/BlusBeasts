package net.blumasc.blusbeasts.datagen;

import com.google.gson.JsonObject;
import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blubasics.item.BaseModItems;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.recipe.NetherLeachRecipe;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        oreSmelting(recipeOutput, Collections.singletonList(ModItems.LIVING_WHEELS), RecipeCategory.MISC, Items.RAW_IRON, 0.25f, 200, "livin_wheels_smelting");
        oreSmelting(recipeOutput, Collections.singletonList(ModItems.SALAMANDER_SCALES), RecipeCategory.MISC, Items.NETHERRACK, 0.25f, 200, "salamander_scales_smelting");
        foodSmelting(recipeOutput, Collections.singletonList(ModItems.MINERS_SNACK), RecipeCategory.FOOD, ModItems.COOKED_MINERS_SNACK, 0.25f, 200, "cooking_miners_snack");

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.INFESTED_ARROW,4)
                .pattern("C")
                .pattern("S")
                .pattern("F")
                .define('C', ModBlocks.INFESTED_NETHERRACK)
                .define('S', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","rods/wooden")))
                .define('F', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","feathers")))
                .unlockedBy("has_infested_netherrack", has(ModBlocks.INFESTED_NETHERRACK)).save(recipeOutput);


        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.AMETHYST_SHARD, 2)
                .requires(ModItems.EMBEDDED_CRYSTALS)
                .unlockedBy("has_embedded_crystal", has(ModItems.EMBEDDED_CRYSTALS))
                .save(recipeOutput, BlusBeastsMod.MODID+":amthyst_shards_from_embedded_crystal");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.GRAVITY_DUST, 2)
                .requires(ModItems.BURROW_ROD)
                .unlockedBy("has_burrow_rod", has(ModItems.BURROW_ROD))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BONE_MEAL)
                .requires(ModItems.FISH_BONE_SKEWER)
                .unlockedBy("has_fish_bone_skewer", has(ModItems.FISH_BONE_SKEWER))
                .save(recipeOutput, BlusBeastsMod.MODID+":bonemeal_from_fish_bones");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BLUE_DYE)
                .requires(ModBlocks.BLUEBELLS)
                .unlockedBy("has_bluebells", has(ModBlocks.BLUEBELLS))
                .save(recipeOutput, BlusBeastsMod.MODID+":blue_from_bluebells");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_CRYSTAL)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.EMBEDDED_CRYSTALS)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_DONUT)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.MINERS_SNACK)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_HORNS)
                .requires(ModTags.Items.PLATE)
                .requires(BaseModItems.BEETLE_HORN)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_LEACH)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.NETHER_LEACH)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_LIGHTNING)
                .requires(ModTags.Items.PLATE)
                .requires(BaseModItems.LIGHTNING_IN_A_BOTTLE)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_MIMICART)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.LIVING_WHEELS)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_PHEROMONES)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.PRAYFINDER_FEATHER)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_PIXIE)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.FAIRY_DUST)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_SAND)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.BURROW_ROD)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_SAPLING)
                .requires(ModTags.Items.PLATE)
                .requires(BaseModItems.SPINE_TREE)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_SCARAB)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.SCARAB)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_SHROOM)
                .requires(ModTags.Items.PLATE)
                .requires(BaseModBlocks.JUMP_MUSHROOM)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_SHROOM)
                .requires(ModTags.Items.PLATE)
                .requires(BaseModBlocks.SPORE_MUSHROOM_BLOCK)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput, BlusBeastsMod.MODID+":mushroom_plate_from_spore");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_SQUID)
                .requires(ModTags.Items.PLATE)
                .requires(ModItems.END_TAKOYAKI)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_VOID)
                .requires(ModTags.Items.PLATE)
                .requires(ModBlocks.VOID_ORE)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.PLATE_WINGS)
                .requires(ModTags.Items.PLATE)
                .requires(ModBlocks.END_WYVERN_EGG)
                .unlockedBy("has_plate", has(ModTags.Items.PLATE))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.RAINBOW_WOOL)
                .requires(ItemTags.WOOL)
                .requires(ModItems.FAIRY_DUST)
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.RAINBOW_CARPET)
                .requires(ItemTags.WOOL_CARPETS)
                .requires(ModItems.FAIRY_DUST)
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST))
                .save(recipeOutput, BlusBeastsMod.MODID+":rainbow_carpet_coloring");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModBlocks.RAINBOW_CONCRETE)
                .requires(ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","concretes")))
                .requires(ModItems.FAIRY_DUST)
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, ModItems.PARTY_POPPER)
                .requires(Items.GUNPOWDER)
                .requires(Items.PAPER)
                .requires(Items.STRING)
                .requires(ModItems.FAIRY_DUST)
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Blocks.BLACK_WOOL)
                .requires(ModBlocks.RAINBOW_WOOL)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_rainbow_wool", has(ModBlocks.RAINBOW_WOOL))
                .save(recipeOutput, BlusBeastsMod.MODID+":black_wool_from_rainbow");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Blocks.BLACK_CARPET)
                .requires(ModBlocks.RAINBOW_CARPET)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_rainbow_carpet", has(ModBlocks.RAINBOW_CARPET))
                .save(recipeOutput, BlusBeastsMod.MODID+":black_carpet_from_rainbow");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Blocks.BLACK_CONCRETE)
                .requires(ModBlocks.RAINBOW_CONCRETE)
                .requires(Items.BLACK_DYE)
                .unlockedBy("has_rainbow_concrete", has(ModBlocks.RAINBOW_CONCRETE))
                .save(recipeOutput, BlusBeastsMod.MODID+":black_concrete_from_rainbow");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.NETHERITE_INGOT, 1)
                .requires(ModItems.SALAMANDER_SCALES)
                .requires(Items.NETHERITE_SCRAP)
                .requires(Items.NETHERITE_SCRAP)
                .requires(Items.NETHERITE_SCRAP)
                .requires(Items.GOLD_INGOT)
                .requires(Items.GOLD_INGOT)
                .requires(Items.GOLD_INGOT)
                .requires(Items.GOLD_INGOT)
                .unlockedBy("has_salamander_scales", has(ModItems.SALAMANDER_SCALES))
                .save(recipeOutput, BlusBeastsMod.MODID+":netherite_ingot_from_salamander_scales");

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.ANTLERS.get())
                .pattern("MSM")
                .define('S', Items.LEATHER)
                .define('M', BaseModItems.BEETLE_HORN)
                .unlockedBy("has_beetle_horn", has(BaseModItems.BEETLE_HORN)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.GOLDEN_ANTLERS.get())
                .pattern("MSM")
                .define('S', Items.LEATHER)
                .define('M', BaseModItems.SUN_HORN)
                .unlockedBy("has_sun_horn", has(BaseModItems.SUN_HORN)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.PERSONAL_MINECART.get())
                .pattern("MXM")
                .pattern("MMM")
                .pattern("W W")
                .define('W', ModItems.LIVING_WHEELS)
                .define('M', Items.IRON_INGOT)
                .define('X', Items.GHAST_TEAR)
                .unlockedBy("has_living_wheels", has(ModItems.LIVING_WHEELS)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TRANSPORTATION, ModItems.PERSONAL_MINECART.get())
                .pattern(" X ")
                .pattern(" M ")
                .pattern("W W")
                .define('W', ModItems.LIVING_WHEELS)
                .define('M', Items.MINECART)
                .define('X', Items.GHAST_TEAR)
                .unlockedBy("has_living_wheels", has(ModItems.LIVING_WHEELS)).save(recipeOutput, BlusBeastsMod.MODID+":personal_minecart_from_minecart");

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CHIMERA_CORE.get())
                .pattern("WAW")
                .pattern("DED")
                .pattern("WGW")
                .define('W', Items.CALCITE)
                .define('A', Items.SOUL_SAND)
                .define('D', Items.DIAMOND)
                .define('E', Items.ENDER_PEARL)
                .define('G', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","eggs")))
                .unlockedBy("has_ender_pearl", has(Items.ENDER_PEARL)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.END_WYVERN_NEST.get(), 4)
                .pattern("WAW")
                .pattern("AEA")
                .pattern("WAW")
                .define('W', Items.STICK)
                .define('A', Items.CHORUS_FRUIT)
                .define('E', Items.CHORUS_FLOWER)
                .unlockedBy("has_chorus_flower", has(Items.CHORUS_FLOWER)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SCARAB_DORMANT)
                .pattern("RGR")
                .pattern("MBM")
                .pattern("R R")
                .define('R', ModItems.BURROW_ROD)
                .define('B', ModItems.BURROW_GEM)
                .define('M', Items.PHANTOM_MEMBRANE)
                .define('G', BaseModItems.SUN_HORN)
                .unlockedBy("has_burrow_gem", has(ModItems.BURROW_GEM)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PICKERANG_UPGRADE_TEMPLATE)
                .pattern("RRR")
                .pattern("RBR")
                .pattern("RDR")
                .define('R', ModItems.GRAVITY_DUST)
                .define('B', ModItems.GRAVE_GEM)
                .define('D', Items.DIAMOND)
                .unlockedBy("has_grave_gem", has(ModItems.GRAVE_GEM)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.VOID_ORE_MAGNET)
                .pattern("VVV")
                .pattern("DRD")
                .pattern("PPP")
                .define('V', ModBlocks.VOID_ORE)
                .define('R', Blocks.REDSTONE_BLOCK)
                .define('D', Items.DEEPSLATE)
                .define('P', Items.PURPUR_BLOCK)
                .unlockedBy("has_void_ore", has(ModBlocks.VOID_ORE)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.GLITTER_BOMB)
                .pattern("FSF")
                .pattern("IFI")
                .pattern("IGI")
                .define('F', ModItems.FAIRY_DUST)
                .define('I', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","nuggets")))
                .define('G', Items.GUNPOWDER)
                .define('S', Items.STRING)
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.MAGIC_WAND)
                .pattern("FPH")
                .pattern("PSP")
                .pattern("SPF")
                .define('F', Items.PHANTOM_MEMBRANE)
                .define('H', Items.GOLDEN_APPLE)
                .define('P', ModItems.FAIRY_DUST)
                .define('S', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","rods/wooden")))
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.PLATE)
                .pattern("GBG")
                .pattern("CCC")
                .define('C', Blocks.CALCITE)
                .define('G', Items.GOLD_NUGGET)
                .define('B', ModBlocks.BLUEBELLS)
                .unlockedBy("has_bluebells", has(ModBlocks.BLUEBELLS)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.PIXIE_TORCH)
                .pattern("G")
                .pattern("C")
                .define('C', ItemTags.create(ResourceLocation.fromNamespaceAndPath("c","rods/wooden")))
                .define('G', ModItems.FAIRY_DUST)
                .unlockedBy("has_fairy_dust", has(ModItems.FAIRY_DUST)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, ModBlocks.RAINBOW_CARPET, 3)
                .pattern("GG")
                .define('G', ModBlocks.RAINBOW_WOOL)
                .unlockedBy("has_rainbow_wool", has(ModBlocks.RAINBOW_WOOL)).save(recipeOutput);

        netherLeach(recipeOutput, ItemTags.LOGS_THAT_BURN, Blocks.CRIMSON_STEM);
        netherLeach(recipeOutput, Items.STONE, Blocks.NETHERRACK);
        netherLeach(recipeOutput, ItemTags.SAND, Blocks.SOUL_SAND);
        netherLeach(recipeOutput, Items.DIRT, Blocks.SOUL_SOIL);
        netherLeach(recipeOutput, Items.STONE_BRICKS, Blocks.NETHER_BRICKS);
        netherLeach(recipeOutput, Items.GRASS_BLOCK, Blocks.CRIMSON_NYLIUM);
        netherLeach(recipeOutput, ItemTags.LEAVES, Blocks.NETHER_WART_BLOCK);
        netherLeach(recipeOutput, Items.DEEPSLATE, Blocks.BASALT);
        netherLeach(recipeOutput, Items.INFESTED_STONE, ModBlocks.INFESTED_NETHERRACK.get());

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(new ItemLike[]{ModItems.PICKERANG_UPGRADE_TEMPLATE}), Ingredient.of(new ItemLike[]{Items.NETHERITE_PICKAXE}), Ingredient.of(new ItemLike[]{ModItems.SCARAB}), RecipeCategory.TOOLS, ModItems.PICKERANG.get()).unlocks("has_scarab", has(ModItems.SCARAB)).save(recipeOutput, BlusBeastsMod.MODID+ ":pickerang_smithing");
    }

    protected static void itemSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                       float pExperience, int pCookingTIme, String pGroup) {
        itemCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void itemBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                       float pExperience, int pCookingTime, String pGroup) {
        itemCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static void itemSmoking(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        itemCooking(recipeOutput, RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_smoking");
    }
    protected static void itemCampfire(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                       float pExperience, int pCookingTime, String pGroup) {
        itemCooking(recipeOutput, RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_campfire");
    }
    protected static void foodSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                       float pExperience, int pCookingTime, String pGroup) {
        itemSmelting(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup);
        itemSmoking(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime/2, pGroup);
        itemCampfire(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime*3, pGroup);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                       float pExperience, int pCookingTime, String pGroup) {
        itemSmelting(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup);
        itemBlasting(recipeOutput, pIngredients, pCategory, pResult, pExperience, pCookingTime/2, pGroup);
    }

    protected static <T extends AbstractCookingRecipe> void itemCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                        List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, BlusBeastsMod.MODID + ":smelting/" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }

    protected void netherLeach(RecipeOutput recipeOutput, ItemLike input, Block output) {
        Ingredient ingredient = Ingredient.of(input);
        NetherLeachRecipe recipe = new NetherLeachRecipe(ingredient, output);
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "nether_leach/"+BuiltInRegistries.BLOCK.getKey(output).getPath());
        recipeOutput.accept(id, recipe, null);
    }

    protected void netherLeach(RecipeOutput recipeOutput, TagKey<Item> input, Block output) {
        Ingredient ingredient = Ingredient.of(input);
        NetherLeachRecipe recipe = new NetherLeachRecipe(ingredient, output);
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "nether_leach/"+BuiltInRegistries.BLOCK.getKey(output).getPath());
        recipeOutput.accept(id, recipe, null);
    }

    protected static void netheriteSmithing(RecipeOutput recipeOutput, Item ingredientItem, RecipeCategory category, Item resultItem) {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(new ItemLike[]{Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE}), Ingredient.of(new ItemLike[]{ingredientItem}), Ingredient.of(new ItemLike[]{Items.NETHERITE_INGOT}), category, resultItem).unlocks("has_netherite_ingot", has(Items.NETHERITE_INGOT)).save(recipeOutput, getItemName(resultItem) + "_smithing");
    }
}
