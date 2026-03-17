package net.blumasc.blusbeasts.entity.client.gemCrab;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.EchoCrabEntity;
import net.blumasc.blusbeasts.entity.custom.GemCrabEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class GemCrabRenderer extends MobRenderer<GemCrabEntity, GemCrabModel<GemCrabEntity>> {

    private static final ResourceLocation TEXTURE_AMETHYST = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/gem_crab/texture_amethyst.png");
    private static final ResourceLocation TEXTURE_ECHO = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"textures/entity/gem_crab/texture_echo.png");
    public GemCrabRenderer(EntityRendererProvider.Context context) {
        super(context, new GemCrabModel<>(context.bakeLayer(GemCrabModel.LAYER_LOCATION)), 0.4f);
    }

    @Override
    public ResourceLocation getTextureLocation(GemCrabEntity crabEntity) {
        if(crabEntity instanceof EchoCrabEntity) return TEXTURE_ECHO;
        return TEXTURE_AMETHYST;
    }
}
