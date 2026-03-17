package net.blumasc.blusbeasts.entity.custom.projectile;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class InfestedArrowEntity extends AbstractArrow {
    public InfestedArrowEntity(EntityType<? extends AbstractArrow> entityType, Level level) {
        super(entityType, level);
    }

    public InfestedArrowEntity(LivingEntity owner, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.INFESTED_ARROW.get(), owner, level, pickupItemStack, firedFromWeapon);
    }

    public InfestedArrowEntity(double x, double y, double z, Level level, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
        super(ModEntities.INFESTED_ARROW.get(), x, y, z, level, pickupItemStack, firedFromWeapon);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return null;
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        spawnRandomMob(this.level(), null, this.position());
        this.discard();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        spawnRandomMob(this.level(), result.getEntity(), result.getEntity().position());
        super.onHitEntity(result);
    }

    private void spawnRandomMob(Level level, Entity target, Vec3 pos) {
        if (level.isClientSide) return;

        List<Holder<EntityType<?>>> validMobs = level.registryAccess().registryOrThrow(Registries.ENTITY_TYPE)
                .getTag(ModTags.EntityTypes.INFESTATION_MOBS)
                .map(tag -> tag.stream().toList())
                .orElse(Collections.emptyList());

        if (validMobs.isEmpty()) return;

        Holder<EntityType<?>> mobHolder = validMobs.get(level.random.nextInt(validMobs.size()));

        EntityType<?> entityType = mobHolder.value();
        if (!(entityType.create(level) instanceof Mob mob)) return;
        mob.moveTo(pos.x, pos.y, pos.z + 0.5, level.random.nextFloat() * 360f, 0f);
        if (target instanceof LivingEntity le) {
            mob.setTarget(le);
        }

        level.addFreshEntity(mob);
    }
}
