package net.blumasc.blusbeasts.block.custom.blockentity.custom.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.block.custom.PlateBlock;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.PlateBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;

public class PlateBlockEntityRenderer implements BlockEntityRenderer<PlateBlockEntity> {

    public PlateBlockEntityRenderer(BlockEntityRendererProvider.Context context) { }

    @Override
    public void render(PlateBlockEntity plateBlockEntity, float partialTick, PoseStack poseStack,
                       MultiBufferSource buffer, int packedLight, int packedOverlay) {

        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        Level level = plateBlockEntity.getLevel();
        BlockPos pos = plateBlockEntity.getBlockPos();

        BlockState state = level.getBlockState(pos);
        if(!(state.getBlock() instanceof PlateBlock))return;
        Direction facing = state.getValue(PlateBlock.FACING);

        poseStack.pushPose();
        poseStack.translate(0.5, 0, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(getFacingAngle(facing)));
        poseStack.translate(-0.5, 0, -0.5);

        int light = getLightLevel(level, pos);
        placeItem(plateBlockEntity.inventory.getStackInSlot(0), poseStack, itemRenderer, light, buffer, level, 0.5f, 0.1f, 0.5f, 0f, 0f, 0f);
        placeItem(plateBlockEntity.inventory.getStackInSlot(1), poseStack, itemRenderer, light, buffer, level, 0.3f, 0.13f, 0.33f, 30f, 140f, 20f);
        placeItem(plateBlockEntity.inventory.getStackInSlot(2), poseStack, itemRenderer, light, buffer, level, 0.65f, 0.13f, 0.70f, -50f, -30f, 30f);

        poseStack.popPose();
    }

    private int getLightLevel(Level level, BlockPos pos){
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(blockLight, skyLight);
    }

    private void placeItem(ItemStack stack, PoseStack poseStack, ItemRenderer itemRenderer, int light, MultiBufferSource buffer, Level level,float x, float y, float z, float xRot, float zRot, float yRot)
    {
        if (stack.isEmpty()) return;

        poseStack.pushPose();

        poseStack.translate(x, y, z);

        poseStack.mulPose(Axis.XP.rotationDegrees(90 + xRot));
        poseStack.mulPose(Axis.ZP.rotationDegrees(zRot));
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        poseStack.scale(0.35f, 0.35f, 0.35f);

        itemRenderer.renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                light,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                buffer,
                level,
                0
        );

        poseStack.popPose();
    }
    private float getFacingAngle(Direction facing) {
        return switch (facing) {
            case NORTH -> 0f;
            case WEST  -> 90f;
            case SOUTH -> 180f;
            case EAST  -> 270f;
            default    -> 0f;
        };
    }
}
