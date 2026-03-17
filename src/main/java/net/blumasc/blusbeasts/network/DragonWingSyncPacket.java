package net.blumasc.blusbeasts.network;

import net.blumasc.blusbeasts.BlusBeastsMod;
import net.blumasc.blusbeasts.client.ClientWyvernData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.Map;
import java.util.UUID;

public record DragonWingSyncPacket(
        Map<UUID, Boolean> hasDragon
) implements CustomPacketPayload {

    public static final Type<DragonWingSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "wyvern_info"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, DragonWingSyncPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        Map<UUID, Boolean> map = pkt.hasDragon();
                        buf.writeInt(map.size());

                        for (Map.Entry<UUID, Boolean> entry : map.entrySet()) {
                            buf.writeUUID(entry.getKey());
                            buf.writeBoolean(entry.getValue());
                        }
                    },
                    buf -> {
                        int size = buf.readInt();
                        Map<UUID, Boolean> map = new java.util.HashMap<>();

                        for (int i = 0; i < size; i++) {
                            UUID uuid = buf.readUUID();
                            boolean hasDragon = buf.readBoolean();
                            map.put(uuid, hasDragon);
                        }

                        return new DragonWingSyncPacket(map);
                    }
            );

    public static void handle(DragonWingSyncPacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ClientWyvernData.hasDragon = pkt.hasDragon;
        });
    }
}
