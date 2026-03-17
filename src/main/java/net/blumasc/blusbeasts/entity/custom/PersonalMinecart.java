package net.blumasc.blusbeasts.entity.custom;

import net.blumasc.blusbeasts.entity.ModEntities;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class PersonalMinecart extends AbstractMinecart {
    public PersonalMinecart(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    public PersonalMinecart(Level level, double x, double y, double z) {
        super(ModEntities.PERSONAL_MINECART.get(), level, x, y, z);
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        double yOffset = 0.3125;
        return new Vec3(this.getX(), this.getY()+yOffset, this.getZ());
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if (this.getPassengers().isEmpty()) {
                this.discard();
            }
        }
    }

    protected @NotNull Item getDropItem() {
        return ItemStack.EMPTY.getItem();
    }

    public void activateMinecart(int x, int y, int z, boolean receivingPower) {
        if (receivingPower) {
            if (this.isVehicle()) {
                this.ejectPassengers();
            }

            if (this.getHurtTime() == 0) {
                this.setHurtDir(-this.getHurtDir());
                this.setHurtTime(10);
                this.setDamage(50.0F);
                this.markHurt();
            }
        }

    }

    public AbstractMinecart.Type getMinecartType() {
        return Type.RIDEABLE;
    }

    @Override
    protected double getMaxSpeed() {
        return (this.isInWater() ? (double)6.0F : (double)12.0F) / (double)20.0F;
    }

    @Override
    public double getMaxSpeedWithRail() {
        return super.getMaxSpeedWithRail()*1.2;
    }
}
