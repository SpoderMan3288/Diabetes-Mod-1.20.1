package net.gartersnake.diabetesmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.gartersnake.diabetesmod.util.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SyncDataS2CPacket {
    public static void recieve(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        ((IEntityDataSaver) client.player).getPersistentData().putInt(buf.readString(), buf.readInt());
    }
}
