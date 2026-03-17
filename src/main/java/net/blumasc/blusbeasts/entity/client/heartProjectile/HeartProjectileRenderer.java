package net.blumasc.blusbeasts.entity.client.heartProjectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.magicalgirl.MagicalGirlModel;
import net.blumasc.blusbeasts.entity.custom.projectile.HeartProjectileEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class HeartProjectileRenderer extends EntityRenderer<HeartProjectileEntity> {
    private final HeartProjectile<HeartProjectileEntity> projectileModel;
    public HeartProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        projectileModel = new HeartProjectile<>(context.bakeLayer(HeartProjectile.LAYER_LOCATION));
    }

    @Override
    public ResourceLocation getTextureLocation(HeartProjectileEntity heartProjectileEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/heart_projectile/texture.png");
    }

    @Override
    public void render(HeartProjectileEntity entity, float entityYaw, float partialTick,
                       PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        poseStack.pushPose();

        poseStack.translate(0.0, 1.5, 0.0);

        poseStack.mulPose(Axis.XP.rotationDegrees(180));

        poseStack.translate(0.0, entity.bounce, 0.0);

        poseStack.mulPose(Axis.YP.rotationDegrees(entity.rotation));

        projectileModel.setupAnim(entity, 0f, 0f, entity.tickCount + partialTick, 0f, 0f);
        projectileModel.renderToBuffer(
                poseStack,
                bufferSource.getBuffer(projectileModel.renderType(getTextureLocation(entity))),
                net.minecraft.client.renderer.LightTexture.FULL_BRIGHT,
                OverlayTexture.NO_OVERLAY,
                0xFFFFFFFF
        );

        poseStack.popPose();

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
