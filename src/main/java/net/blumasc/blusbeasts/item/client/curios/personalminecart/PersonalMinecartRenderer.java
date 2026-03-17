package net.blumasc.blusbeasts.item.client.curios.personalminecart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.PersonalMinecart;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.item.client.curios.horns.HornsModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class PersonalMinecartRenderer implements ICurioRenderer {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/mimicart/personal_minecart.png");

    public static PersonalMinecartModel<LivingEntity> MODEL;

    public PersonalMinecartRenderer() {
        super();
        ModelPart part = Minecraft.getInstance().getEntityModels().bakeLayer(PersonalMinecartModel.LAYER_LOCATION);
        MODEL = new PersonalMinecartModel<>(part);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack, SlotContext slotContext, PoseStack matrixStack,
            RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer,
            int light, float limbSwing, float limbSwingAmount, float partialTicks,
            float ageInTicks, float netHeadYaw, float headPitch) {

        if (MODEL == null) return;

        LivingEntity e = slotContext.entity();

        if(e.getVehicle() instanceof PersonalMinecart){
            return;
        }


        matrixStack.pushPose();

        matrixStack.scale(0.5f, 0.5f, 0.5f);
        if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoidModel) {
            humanoidModel.body.translateAndRotate(matrixStack);
        }

        VertexConsumer buffer = renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        MODEL.core.render(matrixStack, buffer, light, OverlayTexture.NO_OVERLAY);

        matrixStack.popPose();
    }
}
