package net.blumasc.blusbeasts.entity;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.*;
import net.blumasc.blusbeasts.entity.custom.projectile.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Cod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, BlusBeastsMod.MODID);

    public static final Supplier<EntityType<PackwingEntity>> PACKWING =
            ENTITY_TYPES.register("packwing", () -> EntityType.Builder.of(PackwingEntity::new, MobCategory.AMBIENT).sized(0.75f, 0.35f).build("packwing"));

    public static final Supplier<EntityType<SalamanderEntity>> SALAMANDER =
            ENTITY_TYPES.register("salamander", () -> EntityType.Builder.of(SalamanderEntity::new, MobCategory.CREATURE).sized(1f,1f).build("salamander"));

    public static final Supplier<EntityType<EndSquidEntity>> END_SQUID =
            ENTITY_TYPES.register("end_squid", () -> EntityType.Builder.of(EndSquidEntity::new, MobCategory.AMBIENT)
                    .sized(0.4f, 0.5f).build("end_squid"));

    public static final Supplier<EntityType<EchoCrabEntity>> ECHO_CRAB =
            ENTITY_TYPES.register("echo_crab", () -> EntityType.Builder.of(EchoCrabEntity::new, MobCategory.CREATURE).sized(0.8f,0.7f).build("echo_crab"));

    public static final Supplier<EntityType<AmthystCrabEntity>> AMETHYST_CRAB =
            ENTITY_TYPES.register("amethyst_crab", () -> EntityType.Builder.of(AmthystCrabEntity::new, MobCategory.CREATURE).sized(0.8f,0.7f).build("amethyst_crab"));

    public static final Supplier<EntityType<MyceliumToadEntity>> MYCELIUM_TOAD =
            ENTITY_TYPES.register("mycelium_toad", () -> EntityType.Builder.of(MyceliumToadEntity::new, MobCategory.CREATURE)
                    .sized(1.4f, 1.2f).build("mycelium_toad"));

    public static final Supplier<EntityType<PrayfinderEntity>> PRAYFINDER =
            ENTITY_TYPES.register("prayfinder", () -> EntityType.Builder.of(PrayfinderEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.6f).build("prayfinder"));

    public static final Supplier<EntityType<MimicartEntity>> MIMICART =
            ENTITY_TYPES.register("mimicart", () -> EntityType.Builder.of(MimicartEntity::new, MobCategory.MONSTER)
                    .sized(0.98F, 0.7F).build("mimicart"));

    public static final Supplier<EntityType<LivingLightningEntity>> LIVING_LIGHTNING =
            ENTITY_TYPES.register("living_lightning", () -> EntityType.Builder.of(LivingLightningEntity::new, MobCategory.MONSTER)
                    .sized(0.4f, 0.4f).build("living_lightning"));

    public static final Supplier<EntityType<NetherLeachEntity>> NETHER_LEACH =
            ENTITY_TYPES.register("nether_leach", () -> EntityType.Builder.of(NetherLeachEntity::new, MobCategory.MONSTER)
                    .sized(0.4f, 0.3f).build("nether_leach"));

    public static final Supplier<EntityType<RootlingEntity>> ROOTLING =
            ENTITY_TYPES.register("rootling", () -> EntityType.Builder.of(RootlingEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.5f).build("rootling"));

    public static final Supplier<EntityType<EndWyvernEntity>> END_WYVERN =
            ENTITY_TYPES.register("end_wyvern", () -> EntityType.Builder.of(EndWyvernEntity::new, MobCategory.CREATURE)
                    .sized(1.2f, 2.2f).build("end_wyvern"));

    public static final Supplier<EntityType<BurryEntity>> BURRY =
            ENTITY_TYPES.register("burry", () -> EntityType.Builder.of(BurryEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.8f).build("burry"));

    public static final Supplier<EntityType<GraveEntity>> GRAVE =
            ENTITY_TYPES.register("grave", () -> EntityType.Builder.of(GraveEntity::new, MobCategory.MONSTER)
                    .sized(1.8f, 3.2f).build("grave"));

    public static final Supplier<EntityType<DeepshovelerEntity>> DEEPSHOVELER =
            ENTITY_TYPES.register("deepshoveler", () -> EntityType.Builder.of(DeepshovelerEntity::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.1f).build("deepshoveler"));

    public static final Supplier<EntityType<MinersSnackEntity>> MINER_SNACK = ENTITY_TYPES.register(
            "minersnack", () -> EntityType.Builder.of(MinersSnackEntity::new, MobCategory.WATER_AMBIENT).sized(0.5F, 0.3F).eyeHeight(0.195F).clientTrackingRange(4).build("minersnack")
    );

    public static final Supplier<EntityType<DreamPixie>> DREAM_PIXIE = ENTITY_TYPES.register(
            "dream_pixie", () -> EntityType.Builder.of(DreamPixie::new, MobCategory.AMBIENT).sized(0.3F, 0.5F).clientTrackingRange(4).build("dream_pixie")
    );

    public static final Supplier<EntityType<PersonalMinecart>> PERSONAL_MINECART =
            ENTITY_TYPES.register("personal_minecart",
                    () -> EntityType.Builder
                            .<PersonalMinecart>of(PersonalMinecart::new, MobCategory.MISC)
                            .sized(0.98F, 0.7F)
                            .clientTrackingRange(8)
                            .updateInterval(3)
                            .build("personal_minecart"));


    public static final Supplier<EntityType<InfestedArrowEntity>> INFESTED_ARROW =
            ENTITY_TYPES.register("infested_arrow", () -> EntityType.Builder.<InfestedArrowEntity>of(InfestedArrowEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("infested_arrow"));

    public static final Supplier<EntityType<DarknessBlobEntity>> DARKNESS_BLOCK =
            ENTITY_TYPES.register("darkness_block", () -> EntityType.Builder.<DarknessBlobEntity>of(DarknessBlobEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("darkness_block"));

    public static final Supplier<EntityType<GlitterBombEntity>> GLITTER_BOMB =
            ENTITY_TYPES.register("glitter_bomb", () -> EntityType.Builder.<GlitterBombEntity>of(GlitterBombEntity::new, MobCategory.MISC)
                    .sized(0.3f,0.3f).build("glitter_bomb"));

    public static final Supplier<EntityType<HeartProjectileEntity>> HEART =
            ENTITY_TYPES.register("heart_projectile", () -> EntityType.Builder.<HeartProjectileEntity>of(HeartProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build("heart_projectile"));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}
