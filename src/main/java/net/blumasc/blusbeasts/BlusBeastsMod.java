package net.blumasc.blusbeasts;

import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.block.custom.blockentity.ModBlockEntities;
import net.blumasc.blusbeasts.effect.ModEffects;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.item.ModCreativeTabs;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.item.custom.components.ModItemDataComponents;
import net.blumasc.blusbeasts.item.dispensebehaviour.GravityDustDispenseBehavior;
import net.blumasc.blusbeasts.network.ModNetworking;
import net.blumasc.blusbeasts.particle.ModParticles;
import net.blumasc.blusbeasts.potion.ModPotions;
import net.blumasc.blusbeasts.recipe.ModRecipes;
import net.blumasc.blusbeasts.sound.ModSounds;
import net.blumasc.blusbeasts.worldgen.feature.ModFeatures;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;


@Mod(BlusBeastsMod.MODID)
public class BlusBeastsMod {
    public static final String MODID = "blusbeasts";

    public BlusBeastsMod(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModCreativeTabs.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        ModParticles.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModSounds.register(modEventBus);
        ModEntities.register(modEventBus);
        ModEffects.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModPotions.register(modEventBus);
        ModItemDataComponents.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModFeatures.register(modEventBus);

        modEventBus.addListener(ModNetworking::register);


        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

        net.minecraft.world.level.block.DispenserBlock.registerBehavior(
                ModItems.GRAVITY_DUST.get(),
                new GravityDustDispenseBehavior()
        );

        net.minecraft.world.level.block.DispenserBlock.registerBehavior(
                ModItems.INFESTED_ARROW.get(),
                new ProjectileDispenseBehavior(ModItems.INFESTED_ARROW.get())
        );

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
