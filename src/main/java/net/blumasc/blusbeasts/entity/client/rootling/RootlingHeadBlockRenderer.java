package net.blumasc.blusbeasts.entity.client.rootling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.entity.client.myceliumToad.MyceliumToadModel;
import net.blumasc.blusbeasts.entity.custom.MyceliumToadEntity;
import net.blumasc.blusbeasts.entity.custom.RootlingEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

public class RootlingHeadBlockRenderer extends RenderLayer<RootlingEntity, RootlingModel<RootlingEntity>> {
    public RootlingHeadBlockRenderer(RenderLayerParent<RootlingEntity, RootlingModel<RootlingEntity>> renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, RootlingEntity rootlingEntity, float v, float v1, float v2, float v3, float v4, float v5) {
        RootlingModel<RootlingEntity> model = this.getParentModel();
        BlockState state = rootlingEntity.getHeadBlock();
        if (state == null || state.isAir()) return;

        BlockRenderDispatcher dispatcher = Minecraft.getInstance().getBlockRenderer();

        poseStack.pushPose();

        model.head.translateAndRotate(poseStack);

        poseStack.translate(-0.25, 0.25, 0.25);

        model.headPlant.translateAndRotate(poseStack);

        poseStack.mulPose(Axis.XP.rotationDegrees(180F));

        poseStack.scale(0.5F, 0.5F, 0.5F);

        poseStack.translate(0, -2.2, 0);

        dispatcher.renderSingleBlock(state, poseStack, multiBufferSource, i, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}
