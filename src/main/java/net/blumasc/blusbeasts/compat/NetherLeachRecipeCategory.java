package net.blumasc.blusbeasts.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.recipe.NetherLeachRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class NetherLeachRecipeCategory  implements IRecipeCategory<NetherLeachRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "nether_leach");

    public static final RecipeType<NetherLeachRecipe> NETHER_LEACH_RECIPE_TYPE =
            new RecipeType<>(UID, NetherLeachRecipe.class);

    private final IDrawable icon;

    public NetherLeachRecipeCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModItems.NETHER_LEACH.get()));
    }

    @Override
    public RecipeType<NetherLeachRecipe> getRecipeType() {
        return NETHER_LEACH_RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("item.blusbeasts.nether_leach");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, NetherLeachRecipe recipe, IFocusGroup focuses) {
        builder.addInputSlot(5,10).addIngredients(VanillaTypes.ITEM_STACK, List.of(recipe.getIngredient().getItems()));
        var result = recipe.getResultItem(null);
        builder.addOutputSlot(30,10).addIngredient(VanillaTypes.ITEM_STACK, result);
    }

    @Override
    public int getHeight() {
        return 30;
    }

    @Override
    public int getWidth() {
        return 50;
    }
}
