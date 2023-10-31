package net.gartersnake.carbcraft.event;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.gartersnake.carbcraft.networking.ModPackets;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler {
    public static final String KEY_CATEGORY_DIABETES = "key.category.carbcraft.diabetes";

    public static final String KEY_INCREASE_GLUCOSE = "key.carbcraft.increase_glucose";
    public static final String KEY_DECREASE_GLUCOSE = "key.carbcraft.decrease_glucose";

    public static KeyBinding increaseGlucoseKey;
    public static KeyBinding decreaseGlucoseKey;

    private static void registerKeyInputs() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (increaseGlucoseKey.wasPressed()) {
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeString("glucose");
                buffer.writeInt(1);
                ClientPlayNetworking.send(ModPackets.INCREASE_VALUE, buffer);
            } else if (decreaseGlucoseKey.wasPressed()) {
                PacketByteBuf buffer = PacketByteBufs.create();
                buffer.writeString("glucose");
                buffer.writeInt(1);
                ClientPlayNetworking.send(ModPackets.DECREASE_VALUE, buffer);
            }
        });
    }

    public static void registerModKeybinds() {
        increaseGlucoseKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_INCREASE_GLUCOSE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_UP,
                KEY_CATEGORY_DIABETES
        ));

        decreaseGlucoseKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_DECREASE_GLUCOSE,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_PAGE_DOWN,
                KEY_CATEGORY_DIABETES
        ));

        registerKeyInputs();
    }
}
