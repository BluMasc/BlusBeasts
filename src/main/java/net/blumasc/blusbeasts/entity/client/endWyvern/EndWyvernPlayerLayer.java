package net.blumasc.blusbeasts.entity.client.endWyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.client.ClientWyvernData;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.blumasc.blusbeasts.playerstate.PlayerDragonState;
import net.blumasc.blusbeasts.playerstate.PlayerDragonStateHandler;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class EndWyvernPlayerLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final EndWyvernModel<EndWyvernEntity> model;
    private final EndWyvernEyeLayer eyeLayer;
    private final EndWyvernWingsLayer wingsLayer;

    private EndWyvernEntity dummyWyvern;

    public EndWyvernPlayerLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
                                EntityModelSet modelSet) {
        super(parent);

        this.model = new EndWyvernModel<>(modelSet.bakeLayer(EndWyvernModel.LAYER_LOCATION));

        RenderLayerParent<EndWyvernEntity, EndWyvernModel<EndWyvernEntity>> fakeParent =
                new RenderLayerParent<>() {
                    @Override
                    public EndWyvernModel<EndWyvernEntity> getModel() {
                        return model;
                    }

                    @Override
                    public ResourceLocation getTextureLocation(EndWyvernEntity entity) {
                        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/ender_wyvern/texture.png");
                    }
                };

        this.eyeLayer = new EndWyvernEyeLayer(fakeParent);
        this.wingsLayer = new EndWyvernWingsLayer(fakeParent);
    }

    @Override
    public void render(PoseStack poseStack,
                       MultiBufferSource buffer,
                       int packedLight,
                       AbstractClientPlayer player,
                       float limbSwing,
                       float limbSwingAmount,
                       float partialTick,
                       float ageInTicks,
                       float netHeadYaw,
                       float headPitch) {

        if(!ClientWyvernData.hasDragon.get(player.getUUID())){
            return;
        }

        if (dummyWyvern == null) {
            dummyWyvern = new EndWyvernEntity(ModEntities.END_WYVERN.get(), player.level());
        }

        dummyWyvern.setPos(player.getX(), player.getY(), player.getZ());
        dummyWyvern.tickCount = player.tickCount;

        poseStack.pushPose();

        this.getParentModel().body.translateAndRotate(poseStack);
        poseStack.translate(0F, 0.0F, 0.3F);

        int animTime = player.tickCount;

        dummyWyvern.flyingAnimationState.startIfStopped(animTime);
        dummyWyvern.holdingAnimationState.startIfStopped(animTime);


        model.setupAnim(dummyWyvern, limbSwing, limbSwingAmount,
                ageInTicks, 0, 0);

        VertexConsumer vertex =
                buffer.getBuffer(RenderType.entityCutout(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/ender_wyvern/texture.png")));;

        model.renderToBuffer(poseStack, vertex,
                packedLight, OverlayTexture.NO_OVERLAY,
                0xFFFFFF);

        eyeLayer.render(poseStack, buffer, packedLight,
                dummyWyvern, limbSwing, limbSwingAmount,
                partialTick, ageInTicks, netHeadYaw, headPitch);

        poseStack.translate(0F, 0.15F, 0.0F);

        wingsLayer.render(poseStack, buffer, packedLight,
                dummyWyvern, limbSwing, limbSwingAmount,
                partialTick, ageInTicks, netHeadYaw, headPitch);

        poseStack.popPose();
    }
}
