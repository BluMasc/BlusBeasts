package net.blumasc.blusbeasts.worldgen.feature;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(Registries.FEATURE, BlusBeastsMod.MODID);

    public static final DeferredHolder<Feature<?>, BluebellsFeature> BLUEBELLS =
            FEATURES.register("bluebells", BluebellsFeature::new);

    public static void register(IEventBus bus){
        FEATURES.register(bus);
    }
}
