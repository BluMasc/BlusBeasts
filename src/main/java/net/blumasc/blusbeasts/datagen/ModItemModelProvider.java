package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItemModelProvider  extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.SALAMANDER_GOO.get());
        basicItem(ModItems.SALAMANDER_SCALES.get());
        basicItem(ModItems.END_TAKOYAKI.get());
        basicItem(ModItems.EMBEDDED_CRYSTALS.get());
        basicItem(ModItems.ANTLERS.get());
        basicItem(ModItems.GOLDEN_ANTLERS.get());
        basicItem(ModItems.PRAYFINDER_FEATHER.get());
        basicItem(ModItems.PERSONAL_MINECART.get());
        basicItem(ModItems.LIVING_WHEELS.get());
        basicItem(ModItems.INFESTED_ARROW.get());
        basicItem(ModItems.ROOTLING_SPAWN_EGG.get());
        basicItem(ModBlocks.END_WYVERN_EGG.asItem());
        basicItem(ModItems.BURROW_ROD.get());
        basicItem(ModItems.GRAVITY_DUST.get());
        basicItem(ModItems.SCARAB_DORMANT.get());
        basicItem(ModItems.SCARAB.get());
        basicItem(ModItems.BURROW_GEM.get());
        basicItem(ModItems.PICKERANG_UPGRADE_TEMPLATE.get());
        basicItem(ModItems.MINERS_SNACK_BUCKET.get());
        basicItem(ModItems.COOKED_MINERS_SNACK.get());
        basicItem(ModItems.FISH_BONE_SKEWER.get());
        basicItem(ModItems.FAIRY_DUST.get());
        basicItem(ModItems.GRAVE_GEM.get());
        basicItem(ModItems.PARTY_POPPER.get());
        basicItem(ModItems.GLITTER_BOMB.get());
        torch("pixie_torch", modLoc("block/pixie_torch"));
        handheldItem(ModItems.PICKERANG);
        spawnEggItem(ModItems.PACKWING_SPAWN_EGG);
        spawnEggItem(ModItems.SALAMANDER_SPAWN_EGG);
        spawnEggItem(ModItems.END_SQUID_SPAWN_EGG);
        spawnEggItem(ModItems.ECHO_CRAB_SPAWN_EGG);
        spawnEggItem(ModItems.AMETHYST_CRAB_SPAWN_EGG);
        spawnEggItem(ModItems.MYCELIUM_TOAD_SPAWN_EGG);
        spawnEggItem(ModItems.PRAYFINDER_SPAWN_EGG);
        spawnEggItem(ModItems.MIMICART_SPAWN_EGG);
        spawnEggItem(ModItems.LIVING_LIGHTNING_SPAWN_EGG);
        spawnEggItem(ModItems.NETHER_LEACH_SPAWN_EGG);
        spawnEggItem(ModItems.END_WYVERN_SPAWN_EGG);
        spawnEggItem(ModItems.BURRY_SPAWN_EGG);
        spawnEggItem(ModItems.DEEPSHOVELER_SPAWN_EGG);
        spawnEggItem(ModItems.MINERS_SNACK_SPAWN_EGG);
        spawnEggItem(ModItems.DREAM_PIXIE_SPAWN_EGG);
        withExistingParent(ModItems.MINERS_SNACK.getId().getPath(), "item/generated")
                .texture("layer0", modLoc("item/miners_snack"))
                .texture("layer1", modLoc("item/layers/miners_snack_top_fin"))
                .texture("layer2", modLoc("item/layers/miners_snack_bottom_fin"));

        withExistingParent(ModItems.MAGIC_WAND.getId().getPath(), "item/handheld")
                .texture("layer0", modLoc("item/magic_wand"))
                .texture("layer1", modLoc("item/layers/magic_wand_color"));

    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder spawnEggItem(DeferredItem<?> item){
        return withExistingParent(item.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }
}
