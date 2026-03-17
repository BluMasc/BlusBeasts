package net.blumasc.blusbeasts.entity.client.magicalgirl;

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

public class MagicalGirlModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "magicalgirl"), "main");
    public final ModelPart Head;
    public final ModelPart Body;
    public final ModelPart RightLeg;
    public final ModelPart LeftLeg;

    public MagicalGirlModel(ModelPart root) {
        this.Body = root.getChild("Body");
        this.Head = root.getChild("Head");
        this.RightLeg = root.getChild("RightLeg");
        this.LeftLeg = root.getChild("LeftLeg");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Body = partdefinition.addOrReplaceChild("Body", CubeListBuilder.create().texOffs(0, 0).addBox(-5.9F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(32, 28).addBox(-8.1F, -10.0F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(0, 33).addBox(3.3F, -10.0F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(28, 36).addBox(-0.6F, -10.2F, -3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 28).addBox(-4.2F, -10.2F, -3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition cube_r1 = Body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 33).addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-2.3F, -8.3F, -2.1F, 0.0F, 0.0F, -0.9599F));

        PartDefinition cube_r2 = Body.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(4, 33).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5F, -8.3F, -2.2F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r3 = Body.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 11).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(-5.1F, -2.8F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r4 = Body.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(20, 27).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.offsetAndRotation(1.3F, -2.8F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition cube_r5 = Body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 27).addBox(-4.0F, 0.0F, -1.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(-1.9F, -2.0F, 1.4F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r6 = Body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(24, 5).addBox(-4.0F, 0.0F, -1.0F, 8.0F, 4.0F, 2.0F, new CubeDeformation(0.26F)), PartPose.offsetAndRotation(-1.9F, -2.0F, -1.2F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r7 = Body.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(24, 36).addBox(0.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.2F, -3.9F, 3.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r8 = Body.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(12, 33).addBox(0.0F, 0.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1F, -6.5F, 3.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition cube_r9 = Body.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(16, 33).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -4.2F, 3.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r10 = Body.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(36, 32).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, -6.7F, 3.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r11 = Body.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(32, 26).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.021F)), PartPose.offsetAndRotation(3.1F, -4.0F, 3.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition cube_r12 = Body.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(32, 24).addBox(-1.0F, -1.0F, -1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-6.9F, -4.0F, 3.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition cube_r13 = Body.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(32, 22).addBox(-1.0F, -1.0F, -1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9F, -7.0F, 3.0F, 0.0F, 0.0F, -0.48F));

        PartDefinition cube_r14 = Body.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(32, 20).addBox(-6.0F, -1.0F, -1.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -7.0F, 3.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition Head = partdefinition.addOrReplaceChild("Head", CubeListBuilder.create().texOffs(32, 35).addBox(1.0F, -10.6F, -1.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 36).addBox(-2.0F, -10.6F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-3.0F, -8.1F, -1.0F, 6.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r15 = Head.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(28, 11).addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.4F, -8.5F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r16 = Head.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(24, 11).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.4F, -8.5F, -0.1F, 0.0F, 0.0F, 0.7854F));

        PartDefinition RightLeg = partdefinition.addOrReplaceChild("RightLeg", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(28, 36).addBox(1.3F, 4.6F, -3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 28).addBox(-2.5F, 4.6F, -3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 12.0F, 0.0F));

        PartDefinition cube_r17 = RightLeg.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(8, 33).addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 6.5F, -2.1F, 0.0F, 0.0F, -0.9599F));

        PartDefinition cube_r18 = RightLeg.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(4, 33).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.3F, 6.5F, -2.2F, 0.0F, 0.0F, 0.9599F));

        PartDefinition LeftLeg = partdefinition.addOrReplaceChild("LeftLeg", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, 5.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.26F))
                .texOffs(28, 36).mirror().addBox(-2.2F, 4.6F, -3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(36, 28).mirror().addBox(1.4F, 4.6F, -3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(1.9F, 12.0F, 0.0F));

        PartDefinition cube_r19 = LeftLeg.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(8, 33).mirror().addBox(-1.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(0.5F, 6.5F, -2.1F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r20 = LeftLeg.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(4, 33).mirror().addBox(0.0F, -2.0F, -1.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.01F)).mirror(false), PartPose.offsetAndRotation(-0.3F, 6.5F, -2.2F, 0.0F, 0.0F, -0.9599F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        Body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        Head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        RightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        LeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
