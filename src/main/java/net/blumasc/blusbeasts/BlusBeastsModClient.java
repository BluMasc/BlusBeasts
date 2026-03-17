package net.blumasc.blusbeasts;

import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.blumasc.blusbeasts.block.custom.blockentity.custom.client.PlateBlockEntityRenderer;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.client.*;
import net.blumasc.blusbeasts.entity.client.burry.BurryRenderer;
import net.blumasc.blusbeasts.entity.client.deepshoveler.DeepshovelerRenderer;
import net.blumasc.blusbeasts.entity.client.dreampixie.DreamPixieRenderer;
import net.blumasc.blusbeasts.entity.client.endWyvern.EndWyvernRenderer;
import net.blumasc.blusbeasts.entity.client.endsquid.EndsquidRenderer;
import net.blumasc.blusbeasts.entity.client.gemCrab.GemCrabRenderer;
import net.blumasc.blusbeasts.entity.client.grave.GraveRenderer;
import net.blumasc.blusbeasts.entity.client.heartProjectile.HeartProjectileRenderer;
import net.blumasc.blusbeasts.entity.client.livinglightning.LivingLightningRenderer;
import net.blumasc.blusbeasts.entity.client.mimicart.MimicartRenderer;
import net.blumasc.blusbeasts.entity.client.minerssnack.MinerSnackRenderer;
import net.blumasc.blusbeasts.entity.client.myceliumToad.MyceliumToadRenderer;
import net.blumasc.blusbeasts.entity.client.netherLeach.NetherLeachRenderer;
import net.blumasc.blusbeasts.entity.client.packwing.PackwingRenderer;
import net.blumasc.blusbeasts.entity.client.prayfinder.PrayfinderRenderer;
import net.blumasc.blusbeasts.entity.client.rootling.RootlingRenderer;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderRenderer;
import net.blumasc.blusbeasts.entity.custom.MinersSnackEntity;
import net.blumasc.blusbeasts.entity.custom.NetherLeachEntity;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.item.client.curios.armcrystal.ArmCrystalsRenderer;
import net.blumasc.blusbeasts.item.client.curios.horns.HornsRenderer;
import net.blumasc.blusbeasts.item.client.curios.personalminecart.PersonalMinecartRenderer;
import net.blumasc.blusbeasts.item.custom.MinersSnackItem;
import net.blumasc.blusbeasts.particle.ConfettiParticle;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.blumasc.blusbeasts.particle.PinkHeartParticle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterNamedRenderTypesEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = BlusBeastsMod.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = BlusBeastsMod.MODID, value = Dist.CLIENT)
public class BlusBeastsModClient {
    public BlusBeastsModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.PACKWING.get(), PackwingRenderer::new);
        EntityRenderers.register(ModEntities.SALAMANDER.get(), SalamanderRenderer::new);
        EntityRenderers.register(ModEntities.END_SQUID.get(), EndsquidRenderer::new);
        EntityRenderers.register(ModEntities.ECHO_CRAB.get(), GemCrabRenderer::new);
        EntityRenderers.register(ModEntities.AMETHYST_CRAB.get(), GemCrabRenderer::new);
        EntityRenderers.register(ModEntities.MYCELIUM_TOAD.get(), MyceliumToadRenderer::new);
        EntityRenderers.register(ModEntities.PRAYFINDER.get(), PrayfinderRenderer::new);
        EntityRenderers.register(ModEntities.MIMICART.get(), MimicartRenderer::new);
        EntityRenderers.register(ModEntities.LIVING_LIGHTNING.get(), LivingLightningRenderer::new);
        EntityRenderers.register(ModEntities.NETHER_LEACH.get(), NetherLeachRenderer::new);
        EntityRenderers.register(ModEntities.ROOTLING.get(), RootlingRenderer::new);
        EntityRenderers.register(ModEntities.END_WYVERN.get(), EndWyvernRenderer::new);
        EntityRenderers.register(ModEntities.BURRY.get(), BurryRenderer::new);
        EntityRenderers.register(ModEntities.GRAVE.get(), GraveRenderer::new);
        EntityRenderers.register(ModEntities.DEEPSHOVELER.get(), DeepshovelerRenderer::new);
        EntityRenderers.register(ModEntities.MINER_SNACK.get(), MinerSnackRenderer::new);
        EntityRenderers.register(ModEntities.DREAM_PIXIE.get(), DreamPixieRenderer::new);

        EntityRenderers.register(ModEntities.PERSONAL_MINECART.get(), PersonalMinecartEntityRenderer::new);
        EntityRenderers.register(ModEntities.INFESTED_ARROW.get(), InfestedArrowRenderer::new);
        EntityRenderers.register(ModEntities.DARKNESS_BLOCK.get(), DarknessBlockRenderer::new);
        EntityRenderers.register(ModEntities.GLITTER_BOMB.get(), ThrownItemRenderer::new);
        EntityRenderers.register(ModEntities.HEART.get(), HeartProjectileRenderer::new);

        CuriosRendererRegistry.register(ModItems.EMBEDDED_CRYSTALS.get(), ArmCrystalsRenderer::new);
        CuriosRendererRegistry.register(ModItems.ANTLERS.get(), HornsRenderer::new);
        CuriosRendererRegistry.register(ModItems.GOLDEN_ANTLERS.get(), HornsRenderer::new);
        CuriosRendererRegistry.register(ModItems.PERSONAL_MINECART.get(), PersonalMinecartRenderer::new);

        event.enqueueWork(() -> {
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.PIXIE_TORCH_BLOCK.get(),
                    RenderType.cutout()
            );
            ItemBlockRenderTypes.setRenderLayer(
                    ModBlocks.WALL_PIXIE_TORCH.get(),
                    RenderType.cutout()
            );
        });

    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event){
        event.registerBlockEntityRenderer(ModBlockEntities.PLATE_BE.get(), PlateBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void regsiterParicleFactories(RegisterParticleProvidersEvent event){
        event.registerSpriteSet(ModParticles.PINK_HEART.get(), PinkHeartParticle.Provider::new);
        event.registerSpriteSet(ModParticles.CONFETTI.get(), ConfettiParticle.Provider::new);
    }
}
