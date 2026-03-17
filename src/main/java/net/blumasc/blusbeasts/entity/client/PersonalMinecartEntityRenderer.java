package net.blumasc.blusbeasts.entity.client;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.vehicle.AbstractMinecart;

public class PersonalMinecartEntityRenderer<T extends AbstractMinecart> extends MinecartRenderer<T> {

    public static final ModelLayerLocation PERSONAL_MINECART =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "personal_minecart"), "main");
    private static final ResourceLocation MINECART_LOCATION = ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/mimicart/personal_minecart.png");
    public PersonalMinecartEntityRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
        super(context, layer);
    }

    public PersonalMinecartEntityRenderer(EntityRendererProvider.Context context) {
        super(context, PERSONAL_MINECART);
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return MINECART_LOCATION;
    }


}
