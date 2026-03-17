package net.blumasc.blusbeasts.entity.client.myceliumToad;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderAnimation;
import net.blumasc.blusbeasts.entity.custom.MyceliumToadEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;

public class MyceliumToadModel<T extends MyceliumToadEntity> extends HierarchicalModel<T> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "myceliumtoad"), "main");
    public final ModelPart core;
    public final ModelPart backleg1;
    public final ModelPart octoPlace1;
    public final ModelPart backleg2;
    public final ModelPart octoPlace2;
    public final ModelPart frontleg;
    public final ModelPart octoPlace3;
    public final ModelPart frontleg2;
    public final ModelPart octoPlace4;
    public final ModelPart base;
    public final ModelPart body;
    private final ModelPart chin;
    public final ModelPart head;
    private final ModelPart eye1;
    private final ModelPart eye2;
    public final ModelPart block1;
    public final ModelPart block2;
    public final ModelPart block3;

    public MyceliumToadModel(ModelPart root) {
        this.core = root.getChild("core");
        this.backleg1 = this.core.getChild("backleg1");
        this.octoPlace1 = this.backleg1.getChild("octoPlace1");
        this.backleg2 = this.core.getChild("backleg2");
        this.octoPlace2 = this.backleg2.getChild("octoPlace2");
        this.frontleg = this.core.getChild("frontleg");
        this.octoPlace3 = this.frontleg.getChild("octoPlace3");
        this.frontleg2 = this.core.getChild("frontleg2");
        this.octoPlace4 = this.frontleg2.getChild("octoPlace4");
        this.base = this.core.getChild("base");
        this.body = this.base.getChild("body");
        this.chin = this.body.getChild("chin");
        this.head = this.base.getChild("head");
        this.eye1 = this.head.getChild("eye1");
        this.eye2 = this.head.getChild("eye2");
        this.block1 = this.head.getChild("block1");
        this.block2 = this.head.getChild("block2");
        this.block3 = this.head.getChild("block3");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition core = partdefinition.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition backleg1 = core.addOrReplaceChild("backleg1", CubeListBuilder.create().texOffs(92, 28).mirror().addBox(0.0F, -1.0F, -5.0F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 19).mirror().addBox(0.0F, 3.999F, -9.0F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, -4.0F, 13.0F));

        PartDefinition octoPlace1 = backleg1.addOrReplaceChild("octoPlace1", CubeListBuilder.create(), PartPose.offset(2.0F, -1.0F, -2.0F));

        PartDefinition backleg2 = core.addOrReplaceChild("backleg2", CubeListBuilder.create().texOffs(92, 28).addBox(-4.0F, -1.0F, -5.0F, 4.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(92, 19).addBox(-7.0F, 3.999F, -9.0F, 7.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -4.0F, 13.0F));

        PartDefinition octoPlace2 = backleg2.addOrReplaceChild("octoPlace2", CubeListBuilder.create(), PartPose.offset(-1.0F, -1.0F, -2.0F));

        PartDefinition frontleg = core.addOrReplaceChild("frontleg", CubeListBuilder.create().texOffs(92, 40).mirror().addBox(0.0F, -1.0F, -4.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(92, 10).mirror().addBox(-3.0F, 4.999F, -8.0F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, -5.0F, -6.0F));

        PartDefinition octoPlace3 = frontleg.addOrReplaceChild("octoPlace3", CubeListBuilder.create(), PartPose.offset(2.0F, -1.0F, -1.0F));

        PartDefinition frontleg2 = core.addOrReplaceChild("frontleg2", CubeListBuilder.create().texOffs(92, 40).addBox(-4.0F, -1.0F, -4.0F, 4.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(92, 10).addBox(-5.0F, 4.999F, -8.0F, 8.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, -5.0F, -6.0F));

        PartDefinition octoPlace4 = frontleg2.addOrReplaceChild("octoPlace4", CubeListBuilder.create(), PartPose.offset(-2.0F, -1.0F, -1.0F));

        PartDefinition base = core.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = base.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -7.0F, -13.0F, 18.0F, 7.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 68).addBox(-9.0F, -7.0F, -13.0F, 18.0F, 4.0F, 27.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, -2.0F, -0.5F, -0.1309F, 0.0F, 0.0F));

        PartDefinition chin = body.addOrReplaceChild("chin", CubeListBuilder.create().texOffs(92, 0).addBox(-10.0F, -3.0F, -2.0F, 20.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, -13.0F));

        PartDefinition head = base.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 13.5F));

        PartDefinition cube_r2 = head.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(90, 68).addBox(-9.0F, -5.0F, -13.0F, 18.0F, 0.0F, 28.0F, new CubeDeformation(-0.001F))
                .texOffs(0, 35).addBox(-9.0F, -8.0F, -13.0F, 18.0F, 5.0F, 28.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 2.0F, -13.7F, -0.1309F, 0.0F, 0.0F));

        PartDefinition eye1 = head.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(92, 51).addBox(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -6.0F, -21.0F));

        PartDefinition eye2 = head.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(92, 51).mirror().addBox(-2.0F, -4.0F, -1.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(5.0F, -6.0F, -21.0F));

        PartDefinition block1 = head.addOrReplaceChild("block1", CubeListBuilder.create(), PartPose.offset(-5.0F, -6.0F, -14.0F));

        PartDefinition block2 = head.addOrReplaceChild("block2", CubeListBuilder.create(), PartPose.offset(6.0F, -5.0F, -10.0F));

        PartDefinition block3 = head.addOrReplaceChild("block3", CubeListBuilder.create(), PartPose.offset(-5.0F, -4.0F, -1.0F));

        return LayerDefinition.create(meshdefinition, 256, 256);
    }

    @Override
    public void setupAnim(MyceliumToadEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);
        this.animate(entity.idleAnimationState, MyceliumToadAnimation.idle, ageInTicks, 1f);
        this.animate(entity.sleepAnimationState, MyceliumToadAnimation.sleep, ageInTicks, 1f);
        this.animate(entity.eatAnimationState, MyceliumToadAnimation.eat, ageInTicks, 1.0f);
        this.animate(entity.attackAnimationState, MyceliumToadAnimation.attack, ageInTicks, 0.57f);
        if(entity.isInWater()){
            this.animateWalk(MyceliumToadAnimation.swim, limbSwing, limbSwingAmount, 8f, 7.5f);
        }else {
            this.animateWalk(MyceliumToadAnimation.walking, limbSwing, limbSwingAmount, 8f, 7.5f);
        }
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
