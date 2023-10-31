package net.gartersnake.carbcraft;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.gartersnake.carbcraft.block.ModBlocks;
import net.gartersnake.carbcraft.block.entity.ModBlockEntities;
import net.gartersnake.carbcraft.effect.ModEffects;
import net.gartersnake.carbcraft.event.FoodTooltipHandler;
import net.gartersnake.carbcraft.event.PlayerTickHandler;
import net.gartersnake.carbcraft.fluid.ModFluids;
import net.gartersnake.carbcraft.item.ModItemGroups;
import net.gartersnake.carbcraft.item.ModItems;
import net.gartersnake.carbcraft.networking.ModPackets;
import net.gartersnake.carbcraft.screen.ModScreenHandlers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarbCraft implements ModInitializer {

	public static final String MOD_ID = "carbcraft";
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