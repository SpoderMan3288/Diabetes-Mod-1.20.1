package net.gartersnake.carbcraft.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.gartersnake.carbcraft.CarbCraft;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlers {
    public static ScreenHandlerType<FermentationTankScreenHandler> FERMENTATION_TANK_SCREEN_HANDLER;

    public static void registerModScreenHandlers() {
        FERMENTATION_TANK_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(FermentationTankScreenHandler::new);

        Registry.register(Registries.SCREEN_HANDLER, new Identifier(CarbCraft.MOD_ID, "fermentation_tank"),
                FERMENTATION_TANK_SCREEN_HANDLER);
    }
}
