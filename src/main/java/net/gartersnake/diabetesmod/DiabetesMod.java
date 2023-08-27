package net.gartersnake.diabetesmod;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.gartersnake.diabetesmod.block.ModBlocks;
import net.gartersnake.diabetesmod.block.entity.ModBlockEntities;
import net.gartersnake.diabetesmod.effect.ModEffects;
import net.gartersnake.diabetesmod.event.FoodTooltipHandler;
import net.gartersnake.diabetesmod.event.PlayerTickHandler;
import net.gartersnake.diabetesmod.fluid.ModFluids;
import net.gartersnake.diabetesmod.item.ModItemGroups;
import net.gartersnake.diabetesmod.item.ModItems;
import net.gartersnake.diabetesmod.networking.ModPackets;
import net.gartersnake.diabetesmod.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiabetesMod implements ModInitializer {

	public static final String MOD_ID = "diabetesmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModItems.registerModItems();
		ModItemGroups.registerModItemGroups();

		ModBlocks.registerModBlocks();
		ModBlockEntities.registerModBlockEntities();

		ModScreenHandlers.registerModScreenHandlers();
		ModFluids.register();

		ModEffects.registerModEffects();
		ModPackets.registerC2SPackets();

		ServerTickEvents.START_SERVER_TICK.register(new PlayerTickHandler());
		ItemTooltipCallback.EVENT.register(new FoodTooltipHandler());
	}
}