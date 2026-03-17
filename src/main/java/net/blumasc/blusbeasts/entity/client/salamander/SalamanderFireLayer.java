package net.blumasc.blusbeasts.entity.client.salamander;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.client.ModRenderTypes;
import net.blumasc.blusbeasts.entity.custom.SalamanderEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class SalamanderFireLayer extends RenderLayer<SalamanderEntity, SalamanderModel<SalamanderEntity>> {

    private static final ResourceLocation TEXTURE_1 =
            ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,
                    "textures/entity/salamander/texture_fire_1.png");

    private static final ResourceLocation TEXTURE_0 =
            ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,
                    "textures/entity/salamander/texture_fire.png");

    public SalamanderFireLayer(RenderLayerParent<SalamanderEntity, SalamanderModel<SalamanderEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       SalamanderEntity entity,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTicks,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        ResourceLocation texture;

        if(entity.tickCount%10<5){
            texture = TEXTURE_0;
        }else{
            texture = TEXTURE_1;
        }

        var vertexConsumer = buffer.getBuffer(ModRenderTypes.emissive(texture));

        this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                LightTexture.FULL_BRIGHT,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                Color.WHITE.getRGB()
        );
    }
}

