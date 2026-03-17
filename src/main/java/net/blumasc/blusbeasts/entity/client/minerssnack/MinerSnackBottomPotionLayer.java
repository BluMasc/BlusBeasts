package net.blumasc.blusbeasts.entity.client.minerssnack;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.MinersSnackEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class MinerSnackBottomPotionLayer extends RenderLayer<MinersSnackEntity, MinerSnackModel<MinersSnackEntity>> {
    public MinerSnackBottomPotionLayer(RenderLayerParent<MinersSnackEntity, MinerSnackModel<MinersSnackEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource multiBufferSource,
                       int packedLight,
                       MinersSnackEntity entity,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTick,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        var vertexConsumer = multiBufferSource.getBuffer(
                RenderType.entityCutoutNoCull(getFinTextureLocation(entity))
        );

        this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY,
                entity.getColorBottom()
        );
    }

    private ResourceLocation getFinTextureLocation(MinersSnackEntity minersSnackEntity) {
        String location = "textures/entity/minersnack/texture_potion_bottom_"+(minersSnackEntity.getDurationBottom()+1)+".png";
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, location);
    }
}
