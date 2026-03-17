package net.blumasc.blusbeasts.datagen;

import net.blumasc.blubasics.util.BaseModTags;
import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends EntityTypeTagsProvider {
    public ModEntityTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, BlusBeastsMod.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.EntityTypes.PRAYFINDER_FRIENDS)
                .add(EntityType.ZOMBIE)
                .add(EntityType.ZOMBIE_VILLAGER)
                .add(EntityType.HUSK);
        tag(ModTags.EntityTypes.IGNORES_PHEROMONES)
                .add(ModEntities.PRAYFINDER.get())
                .add(EntityType.WITHER);
        tag(ModTags.EntityTypes.INFESTATION_MOBS)
                .add(ModEntities.NETHER_LEACH.get())
                .add(EntityType.SILVERFISH)
                .add(EntityType.ENDERMITE)
                .add(EntityType.BEE);
        tag(EntityTypeTags.ARTHROPOD)
                .add(ModEntities.ECHO_CRAB.get())
                .add(ModEntities.NETHER_LEACH.get());
        tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(ModEntities.END_SQUID.get())
                .add(ModEntities.PACKWING.get())
                .add(ModEntities.MYCELIUM_TOAD.get());
        tag(EntityTypeTags.FREEZE_HURTS_EXTRA_TYPES)
                .add(ModEntities.SALAMANDER.get());
        tag(EntityTypeTags.SENSITIVE_TO_BANE_OF_ARTHROPODS)
                .add(ModEntities.ECHO_CRAB.get())
                .add(ModEntities.NETHER_LEACH.get());
        tag(EntityTypeTags.AQUATIC)
                .add(ModEntities.MYCELIUM_TOAD.get());
        tag(EntityTypeTags.SENSITIVE_TO_IMPALING)
                .add(ModEntities.MYCELIUM_TOAD.get());
        tag(EntityTypeTags.IMMUNE_TO_INFESTED)
                .add(ModEntities.NETHER_LEACH.get());
        tag(EntityTypeTags.ARROWS)
                .add(ModEntities.INFESTED_ARROW.get());
        tag(BaseModTags.EntityTypes.SAND_IMMUNE)
                .add(ModEntities.BURRY.get())
                .add(ModEntities.GRAVE.get());
        tag(Tags.EntityTypes.BOSSES)
                .add(ModEntities.GRAVE.get());
        tag(Tags.EntityTypes.CAPTURING_NOT_SUPPORTED)
                .add(ModEntities.GRAVE.get());
    }
}
