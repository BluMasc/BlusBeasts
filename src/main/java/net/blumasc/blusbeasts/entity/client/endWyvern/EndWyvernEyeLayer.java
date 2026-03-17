package net.blumasc.blusbeasts.entity.client.endWyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.client.ModRenderTypes;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class EndWyvernEyeLayer extends RenderLayer<EndWyvernEntity, EndWyvernModel<EndWyvernEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,
                    "textures/entity/ender_wyvern/eyes.png");

    public EndWyvernEyeLayer(RenderLayerParent<EndWyvernEntity, EndWyvernModel<EndWyvernEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, EndWyvernEntity endWyvernEntity, float v, float v1, float v2, float v3, float v4, float v5) {

        var vertexConsumer = multiBufferSource.getBuffer(ModRenderTypes.emissive(TEXTURE));

        this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                LightTexture.FULL_BRIGHT,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                Color.WHITE.getRGB()
        );
    }
}
