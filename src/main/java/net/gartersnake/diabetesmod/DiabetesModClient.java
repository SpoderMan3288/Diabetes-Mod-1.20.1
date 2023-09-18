package net.gartersnake.diabetesmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.gartersnake.diabetesmod.event.KeyInputHandler;
import net.gartersnake.diabetesmod.fluid.ModFluids;
import net.gartersnake.diabetesmod.item.ModItems;
import net.gartersnake.diabetesmod.networking.ModPackets;
import net.gartersnake.diabetesmod.screen.FermentationTankScreen;
import net.gartersnake.diabetesmod.screen.ModScreenHandlers;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

public class DiabetesModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(ModItems.INSULIN_SYRINGE,
                new Identifier(DiabetesMod.MOD_ID, "insulin"), (stack, world, entity, seed) -> {
                    NbtCompound nbt = stack.getOrCreateNbt();
                    int insulin = nbt.getInt("insulin");
                    return switch (insulin) {
                        case 20 -> 0.5f;
                        case 40 -> 1.0f;
                        default -> 0.0f;
                    };
                });

        KeyInputHandler.registerModKeybinds();
        ModPackets.registerS2CPackets();

        HandledScreens.register(ModScreenHandlers.FERMENTATION_TANK_SCREEN_HANDLER,
                FermentationTankScreen::new);



        // Fluid
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.STILL_INSULIN, ModFluids.FLOWING_INSULIN,
                new SimpleFluidRenderHandler(
                        new Identifier("minecraft:block/water_still"),
                        new Identifier("minecraft:block/water_flow"),
                        0x979cae
                ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(),
                ModFluids.STILL_INSULIN, ModFluids.FLOWING_INSULIN);
    }
}
