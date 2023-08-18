package net.gartersnake.diabetesmod;

import net.fabricmc.api.ClientModInitializer;
import net.gartersnake.diabetesmod.event.KeyInputHandler;
import net.gartersnake.diabetesmod.networking.ModPackets;
import net.gartersnake.diabetesmod.screen.FermentationTankScreen;
import net.gartersnake.diabetesmod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class DiabetesModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        KeyInputHandler.registerModKeybinds();
        ModPackets.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.FERMENTATION_TANK_SCREEN_HANDLER,
                FermentationTankScreen::new);
    }
}
