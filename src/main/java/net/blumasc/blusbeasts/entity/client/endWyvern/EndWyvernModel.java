package net.blumasc.blusbeasts.entity.client.endWyvern;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderAnimation;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class EndWyvernModel<T extends EndWyvernEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "enderwyvern"), "main");
    private final ModelPart core;
    final ModelPart body;
    private final ModelPart tail;
    private final ModelPart tailmiddle;
    private final ModelPart tailtip;
    private final ModelPart neck;
    private final ModelPart neckMiddle;
    private final ModelPart neckEnd;
    private final ModelPart head;
    private final ModelPart hair;
    private final ModelPart hairMid;
    private final ModelPart hairLowerMid;
    private final ModelPart hairEnd;
    final ModelPart wings;
    private final ModelPart wingsRight;
    private final ModelPart upperRightWing;
    private final ModelPart upperRightWingMidSegment;
    private final ModelPart upperRightWingMidSegmentSecond;
    private final ModelPart upperRightWingEnd;
    private final ModelPart lowerRightWing;
    private final ModelPart lowerRightWingMid;
    private final ModelPart lowerRightWingEnd;
    private final ModelPart wingsLeft;
    private final ModelPart upperLeftWing;
    private final ModelPart upperLeftWingMidSegment;
    private final ModelPart upperLeftWingMidSegmentSecond;
    private final ModelPart upperLeftWingEnd;
    private final ModelPart lowerLeftWing;
    private final ModelPart lowerLeftWingMid;
    private final ModelPart lowerLeftWingEnd;
    private final ModelPart rightArm2;
    private final ModelPart rightLowerArm;
    private final ModelPart rightHand;
    private final ModelPart leftArm2;
    private final ModelPart leftLowerArm;
    private final ModelPart leftHand;

    public EndWyvernModel(ModelPart root) {
        this.core = root.getChild("core");
        this.body = this.core.getChild("body");
        this.tail = this.body.getChild("tail");
        this.tailmiddle = this.tail.getChild("tailmiddle");
        this.tailtip = this.tailmiddle.getChild("tailtip");
        this.neck = this.body.getChild("neck");
        this.neckMiddle = this.neck.getChild("neckMiddle");
        this.neckEnd = this.neckMiddle.getChild("neckEnd");
        this.head = this.neckEnd.getChild("head");
        this.hair = this.head.getChild("hair");
        this.hairMid = this.hair.getChild("hairMid");
        this.hairLowerMid = this.hairMid.getChild("hairLowerMid");
        this.hairEnd = this.hairLowerMid.getChild("hairEnd");
        this.wings = this.body.getChild("wings");
        this.wingsRight = this.wings.getChild("wingsRight");
        this.upperRightWing = this.wingsRight.getChild("upperRightWing");
        this.upperRightWingMidSegment = this.upperRightWing.getChild("upperRightWingMidSegment");
        this.upperRightWingMidSegmentSecond = this.upperRightWingMidSegment.getChild("upperRightWingMidSegmentSecond");
        this.upperRightWingEnd = this.upperRightWingMidSegmentSecond.getChild("upperRightWingEnd");
        this.lowerRightWing = this.wingsRight.getChild("lowerRightWing");
        this.lowerRightWingMid = this.lowerRightWing.getChild("lowerRightWingMid");
        this.lowerRightWingEnd = this.lowerRightWingMid.getChild("lowerRightWingEnd");
        this.wingsLeft = this.wings.getChild("wingsLeft");
        this.upperLeftWing = this.wingsLeft.getChild("upperLeftWing");
        this.upperLeftWingMidSegment = this.upperLeftWing.getChild("upperLeftWingMidSegment");
        this.upperLeftWingMidSegmentSecond = this.upperLeftWingMidSegment.getChild("upperLeftWingMidSegmentSecond");
        this.upperLeftWingEnd = this.upperLeftWingMidSegmentSecond.getChild("upperLeftWingEnd");
        this.lowerLeftWing = this.wingsLeft.getChild("lowerLeftWing");
        this.lowerLeftWingMid = this.lowerLeftWing.getChild("lowerLeftWingMid");
        this.lowerLeftWingEnd = this.lowerLeftWingMid.getChild("lowerLeftWingEnd");
        this.rightArm2 = this.body.getChild("rightArm2");
        this.rightLowerArm = this.rightArm2.getChild("rightLowerArm");
        this.rightHand = this.rightLowerArm.getChild("rightHand");
        this.leftArm2 = this.body.getChild("leftArm2");
        this.leftLowerArm = this.leftArm2.getChild("leftLowerArm");
        this.leftHand = this.leftLowerArm.getChild("leftHand");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 45).addBox(-3.0F, -19.0F, -1.0F, 6.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-5.0F, -25.0F, -2.0F, 10.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -27.0F, -3.0F, 12.0F, 6.0F, 8.0F, new CubeDeformation(-0.2F))
                .texOffs(66, 32).addBox(-2.0F, -19.0F, 1.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 45).addBox(-1.0F, -16.0F, 1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(36, 61).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition tailmiddle = tail.addOrReplaceChild("tailmiddle", CubeListBuilder.create().texOffs(12, 54).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 0.0F));

        PartDefinition tailtip = tailmiddle.addOrReplaceChild("tailtip", CubeListBuilder.create().texOffs(62, 69).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(62, 0).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -25.0F, 1.5F));

        PartDefinition neckMiddle = neck.addOrReplaceChild("neckMiddle", CubeListBuilder.create().texOffs(62, 49).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition neckEnd = neckMiddle.addOrReplaceChild("neckEnd", CubeListBuilder.create().texOffs(0, 65).addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition head = neckEnd.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, -5.0F, -6.0F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(56, 60).addBox(-2.0F, -2.0F, -9.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 38).addBox(0.75F, -3.0F, -8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(12, 59).addBox(-1.75F, -3.0F, -8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 6).addBox(-1.0F, -7.0F, -4.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(58, 44).addBox(-2.0F, -6.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 68).addBox(0.0F, -7.0F, -6.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -0.5F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(56, 69).addBox(-3.0F, -2.4F, -1.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -3.0F, 0.0F, 0.0453F, 0.478F, 0.0983F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(68, 30).addBox(0.0F, -2.4F, -1.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -3.0F, 0.0F, 0.0453F, -0.478F, -0.0983F));

        PartDefinition hair = head.addOrReplaceChild("hair", CubeListBuilder.create().texOffs(48, 34).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(32, 14).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 8.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -5.0F, 1.0F));

        PartDefinition hairMid = hair.addOrReplaceChild("hairMid", CubeListBuilder.create().texOffs(44, 50).addBox(-2.0F, -2.0F, 1.0F, 4.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-3.0F, -1.0F, 0.0F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition hairLowerMid = hairMid.addOrReplaceChild("hairLowerMid", CubeListBuilder.create().texOffs(44, 67).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(56, 65).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 5.0F));

        PartDefinition hairEnd = hairLowerMid.addOrReplaceChild("hairEnd", CubeListBuilder.create().texOffs(12, 68).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, -23.0F, 3.0F));

        PartDefinition wingsRight = wings.addOrReplaceChild("wingsRight", CubeListBuilder.create(), PartPose.offset(4.0F, 0.0F, 0.0F));

        PartDefinition upperRightWing = wingsRight.addOrReplaceChild("upperRightWing", CubeListBuilder.create().texOffs(26, 34).addBox(-1.0F, -5.0F, 1.0F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition upperRightWingMidSegment = upperRightWing.addOrReplaceChild("upperRightWingMidSegment", CubeListBuilder.create().texOffs(0, 54).addBox(0.0F, -6.0F, 0.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(10.0F, -2.0F, 1.0F));

        PartDefinition upperRightWingMidSegmentSecond = upperRightWingMidSegment.addOrReplaceChild("upperRightWingMidSegmentSecond", CubeListBuilder.create().texOffs(16, 50).addBox(0.0F, -6.0F, 0.0F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(6.0F, 0.0F, 0.0F));

        PartDefinition upperRightWingEnd = upperRightWingMidSegmentSecond.addOrReplaceChild("upperRightWingEnd", CubeListBuilder.create().texOffs(12, 61).addBox(0.0F, -3.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(7.0F, 0.0F, 0.0F));

        PartDefinition lowerRightWing = wingsRight.addOrReplaceChild("lowerRightWing", CubeListBuilder.create().texOffs(52, 14).addBox(-1.0F, -4.0F, -1.0F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition lowerRightWingMid = lowerRightWing.addOrReplaceChild("lowerRightWingMid", CubeListBuilder.create().texOffs(22, 41).addBox(0.0F, -6.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(8.0F, 0.0F, -1.0F));

        PartDefinition lowerRightWingEnd = lowerRightWingMid.addOrReplaceChild("lowerRightWingEnd", CubeListBuilder.create().texOffs(52, 26).addBox(0.0F, -4.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(9.0F, 0.0F, 0.0F));

        PartDefinition wingsLeft = wings.addOrReplaceChild("wingsLeft", CubeListBuilder.create(), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition upperLeftWing = wingsLeft.addOrReplaceChild("upperLeftWing", CubeListBuilder.create().texOffs(0, 38).addBox(-10.0F, -5.0F, 1.0F, 11.0F, 7.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition upperLeftWingMidSegment = upperLeftWing.addOrReplaceChild("upperLeftWingMidSegment", CubeListBuilder.create().texOffs(44, 56).addBox(-6.0F, -6.0F, 0.0F, 6.0F, 11.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-10.0F, -2.0F, 1.0F));

        PartDefinition upperLeftWingMidSegmentSecond = upperLeftWingMidSegment.addOrReplaceChild("upperLeftWingMidSegmentSecond", CubeListBuilder.create().texOffs(30, 50).addBox(-7.0F, -6.0F, 0.0F, 7.0F, 11.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-6.0F, 0.0F, 0.0F));

        PartDefinition upperLeftWingEnd = upperLeftWingMidSegmentSecond.addOrReplaceChild("upperLeftWingEnd", CubeListBuilder.create().texOffs(24, 61).addBox(-6.0F, -3.0F, 0.0F, 6.0F, 7.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-7.0F, 0.0F, 0.0F));

        PartDefinition lowerLeftWing = wingsLeft.addOrReplaceChild("lowerLeftWing", CubeListBuilder.create().texOffs(52, 20).addBox(-8.0F, -4.0F, -1.0F, 9.0F, 6.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition lowerLeftWingMid = lowerLeftWing.addOrReplaceChild("lowerLeftWingMid", CubeListBuilder.create().texOffs(40, 41).addBox(-9.0F, -6.0F, 0.0F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-8.0F, 0.0F, -1.0F));

        PartDefinition lowerLeftWingEnd = lowerLeftWingMid.addOrReplaceChild("lowerLeftWingEnd", CubeListBuilder.create().texOffs(56, 6).addBox(-8.0F, -4.0F, 0.0F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-9.0F, 0.0F, 0.0F));

        PartDefinition rightArm2 = body.addOrReplaceChild("rightArm2", CubeListBuilder.create().texOffs(56, 56).addBox(-1.0F, -1.0F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(5.0F, -24.0F, 0.0F));

        PartDefinition rightLowerArm = rightArm2.addOrReplaceChild("rightLowerArm", CubeListBuilder.create().texOffs(26, 26).addBox(-1.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(6.0F, 0.0F, -1.0F));

        PartDefinition rightHand = rightLowerArm.addOrReplaceChild("rightHand", CubeListBuilder.create().texOffs(24, 68).addBox(0.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 69).addBox(1.5F, -2.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(70, 14).addBox(2.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.11F))
                .texOffs(70, 18).addBox(2.5F, 0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.11F))
                .texOffs(44, 23).addBox(2.0F, -3.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(32, 23).addBox(3.0F, -0.2F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 12).addBox(3.0F, 1.2F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 0.0F, 0.0F));

        PartDefinition leftArm2 = body.addOrReplaceChild("leftArm2", CubeListBuilder.create().texOffs(58, 40).addBox(-6.0F, -1.0F, -2.0F, 7.0F, 2.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(-5.0F, -24.0F, 0.0F));

        PartDefinition leftLowerArm = leftArm2.addOrReplaceChild("leftLowerArm", CubeListBuilder.create().texOffs(26, 30).addBox(-10.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-6.0F, 0.0F, -1.0F));

        PartDefinition leftHand = leftLowerArm.addOrReplaceChild("leftHand", CubeListBuilder.create().texOffs(68, 26).addBox(-3.0F, -1.5F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(68, 69).addBox(-3.5F, 0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.11F))
                .texOffs(70, 16).addBox(-2.5F, -2.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(70, 20).addBox(-3.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.11F))
                .texOffs(68, 12).addBox(-6.0F, -3.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(52, 32).addBox(-7.0F, 1.2F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(66, 37).addBox(-7.0F, -0.2F, -0.5F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(EndWyvernEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        this.animate(entity.idleAnimationState, EndWyvernAnimations.idle, ageInTicks, 1f);
        this.animate(entity.holdingAnimationState, EndWyvernAnimations.holding, ageInTicks, 1f);
        this.animate(entity.flyingAnimationState, EndWyvernAnimations.flying, ageInTicks, 1f);
    }

    private void applyHeadRotation(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30f, 30f);
        headPitch = Mth.clamp(headPitch, -25f, 45);

        this.head.yRot = headYaw * ((float)Math.PI / 180f);
        this.hair.yRot = -headYaw * ((float)Math.PI / 180f);
        this.head.xRot = headPitch *  ((float)Math.PI / 180f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        poseStack.pushPose();

        boolean oldVisible = wings.visible;
        wings.visible = false;

        core.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);

        wings.visible = oldVisible;

        poseStack.popPose();

    }

    @Override
    public ModelPart root() {
        return this.core;
    }
}
