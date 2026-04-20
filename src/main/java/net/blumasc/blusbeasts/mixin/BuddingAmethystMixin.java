package net.blumasc.blusbeasts.mixin;

import net.blumasc.blusbeasts.Config;
import net.blumasc.blusbeasts.entity.ModEntities;
import net.blumasc.blusbeasts.entity.custom.AmthystCrabEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.renderer.LevelRenderer.DIRECTIONS;
import static net.minecraft.world.level.block.BuddingAmethystBlock.canClusterGrowAtState;

@Mixin(BuddingAmethystBlock.class)
public class BuddingAmethystMixin {


    @Inject(
            method = "randomTick",
            at = @At("HEAD"),
            cancellable = true
    )
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random, CallbackInfo ci) {
        if(!Config.SPAWN_AMETHYST_CRAB.get()) return;
        if (random.nextInt(30) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockpos = pos.relative(direction);
            BlockState blockstate = level.getBlockState(blockpos);
            if (canClusterGrowAtState(blockstate)) {
                AmthystCrabEntity e = ModEntities.AMETHYST_CRAB.get().create(level);
                e.moveTo(blockpos.getBottomCenter());
                level.addFreshEntity(e);
                ci.cancel();
            }
        }

    }
}
