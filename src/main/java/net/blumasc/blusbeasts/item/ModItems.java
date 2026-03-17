package net.blumasc.blusbeasts.item;

import net.blumasc.blubasics.item.custom.CurioItem;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.item.custom.*;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlusBeastsMod.MODID);

    public static final DeferredItem<Item> SALAMANDER_GOO = ITEMS.register("salamander_goo",
            () -> new SalamanderGooItem(new Item.Properties()));

    public static final DeferredItem<Item> SALAMANDER_SCALES = ITEMS.register("salamander_scales",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> END_TAKOYAKI= ITEMS.register("end_takoyaki",
            () -> new Item(new Item.Properties().food(ModFoodProperties.LUNAR_TAKOYAKI)));

    public static final DeferredItem<CurioItem> EMBEDDED_CRYSTALS = ITEMS.register("embedded_crystals",
            () -> new CurioItem(new Item.Properties()));

    public static final DeferredItem<CurioItem> ANTLERS = ITEMS.register("antlers",
            () -> new CurioItem(new Item.Properties()));

    public static final DeferredItem<CurioItem> GOLDEN_ANTLERS = ITEMS.register("golden_antlers",
            () -> new CurioItem(new Item.Properties()));

    public static final DeferredItem<Item> PRAYFINDER_FEATHER= ITEMS.register("prayfinder_feather",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<CurioItem> PERSONAL_MINECART = ITEMS.register("personal_minecart",
            () -> new CurioItem(new Item.Properties()));

    public static final DeferredItem<Item> LIVING_WHEELS = ITEMS.register("living_wheels",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<PickerangItem> PICKERANG = ITEMS.register("pickerang",
            () -> new PickerangItem(Tiers.NETHERITE, new Item.Properties().fireResistant().attributes(PickaxeItem.createAttributes(Tiers.NETHERITE, 0.8F, -3.0F))));

    public static final DeferredItem<ChimeraCoreItem> CHIMERA_CORE = ITEMS.register("chimera_core",
            () -> new ChimeraCoreItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<NetherLeachItem> NETHER_LEACH = ITEMS.register("nether_leach",
            () -> new NetherLeachItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<InfestedArrowItem> INFESTED_ARROW = ITEMS.register("infested_arrow",
            () -> new InfestedArrowItem(new Item.Properties()));

    public static final DeferredItem<Item> BURROW_ROD = ITEMS.register("burrow_rod",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<DormantScarabItem> SCARAB_DORMANT = ITEMS.register("scarab_dormant",
            () -> new DormantScarabItem(new Item.Properties()));

    public static final DeferredItem<Item> SCARAB = ITEMS.register("scarab",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PICKERANG_UPGRADE_TEMPLATE = ITEMS.register("pickerang_upgrade_template",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BURROW_GEM = ITEMS.register("burrow_gem",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<GravityDustItem> GRAVITY_DUST = ITEMS.register("gravity_dust",
            () -> new GravityDustItem(new Item.Properties()));

    public static final DeferredItem<Item> MINERS_SNACK_BUCKET = ITEMS.register("miners_snack_bucket",
            () -> new MobBucketItem(ModEntities.MINER_SNACK.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, (new Item.Properties()).stacksTo(1).component(DataComponents.BUCKET_ENTITY_DATA, CustomData.EMPTY)));

    public static final DeferredItem<Item> MINERS_SNACK = ITEMS.register("miners_snack",
            () -> new MinersSnackItem(new Item.Properties().food(ModFoodProperties.COOKED_MINERS_SNACK).stacksTo(1)));

    public static final DeferredItem<Item> COOKED_MINERS_SNACK = ITEMS.register("cooked_miners_snack",
            () -> new FishBoneSkewerFoodItem(new Item.Properties().food(ModFoodProperties.COOKED_MINERS_SNACK)));

    public static final DeferredItem<Item> FISH_BONE_SKEWER = ITEMS.register("fish_bone_skewer",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> FAIRY_DUST = ITEMS.register("fairy_dust",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GRAVE_GEM = ITEMS.register("grave_gem",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PARTY_POPPER = ITEMS.register("party_popper",
            () -> new PartyPopperItem(new Item.Properties()));

    public static final DeferredItem<Item> GLITTER_BOMB = ITEMS.register("glitter_bomb",
            () -> new GlitterBombItem(new Item.Properties()));

    public static final DeferredItem<Item> MAGIC_WAND = ITEMS.register("magic_wand",
            () -> new MagicWandItem(new Item.Properties().stacksTo(1).durability(50).component(DataComponents.DYED_COLOR, new DyedItemColor(0xFFFFFF, false))));

    public static final DeferredItem<Item> PACKWING_SPAWN_EGG = ITEMS.register("packwing_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PACKWING, 0xffffff, 0xfafafa, new Item.Properties()));

    public static final DeferredItem<Item> SALAMANDER_SPAWN_EGG = ITEMS.register("salamander_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SALAMANDER, 0xb67c3b, 0xff6c2c, new Item.Properties()));

    public static final DeferredItem<Item> END_SQUID_SPAWN_EGG = ITEMS.register("end_squid_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.END_SQUID, 0x7250bd, 0xd686be, new Item.Properties()));

    public static final DeferredItem<Item> ECHO_CRAB_SPAWN_EGG = ITEMS.register("echo_crab_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.ECHO_CRAB, 0x101b21, 0x29dfeb, new Item.Properties()));

    public static final DeferredItem<Item> AMETHYST_CRAB_SPAWN_EGG = ITEMS.register("amethyst_crab_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.AMETHYST_CRAB, 0x7857b2, 0x8a8a8e, new Item.Properties()));

    public static final DeferredItem<Item> MYCELIUM_TOAD_SPAWN_EGG = ITEMS.register("mycelium_toad_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MYCELIUM_TOAD, 0x947393, 0x7d7a41, new Item.Properties()));

    public static final DeferredItem<Item> PRAYFINDER_SPAWN_EGG = ITEMS.register("prayfinder_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PRAYFINDER, 0x21392d, 0x151c3c, new Item.Properties()));

    public static final DeferredItem<Item> MIMICART_SPAWN_EGG = ITEMS.register("mimicart_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MIMICART, 0xaf3465, 0x676f73, new Item.Properties()));

    public static final DeferredItem<Item> LIVING_LIGHTNING_SPAWN_EGG = ITEMS.register("living_lightning_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.LIVING_LIGHTNING, 0xd5fbfb, 0x5d639c, new Item.Properties()));

    public static final DeferredItem<Item> NETHER_LEACH_SPAWN_EGG = ITEMS.register("nether_leach_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.NETHER_LEACH, 0x903939, 0x302020, new Item.Properties()));

    public static final DeferredItem<Item> ROOTLING_SPAWN_EGG = ITEMS.register("rootling_spawn_egg",
            () -> new RootlingSpawnEggItem(new Item.Properties()));

    public static final DeferredItem<Item> END_WYVERN_SPAWN_EGG = ITEMS.register("end_wyvern_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.END_WYVERN, 0x553c55, 0xc6cb97, new Item.Properties()));

    public static final DeferredItem<Item> BURRY_SPAWN_EGG = ITEMS.register("burry_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.BURRY, 0xded4a7, 0xffff27, new Item.Properties()));

    public static final DeferredItem<Item> DEEPSHOVELER_SPAWN_EGG = ITEMS.register("deepshoveler_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.DEEPSHOVELER, 0x3d3d43, 0xcb9ed8, new Item.Properties()));

    public static final DeferredItem<Item> MINERS_SNACK_SPAWN_EGG = ITEMS.register("miners_snack_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.MINER_SNACK, 0x6b5c71, 0x15462d, new Item.Properties()));

    public static final DeferredItem<Item> DREAM_PIXIE_SPAWN_EGG = ITEMS.register("dream_pixie_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.DREAM_PIXIE, 0xbf76ce, 0x4c41a2, new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_FOX = ITEMS.register("chimera_core_fox",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_CHICKEN = ITEMS.register("chimera_core_chicken",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_GOAT = ITEMS.register("chimera_core_goat",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_GUARDIAN = ITEMS.register("chimera_core_guardian",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_HOGLIN = ITEMS.register("chimera_core_hoglin",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_PHANTOM = ITEMS.register("chimera_core_phantom",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> CHIMERA_CORE_RABBIT = ITEMS.register("chimera_core_rabbit",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}
