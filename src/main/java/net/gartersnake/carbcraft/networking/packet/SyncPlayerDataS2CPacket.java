package net.gartersnake.carbcraft.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.gartersnake.carbcraft.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SyncPlayerDataS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ((IEntityDataSaver) client.player).getPersistentData().putInt(buf.readString(), buf.readInt());
    }
}
