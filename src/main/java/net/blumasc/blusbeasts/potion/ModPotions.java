package net.blumasc.blusbeasts.potion;

import net.blumasc.blubasics.effect.BaseModEffects;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, BlusBeastsMod.MODID);

    public static final Holder<Potion> PHEROMONES_POTION = POTIONS.register("bb_pheromones_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.PHEROMONES, 200, 0)));

    public static final Holder<Potion> VOID_POTION = POTIONS.register("bb_void_potion",
            () -> new Potion(new MobEffectInstance(BaseModEffects.VOID_EFFECT, 400, 0)));

    public static void register(IEventBus bus)
    {
        POTIONS.register(bus);
    }
}
