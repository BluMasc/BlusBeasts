package net.blumasc.blusbeasts.entity.client.heartProjectile;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class HeartProjectile<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "heartprojectile"), "main");
    private final ModelPart heart;

    public HeartProjectile(ModelPart root) {
        this.heart = root.getChild("heart");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition heart = partdefinition.addOrReplaceChild("heart", CubeListBuilder.create().texOffs(26, 25).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-2.0F, 0.3F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 10).addBox(-3.0F, -0.6F, -2.0F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-4.0F, -1.6F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(20, 10).addBox(-4.0F, -3.6F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(16, 20).addBox(1.0F, -3.6F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 5).addBox(-4.0F, -2.6F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 15).addBox(0.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(-0.05F))
                .texOffs(24, 6).addBox(-3.5F, -4.3F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(1.5F, -4.3F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(26, 28).addBox(-3.5F, -4.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 29).addBox(1.5F, -4.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 20).addBox(-4.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(-0.05F))
                .texOffs(8, 29).addBox(-4.3F, -2.6F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.01F))
                .texOffs(14, 29).addBox(3.3F, -2.6F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.01F))
                .texOffs(10, 25).addBox(-1.0F, -1.2F, -2.4F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 25).addBox(-1.0F, -1.1F, 0.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-2.0F, -2.2F, -2.4F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 3).addBox(-2.0F, -2.1F, 0.2F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        heart.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
