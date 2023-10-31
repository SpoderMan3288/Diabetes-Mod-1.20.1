package net.gartersnake.carbcraft.mixin;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.gartersnake.carbcraft.networking.ModPackets;
import net.gartersnake.carbcraft.util.CarbUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class FoodConsumedMixin {

    @Inject(method = "consumeItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;spawnConsumptionEffects(Lnet/minecraft/item/ItemStack;I)V"))
    protected void injectConsumeMethod(CallbackInfo info) {
        int carbs = 0;
        if (CarbUtil.hasItemCarbs(((LivingEntity)(Object)this).getActiveItem())) {
            carbs = CarbUtil.getItemCarbs(((LivingEntity)(Object)this).getActiveItem());
        }

        if (((LivingEntity)(Object)this).getWorld().isClient) {
            PacketByteBuf buffer = PacketByteBufs.create();
            buffer.writeString("active_carbs");
            buffer.writeInt(carbs);
            ClientPlayNetworking.send(ModPackets.INCREASE_VALUE, buffer);
        }
    }
}
