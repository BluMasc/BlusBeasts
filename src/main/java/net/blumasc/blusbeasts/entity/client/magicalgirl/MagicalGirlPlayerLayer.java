package net.blumasc.blusbeasts.entity.client.magicalgirl;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class MagicalGirlPlayerLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final MagicalGirlModel<T> magicalGirlModel;
    private static ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/magical_girl/texture_white.png");
    private static ResourceLocation TEXTURE_COLORED = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/magical_girl/texture_colored.png");
    public MagicalGirlPlayerLayer(RenderLayerParent<T, PlayerModel<T>> renderer, EntityModelSet modelSet) {
        super(renderer);
        this.magicalGirlModel = new MagicalGirlModel<>(modelSet.bakeLayer(MagicalGirlModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, T player,
                       float limbSwing, float limbSwingAmount, float partialTick,
                       float ageInTicks, float netHeadYaw, float headPitch) {
        if(!player.getMainHandItem().is(ModItems.MAGIC_WAND))return;
        PlayerModel<T> playerModel = this.getParentModel();

        magicalGirlModel.Head.xRot = playerModel.head.xRot;
        magicalGirlModel.Head.yRot = playerModel.head.yRot;
        magicalGirlModel.Head.zRot = playerModel.head.zRot;

        magicalGirlModel.Body.xRot = playerModel.body.xRot;
        magicalGirlModel.Body.yRot = playerModel.body.yRot;
        magicalGirlModel.Body.zRot = playerModel.body.zRot;

        magicalGirlModel.RightLeg.xRot = playerModel.rightLeg.xRot;
        magicalGirlModel.RightLeg.yRot = playerModel.rightLeg.yRot;
        magicalGirlModel.RightLeg.zRot = playerModel.rightLeg.zRot;

        magicalGirlModel.LeftLeg.xRot = playerModel.leftLeg.xRot;
        magicalGirlModel.LeftLeg.yRot = playerModel.leftLeg.yRot;
        magicalGirlModel.LeftLeg.zRot = playerModel.leftLeg.zRot;

        VertexConsumer vertexConsumer = multiBufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        this.magicalGirlModel.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

        VertexConsumer vertexConsumerColor = multiBufferSource.getBuffer(RenderType.entityCutout(TEXTURE_COLORED));
        this.magicalGirlModel.renderToBuffer(poseStack, vertexConsumerColor, packedLight, OverlayTexture.NO_OVERLAY, 0xFF000000 |player.getMainHandItem().get(DataComponents.DYED_COLOR).rgb());

    }
}
