package net.blumasc.blusbeasts.entity.client;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.projectile.InfestedArrowEntity;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class InfestedArrowRenderer extends ArrowRenderer<InfestedArrowEntity> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/infested_arrow.png");

    public InfestedArrowRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(InfestedArrowEntity entity) {
        return TEXTURE;
    }
}
