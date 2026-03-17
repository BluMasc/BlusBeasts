package net.blumasc.blusbeasts.entity.client.deepshoveler;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderAnimation;
import net.blumasc.blusbeasts.entity.custom.DeepshovelerEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class DeepshovelerModel<T extends DeepshovelerEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "shademole"), "main");
    private final ModelPart core;
    private final ModelPart body;
    private final ModelPart foot1;
    private final ModelPart foot2;
    private final ModelPart arm1;
    private final ModelPart arm2;
    private final ModelPart head;
    private final ModelPart snout;

    public DeepshovelerModel(ModelPart root) {
        this.core = root.getChild("core");
        this.body = this.core.getChild("body");
        this.foot1 = this.body.getChild("foot1");
        this.foot2 = this.body.getChild("foot2");
        this.arm1 = this.body.getChild("arm1");
        this.arm2 = this.body.getChild("arm2");
        this.head = this.body.getChild("head");
        this.snout = this.head.getChild("snout");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -15.0F, -5.0F, 10.0F, 15.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition foot1 = body.addOrReplaceChild("foot1", CubeListBuilder.create().texOffs(24, 25).addBox(-2.0F, -0.01F, -5.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, -4.0F));

        PartDefinition foot2 = body.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(24, 32).addBox(-3.0F, -0.01F, -5.0F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, -4.0F));

        PartDefinition arm1 = body.addOrReplaceChild("arm1", CubeListBuilder.create().texOffs(0, 37).addBox(-1.0F, -1.5F, -1.5F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 6).addBox(5.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(5.0F, -12.0F, 0.0F));

        PartDefinition arm2 = body.addOrReplaceChild("arm2", CubeListBuilder.create().texOffs(20, 39).addBox(-6.0F, -1.5F, -1.5F, 7.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 12).addBox(-9.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.offset(-5.0F, -12.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 25).addBox(-3.0F, -6.0F, -4.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(40, 18).addBox(-2.0F, -4.0F, -6.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(40, 0).addBox(-3.0F, -4.0F, 2.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -15.0F, 0.0F));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(40, 39).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -6.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(DeepshovelerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        this.animate(entity.idleAnimationState, DeepshovelerAnimation.idle, ageInTicks, 1f);
        this.animate(entity.diggingAnimationState, DeepshovelerAnimation.digging, ageInTicks, 1f);
        this.animate(entity.shootAnimationState, DeepshovelerAnimation.shoot, ageInTicks, 1f);
        this.animateWalk(DeepshovelerAnimation.walking, limbSwing, limbSwingAmount, 3f, 5.5f);
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
