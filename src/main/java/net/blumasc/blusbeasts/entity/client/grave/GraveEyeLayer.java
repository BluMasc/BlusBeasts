package net.blumasc.blusbeasts.entity.client.grave;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.client.ModRenderTypes;
import net.blumasc.blusbeasts.entity.custom.BurryEntity;
import net.blumasc.blusbeasts.entity.custom.GraveEntity;
import net.blumasc.blusbeasts.entity.variants.BurryVariant;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class GraveEyeLayer extends RenderLayer<GraveEntity, GraveModel<GraveEntity>> {
    public GraveEyeLayer(RenderLayerParent<GraveEntity, GraveModel<GraveEntity>> renderer) {
        super(renderer);
    }

    public ResourceLocation getTexture(GraveEntity burryEntity) {
        if(burryEntity.getVariant()== BurryVariant.RED_SAND) {
            return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/grave/texture_red_sand_eyes.png");
        }
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/grave/texture_sand_eyes.png");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, GraveEntity graveEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        var vertexConsumer = multiBufferSource.getBuffer(ModRenderTypes.emissive(getTexture(graveEntity)));

        this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                LightTexture.FULL_BRIGHT,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                Color.WHITE.getRGB()
        );
    }
}
