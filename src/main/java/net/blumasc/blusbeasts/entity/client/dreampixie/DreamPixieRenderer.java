package net.blumasc.blusbeasts.entity.client.dreampixie;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.DreamPixie;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DreamPixieRenderer extends MobRenderer<DreamPixie, DreamPixieModel<DreamPixie>> {
    public DreamPixieRenderer(EntityRendererProvider.Context context) {
        super(context, new DreamPixieModel<>(context.bakeLayer(DreamPixieModel.LAYER_LOCATION)), 0.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(DreamPixie dreamPixie) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/dream_pixie/texture.png");
    }
}
