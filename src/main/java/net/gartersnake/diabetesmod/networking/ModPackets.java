package net.gartersnake.diabetesmod.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.gartersnake.diabetesmod.DiabetesMod;
import net.gartersnake.diabetesmod.networking.packet.DecreaseValueC2SPacket;
import net.gartersnake.diabetesmod.networking.packet.IncreaseValueC2SPacket;
import net.gartersnake.diabetesmod.networking.packet.SyncDataS2CPacket;
import net.minecraft.util.Identifier;

public class ModPackets {
    public static final Identifier INCREASE_VALUE = new Identifier(DiabetesMod.MOD_ID,
            "increase_value");
    public static final Identifier DECREASE_VALUE = new Identifier(DiabetesMod.MOD_ID,
            "decrease_value");
    public static final Identifier SYNC_DATA = new Identifier(DiabetesMod.MOD_ID,
            "sync_data");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(INCREASE_VALUE, IncreaseValueC2SPacket::recieve);
        ServerPlayNetworking.registerGlobalReceiver(DECREASE_VALUE, DecreaseValueC2SPacket::recieve);
    }
    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(SYNC_DATA, SyncDataS2CPacket::recieve);
    }
}
