package net.blumasc.blusbeasts.recipe;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, BlusBeastsMod.MODID);
    public static final DeferredRegister<RecipeType<?>> TYPESS =
            DeferredRegister.create(Registries.RECIPE_TYPE, BlusBeastsMod.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<NetherLeachRecipe>> NETHER_LEACH_SERIALIZER =
            SERIALIZERS.register("nether_leach", NetherLeachRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<NetherLeachRecipe>> NETHER_LEACH_TYPE =
            TYPESS.register("nether_leach", () -> new RecipeType<NetherLeachRecipe>() {
                @Override
                public String toString(){
                    return "nether_leach";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<DyableMagicWand>> DYEABLE_WAND_SERIALIZER =
            SERIALIZERS.register("dyeable_wand", () ->
                    new SimpleCraftingRecipeSerializer<>(DyableMagicWand::new)
            );

    public static void register(IEventBus bus)
    {
        SERIALIZERS.register(bus);
        TYPESS.register(bus);
    }
}
