package net.blumasc.blusbeasts.item.custom.components;

import com.mojang.serialization.Codec;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.HashSet;
import java.util.Set;

public class ModItemDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENTS =
            DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, BlusBeastsMod.MODID);

    private static final Codec<Set<EntityType<?>>> ENTITY_SET_CODEC =
            ResourceLocation.CODEC.listOf().xmap(
                    list -> {
                        Set<EntityType<?>> set = new HashSet<>();
                        for (ResourceLocation id : list) {
                            set.add(BuiltInRegistries.ENTITY_TYPE.get(id));
                        }
                        return set;
                    },
                    set -> set.stream()
                            .map(BuiltInRegistries.ENTITY_TYPE::getKey)
                            .toList()
            );

    private static final StreamCodec<RegistryFriendlyByteBuf, Set<EntityType<?>>> ENTITY_SET_STREAM_CODEC =
            ByteBufCodecs.registry(Registries.ENTITY_TYPE)
                    .apply(ByteBufCodecs.collection(HashSet::new));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Set<EntityType<?>>>> COLLECTED_MOBS =
            DATA_COMPONENTS.register("collected_mobs", () ->
                    DataComponentType.<Set<EntityType<?>>>builder()
                            .persistent(ENTITY_SET_CODEC)
                            .networkSynchronized(ENTITY_SET_STREAM_CODEC)
                            .build()
            );

    public static void register(IEventBus bus){
        DATA_COMPONENTS.register(bus);
    }
}
