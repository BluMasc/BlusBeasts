package net.blumasc.blusbeasts.entity.client.netherLeach;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.client.endsquid.EndsquidModel;
import net.blumasc.blusbeasts.entity.client.endsquid.EndsquidRenderer;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerNetherLeachLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {

    private final NetherLeachModel model;
    private static class LeachPosition {
        public final ModelPart part;
        public final float x, y, z;
        public final float xRot, zRot;
        public LeachPosition(ModelPart part, float x, float y, float z, float xRot, float zRot) {
            this.part = part; this.x = x; this.y = y; this.z = z; this.xRot = xRot; this.zRot = zRot;
        }
    }

    private LeachPosition[] positions;

    public PlayerNetherLeachLayer(RenderLayerParent<T, PlayerModel<T>> parent, EntityModelSet modelSet) {
        super(parent);
        this.model = new NetherLeachModel(modelSet.bakeLayer(NetherLeachModel.LAYER_LOCATION));
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T player,
                       float limbSwing, float limbSwingAmount, float partialTicks,
                       float ageInTicks, float netHeadYaw, float headPitch) {

        int leachCount = player.getInventory().countItem(ModItems.NETHER_LEACH.get());
        if (leachCount <= 0) return;

        long seed = player.getUUID().getMostSignificantBits() ^ player.getUUID().getLeastSignificantBits() ^ 123456789L;
        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            var stack = player.getInventory().getItem(slot);

            if (!stack.isEmpty() && stack.is(ModItems.NETHER_LEACH.get())) {
                seed ^= (long)slot * 0x9E3779B97F4A7C15L;
                seed ^= stack.getCount() * 0xC2B2AE3D27D4EB4FL;
            }
        }
        Random random = new Random(seed);


        if (positions == null) {
            PlayerModel<T> model = this.getParentModel();
            positions = new LeachPosition[] {
                    new LeachPosition(model.rightLeg, -0.57f, 0.4f, -0.0f, -90f,-90f),
                    new LeachPosition(model.leftLeg, 0.57f, 0.3f, -0.0f, -90f,90f),
                    new LeachPosition(model.rightLeg, 0.0f, 0.4f, -0.57f, 90f,0f),
                    new LeachPosition(model.leftLeg, 0.0f, 0.25f, -0.57f, 90f,0f),
                    new LeachPosition(model.rightLeg, 0f, 0.6f, 0.57f, -90f,0f),
                    new LeachPosition(model.leftLeg, 0f, 0.2f, 0.57f, -90f,0f),
                    new LeachPosition(model.rightArm, 0f, 0.3f, -0.57f, 90f,0f),
                    new LeachPosition(model.rightArm, -0.04f, 0.1f, 0.57f, -90f,0f),
                    new LeachPosition(model.rightArm, 0.0f, 1.07f, 0.0f, 180f,0f),
                    new LeachPosition(model.leftArm, 0.0f, 1.07f, 0.0f, 180f,0f),
                    new LeachPosition(model.leftArm, 0.1f, 0.1f, 0.57f, -90f,0f),
                    new LeachPosition(model.leftArm, 0f, 0.3f, -0.57f, 90f,0f),
                    new LeachPosition(model.body, +0.1f, 0.3f, 0.6f, -90f, 0f),
                    new LeachPosition(model.body, -0.1f, 0.2f, 0.6f, -90f, 0f),
                    new LeachPosition(model.body, +0.1f, 0.3f, -0.6f, 90f, 0f),
                    new LeachPosition(model.body, -0.1f, 0.2f, -0.6f, 90f,0f),
                    new LeachPosition(model.head, 0f, -0.25f, 0.8f, -90f,0f),
            };
        }


        int toRender = Math.min(leachCount, positions.length);
        int[] order = getShuffledOrder(random);
        for (int i = 0; i < toRender; i++) {
            LeachPosition pos = positions[order[i]];
            poseStack.pushPose();


            pos.part.translateAndRotate(poseStack);
            poseStack.translate(pos.x, pos.y, pos.z);
            poseStack.mulPose(Axis.XP.rotationDegrees(pos.xRot));
            poseStack.mulPose(Axis.ZP.rotationDegrees(pos.zRot));
            poseStack.mulPose(Axis.YP.rotationDegrees(random.nextFloat()*360));

            float scale = 0.3f;
            poseStack.scale(scale, scale, scale);

            VertexConsumer vertexConsumer = buffer.getBuffer(this.model.renderType(
                    ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/item/nether_leach.png")));
            this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF);

            poseStack.popPose();
        }
    }

    private int[] getShuffledOrder(Random random) {
        int[] order = new int[positions.length];
        for (int i = 0; i < order.length; i++) order[i] = i;
        for (int i = order.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int tmp = order[i];
            order[i] = order[j];
            order[j] = tmp;
        }
        return order;
    }
}