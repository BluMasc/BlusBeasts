package net.blumasc.blusbeasts.entity.client.minerssnack;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class MinerSnackModel<T extends Entity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "minerssnack"), "main");
    private final ModelPart root;
    private final ModelPart tailFin;

    public MinerSnackModel(ModelPart root) {
        this.root = root;
        ModelPart body = this.root.getChild("body");
        this.tailFin = body.getChild("tailfin");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, -2.0F, 1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(20, 0).addBox(-1.0F, -8.0F, 1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.0F, -11.0F, 1.0F, 0.0F, 3.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(20, 10).addBox(0.0F, 0.0F, 2.0F, 0.0F, 3.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(22, 26).addBox(-1.0F, -6.0F, 7.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(30, 26).addBox(-1.0F, -6.0F, 1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, -4.8F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 32).addBox(-0.9992F, -2.0008F, -5.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 23).addBox(-1.0F, -3.0F, -3.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 32).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition leftFin = body.addOrReplaceChild("leftFin", CubeListBuilder.create().texOffs(20, 20).mirror().addBox(0.0F, 0.0F, 1.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition rightFin = body.addOrReplaceChild("rightFin", CubeListBuilder.create().texOffs(20, 20).addBox(-3.0F, 0.0F, 1.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition tailfin = body.addOrReplaceChild("tailfin", CubeListBuilder.create().texOffs(12, 26).addBox(0.0F, -7.0F, 1.0F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 8.0F));

        PartDefinition waist = body.addOrReplaceChild("waist", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public ModelPart root() {
        return this.root;
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f = 1.0F;
        if (!entity.isInWater()) {
            f = 1.5F;
        }

        this.tailFin.yRot = -f * 0.45F * Mth.sin(0.6F * ageInTicks);
    }
}