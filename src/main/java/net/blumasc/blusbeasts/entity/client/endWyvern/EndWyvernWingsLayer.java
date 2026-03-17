package net.blumasc.blusbeasts.entity.client.endWyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EndWyvernWingsLayer extends RenderLayer<EndWyvernEntity, EndWyvernModel<EndWyvernEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/ender_wyvern/texture.png");

    public EndWyvernWingsLayer(RenderLayerParent<EndWyvernEntity, EndWyvernModel<EndWyvernEntity>> parent) {
        super(parent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, EndWyvernEntity entity,
                       float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                       float netHeadYaw, float headPitch) {

        VertexConsumer vc = buffer.getBuffer(RenderType.entityTranslucentCull(TEXTURE));

        ModelPart wings = this.getParentModel().wings;

        poseStack.pushPose();
        poseStack.translate(0, 1.4F, 0);
        wings.render(poseStack, vc, light, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }
}
