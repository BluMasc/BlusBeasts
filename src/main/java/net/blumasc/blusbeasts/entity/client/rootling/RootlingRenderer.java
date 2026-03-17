package net.blumasc.blusbeasts.entity.client.rootling;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.RootlingEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RootlingRenderer extends MobRenderer<RootlingEntity, RootlingModel<RootlingEntity>> {

    public RootlingRenderer(EntityRendererProvider.Context context) {
        super(context, new RootlingModel<>(context.bakeLayer(RootlingModel.LAYER_LOCATION)), 0.2f);
        this.addLayer(new RootlingHeadBlockRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(RootlingEntity rootlingEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/rootling/texture.png");
    }
}
