package net.blumasc.blusbeasts.playerstate;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class PlayerDragonStateHandler {
    private static final String NBT_KEY = BlusBeastsMod.MODID+":wyvern_state";
    public static void saveState(LivingEntity player, PlayerDragonState state) {
        CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        CompoundTag stateTag = new CompoundTag();

        if(state.emptyBack()){
            stateTag.putBoolean("emptyBack", true);
        }else{
            stateTag.putBoolean("emptyBack", false);
            stateTag.putInt("homeX", state.home.getX());
            stateTag.putInt("homeY", state.home.getY());
            stateTag.putInt("homeZ", state.home.getZ());
            CompoundTag compoundtag = new CompoundTag();
            compoundtag.putString("id", state.backWyvern.getEncodeId());
            state.backWyvern.saveWithoutId(compoundtag);
            stateTag.put("backWyvern", compoundtag);
        }

        tag.put(NBT_KEY, stateTag);
        player.getPersistentData().put(Player.PERSISTED_NBT_TAG, tag);
    }

    public static PlayerDragonState loadState(LivingEntity player) {
        PlayerDragonState state = new PlayerDragonState();
        CompoundTag tag = player.getPersistentData().getCompound(Player.PERSISTED_NBT_TAG);
        if (!tag.contains(NBT_KEY)) return state;

        CompoundTag stateTag = tag.getCompound(NBT_KEY);

        if(stateTag.getBoolean("emptyBack")) return state;
        state.home = new BlockPos(stateTag.getInt("homeX"), stateTag.getInt("homeY"), stateTag.getInt("homeZ"));
        CompoundTag entityTag = stateTag.getCompound("backWyvern");

        Entity entity = EntityType.create(entityTag, player.level()).get();

        if (entity != null) {
            state.backWyvern = (EndWyvernEntity) entity;
        }


        return state;
    }
}
