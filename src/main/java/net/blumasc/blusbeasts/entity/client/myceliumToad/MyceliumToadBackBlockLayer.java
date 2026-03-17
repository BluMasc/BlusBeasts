package net.blumasc.blusbeasts.entity.client.myceliumToad;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderModel;
import net.blumasc.blusbeasts.entity.custom.MyceliumToadEntity;
import net.blumasc.blusbeasts.entity.custom.SalamanderEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

public class MyceliumToadBackBlockLayer extends RenderLayer<MyceliumToadEntity, MyceliumToadModel<MyceliumToadEntity>> {
    public MyceliumToadBackBlockLayer(RenderLayerParent<MyceliumToadEntity, MyceliumToadModel<MyceliumToadEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                       MyceliumToadEntity entity, float limbSwing, float limbSwingAmount,
                       float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        MyceliumToadModel<MyceliumToadEntity> model = this.getParentModel();

        renderBlock(entity.getBackBlock0(), model.block1, poseStack, buffer, packedLight, model);
        renderBlock(entity.getBackBlock1(), model.block2, poseStack, buffer, packedLight, model);
        renderBlock(entity.getBackBlock2(), model.block3, poseStack, buffer, packedLight,model);
    }

    private void renderBlock(BlockState state, ModelPart anchor,
                             PoseStack poseStack, MultiBufferSource buffer, int packedLight, MyceliumToadModel<MyceliumToadEntity> model) {

        if (state == null || state.isAir()) return;

        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

        poseStack.pushPose();
        poseStack.translate(-0.3, 1.6, 0.3);
        model.base.translateAndRotate(poseStack);
        model.head.translateAndRotate(poseStack);
        anchor.translateAndRotate(poseStack);
        poseStack.scale(0.7F, 0.7F, 0.7F);
        poseStack.mulPose(Axis.XP.rotationDegrees(170F));
        dispatcher.renderSingleBlock(state, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }

}
