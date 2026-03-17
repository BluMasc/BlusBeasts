package net.blumasc.blusbeasts.particle;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLA_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BlusBeastsMod.MODID);

    public static final Supplier<SimpleParticleType> PINK_HEART =
            PARTICLA_TYPES.register("pink_heart_particles", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> CONFETTI =
            PARTICLA_TYPES.register("confetti_particles", () -> new SimpleParticleType(true));

    public static void register(IEventBus bus){
        PARTICLA_TYPES.register(bus);
    }
}
