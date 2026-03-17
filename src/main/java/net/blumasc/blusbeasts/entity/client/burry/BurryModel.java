package net.blumasc.blusbeasts.entity.client.burry;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.BurryEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class BurryModel<T extends BurryEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "burry"), "main");
    private final ModelPart core;
    private final ModelPart tail3;
    private final ModelPart rocks3;
    private final ModelPart tail2;
    private final ModelPart rocks2;
    private final ModelPart tail;
    private final ModelPart rocks;
    private final ModelPart head;

    public BurryModel(ModelPart root) {
        this.core = root.getChild("core");
        this.tail3 = this.core.getChild("tail3");
        this.rocks3 = this.tail3.getChild("rocks3");
        this.tail2 = this.core.getChild("tail2");
        this.rocks2 = this.tail2.getChild("rocks2");
        this.tail = this.core.getChild("tail");
        this.rocks = this.tail.getChild("rocks");
        this.head = this.core.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition tail3 = core.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(32, 39).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition rocks3 = tail3.addOrReplaceChild("rocks3", CubeListBuilder.create().texOffs(48, 16).addBox(-2.0F, -8.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 33).addBox(-1.0F, -9.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.01F))
                .texOffs(56, 37).addBox(2.0F, -10.0F, 0.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 41).addBox(-4.0F, -8.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 45).addBox(-2.0F, -8.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

        PartDefinition tail2 = core.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 21).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition rocks2 = tail2.addOrReplaceChild("rocks2", CubeListBuilder.create().texOffs(32, 59).addBox(-3.0F, -17.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 59).addBox(-2.0F, -15.0F, -6.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 16).addBox(4.0F, -15.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(48, 59).addBox(4.0F, -18.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 22).addBox(-6.0F, -17.0F, -2.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 59).addBox(-6.0F, -14.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(56, 28).addBox(-3.0F, -18.0F, 4.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 62).addBox(1.0F, -14.0F, 4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 15.0F, 0.0F));

        PartDefinition tail = core.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.0F, -5.5F, 12.0F, 9.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -16.0F, -0.5F));

        PartDefinition rocks = tail.addOrReplaceChild("rocks", CubeListBuilder.create().texOffs(40, 29).addBox(3.0F, -25.0F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 0).addBox(-6.0F, -25.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F))
                .texOffs(48, 8).addBox(3.0F, -27.0F, 2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 51).addBox(-1.0F, -25.0F, -7.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(48, 51).addBox(-3.0F, -27.0F, -6.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(0, 54).addBox(0.0F, -23.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F))
                .texOffs(40, 21).addBox(-5.0F, -29.0F, 3.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 54).addBox(-7.0F, -23.0F, -5.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 23.0F, 0.0F));

        PartDefinition head = core.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 39).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -21.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(BurryEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw, headPitch);
        this.animate(entity.idleAnimationState, BurryAnimation.idle, ageInTicks, 1f);
        this.animateWalk(BurryAnimation.walking, limbSwing, limbSwingAmount, 2f, 2.5f);
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
