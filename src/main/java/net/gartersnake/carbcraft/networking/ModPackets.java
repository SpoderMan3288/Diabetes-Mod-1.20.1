package net.gartersnake.carbcraft.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.networking.packet.DecreaseValueC2SPacket;
import net.gartersnake.carbcraft.networking.packet.IncreaseValueC2SPacket;
import net.gartersnake.carbcraft.networking.packet.SyncInsulinS2CPacket;
import net.gartersnake.carbcraft.networking.packet.SyncPlayerDataS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier INCREASE_VALUE = new Identifier(CarbCraft.MOD_ID,
            "increase_value");
    public static final Identifier DECREASE_VALUE = new Identifier(CarbCraft.MOD_ID,
            "decrease_value");

    public static final Identifier SYNC_DATA = new Identifier(CarbCraft.MOD_ID,
            "sync_data");
    public static final Identifier SYNC_INSULIN = new Identifier(CarbCraft.MOD_ID,
            "sync_insulin");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(INCREASE_VALUE, IncreaseValueC2SPacket::receive);
        ServerPlayNetworking.registerGlobalReceiver(DECREASE_VALUE, DecreaseValueC2SPacket::receive);
    }
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_DATA, SyncPlayerDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SYNC_INSULIN, SyncInsulinS2CPacket::receive);
    }
}
