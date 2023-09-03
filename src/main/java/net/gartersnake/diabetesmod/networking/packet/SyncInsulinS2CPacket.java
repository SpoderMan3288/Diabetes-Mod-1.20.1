package net.gartersnake.diabetesmod.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.gartersnake.diabetesmod.block.entity.FermentationTankBlockEntity;
import net.gartersnake.diabetesmod.screen.FermentationTankScreenHandler;
import net.gartersnake.diabetesmod.util.FluidStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;

public class SyncInsulinS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        FluidVariant variant = FluidVariant.fromPacket(buf);
        long insulinLevel = buf.readLong();
        BlockPos position = buf.readBlockPos();

        if(client.world.getBlockEntity(position) instanceof FermentationTankBlockEntity blockEntity) {
            blockEntity.setInsulinLevel(variant, insulinLevel);

            if(client.player.currentScreenHandler instanceof FermentationTankScreenHandler screenHandler &&
                    screenHandler.blockEntity.getPos().equals(position)) {
                blockEntity.setInsulinLevel(variant, insulinLevel);
                screenHandler.setFluid(new FluidStack(variant, insulinLevel));
            }
        }
    }
}