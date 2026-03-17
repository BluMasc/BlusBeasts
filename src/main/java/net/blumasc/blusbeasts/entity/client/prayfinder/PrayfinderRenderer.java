package net.blumasc.blusbeasts.entity.client.prayfinder;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.PackwingEntity;
import net.blumasc.blusbeasts.entity.custom.PrayfinderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PrayfinderRenderer extends MobRenderer<PrayfinderEntity, PrayfinderModel<PrayfinderEntity>> {
    public PrayfinderRenderer(EntityRendererProvider.Context context) {
        super(context, new PrayfinderModel<>(context.bakeLayer(PrayfinderModel.LAYER_LOCATION)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(PrayfinderEntity prayfinderEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/prayfinder/texture.png");
    }
}
