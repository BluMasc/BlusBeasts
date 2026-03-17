package net.blumasc.blusbeasts.entity.client.mimicart;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.prayfinder.PrayfinderAnimation;
import net.blumasc.blusbeasts.entity.custom.MimicartEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class MimicartModel <T extends MimicartEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "minecraftmimic"), "main");
    private final ModelPart core;
    private final ModelPart bottom;
    private final ModelPart jawstrings;
    private final ModelPart frontjaw;
    private final ModelPart front;
    private final ModelPart backjaw;
    private final ModelPart back;
    private final ModelPart tongue;
    private final ModelPart tongueMid;
    private final ModelPart toungeTipStart;
    private final ModelPart tongueTipEnd;

    public MimicartModel(ModelPart root) {
        this.core = root.getChild("core");
        this.bottom = this.core.getChild("bottom");
        this.jawstrings = this.bottom.getChild("jawstrings");
        this.frontjaw = this.core.getChild("frontjaw");
        this.front = this.frontjaw.getChild("front");
        this.backjaw = this.core.getChild("backjaw");
        this.back = this.backjaw.getChild("back");
        this.tongue = this.core.getChild("tongue");
        this.tongueMid = this.tongue.getChild("tongueMid");
        this.toungeTipStart = this.tongueMid.getChild("toungeTipStart");
        this.tongueTipEnd = this.toungeTipStart.getChild("tongueTipEnd");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bottom = core.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -8.0F, -20.0F, 20.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition right_r1 = bottom.addOrReplaceChild("right_r1", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-8.0F, -8.0F, -8.0F, 8.0F, 6.0F, 2.0F, new CubeDeformation(-0.01F)).mirror(false)
                .texOffs(42, 0).addBox(-8.0F, -8.0F, 6.0F, 8.0F, 6.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(4.0F, 0.0F, -21.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition jawstrings = bottom.addOrReplaceChild("jawstrings", CubeListBuilder.create().texOffs(24, 10).addBox(9.0F, -6.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F))
                .texOffs(24, 10).addBox(-10.0F, -6.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F))
                .texOffs(24, 10).addBox(9.0F, 5.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F))
                .texOffs(24, 10).addBox(-10.0F, 5.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.01F))
                .texOffs(24, 10).addBox(8.1F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 10).addBox(-9.1F, -1.0F, 0.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -19.0F));

        PartDefinition frontjaw = core.addOrReplaceChild("frontjaw", CubeListBuilder.create().texOffs(42, 0).mirror().addBox(-3.0F, -5.0F, 6.0F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 37).addBox(-3.0F, -5.0F, -8.0F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).addBox(-4.0F, -7.0F, -7.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -5.0F, 0.0F));

        PartDefinition bottom_r1 = frontjaw.addOrReplaceChild("bottom_r1", CubeListBuilder.create().texOffs(36, 17).addBox(-10.0F, -28.0F, -19.0F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 22.0F, -20.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition front = frontjaw.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, 15.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -20.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition backjaw = core.addOrReplaceChild("backjaw", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(-5.0F, -5.0F, -8.0F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(42, 0).addBox(-5.0F, -5.0F, 6.0F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 48).mirror().addBox(-5.0F, -7.0F, -7.0F, 9.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -5.0F, 0.0F));

        PartDefinition bottom_r2 = backjaw.addOrReplaceChild("bottom_r2", CubeListBuilder.create().texOffs(36, 34).addBox(-10.0F, -28.0F, -19.0F, 10.0F, 16.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 22.0F, -20.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition back = backjaw.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 17).addBox(-8.0F, 15.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -20.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition tongue = core.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(20, 37).addBox(-1.0F, -3.0F, -2.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition tongueMid = tongue.addOrReplaceChild("tongueMid", CubeListBuilder.create().texOffs(40, 51).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition toungeTipStart = tongueMid.addOrReplaceChild("toungeTipStart", CubeListBuilder.create().texOffs(42, 10).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition tongueTipEnd = toungeTipStart.addOrReplaceChild("tongueTipEnd", CubeListBuilder.create().texOffs(32, 37).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(MimicartEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.attackAnimationState, MimicartAnimation.bite, ageInTicks, 1f);
        this.animate(entity.lickAnimationState, MimicartAnimation.lick, ageInTicks, 1f);
        this.animate(entity.wiggleAnimationState, MimicartAnimation.wiggleTongue, ageInTicks, 1f);
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
