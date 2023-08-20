package net.gartersnake.diabetesmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.gartersnake.diabetesmod.util.DataUtil;
import net.gartersnake.diabetesmod.util.IEntityDataSaver;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class DecreaseValueC2SPacket {
    public static void recieve(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
        DataUtil.decreaseValue(((IEntityDataSaver) player), buf.readString(), buf.readInt());
    }
}