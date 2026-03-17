package net.blumasc.blusbeasts.compat;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.recipe.ModRecipes;
import net.blumasc.blusbeasts.recipe.NetherLeachRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JeiModPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new NetherLeachRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<NetherLeachRecipe> altarRecipes = recipeManager
                .getAllRecipesFor(ModRecipes.NETHER_LEACH_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(NetherLeachRecipeCategory.NETHER_LEACH_RECIPE_TYPE, altarRecipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(ModItems.NETHER_LEACH.asItem(), NetherLeachRecipeCategory.NETHER_LEACH_RECIPE_TYPE);
    }
}
