package net.blumasc.blusbeasts.entity.client.myceliumToad;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderFireLayer;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderModel;
import net.blumasc.blusbeasts.entity.custom.MyceliumToadEntity;
import net.blumasc.blusbeasts.entity.custom.SalamanderEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class MyceliumToadRenderer  extends MobRenderer<MyceliumToadEntity, MyceliumToadModel<MyceliumToadEntity>> {
    public MyceliumToadRenderer(EntityRendererProvider.Context context) {
        super(context, new MyceliumToadModel<>(context.bakeLayer(MyceliumToadModel.LAYER_LOCATION)), 0.7f);
        this.addLayer(new MyceliumToadSideShroomsLayer(this));
        this.addLayer(new MyceliumToadBackBlockLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(MyceliumToadEntity myceliumToadEntity) {
        return ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "textures/entity/mycelium_toad/texture.png");
    }
}
