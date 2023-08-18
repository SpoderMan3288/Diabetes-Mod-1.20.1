package net.gartersnake.diabetesmod.screen;

import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {
    public static ScreenHandlerType<FermentationTankScreenHandler> FERMENTATION_TANK_SCREEN_HANDLER;

    public static void registerModScreenHandlers() {
        FERMENTATION_TANK_SCREEN_HANDLER = new ScreenHandlerType<>(FermentationTankScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    }
}
