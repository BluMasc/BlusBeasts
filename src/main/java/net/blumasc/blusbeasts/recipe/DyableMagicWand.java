package net.blumasc.blusbeasts.recipe;

import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class DyableMagicWand implements CraftingRecipe {
    private final CraftingBookCategory category;

    public DyableMagicWand(CraftingBookCategory category) {
        this.category = category;
    }

    @Override
    public CraftingBookCategory category() {
        return category;
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        ItemStack dyeable = ItemStack.EMPTY;
        List<ItemStack> dyes = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(ModItems.MAGIC_WAND.get())) {
                if (!dyeable.isEmpty()) {
                    return false;
                }
                dyeable = stack;
            } else if (stack.getItem() instanceof DyeItem) {
                dyes.add(stack);
            } else {
                return false;
            }
        }

        boolean result = !dyeable.isEmpty() && !dyes.isEmpty();
        return result;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack dyeable = ItemStack.EMPTY;
        List<DyeItem> dyes = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            ItemStack stack = input.getItem(i);
            if (stack.isEmpty()) continue;

            if (stack.is(ModItems.MAGIC_WAND)) {
                dyeable = stack.copy();
            } else if (stack.getItem() instanceof DyeItem dye) {
                dyes.add(dye);
            }
        }

        if (dyeable.isEmpty() || dyes.isEmpty()) return ItemStack.EMPTY;
        int finalColor = getFinalColor(dyes);
        dyeable.set(DataComponents.DYED_COLOR, new DyedItemColor(finalColor, false));
        return dyeable;
    }

    private static int getFinalColor(List<DyeItem> dyes) {
        int existingColor = 0xFFFFFF;

        int r = 0;
        int g = 0;
        int b = 0;
        int count = 0;

        for (DyeItem dye : dyes) {
            int dyeColor = dye.getDyeColor().getFireworkColor();
            r += (dyeColor >> 16) & 0xFF;
            g += (dyeColor >> 8) & 0xFF;
            b += dyeColor & 0xFF;
            count++;
        }

        r /= count;
        g /= count;
        b /= count;

        int finalColor = (r << 16) | (g << 8) | b;
        return finalColor;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return new ItemStack(ModItems.MAGIC_WAND.get());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DYEABLE_WAND_SERIALIZER.get();
    }
}