package net.blumasc.blusbeasts.network;

import net.blumasc.blusbeasts.BlusBeastsMod;

public class ModNetworking {
    public static void register(net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent event) {
        event.registrar(BlusBeastsMod.MODID).playToClient(
                DragonWingSyncPacket.TYPE,
                DragonWingSyncPacket.CODEC,
                DragonWingSyncPacket::handle
        );

        event.registrar(BlusBeastsMod.MODID).playToClient(
                DragonWingSingleSyncPacket.TYPE,
                DragonWingSingleSyncPacket.CODEC,
                DragonWingSingleSyncPacket::handle
        );
    }
}
