package net.gartersnake.diabetesmod.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.gartersnake.diabetesmod.DiabetesMod;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<FermentationTankScreenHandler> FERMENTATION_TANK_SCREEN_HANDLER;

    public static void registerModScreenHandlers() {
        FERMENTATION_TANK_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(FermentationTankScreenHandler::new);

        Registry.register(Registries.SCREEN_HANDLER, new Identifier(DiabetesMod.MOD_ID, "fermentation_tank"),
                FERMENTATION_TANK_SCREEN_HANDLER);
    }
}
