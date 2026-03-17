package net.blumasc.blusbeasts.entity.client.rootling;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.entity.client.netherLeach.NetherLeachAnimation;
import net.blumasc.blusbeasts.entity.custom.RootlingEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class RootlingModel<T extends RootlingEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "rootling"), "main");
    public final ModelPart base;
    public final ModelPart head;
    public final ModelPart headPlant;
    private final ModelPart tentacle1;
    private final ModelPart middle1;
    private final ModelPart tip1;
    private final ModelPart tentacle2;
    private final ModelPart middle2;
    private final ModelPart tip2;
    private final ModelPart tentacle3;
    private final ModelPart middle3;
    private final ModelPart tip3;
    private final ModelPart tentacle4;
    private final ModelPart middle4;
    private final ModelPart tip4;

    public RootlingModel(ModelPart root) {
        this.base = root.getChild("base");
        this.head = this.base.getChild("head");
        this.headPlant = this.head.getChild("headPlant");
        this.tentacle1 = this.base.getChild("tentacle1");
        this.middle1 = this.tentacle1.getChild("middle1");
        this.tip1 = this.middle1.getChild("tip1");
        this.tentacle2 = this.base.getChild("tentacle2");
        this.middle2 = this.tentacle2.getChild("middle2");
        this.tip2 = this.middle2.getChild("tip2");
        this.tentacle3 = this.base.getChild("tentacle3");
        this.middle3 = this.tentacle3.getChild("middle3");
        this.tip3 = this.middle3.getChild("tip3");
        this.tentacle4 = this.base.getChild("tentacle4");
        this.middle4 = this.tentacle4.getChild("middle4");
        this.tip4 = this.middle4.getChild("tip4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-1.5F, -2.8F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition headPlant = head.addOrReplaceChild("headPlant", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tentacle1 = base.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(8, 6).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2.0F, 0.5F));

        PartDefinition middle1 = tentacle1.addOrReplaceChild("middle1", CubeListBuilder.create().texOffs(12, 0).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition tip1 = middle1.addOrReplaceChild("tip1", CubeListBuilder.create().texOffs(12, 12).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition tentacle2 = base.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(8, 10).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -2.0F, 0.5F));

        PartDefinition middle2 = tentacle2.addOrReplaceChild("middle2", CubeListBuilder.create().texOffs(12, 3).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition tip2 = middle2.addOrReplaceChild("tip2", CubeListBuilder.create().texOffs(8, 14).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition tentacle3 = base.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 11).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2.0F, -0.5F));

        PartDefinition middle3 = tentacle3.addOrReplaceChild("middle3", CubeListBuilder.create().texOffs(12, 6).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition tip3 = middle3.addOrReplaceChild("tip3", CubeListBuilder.create().texOffs(0, 15).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 1.5F, 0.0F));

        PartDefinition tentacle4 = base.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(4, 11).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, -2.0F, -0.5F));

        PartDefinition middle4 = tentacle4.addOrReplaceChild("middle4", CubeListBuilder.create().texOffs(12, 9).addBox(-0.5F, -0.2F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition tip4 = middle4.addOrReplaceChild("tip4", CubeListBuilder.create().texOffs(4, 15).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 1.5F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(RootlingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        this.animateWalk(RootlingAnimation.walking, limbSwing, limbSwingAmount, 1f, 1f);
        this.animate(entity.idleAnimationState, RootlingAnimation.idle, ageInTicks, 1f);
        this.animate(entity.diggingAnimationState, RootlingAnimation.digging, ageInTicks, 1f);
        this.animate(entity.happyAnimationState, RootlingAnimation.happy, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        //this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.base;
    }
}