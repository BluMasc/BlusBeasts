package net.blumasc.blusbeasts.entity.client.minerssnack;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.MinersSnackEntity;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.CodRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Cod;

public class MinerSnackRenderer extends MobRenderer<MinersSnackEntity, MinerSnackModel<MinersSnackEntity>> {
    private static final ResourceLocation COD_LOCATION = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/minersnack/texture_body.png");

    public MinerSnackRenderer(EntityRendererProvider.Context p_173954_) {
        super(p_173954_, new MinerSnackModel<>(p_173954_.bakeLayer(MinerSnackModel.LAYER_LOCATION)), 0.3F);
        this.addLayer(new MinerSnackHungerLayer(this));
        this.addLayer(new MinerSnackSaturationLayer(this));
        this.addLayer(new MinerSnackTopPotionLayer(this));
        this.addLayer(new MinerSnackBottomPotionLayer(this));
    }

    public ResourceLocation getTextureLocation(MinersSnackEntity entity) {
        return COD_LOCATION;
    }

    protected void setupRotations(MinersSnackEntity entity, PoseStack poseStack, float bob, float yBodyRot, float partialTick, float scale) {
        super.setupRotations(entity, poseStack, bob, yBodyRot, partialTick, scale);
        float f = 4.3F * Mth.sin(0.6F * bob);
        poseStack.mulPose(Axis.YP.rotationDegrees(f));
        if (!entity.isInWater()) {
            poseStack.translate(0.1F, 0.1F, -0.1F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        }

    }
}
