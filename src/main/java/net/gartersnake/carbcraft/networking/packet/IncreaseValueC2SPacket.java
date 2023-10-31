package net.gartersnake.carbcraft.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.gartersnake.carbcraft.util.DataUtil;
import net.gartersnake.carbcraft.util.IEntityDataSaver;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class IncreaseValueC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
        DataUtil.increaseValue(((IEntityDataSaver) player), buf.readString(), buf.readInt());
    }
}
