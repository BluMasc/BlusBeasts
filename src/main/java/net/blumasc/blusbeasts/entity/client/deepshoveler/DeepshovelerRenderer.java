package net.blumasc.blusbeasts.entity.client.deepshoveler;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.packwing.PackwingModel;
import net.blumasc.blusbeasts.entity.client.packwing.PackwingTailItemLayer;
import net.blumasc.blusbeasts.entity.custom.DeepshovelerEntity;
import net.blumasc.blusbeasts.entity.custom.PackwingEntity;
import net.blumasc.blusbeasts.entity.variants.DeepshovelerVariant;
import net.blumasc.blusbeasts.entity.variants.PackwingVariant;
import net.minecraft.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class DeepshovelerRenderer extends MobRenderer<DeepshovelerEntity, DeepshovelerModel<DeepshovelerEntity>> {

    private static final Map<DeepshovelerVariant, ResourceLocation> LOCATION_BY_VARIANT=
            Util.make(Maps.newEnumMap(DeepshovelerVariant.class), map ->{
                map.put(DeepshovelerVariant.STONE,
                        ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/deepshoveler/texture_stone.png"));
                map.put(DeepshovelerVariant.IRON,
                        ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/deepshoveler/texture_iron.png"));
                map.put(DeepshovelerVariant.GOLD,
                        ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/deepshoveler/texture_gold.png"));
                map.put(DeepshovelerVariant.DIAMOND,
                        ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/deepshoveler/texture_diamond.png"));
            });
    public DeepshovelerRenderer(EntityRendererProvider.Context context) {
        super(context, new DeepshovelerModel<>(context.bakeLayer(DeepshovelerModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(DeepshovelerEntity deepshovelerEntity) {
        return LOCATION_BY_VARIANT.get(deepshovelerEntity.getVariant());
    }
}