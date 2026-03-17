package net.blumasc.blusbeasts.effect;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.effect.custom.*;
import net.blumasc.blusbeasts.effect.custom.PheromonesEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS= DeferredRegister.create(
            BuiltInRegistries.MOB_EFFECT, BlusBeastsMod.MODID
    );

    public static final Holder<MobEffect> PHEROMONES = MOB_EFFECTS.register("pheromones",
            () -> new PheromonesEffect());

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
