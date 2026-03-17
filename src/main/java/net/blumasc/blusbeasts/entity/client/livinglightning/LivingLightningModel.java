package net.blumasc.blusbeasts.entity.client.livinglightning;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.mimicart.MimicartAnimation;
import net.blumasc.blusbeasts.entity.custom.LivingLightningEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;

public class LivingLightningModel<T extends LivingLightningEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "living_spark"), "main");
    private final ModelPart core;
    private final ModelPart eye2;
    private final ModelPart pupil2;
    private final ModelPart eye1;
    private final ModelPart pupil1;

    public LivingLightningModel(ModelPart root) {
        this.core = root.getChild("core");
        this.eye2 = this.core.getChild("eye2");
        this.pupil2 = this.eye2.getChild("pupil2");
        this.eye1 = this.core.getChild("eye1");
        this.pupil1 = this.eye1.getChild("pupil1");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -6.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition eye2 = core.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 12).addBox(-1.5F, -0.8F, -0.7F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -3.0F, -2.6F));

        PartDefinition pupil2 = eye2.addOrReplaceChild("pupil2", CubeListBuilder.create().texOffs(6, 12).addBox(-0.5F, -1.0F, 0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-0.5F, 0.2F, -1.5F));

        PartDefinition eye1 = core.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(0, 12).addBox(-0.5F, -0.8F, -0.7F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -3.0F, -2.6F));

        PartDefinition pupil1 = eye1.addOrReplaceChild("pupil1", CubeListBuilder.create().texOffs(6, 12).addBox(-0.5F, -1.0F, 0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.5F, 0.2F, -1.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(LivingLightningEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.angryAnimationState, LivingLightningAnimation.angry, ageInTicks, 1f);
        this.animate(entity.happyAnimationState, LivingLightningAnimation.calm, ageInTicks, 1f);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        core.render(poseStack, vertexConsumer, LightTexture.FULL_BRIGHT, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.core;
    }
}
