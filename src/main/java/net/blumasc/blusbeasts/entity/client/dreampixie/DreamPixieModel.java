package net.blumasc.blusbeasts.entity.client.dreampixie;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.entity.client.burry.BurryAnimation;
import net.blumasc.blusbeasts.entity.custom.DreamPixie;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;

public class DreamPixieModel<T extends DreamPixie> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath("modid", "dreamfairy"), "main");
    private final ModelPart core;
    private final ModelPart body;
    private final ModelPart tentacles;
    private final ModelPart frontRightTentacle;
    private final ModelPart frontRightFoot;
    private final ModelPart frontLeftTentacle;
    private final ModelPart frontLeftFoot;
    private final ModelPart backRightTentacle;
    private final ModelPart frontRightFoot2;
    private final ModelPart backLeftTentacle;
    private final ModelPart frontLeftFoot2;
    private final ModelPart midRightTentacle;
    private final ModelPart midRightFoot;
    private final ModelPart midLeftTentacle;
    private final ModelPart midLeftFoot;
    private final ModelPart rightArm;
    private final ModelPart rightHand;
    private final ModelPart leftArm;
    private final ModelPart leftHand;
    private final ModelPart head;
    private final ModelPart wings;
    private final ModelPart rightWing;
    private final ModelPart rightWingTip;
    private final ModelPart leftWing;
    private final ModelPart leftWingTip;

    public DreamPixieModel(ModelPart root) {
        this.core = root.getChild("core");
        this.body = this.core.getChild("body");
        this.tentacles = this.body.getChild("tentacles");
        this.frontRightTentacle = this.tentacles.getChild("frontRightTentacle");
        this.frontRightFoot = this.frontRightTentacle.getChild("frontRightFoot");
        this.frontLeftTentacle = this.tentacles.getChild("frontLeftTentacle");
        this.frontLeftFoot = this.frontLeftTentacle.getChild("frontLeftFoot");
        this.backRightTentacle = this.tentacles.getChild("backRightTentacle");
        this.frontRightFoot2 = this.backRightTentacle.getChild("frontRightFoot2");
        this.backLeftTentacle = this.tentacles.getChild("backLeftTentacle");
        this.frontLeftFoot2 = this.backLeftTentacle.getChild("frontLeftFoot2");
        this.midRightTentacle = this.tentacles.getChild("midRightTentacle");
        this.midRightFoot = this.midRightTentacle.getChild("midRightFoot");
        this.midLeftTentacle = this.tentacles.getChild("midLeftTentacle");
        this.midLeftFoot = this.midLeftTentacle.getChild("midLeftFoot");
        this.rightArm = this.body.getChild("rightArm");
        this.rightHand = this.rightArm.getChild("rightHand");
        this.leftArm = this.body.getChild("leftArm");
        this.leftHand = this.leftArm.getChild("leftHand");
        this.head = this.body.getChild("head");
        this.wings = this.body.getChild("wings");
        this.rightWing = this.wings.getChild("rightWing");
        this.rightWingTip = this.rightWing.getChild("rightWingTip");
        this.leftWing = this.wings.getChild("leftWing");
        this.leftWingTip = this.leftWing.getChild("leftWingTip");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.8F, 20.0F, -0.7F));

        PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 0).addBox(-0.7F, -3.0F, -0.3F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.01F))
                .texOffs(0, 17).addBox(-0.7F, -3.9F, -0.3F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offset(-1.6F, 0.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 21).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.8F, 0.0F, -0.3F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r2 = body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 21).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.7F, 0.0F, 1.2F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r3 = body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(20, 12).addBox(-1.0F, -1.0F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(2.3F, 0.0F, 1.2F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r4 = body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 21).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.8F, 0.0F, 2.6F, -0.7854F, 0.0F, 0.0F));

        PartDefinition tentacles = body.addOrReplaceChild("tentacles", CubeListBuilder.create(), PartPose.offset(1.6F, 0.0F, 0.0F));

        PartDefinition frontRightTentacle = tentacles.addOrReplaceChild("frontRightTentacle", CubeListBuilder.create().texOffs(16, 29).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.6F, 0.0F, 0.0F));

        PartDefinition frontRightFoot = frontRightTentacle.addOrReplaceChild("frontRightFoot", CubeListBuilder.create().texOffs(28, 0).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.3927F, 0.0F));

        PartDefinition frontLeftTentacle = tentacles.addOrReplaceChild("frontLeftTentacle", CubeListBuilder.create().texOffs(4, 30).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

        PartDefinition frontLeftFoot = frontLeftTentacle.addOrReplaceChild("frontLeftFoot", CubeListBuilder.create().texOffs(28, 3).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition backRightTentacle = tentacles.addOrReplaceChild("backRightTentacle", CubeListBuilder.create().texOffs(6, 30).addBox(-0.3F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8F, 0.0F, 2.4F, 0.0F, -0.3927F, 0.0F));

        PartDefinition frontRightFoot2 = backRightTentacle.addOrReplaceChild("frontRightFoot2", CubeListBuilder.create().texOffs(18, 28).addBox(-0.3F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition backLeftTentacle = tentacles.addOrReplaceChild("backLeftTentacle", CubeListBuilder.create().texOffs(30, 6).addBox(-0.7F, 0.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2F, 0.0F, 2.4F, 0.0F, 0.3927F, 0.0F));

        PartDefinition frontLeftFoot2 = backLeftTentacle.addOrReplaceChild("frontLeftFoot2", CubeListBuilder.create().texOffs(28, 25).addBox(-0.7F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition midRightTentacle = tentacles.addOrReplaceChild("midRightTentacle", CubeListBuilder.create().texOffs(12, 29).addBox(-0.2F, 0.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.8F, 0.0F, 1.2F));

        PartDefinition midRightFoot = midRightTentacle.addOrReplaceChild("midRightFoot", CubeListBuilder.create().texOffs(26, 28).addBox(-0.7F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition midLeftTentacle = tentacles.addOrReplaceChild("midLeftTentacle", CubeListBuilder.create().texOffs(14, 29).addBox(0.2F, 0.0F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2F, 0.0F, 1.2F));

        PartDefinition midLeftFoot = midLeftTentacle.addOrReplaceChild("midLeftFoot", CubeListBuilder.create().texOffs(8, 29).addBox(-0.3F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(30, 9).addBox(0.0F, -0.9F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, 1.2F, 0.0F, 0.0F, 0.5236F));

        PartDefinition rightHand = rightArm.addOrReplaceChild("rightHand", CubeListBuilder.create().texOffs(16, 6).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(30, 12).addBox(0.0F, -0.9F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6F, -3.0F, 1.2F, 0.0F, 0.0F, -0.5236F));

        PartDefinition leftHand = leftArm.addOrReplaceChild("leftHand", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -3.1F, -1.5F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.7F))
                .texOffs(24, 17).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.8F, -4.0F, 0.7F));

        PartDefinition cube_r5 = head.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(24, 17).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.offsetAndRotation(0.0F, -1.8F, 2.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = head.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(10, 26).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.0F, 0.4F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(20, 25).addBox(0.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -2.0F, 0.4F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 27).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.1F, 0.7F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(12, 17).addBox(-1.0F, -1.0F, -1.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, 0.9F, 0.2F, 0.1309F, 0.0F, 0.0F));

        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.8F, -1.5F, 2.7F));

        PartDefinition rightWing = wings.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(20, 6).addBox(-3.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightWingTip = rightWing.addOrReplaceChild("rightWingTip", CubeListBuilder.create().texOffs(0, 8).addBox(-5.0F, -6.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 0.0F, 0.0F));

        PartDefinition leftWing = wings.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(2, 21).addBox(0.0F, -4.0F, 0.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition leftWingTip = leftWing.addOrReplaceChild("leftWingTip", CubeListBuilder.create().texOffs(10, 8).addBox(0.0F, -6.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(DreamPixie entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        if(entity.getPanicing()>0){
            this.applyRandomHeadRotation(entity.getRandom());
        }else {
            this.applyHeadRotation(netHeadYaw, headPitch);
        }
        this.animate(entity.idleAnimationState, DreamPixieAnimation.idle, ageInTicks, 1f);
        this.animate(entity.armSwingAnimationState, DreamPixieAnimation.armsIdle, ageInTicks, 1f);
        this.animate(entity.eatingAnimationState, DreamPixieAnimation.eat, ageInTicks, 1f);
        this.animate(entity.scaleAnimationState, DreamPixieAnimation.scale, ageInTicks, 1f);
    }

    private void applyRandomHeadRotation(RandomSource random) {
        float headYaw = (random.nextFloat()*60f)-30f;
        float headPitch = (random.nextFloat()*70f)-25f;

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
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
