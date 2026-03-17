package net.blumasc.blusbeasts.entity.client.endsquid;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.EndSquidEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class EndsquidRenderer extends MobRenderer<EndSquidEntity, EndsquidModel<EndSquidEntity>> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/end_squid/texture.png");

    public EndsquidRenderer(EntityRendererProvider.Context context) {
        super(context, new EndsquidModel<>(context.bakeLayer(EndsquidModel.LAYER_LOCATION)), 0.2f);
    }

    @Override
    public ResourceLocation getTextureLocation(EndSquidEntity moonsquidEntity) {
        return TEXTURE;
    }
    public static ResourceLocation getStaticTextureLocation() {
        return TEXTURE;
    }
}
