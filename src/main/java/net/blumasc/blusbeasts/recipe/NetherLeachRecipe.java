package net.blumasc.blusbeasts.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public record NetherLeachRecipe (Ingredient inputItem, Block outputBlock) implements Recipe<NetherLeachRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(inputItem);
    }

    public Ingredient getIngredient(){
        return inputItem;
    }

    @Override
    public boolean matches(NetherLeachRecipeInput netherLeachRecipeInput, Level level) {
        boolean ret = inputItem.test(netherLeachRecipeInput.getItem(0));
        return ret;
    }

    @Override
    public ItemStack assemble(NetherLeachRecipeInput netherLeachRecipeInput, HolderLookup.Provider provider) {
        return outputBlock.asItem().getDefaultInstance().copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return outputBlock.asItem().getDefaultInstance();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.NETHER_LEACH_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.NETHER_LEACH_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<NetherLeachRecipe>
    {
        public static final MapCodec<NetherLeachRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(NetherLeachRecipe::inputItem),
                ResourceLocation.CODEC.fieldOf("output").xmap(
                        id -> BuiltInRegistries.BLOCK.get(id),
                        BuiltInRegistries.BLOCK::getKey
                ).forGetter(NetherLeachRecipe::outputBlock)
        ).apply(inst, NetherLeachRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, NetherLeachRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, NetherLeachRecipe::inputItem,
                        StreamCodec.of(
                                (buf, block) -> buf.writeResourceLocation(BuiltInRegistries.BLOCK.getKey(block)),
                                buf -> BuiltInRegistries.BLOCK.get(buf.readResourceLocation())
                        ), NetherLeachRecipe::outputBlock,
                        NetherLeachRecipe::new
                );

        @Override
        public MapCodec<NetherLeachRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, NetherLeachRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
