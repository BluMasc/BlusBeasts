package net.blumasc.blusbeasts.mixin;

import net.blumasc.blusbeasts.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import top.theillusivec4.curios.api.CuriosApi;

@Mixin(TemptGoal.class)
public class GoldenAntlersTemptGoalMixin {

    @Inject(
            method = "shouldFollow",
            at = @At("HEAD"),
            cancellable = true
    )
    private void shouldFollow(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if(entity instanceof Player p) {
            if (isGoldenAntlers(p))cir.setReturnValue(true);
        }
    }

    private static boolean isGoldenAntlers(Player player) {
        return CuriosApi.getCuriosInventory(player)
                .flatMap(inv -> inv.getStacksHandler("head"))
                .map(handler -> {
                    for (int slot = 0; slot < handler.getSlots(); slot++) {
                        ItemStack stack = handler.getStacks().getStackInSlot(slot);
                        if (!stack.isEmpty() && stack.is(ModItems.GOLDEN_ANTLERS.get())) {
                            return true;
                        }
                    }
                    return false;
                })
                .orElse(false);
    }
}
