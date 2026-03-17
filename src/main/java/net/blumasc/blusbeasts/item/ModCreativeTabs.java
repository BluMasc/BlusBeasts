package net.blumasc.blusbeasts.item;

import net.blumasc.blubasics.block.BaseModBlocks;
import net.blumasc.blubasics.item.BaseModItems;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlusBeastsMod.MODID);

    public static final Supplier<CreativeModeTab> SELECTIVE_POWERS_TAB = CREATIVE_MODE_TAB.register("blus_beasts_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.SALAMANDER_SCALES.get())).title(Component.translatable("itemGroup.blusbeasts"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(BaseModItems.BEETLE_HORN);
                        output.accept(BaseModItems.SUN_HORN);
                        output.accept(BaseModItems.LIGHTNING_IN_A_BOTTLE);
                        output.accept(ModItems.SALAMANDER_SCALES);
                        output.accept(ModItems.SALAMANDER_GOO);
                        output.accept(ModItems.END_TAKOYAKI);
                        output.accept(BaseModItems.MUSHROOM_SKEWER);
                        output.accept(BaseModItems.COOKED_MUSHROOM_SKEWER);
                        output.accept(BaseModItems.SPINE_TREE);
                        output.accept(ModItems.EMBEDDED_CRYSTALS);
                        output.accept(ModItems.ANTLERS);
                        output.accept(ModItems.GOLDEN_ANTLERS);
                        output.accept(ModItems.PRAYFINDER_FEATHER);
                        output.accept(ModItems.LIVING_WHEELS);
                        output.accept(ModItems.PERSONAL_MINECART);
                        output.accept(ModItems.CHIMERA_CORE);
                        output.accept(ModItems.NETHER_LEACH);
                        output.accept(ModItems.INFESTED_ARROW);
                        output.accept(ModItems.BURROW_ROD);
                        output.accept(ModItems.GRAVITY_DUST);
                        output.accept(ModItems.BURROW_GEM);
                        output.accept(ModItems.SCARAB_DORMANT);
                        output.accept(ModItems.SCARAB);
                        output.accept(ModItems.GRAVE_GEM);
                        output.accept(ModItems.PICKERANG_UPGRADE_TEMPLATE);
                        output.accept(ModItems.PICKERANG);
                        output.accept(ModItems.MINERS_SNACK);
                        output.accept(ModItems.MINERS_SNACK_BUCKET);
                        output.accept(ModItems.COOKED_MINERS_SNACK);
                        output.accept(ModItems.FISH_BONE_SKEWER);
                        output.accept(ModItems.FAIRY_DUST);
                        output.accept(ModItems.PARTY_POPPER);
                        output.accept(ModItems.GLITTER_BOMB);
                        output.accept(ModItems.MAGIC_WAND);
                        output.accept(BaseModBlocks.JUMP_MUSHROOM);
                        output.accept(BaseModBlocks.SPORE_MUSHROOM_BLOCK);
                        output.accept(ModBlocks.INFESTED_NETHERRACK);
                        output.accept(ModBlocks.END_WYVERN_EGG);
                        output.accept(ModBlocks.END_WYVERN_NEST);
                        output.accept(ModBlocks.VOID_ORE);
                        output.accept(ModBlocks.VOID_ORE_MAGNET);
                        output.accept(ModBlocks.PLATE);
                        output.accept(ModBlocks.BLUEBELLS);
                        output.accept(ModBlocks.GRAVE_SANDSTONE);
                        output.accept(ModBlocks.GRAVE_RED_SANDSTONE);
                        output.accept(ModBlocks.PIXIE_TORCH);
                        output.accept(ModBlocks.RAINBOW_WOOL);
                        output.accept(ModBlocks.RAINBOW_CARPET);
                        output.accept(ModBlocks.RAINBOW_CONCRETE);
                        output.accept(ModItems.PACKWING_SPAWN_EGG);
                        output.accept(ModItems.SALAMANDER_SPAWN_EGG);
                        output.accept(BaseModItems.SOLAR_BEETLE_SPAWN_EGG);
                        output.accept(ModItems.END_SQUID_SPAWN_EGG);
                        output.accept(ModItems.ECHO_CRAB_SPAWN_EGG);
                        output.accept(ModItems.AMETHYST_CRAB_SPAWN_EGG);
                        output.accept(BaseModItems.CHIMERA_SPAWN_EGG);
                        output.accept(ModItems.MYCELIUM_TOAD_SPAWN_EGG);
                        output.accept(ModItems.PRAYFINDER_SPAWN_EGG);
                        output.accept(ModItems.MIMICART_SPAWN_EGG);
                        output.accept(ModItems.LIVING_LIGHTNING_SPAWN_EGG);
                        output.accept(ModItems.NETHER_LEACH_SPAWN_EGG);
                        output.accept(ModItems.ROOTLING_SPAWN_EGG);
                        output.accept(ModItems.END_WYVERN_SPAWN_EGG);
                        output.accept(ModItems.BURRY_SPAWN_EGG);
                        output.accept(ModItems.DEEPSHOVELER_SPAWN_EGG);
                        output.accept(ModItems.MINERS_SNACK_SPAWN_EGG);
                        output.accept(ModItems.DREAM_PIXIE_SPAWN_EGG);
                        output.accept(ModBlocks.PLATE_CRYSTAL);
                        output.accept(ModBlocks.PLATE_DONUT);
                        output.accept(ModBlocks.PLATE_HORNS);
                        output.accept(ModBlocks.PLATE_LEACH);
                        output.accept(ModBlocks.PLATE_LIGHTNING);
                        output.accept(ModBlocks.PLATE_MIMICART);
                        output.accept(ModBlocks.PLATE_PHEROMONES);
                        output.accept(ModBlocks.PLATE_PIXIE);
                        output.accept(ModBlocks.PLATE_SAND);
                        output.accept(ModBlocks.PLATE_SAPLING);
                        output.accept(ModBlocks.PLATE_SCARAB);
                        output.accept(ModBlocks.PLATE_SHROOM);
                        output.accept(ModBlocks.PLATE_SQUID);
                        output.accept(ModBlocks.PLATE_VOID);
                        output.accept(ModBlocks.PLATE_WINGS);
                    }).build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
