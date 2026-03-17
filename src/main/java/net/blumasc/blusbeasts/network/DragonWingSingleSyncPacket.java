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

public record DragonWingSingleSyncPacket(
        UUID player,
        boolean hasWings
) implements CustomPacketPayload {

    public static final Type<DragonWingSingleSyncPacket> TYPE =
            new Type<>(ResourceLocation.fromNamespaceAndPath(BlusBeastsMod.MODID, "wyvern_info_single"));

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final StreamCodec<FriendlyByteBuf, DragonWingSingleSyncPacket> CODEC =
            StreamCodec.of(
                    (buf, pkt) -> {
                        buf.writeUUID(pkt.player);
                        buf.writeBoolean(pkt.hasWings);
                    },
                    buf -> {
                        UUID player = buf.readUUID();
                        boolean hasWings = buf.readBoolean();
                        return new DragonWingSingleSyncPacket(player, hasWings);
                    }
            );

    public static void handle(DragonWingSingleSyncPacket pkt, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ClientWyvernData.hasDragon.put(pkt.player, pkt.hasWings);
        });
    }
}
