package net.blumasc.blusbeasts.entity.client.mimicart;

import com.mojang.blaze3d.vertex.PoseStack;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.MimicartEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MimicartRenderer extends MobRenderer<MimicartEntity, EntityModel<MimicartEntity>> {

    private static final ResourceLocation MIMICART_TEXTURE = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/mimicart/texture.png");
    private static final ResourceLocation MIMICART_HIDDEN_TEXTURE = ResourceLocation.parse("minecraft:textures/entity/minecart.png");
    private final MimicartHiddenModel minecartModel;
    private final MimicartModel customModel;

    public MimicartRenderer(EntityRendererProvider.Context context) {
        super(context, new MimicartModel(context.bakeLayer(MimicartModel.LAYER_LOCATION)), 0.7f);

        this.minecartModel = new MimicartHiddenModel(context.bakeLayer(MimicartHiddenModel.LAYER_LOCATION));
        this.customModel = new MimicartModel(context.bakeLayer(MimicartModel.LAYER_LOCATION));
    }

    @Override
    public void render(MimicartEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isRevealed()) {
            this.model = customModel;
        } else {
            this.model = minecartModel;
        }

        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(MimicartEntity entity) {
        return entity.isRevealed() ? MIMICART_TEXTURE : MIMICART_HIDDEN_TEXTURE;
    }
}
