package net.blumasc.blusbeasts.item.client.curios.personalminecart;

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

public class PersonalMinecartModel<T extends Entity> extends EntityModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "personalminecart"), "main");
    public final ModelPart core;
    private final ModelPart bottom;
    private final ModelPart front;
    private final ModelPart back;
    private final ModelPart right;
    private final ModelPart left;

	public PersonalMinecartModel(ModelPart root) {
        this.core = root.getChild("core");
        this.bottom = this.core.getChild("bottom");
        this.front = this.core.getChild("front");
        this.back = this.core.getChild("back");
        this.right = this.core.getChild("right");
        this.left = this.core.getChild("left");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 7.0F, 3.0F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition bottom = core.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 10).mirror().addBox(-10.0F, -8.0F, -20.0F, 20.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -20.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition front = core.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, 15.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-9.0F, -25.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition back = core.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, 15.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(9.0F, -25.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition right = core.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, 15.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -25.0F, -7.0F, 0.0F, -3.1416F, 0.0F));

        PartDefinition left = core.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, 15.0F, -1.0F, 16.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -25.0F, 7.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        core.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }
}
