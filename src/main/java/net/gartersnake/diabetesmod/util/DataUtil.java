package net.gartersnake.diabetesmod.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.gartersnake.diabetesmod.networking.ModPackets;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class DataUtil {

    public static int increaseValue(IEntityDataSaver player, String key, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int value = nbt.getInt(key);

        value += amount;

        nbt.putInt(key, value);
        syncValue((ServerPlayerEntity)player, key, amount);
        return value;
    }

    public static int decreaseValue(IEntityDataSaver player, String key, int amount) {
        NbtCompound nbt = player.getPersistentData();
        int value = nbt.getInt(key);

        if (value - amount < 0) {
            value = 0;
        }else {
            value -= amount;
        }

        nbt.putInt(key, value);
        syncValue((ServerPlayerEntity)player, key, amount);
        return value;
    }

    public static int getValue(IEntityDataSaver player, String key) {
        NbtCompound nbt = player.getPersistentData();
        int value = nbt.getInt(key);

        return value;
    }

    public static void syncValue(ServerPlayerEntity player, String key, int amount ) {
        PacketByteBuf buffer = PacketByteBufs.create();
        buffer.writeString(key);
        buffer.writeInt(amount);
        ServerPlayNetworking.send(player, ModPackets.SYNC_DATA, buffer);
    }
}
