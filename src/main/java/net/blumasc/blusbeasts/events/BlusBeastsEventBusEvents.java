package net.blumasc.blusbeasts.events;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.client.PersonalMinecartEntityRenderer;
import net.blumasc.blusbeasts.entity.client.burry.BurryModel;
import net.blumasc.blusbeasts.entity.client.deepshoveler.DeepshovelerModel;
import net.blumasc.blusbeasts.entity.client.dreampixie.DreamPixieModel;
import net.blumasc.blusbeasts.entity.client.endWyvern.EndWyvernModel;
import net.blumasc.blusbeasts.entity.client.endsquid.EndsquidModel;
import net.blumasc.blusbeasts.entity.client.gemCrab.GemCrabModel;
import net.blumasc.blusbeasts.entity.client.grave.GraveModel;
import net.blumasc.blusbeasts.entity.client.heartProjectile.HeartProjectile;
import net.blumasc.blusbeasts.entity.client.livinglightning.LivingLightningModel;
import net.blumasc.blusbeasts.entity.client.magicalgirl.MagicalGirlModel;
import net.blumasc.blusbeasts.entity.client.mimicart.MimicartHiddenModel;
import net.blumasc.blusbeasts.entity.client.mimicart.MimicartModel;
import net.blumasc.blusbeasts.entity.client.minerssnack.MinerSnackModel;
import net.blumasc.blusbeasts.entity.client.myceliumToad.MyceliumToadModel;
import net.blumasc.blusbeasts.entity.client.myceliumToad.OctopusMushroomModel;
import net.blumasc.blusbeasts.entity.client.myceliumToad.ShelfMushroomModel;
import net.blumasc.blusbeasts.entity.client.netherLeach.NetherLeachModel;
import net.blumasc.blusbeasts.entity.client.packwing.PackwingModel;
import net.blumasc.blusbeasts.entity.client.prayfinder.PrayfinderModel;
import net.blumasc.blusbeasts.entity.client.rootling.RootlingModel;
import net.blumasc.blusbeasts.entity.client.salamander.SalamanderModel;
import net.blumasc.blusbeasts.entity.custom.*;
import net.blumasc.blusbeasts.item.ModItems;
import net.blumasc.blusbeasts.item.client.curios.armcrystal.ArmCrystalsModel;
import net.blumasc.blusbeasts.item.client.curios.horns.HornsModel;
import net.blumasc.blusbeasts.item.client.curios.personalminecart.PersonalMinecartModel;
import net.blumasc.blusbeasts.potion.ModPotions;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.GlowSquid;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = BlusBeastsMod.MODID)
public class BlusBeastsEventBusEvents {

    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(PackwingModel.LAYER_LOCATION, PackwingModel::createBodyLayer);
        event.registerLayerDefinition(SalamanderModel.LAYER_LOCATION, SalamanderModel::createBodyLayer);
        event.registerLayerDefinition(EndsquidModel.LAYER_LOCATION, EndsquidModel::createBodyLayer);
        event.registerLayerDefinition(GemCrabModel.LAYER_LOCATION, GemCrabModel::createBodyLayer);
        event.registerLayerDefinition(MyceliumToadModel.LAYER_LOCATION, MyceliumToadModel::createBodyLayer);
        event.registerLayerDefinition(PrayfinderModel.LAYER_LOCATION, PrayfinderModel::createBodyLayer);
        event.registerLayerDefinition(MimicartModel.LAYER_LOCATION, MimicartModel::createBodyLayer);
        event.registerLayerDefinition(MimicartHiddenModel.LAYER_LOCATION, MimicartHiddenModel::createBodyLayer);
        event.registerLayerDefinition(LivingLightningModel.LAYER_LOCATION, LivingLightningModel::createBodyLayer);
        event.registerLayerDefinition(NetherLeachModel.LAYER_LOCATION, NetherLeachModel::createBodyLayer);
        event.registerLayerDefinition(RootlingModel.LAYER_LOCATION, RootlingModel::createBodyLayer);
        event.registerLayerDefinition(EndWyvernModel.LAYER_LOCATION, EndWyvernModel::createBodyLayer);
        event.registerLayerDefinition(BurryModel.LAYER_LOCATION, BurryModel::createBodyLayer);
        event.registerLayerDefinition(GraveModel.LAYER_LOCATION, GraveModel::createBodyLayer);
        event.registerLayerDefinition(DeepshovelerModel.LAYER_LOCATION, DeepshovelerModel::createBodyLayer);
        event.registerLayerDefinition(MinerSnackModel.LAYER_LOCATION, MinerSnackModel::createBodyLayer);
        event.registerLayerDefinition(DreamPixieModel.LAYER_LOCATION, DreamPixieModel::createBodyLayer);

        event.registerLayerDefinition(ShelfMushroomModel.LAYER_LOCATION, ShelfMushroomModel::createBodyLayer);
        event.registerLayerDefinition(OctopusMushroomModel.LAYER_LOCATION, OctopusMushroomModel::createBodyLayer);
        event.registerLayerDefinition(PersonalMinecartEntityRenderer.PERSONAL_MINECART, MinecartModel::createBodyLayer);

        event.registerLayerDefinition(MagicalGirlModel.LAYER_LOCATION, MagicalGirlModel::createBodyLayer);

        event.registerLayerDefinition(HeartProjectile.LAYER_LOCATION, HeartProjectile::createBodyLayer);

        event.registerLayerDefinition(ArmCrystalsModel.LAYER_LOCATION, ArmCrystalsModel::createBodyLayer);
        event.registerLayerDefinition(HornsModel.LAYER_LOCATION, HornsModel::createBodyLayer);
        event.registerLayerDefinition(PersonalMinecartModel.LAYER_LOCATION, PersonalMinecartModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAtributes(EntityAttributeCreationEvent event){
        event.put(ModEntities.PACKWING.get(), PackwingEntity.createAttributes().build());
        event.put(ModEntities.SALAMANDER.get(), SalamanderEntity.createAttributes().build());
        event.put(ModEntities.END_SQUID.get(), EndSquidEntity.createAttributes().build());
        event.put(ModEntities.ECHO_CRAB.get(), EchoCrabEntity.createAttributes().build());
        event.put(ModEntities.AMETHYST_CRAB.get(), AmthystCrabEntity.createAttributes().build());
        event.put(ModEntities.MYCELIUM_TOAD.get(), MyceliumToadEntity.createAttributes().build());
        event.put(ModEntities.PRAYFINDER.get(), PrayfinderEntity.createAttributes().build());
        event.put(ModEntities.MIMICART.get(), MimicartEntity.createAttributes().build());
        event.put(ModEntities.LIVING_LIGHTNING.get(), LivingLightningEntity.createAttributes().build());
        event.put(ModEntities.NETHER_LEACH.get(), NetherLeachEntity.createAttributes().build());
        event.put(ModEntities.ROOTLING.get(), RootlingEntity.createAttributes().build());
        event.put(ModEntities.END_WYVERN.get(), EndWyvernEntity.createAttributes().build());
        event.put(ModEntities.BURRY.get(), BurryEntity.createAttributes().build());
        event.put(ModEntities.GRAVE.get(), GraveEntity.createAttributes().build());
        event.put(ModEntities.DEEPSHOVELER.get(), DeepshovelerEntity.createAttributes().build());
        event.put(ModEntities.MINER_SNACK.get(), AbstractFish.createAttributes().build());
        event.put(ModEntities.DREAM_PIXIE.get(), DreamPixie.createAttributes().build());
    }
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event){
        event.register(ModEntities.PACKWING.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, PackwingEntity::checkPackwingSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ModEntities.SALAMANDER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ModEntities.END_SQUID.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, EndSquidEntity::checkSquidSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ModEntities.AMETHYST_CRAB.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, AmthystCrabEntity::checkAmethystCrabSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(ModEntities.MYCELIUM_TOAD.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MyceliumToadEntity::checkMushroomSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(
                ModEntities.MIMICART.get(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MimicartEntity::checkMimicartSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR
        );
        event.register(
                ModEntities.LIVING_LIGHTNING.get(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                LivingLightningEntity::checkLivingLightningSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR
        );

        event.register(
                ModEntities.NETHER_LEACH.get(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                NetherLeachEntity::checkNetherLeachSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR
        );

        event.register(
                ModEntities.BURRY.get(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BurryEntity::checkBurryRules, RegisterSpawnPlacementsEvent.Operation.OR
        );
        event.register(ModEntities.MINER_SNACK.get(),
                SpawnPlacementTypes.IN_WATER,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MinersSnackEntity::checkMinersSnackSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR);
        event.register(
                ModEntities.DEEPSHOVELER.get(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                DeepshovelerEntity::checkDeepshovelerSpawnRules, RegisterSpawnPlacementsEvent.Operation.OR
        );
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent e)
    {
        PotionBrewing.Builder builder = e.getBuilder();
        builder.addMix(Potions.AWKWARD, ModItems.PRAYFINDER_FEATHER.get(), ModPotions.PHEROMONES_POTION);
        builder.addMix(Potions.AWKWARD, ModBlocks.VOID_ORE.asItem(), ModPotions.VOID_POTION);
    }

}
