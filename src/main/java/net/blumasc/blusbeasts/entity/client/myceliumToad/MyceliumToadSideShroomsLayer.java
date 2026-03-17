package net.blumasc.blusbeasts.entity.client.myceliumToad;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.MyceliumToadEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class MyceliumToadSideShroomsLayer extends RenderLayer<MyceliumToadEntity, MyceliumToadModel<MyceliumToadEntity>> {
    private static final ResourceLocation TEXTURE_OCTOPUS = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/mycelium_toad/octopus_mushroom.png");
    private static final ResourceLocation TEXTURE_SHELF = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/mycelium_toad/platform_mushroom.png");

    public static ShelfMushroomModel<LivingEntity> SHELF_MUSHROOM_MODEL;
    public static OctopusMushroomModel<LivingEntity> OCTOPUS_MUSHROOM_MODEL;

    public MyceliumToadSideShroomsLayer(RenderLayerParent<MyceliumToadEntity, MyceliumToadModel<MyceliumToadEntity>> renderer) {
        super(renderer);
        ModelPart part_shelf = Minecraft.getInstance().getEntityModels().bakeLayer(ShelfMushroomModel.LAYER_LOCATION);
        SHELF_MUSHROOM_MODEL = new ShelfMushroomModel<>(part_shelf);
        ModelPart part_octopus = Minecraft.getInstance().getEntityModels().bakeLayer(OctopusMushroomModel.LAYER_LOCATION);
        OCTOPUS_MUSHROOM_MODEL = new OctopusMushroomModel<>(part_octopus);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, MyceliumToadEntity myceliumToadEntity, float v, float v1, float v2, float v3, float v4, float v5) {

        MyceliumToadModel<MyceliumToadEntity> parentModel = this.getParentModel();
        if (OCTOPUS_MUSHROOM_MODEL != null && !(myceliumToadEntity.getSquidLeg()>=4)){
            poseStack.pushPose();
            poseStack.translate(0 ,1.5, 0);
            switch(myceliumToadEntity.getSquidLeg())
            {
                case 0: parentModel.backleg2.translateAndRotate(poseStack);parentModel.octoPlace2.translateAndRotate(poseStack); break;
                case 1: parentModel.frontleg2.translateAndRotate(poseStack);parentModel.octoPlace4.translateAndRotate(poseStack); break;
                case 2: parentModel.backleg1.translateAndRotate(poseStack);parentModel.octoPlace1.translateAndRotate(poseStack);poseStack.mulPose(Axis.YP.rotationDegrees(180F));break;
                case 3: parentModel.frontleg.translateAndRotate(poseStack);parentModel.octoPlace3.translateAndRotate(poseStack);poseStack.mulPose(Axis.YP.rotationDegrees(180F));break;
            }
            VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_OCTOPUS));
            OCTOPUS_MUSHROOM_MODEL.body.render(poseStack, buffer, i, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
        if(SHELF_MUSHROOM_MODEL != null && myceliumToadEntity.hasShelfRight())
        {
            poseStack.pushPose();
            parentModel.base.translateAndRotate(poseStack);
            parentModel.body.translateAndRotate(poseStack);
            poseStack.translate(0.7, ((float)myceliumToadEntity.getShelfRightHeight())/10, ((float)myceliumToadEntity.getShelfRightDepth())/10);
            VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_SHELF));
            SHELF_MUSHROOM_MODEL.left_leg.render(poseStack, buffer, i, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }

        if(SHELF_MUSHROOM_MODEL != null && myceliumToadEntity.hasShelfLeft())
        {
            poseStack.pushPose();
            parentModel.base.translateAndRotate(poseStack);
            parentModel.body.translateAndRotate(poseStack);
            poseStack.mulPose(Axis.YP.rotationDegrees(180F));
            poseStack.translate(0.7, ((float)myceliumToadEntity.getShelfLeftHeight())/10, ((float)myceliumToadEntity.getShelfLeftDepth())/10);
            VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE_SHELF));
            SHELF_MUSHROOM_MODEL.left_leg.render(poseStack, buffer, i, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
