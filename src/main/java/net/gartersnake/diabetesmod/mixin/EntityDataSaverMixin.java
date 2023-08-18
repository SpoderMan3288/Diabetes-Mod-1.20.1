package net.gartersnake.diabetesmod.mixin;

import net.gartersnake.diabetesmod.util.IEntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityDataSaverMixin implements IEntityDataSaver {
    //@Shadow public abstract void sendMessage(Text message);

    //@Shadow public abstract @Nullable MinecraftServer getServer();

    private NbtCompound persistantData;

    public NbtCompound getPersistentData() {
        if (this.persistantData == null) {
            this.persistantData = new NbtCompound();
            persistantData.putInt("glucose", 100);
            persistantData.putInt("active_carbs", 0);
            persistantData.putInt("active_insulin", 0);
        }
        return persistantData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfoReturnable info) {
        if(persistantData != null) {
            nbt.put("diabetesmod.data", persistantData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo info) {
        if (nbt.contains("diabetesmod.data")) {
            persistantData = nbt.getCompound("diabetesmod.data");
        }
    }
}
