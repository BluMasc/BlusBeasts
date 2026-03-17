package net.blumasc.blusbeasts.events;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.endWyvern.EndWyvernPlayerLayer;
import net.blumasc.blusbeasts.entity.client.endsquid.PlayerEndSquidLayer;
import net.blumasc.blusbeasts.entity.client.magicalgirl.MagicalGirlPlayerLayer;
import net.blumasc.blusbeasts.entity.client.netherLeach.PlayerNetherLeachLayer;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.item.client.ChimeraCoreOverrides;
import net.blumasc.blusbeasts.item.client.CombinedBakedModel;
import net.blumasc.blusbeasts.item.custom.components.ModItemDataComponents;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@EventBusSubscriber(modid = BlusBeastsMod.MODID, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        EntityModelSet models = event.getEntityModels();

        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer renderer = event.getSkin(skin);


            if (renderer != null) {
                renderer.addLayer(
                        new PlayerEndSquidLayer<>(renderer, models)
                );
                renderer.addLayer(
                        new PlayerNetherLeachLayer<>(renderer, models)
                );
                renderer.addLayer(
                        new EndWyvernPlayerLayer(renderer, models)
                );
                renderer.addLayer(
                        new MagicalGirlPlayerLayer<>(renderer, models)
                );
            }


        }
    }

    private static String[] layerNames = {
            "chimera_core_fox",
            "chimera_core_chicken",
            "chimera_core_goat",
            "chimera_core_guardian",
            "chimera_core_phantom",
            "chimera_core_rabbit",
            "chimera_core_hoglin"
    };

    @SubscribeEvent
    public static void onModelBake(ModelEvent.ModifyBakingResult event) {

        ModelResourceLocation baseLoc =
                new ModelResourceLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "chimera_core"), "inventory");

        BakedModel baseModel = event.getModels().get(baseLoc);
        if (baseModel == null) return;
        for (String layer : layerNames) {
            event.getModels().computeIfAbsent(
                    ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, layer)),
                    loc -> baseModel
            );
        }

        Map<Integer, BakedModel> variants = new HashMap<>();
        for (int mask = 0; mask < 128; mask++) {
            variants.put(mask, buildVariant(event, mask, baseModel, Arrays.stream(layerNames).toList()));
        }

        BakedModel wrapped = new BakedModelWrapper<>(baseModel) {
            @Override
            public ItemOverrides getOverrides() {
                return new ChimeraCoreOverrides(variants);
            }
        };

        event.getModels().put(baseLoc, wrapped);
    }

    private static BakedModel buildVariant(ModelEvent.ModifyBakingResult event,
                                           int mask,
                                           BakedModel baseModel,
                                           List<String> layerNames) {

        List<BakedModel> activeLayers = new ArrayList<>();
        for (int i = 0; i < layerNames.size(); i++) {
            if ((mask & (1 << i)) != 0) {
                ModelResourceLocation layerLoc =
                        ModelResourceLocation.inventory(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, layerNames.get(i)));
                BakedModel layer = event.getModels().get(layerLoc);
                if (layer != null) activeLayers.add(layer);
            }
        }

        return new CombinedBakedModel(baseModel, activeLayers);
    }
    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {

        event.register((stack, tintIndex) -> {

            if (tintIndex != 1 && tintIndex != 2) {
                return 0xFFFFFFFF;
            }

            CustomData data = stack.get(DataComponents.CUSTOM_DATA);
            if (data == null) return 0xFFFFFFFF;

            CompoundTag tag = data.copyTag();

            if (tintIndex == 1 && tag.contains("effectTop")) {
                MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(
                        ResourceLocation.parse(tag.getString("effectTop"))
                );
                if (effect != null) {
                    return 0xFF000000 | effect.getColor();
                }
            }

            if (tintIndex == 2 && tag.contains("effectBottom")) {
                MobEffect effect = BuiltInRegistries.MOB_EFFECT.get(
                        ResourceLocation.parse(tag.getString("effectBottom"))
                );
                if (effect != null) {
                    return 0xFF000000 | effect.getColor();
                }
            }

            return 0xFFFFFFFF;

        }, ModItems.MINERS_SNACK.get());

        event.register(
                (stack, tintIndex) -> {
                    if (tintIndex == 1) {
                        DyedItemColor dyedColor = stack.get(DataComponents.DYED_COLOR);
                        return dyedColor != null ? 0xFF000000 |dyedColor.rgb() : 0xFFFFFFFF;
                    }
                    return 0xFFFFFFFF;
                },
                ModItems.MAGIC_WAND.get()
        );
    }

}
