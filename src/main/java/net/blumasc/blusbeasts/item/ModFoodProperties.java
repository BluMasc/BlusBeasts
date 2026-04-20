package net.blumasc.blusbeasts.item;

import net.blumasc.blusbeasts.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoodProperties {
    public static final FoodProperties LUNAR_TAKOYAKI = new FoodProperties.Builder().nutrition(4).saturationModifier(0.2f).fast().build();
    public static final FoodProperties COOKED_MINERS_SNACK = new FoodProperties.Builder().nutrition(5).saturationModifier(0.7f).build();
}
