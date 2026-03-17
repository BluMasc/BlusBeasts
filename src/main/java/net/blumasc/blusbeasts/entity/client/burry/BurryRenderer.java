package net.blumasc.blusbeasts.entity.client.burry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.BurryEntity;
import net.blumasc.blusbeasts.entity.variants.BurryVariant;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BurryRenderer extends MobRenderer<BurryEntity, BurryModel<BurryEntity>> {
    public BurryRenderer(EntityRendererProvider.Context context) {
        super(context, new BurryModel<>(context.bakeLayer(BurryModel.LAYER_LOCATION)), 0.1f);
        this.addLayer(new BurryEyeLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BurryEntity burryEntity) {
        if(burryEntity.getVariant()== BurryVariant.RED_SAND) {
            return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/burry/texture_red_sand.png");
        }
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/burry/texture_sand.png");
    }

    @Override
    public void render(BurryEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        float scale = 1.0f+ 0.01f*entity.getGrowth();
        poseStack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}
