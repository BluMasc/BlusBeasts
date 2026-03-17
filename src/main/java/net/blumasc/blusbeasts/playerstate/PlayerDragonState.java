package net.blumasc.blusbeasts.playerstate;

import net.blumasc.blusbeasts.entity.custom.EndWyvernEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class PlayerDragonState {
    BlockPos home = null;
    EndWyvernEntity backWyvern = null;
    public boolean emptyBack()
    {
        return backWyvern==null;
    }
    public boolean toFarFromHome(BlockPos pos){
        if(home == null) return true;
        else return home.distSqr(pos) > 2500;
    }
    public EndWyvernEntity detatchWyvern(){
        EndWyvernEntity e = backWyvern;
        backWyvern = null;
        return e;
    }
    public EndWyvernEntity getWyvern(){
        return backWyvern;
    }
    public void attachWyvenrn(EndWyvernEntity e){
        home = e.getHome();
        backWyvern = e;
    }
}
