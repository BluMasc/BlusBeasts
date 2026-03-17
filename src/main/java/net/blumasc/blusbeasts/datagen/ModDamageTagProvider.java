package net.blumasc.blusbeasts.datagen;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.damage.ModDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModDamageTagProvider extends DamageTypeTagsProvider {
    public ModDamageTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.NO_KNOCKBACK)
                .add(ModDamageTypes.LEACH_DAMAGE);
        tag(DamageTypeTags.BYPASSES_SHIELD)
                .add(ModDamageTypes.LEACH_DAMAGE);
        tag(DamageTypeTags.BYPASSES_ARMOR)
                .add(ModDamageTypes.LEACH_DAMAGE);
    }
}