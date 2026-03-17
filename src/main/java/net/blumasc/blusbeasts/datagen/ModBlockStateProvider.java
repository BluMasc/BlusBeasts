package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.block.ModBlocks;
import net.blumasc.blusbeasts.block.custom.VoidOreMagnetBlock;
import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.fml.loading.moddiscovery.ModValidator;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BlusBeastsMod.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.INFESTED_NETHERRACK);
        blockWithItem(ModBlocks.GRAVE_SANDSTONE);
        blockWithItem(ModBlocks.GRAVE_RED_SANDSTONE);
        blockWithItem(ModBlocks.VOID_ORE);
        blockWithItem(ModBlocks.RAINBOW_WOOL);
        blockWithItem(ModBlocks.RAINBOW_CONCRETE);
        simpleBlock(ModBlocks.RAINBOW_CARPET.get(),
                models().carpet("rainbow_carpet", modLoc("block/rainbow_wool")));
        blockItem(ModBlocks.RAINBOW_CARPET);
        simpleBlock(ModBlocks.PIXIE_TORCH_BLOCK.get(),
                models().withExistingParent("pixie_torch",  mcLoc("block/template_torch"))
                        .texture("torch", modLoc("block/pixie_torch")));
        getVariantBuilder(ModBlocks.WALL_PIXIE_TORCH.get())
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.EAST)
                .modelForState().modelFile(models().torchWall("wall_pixie_torch", modLoc("block/pixie_torch"))).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.WEST)
                .modelForState().modelFile(models().torchWall("wall_pixie_torch", modLoc("block/pixie_torch"))).rotationY(180).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.NORTH)
                .modelForState().modelFile(models().torchWall("wall_pixie_torch", modLoc("block/pixie_torch"))).rotationY(270).addModel()
                .partialState().with(HorizontalDirectionalBlock.FACING, Direction.SOUTH)
                .modelForState().modelFile(models().torchWall("wall_pixie_torch", modLoc("block/pixie_torch"))).rotationY(90).addModel();
        var voidOreMagnetBlock = ModBlocks.VOID_ORE_MAGNET.get();
        ModelFile inactiveModel = models().cubeBottomTop(
                name(voidOreMagnetBlock),
                modLoc("block/void_ore_magnet_side"),
                mcLoc("block/purpur_block"),
                modLoc("block/void_ore_magnet_top")
        );
        ModelFile activeModel = models().cubeBottomTop(
                name(voidOreMagnetBlock) + "_active",
                modLoc("block/void_ore_magnet_side_active"),
                mcLoc("block/purpur_block"),
                modLoc("block/void_ore_magnet_top_active")
        );
        getVariantBuilder(voidOreMagnetBlock)
                .partialState().with(VoidOreMagnetBlock.ACTIVE, false)
                .modelForState().modelFile(inactiveModel).addModel()

                .partialState().with(VoidOreMagnetBlock.ACTIVE, true)
                .modelForState().modelFile(activeModel).addModel();
        simpleBlockItem(voidOreMagnetBlock, inactiveModel);
        plateItem(ModBlocks.PLATE, "empty");
        plateItem(ModBlocks.PLATE_CRYSTAL, "crystal");
        plateItem(ModBlocks.PLATE_DONUT, "donut");
        plateItem(ModBlocks.PLATE_HORNS, "horns");
        plateItem(ModBlocks.PLATE_LEACH, "leach");
        plateItem(ModBlocks.PLATE_LIGHTNING, "lightning");
        plateItem(ModBlocks.PLATE_MIMICART, "mimicart");
        plateItem(ModBlocks.PLATE_PHEROMONES, "pheromones");
        plateItem(ModBlocks.PLATE_PIXIE, "pixie");
        plateItem(ModBlocks.PLATE_SAND, "sand");
        plateItem(ModBlocks.PLATE_SAPLING, "sapling");
        plateItem(ModBlocks.PLATE_SCARAB, "scarab");
        plateItem(ModBlocks.PLATE_SHROOM, "shroom");
        plateItem(ModBlocks.PLATE_SQUID, "squid");
        plateItem(ModBlocks.PLATE_VOID, "void");
        plateItem(ModBlocks.PLATE_WINGS, "wings");
    }

    private void plateItem(DeferredBlock<?> deferredBlock, String name){
        ModelFile simplePlateModel = models().withExistingParent(deferredBlock.getId().getPath(), BlusBeastsMod.MODID+":block/plate_base")
                .texture("0", modLoc("block/plate_"+name));
        horizontalBlock(deferredBlock.get(), simplePlateModel);
        blockItem(deferredBlock);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock){
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock)
    {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(BlusBeastsMod.MODID+":block/"+deferredBlock.getId().getPath()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String apendix)
    {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(BlusBeastsMod.MODID+":block/"+deferredBlock.getId().getPath()+apendix));
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private String name(Block block) {
        return this.key(block).getPath();
    }
}
