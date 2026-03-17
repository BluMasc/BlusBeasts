package net.blumasc.blusbeasts.entity.client.prayfinder;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderAnimation;
import net.blumasc.blusbeasts.entity.custom.PrayfinderEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PrayfinderModel<T extends PrayfinderEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "prayfinder"), "main");
    private final ModelPart core;
    private final ModelPart body;
    private final ModelPart wing1;
    private final ModelPart wing2;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart leg1;
    private final ModelPart leg2;

    public PrayfinderModel(ModelPart root) {
        this.core = root.getChild("core");
        this.body = this.core.getChild("body");
        this.wing1 = this.body.getChild("wing1");
        this.wing2 = this.body.getChild("wing2");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");
        this.leg1 = this.core.getChild("leg1");
        this.leg2 = this.core.getChild("leg2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = core.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -1.5F, -3.0F, 4.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition wing1 = body.addOrReplaceChild("wing1", CubeListBuilder.create(), PartPose.offset(2.0F, 0.0F, -2.0F));

        PartDefinition wing1_r1 = wing1.addOrReplaceChild("wing1_r1", CubeListBuilder.create().texOffs(18, 11).addBox(0.5F, -3.0F, -1.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 2.4771F, 2.13F, 0.9163F, 0.0F, 0.0F));

        PartDefinition wing2 = body.addOrReplaceChild("wing2", CubeListBuilder.create(), PartPose.offset(-2.0F, 0.0F, -2.0F));

        PartDefinition wing2_r1 = wing2.addOrReplaceChild("wing2_r1", CubeListBuilder.create().texOffs(12, 20).addBox(-1.5F, -3.0F, -1.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 2.863F, 1.9344F, 0.9163F, 0.0F, 0.0F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-1.5F, -1.25F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(22, 2).addBox(-0.5F, 0.25F, -2.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 11).addBox(-1.5F, 2.75F, -1.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 4.0F));

        PartDefinition leg1 = core.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(20, 20).addBox(-0.1F, 3.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 5).addBox(-0.1F, 0.0F, 2.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, 1.0F));

        PartDefinition leg2 = core.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(22, 0).addBox(-1.9F, 3.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 8).addBox(-0.9F, 0.0F, 2.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, 1.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(PrayfinderEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.applyHeadRotation(netHeadYaw,headPitch);
        this.animate(entity.flyAnimationState, PrayfinderAnimation.flying, ageInTicks, 1f);
        this.animateWalk(PrayfinderAnimation.walking, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.animate(entity.idleAnimationState, PrayfinderAnimation.idle, ageInTicks, 1f);
        this.animate(entity.perchAnimationState, PrayfinderAnimation.perching, ageInTicks, 1f);
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
        return core;
    }
}
