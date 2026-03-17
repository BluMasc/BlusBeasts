package net.blumasc.blusbeasts.entity.client.netherLeach;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.NetherLeachEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class NetherLeachRenderer extends MobRenderer<NetherLeachEntity, NetherLeachModel<NetherLeachEntity>> {
    public NetherLeachRenderer(EntityRendererProvider.Context context) {
        super(context, new NetherLeachModel<>(context.bakeLayer(NetherLeachModel.LAYER_LOCATION)), 0.1f);
    }

    @Override
    public ResourceLocation getTextureLocation(NetherLeachEntity netherLeachEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/item/nether_leach.png");
    }
}
