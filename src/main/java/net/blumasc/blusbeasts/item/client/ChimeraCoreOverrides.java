package net.blumasc.blusbeasts.item.client;

import net.blumasc.blubasics.util.BaseModTags;
import net.blumasc.blusbeasts.item.custom.components.ModItemDataComponents;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChimeraCoreOverrides extends ItemOverrides {

    private final Map<Integer, BakedModel> variants;

    public ChimeraCoreOverrides(Map<Integer, BakedModel> variants) {
        this.variants = variants;
    }

    @Override
    public BakedModel resolve(BakedModel originalModel,
                              ItemStack stack,
                              @Nullable ClientLevel level,
                              @Nullable LivingEntity entity,
                              int seed) {

        int mask = 0;
        Set<EntityType<?>> collected =
                stack.getOrDefault(ModItemDataComponents.COLLECTED_MOBS.get(), Set.of());

        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_FOX))) mask |= 1 << 0;
        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_CHICKEN))) mask |= 1 << 1;
        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_GOAT))) mask |= 1 << 2;
        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_GUARDIAN))) mask |= 1 << 3;
        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_PHANTOM))) mask |= 1 << 4;
        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_RABBIT))) mask |= 1 << 5;
        if (collected.stream().anyMatch(e -> e.is(BaseModTags.EntityTypes.CHIMERA_LIKE_HOGLIN))) mask |= 1 << 6;

        return variants.getOrDefault(mask, originalModel);
    }
}
