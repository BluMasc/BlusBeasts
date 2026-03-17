package net.blumasc.blusbeasts.entity.client.grave;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.GraveEntity;
import net.blumasc.blusbeasts.entity.variants.BurryVariant;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GraveRenderer extends MobRenderer<GraveEntity, GraveModel<GraveEntity>> {
    public GraveRenderer(EntityRendererProvider.Context context) {
        super(context, new GraveModel<>(context.bakeLayer(GraveModel.LAYER_LOCATION)), 0.2f);
        this.addLayer(new GraveEyeLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(GraveEntity graveEntity) {
        if(graveEntity.getVariant()== BurryVariant.RED_SAND) {
            return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/grave/texture_red_sand.png");
        }
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/grave/texture_sand.png");
    }

    @Override
    public void render(GraveEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.scale(1.4f, 1.4f, 1.4f);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
