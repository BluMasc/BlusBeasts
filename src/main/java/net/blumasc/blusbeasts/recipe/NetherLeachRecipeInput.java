package net.blumasc.blusbeasts.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record NetherLeachRecipeInput(ItemStack input)  implements RecipeInput {

    @Override
    public ItemStack getItem(int i) {
        return this.input;
    }

    @Override
    public int size() {
        return 1;
    }
}
