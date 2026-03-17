package net.blumasc.blusbeasts.block.custom;

import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class InfestedBlock extends Block {
    public InfestedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if(!level.isClientSide()){
            ItemStack heldItem = player.getMainHandItem();
            Holder<Enchantment> sandHolder = level.registryAccess()
                    .registryOrThrow(Registries.ENCHANTMENT)
                    .getHolderOrThrow(Enchantments.SILK_TOUCH);
            int eLevel = heldItem.getEnchantmentLevel(sandHolder);
            if (eLevel<=0) {
                spawnRandomMob(level, player, pos);
            }
        }
        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    private void spawnRandomMob(Level level, Player player, BlockPos pos) {
        if (level.isClientSide) return;

        List<Holder<EntityType<?>>> validMobs = level.registryAccess().registryOrThrow(Registries.ENTITY_TYPE)
                .getTag(ModTags.EntityTypes.INFESTATION_MOBS)
                .map(tag -> tag.stream().toList())
                .orElse(Collections.emptyList());

        if (validMobs.isEmpty()) return;

        Holder<EntityType<?>> mobHolder = validMobs.get(level.random.nextInt(validMobs.size()));

        EntityType<?> entityType = mobHolder.value();
        if (!(entityType.create(level) instanceof Mob mob)) return;
        mob.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, level.random.nextFloat() * 360f, 0f);
        if (player instanceof LivingEntity) {
            mob.setTarget(player);
        }

        level.addFreshEntity(mob);
    }

    @Override
    public PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        if(mob == null)return PathType.DAMAGE_OTHER;
        if(mob.getType().is(ModTags.EntityTypes.INFESTATION_MOBS)) {
            return PathType.WALKABLE;
        }
        return PathType.DAMAGE_OTHER;
    }


}
