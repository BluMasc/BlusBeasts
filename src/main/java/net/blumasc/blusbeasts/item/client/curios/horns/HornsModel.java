package net.blumasc.blusbeasts.item.client.curios.horns;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

public class HornsModel<T extends LivingEntity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "hornmodel"), "main");
    public final ModelPart head;

    public HornsModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition horn1 = head.addOrReplaceChild("horn1", CubeListBuilder.create().texOffs(0, 16).addBox(-2.0F, -1.0F, -1.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition lowerPart1 = horn1.addOrReplaceChild("lowerPart1", CubeListBuilder.create().texOffs(0, 22).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upperPart1 = lowerPart1.addOrReplaceChild("upperPart1", CubeListBuilder.create().texOffs(16, 22).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition spike1 = upperPart1.addOrReplaceChild("spike1", CubeListBuilder.create().texOffs(24, 22).addBox(-1.5F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition horn2 = head.addOrReplaceChild("horn2", CubeListBuilder.create().texOffs(16, 16).addBox(-2.0F, -1.0F, -1.5F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -8.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition lowerPart2 = horn2.addOrReplaceChild("lowerPart2", CubeListBuilder.create().texOffs(8, 22).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition upperPart2 = lowerPart2.addOrReplaceChild("upperPart2", CubeListBuilder.create().texOffs(20, 22).addBox(-0.5F, -5.0F, -1.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 1.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition spike2 = upperPart2.addOrReplaceChild("spike2", CubeListBuilder.create().texOffs(24, 24).addBox(0.5F, -5.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void setupAnim(LivingEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
