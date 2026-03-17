package net.blumasc.blusbeasts.effect.custom;

import net.blumasc.blusbeasts.util.ModTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class PheromonesEffect extends MobEffect {
    public PheromonesEffect() {
        super(MobEffectCategory.HARMFUL, 0x00f4d8);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        double range = 16.0D + (amplifier * 4.0D);
        AABB box = entity.getBoundingBox().inflate(range);

        List<Mob> mobs = entity.level().getEntitiesOfClass(
                Mob.class,
                box,
                mob -> mob.isAlive()
                        && mob != entity
                        && mob.canAttack(entity)
                        && !mob.getType().is(ModTags.EntityTypes.IGNORES_PHEROMONES)
                        && mob.getTarget() == null
        );

        for (Mob mob : mobs) {
            mob.setTarget(entity);
        }

        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        if (duration % 20 == 0) {
            return true;
        }
        return false;
    }
}
