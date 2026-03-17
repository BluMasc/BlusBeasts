package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.StructureTagsProvider;
import net.minecraft.tags.StructureTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModStructureTagProvider extends StructureTagsProvider {
    public ModStructureTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider,  @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        super.addTags(provider);
        tag(ModTags.Structures.MIMICART_SPAWNABLE)
                .addTag(StructureTags.MINESHAFT);
    }
}
