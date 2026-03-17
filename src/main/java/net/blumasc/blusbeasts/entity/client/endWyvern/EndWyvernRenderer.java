package net.blumasc.blusbeasts.entity.client.endWyvern;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EndWyvernRenderer extends MobRenderer<EndWyvernEntity, EndWyvernModel<EndWyvernEntity>> {
    public EndWyvernRenderer(EntityRendererProvider.Context context) {
        super(context, new EndWyvernModel<>(context.bakeLayer(EndWyvernModel.LAYER_LOCATION)), 0.4f);
        this.addLayer(new EndWyvernEyeLayer(this));
        this.addLayer(new EndWyvernWingsLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EndWyvernEntity endWyvernEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/ender_wyvern/texture.png");
    }
}
