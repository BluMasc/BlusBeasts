package net.blumasc.blusbeasts.entity.client.burry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.client.ModRenderTypes;
import net.blumasc.blusbeasts.entity.custom.BurryEntity;
import net.blumasc.blusbeasts.entity.variants.BurryVariant;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class BurryEyeLayer extends RenderLayer<BurryEntity, BurryModel<BurryEntity>> {
    public BurryEyeLayer(RenderLayerParent<BurryEntity, BurryModel<BurryEntity>> renderer) {
        super(renderer);
    }

    public ResourceLocation getTexture(BurryEntity burryEntity) {
        if(burryEntity.getVariant()== BurryVariant.RED_SAND) {
            return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/burry/texture_red_sand_eyes.png");
        }
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/burry/texture_sand_eyes.png");
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, BurryEntity burryEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        var vertexConsumer = multiBufferSource.getBuffer(ModRenderTypes.emissive(getTexture(burryEntity)));

        this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                LightTexture.FULL_BRIGHT,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                Color.WHITE.getRGB()
        );
    }
}
