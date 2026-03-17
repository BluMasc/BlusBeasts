package net.blumasc.blusbeasts.entity.client.grave;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.burry.BurryAnimation;
import net.blumasc.blusbeasts.entity.custom.GraveEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class GraveModel<T extends GraveEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "grave"), "main");
    private final ModelPart core;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart upperJaw;
    private final ModelPart tail;
    private final ModelPart rocks;
    private final ModelPart tail2;
    private final ModelPart rocks2;
    private final ModelPart tail3;
    private final ModelPart rocks3;
    private final ModelPart arm;
    private final ModelPart bone;
    private final ModelPart bone3;
    private final ModelPart arm2;
    private final ModelPart bone2;
    private final ModelPart bone4;

    public GraveModel(ModelPart root) {
        this.core = root.getChild("core");
        this.body = this.core.getChild("body");
        this.head = this.body.getChild("head");
        this.upperJaw = this.head.getChild("upperJaw");
        this.tail = this.body.getChild("tail");
        this.rocks = this.tail.getChild("rocks");
        this.tail2 = this.body.getChild("tail2");
        this.rocks2 = this.tail2.getChild("rocks2");
        this.tail3 = this.body.getChild("tail3");
        this.rocks3 = this.tail3.getChild("rocks3");
        this.arm = this.body.getChild("arm");
        this.bone = this.arm.getChild("bone");
        this.bone3 = this.bone.getChild("bone3");
        this.arm2 = this.body.getChild("arm2");
        this.bone2 = this.arm2.getChild("bone2");
        this.bone4 = this.bone2.getChild("bone4");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 29.0F, 0.0F));

        PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, -30.0F, -3.0F, 26.0F, 9.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(54, 15).addBox(-5.0F, -21.0F, -3.0F, 10.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(86, 8).addBox(2.0F, -28.0F, -5.0F, 5.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 91).addBox(1.0F, -29.0F, -4.0F, 5.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 79).addBox(-8.0F, -26.0F, -4.0F, 6.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(32, 63).addBox(-7.0F, -28.0F, 0.0F, 10.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(64, 8).addBox(5.0F, -21.0F, -2.0F, 5.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 86).addBox(-12.0F, -31.0F, -4.0F, 7.0F, 3.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(70, 79).addBox(5.0F, -31.0F, -1.0F, 7.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(54, 27).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 55).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -30.0F, 0.0F));

        PartDefinition upperJaw = head.addOrReplaceChild("upperJaw", CubeListBuilder.create().texOffs(40, 37).addBox(-4.0F, -6.0F, -7.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(40, 50).addBox(-4.0F, -5.0F, -7.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(-0.02F))
                .texOffs(24, 75).addBox(-4.0F, -7.0F, -7.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.2F))
                .texOffs(96, 53).addBox(0.0F, -5.0F, 0.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 105).addBox(-1.0F, -6.0F, -7.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -2.0F, 3.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 15).addBox(-7.0F, -5.0F, -6.5F, 14.0F, 9.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition rocks = tail.addOrReplaceChild("rocks", CubeListBuilder.create().texOffs(24, 86).addBox(4.0F, -25.0F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 25).addBox(-7.0F, -25.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F))
                .texOffs(70, 88).addBox(4.0F, -27.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(86, 88).addBox(-1.0F, -25.0F, -7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(90, 61).addBox(-3.0F, -27.0F, -7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(90, 69).addBox(0.0F, -23.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
                .texOffs(86, 17).addBox(-5.0F, -29.0F, 4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(40, 91).addBox(-8.0F, -23.0F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 23.0F, -0.5F));

        PartDefinition tail2 = body.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 37).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition rocks2 = tail2.addOrReplaceChild("rocks2", CubeListBuilder.create().texOffs(32, 55).addBox(-3.0F, -17.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 59).addBox(-2.0F, -15.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 94).addBox(4.0F, -15.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(48, 75).addBox(4.0F, -18.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 42).addBox(-6.0F, -17.0F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 86).addBox(-6.0F, -14.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(96, 48).addBox(-3.0F, -18.0F, 4.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(70, 96).addBox(1.0F, -14.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition tail3 = body.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(72, 37).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition rocks3 = tail3.addOrReplaceChild("rocks3", CubeListBuilder.create().texOffs(78, 96).addBox(-2.0F, -8.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(86, 96).addBox(-1.0F, -9.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(94, 96).addBox(2.0F, -10.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 99).addBox(-4.0F, -8.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(8, 99).addBox(-2.0F, -8.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition arm = body.addOrReplaceChild("arm", CubeListBuilder.create().texOffs(60, 63).addBox(-1.0F, -2.0F, -2.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 94).addBox(1.0F, -3.0F, -3.0F, 6.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(94, 82).addBox(2.0F, 0.0F, 1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(13.0F, -26.0F, 0.0F));

        PartDefinition bone = arm.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 66).addBox(-1.0F, -2.0F, -2.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
                .texOffs(94, 0).addBox(3.0F, -3.0F, -2.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(16, 94).addBox(0.0F, 0.0F, 0.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 0.0F, 0.0F));

        PartDefinition bone3 = bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(72, 49).addBox(-1.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 99).addBox(4.0F, -5.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 99).addBox(4.0F, -5.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 100).addBox(4.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(64, 100).addBox(4.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, 1.0F, 0.0F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(64, 0).addBox(-10.0F, -2.0F, -2.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(96, 37).addBox(-6.0F, -2.0F, -2.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(80, 100).addBox(-7.0F, 0.0F, 1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-13.0F, -26.0F, 0.0F));

        PartDefinition bone2 = arm2.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(60, 71).addBox(-10.0F, -2.0F, -2.0F, 11.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
                .texOffs(94, 77).addBox(-6.0F, 1.0F, -1.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.02F))
                .texOffs(86, 33).addBox(-5.0F, -3.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 0.0F, 0.0F));

        PartDefinition bone4 = bone2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(0, 74).addBox(-5.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(48, 99).addBox(-6.0F, -5.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 100).addBox(-6.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(32, 100).addBox(-6.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(72, 100).addBox(-6.0F, -5.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-10.0F, 1.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(GraveEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animate(entity.idleAnimationState, GraveAnimation.idle, ageInTicks, 1f);
        this.animate(entity.whirlingAnimationState, GraveAnimation.whirling, ageInTicks, 1f);
        this.animate(entity.throwAnimationState, GraveAnimation.throwing, ageInTicks, 0.5f);
        this.animate(entity.hitAnimationState, GraveAnimation.hit, ageInTicks, 1f);
        this.animate(entity.screamAnimationState, GraveAnimation.scream, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        core.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.core;
    }
}
