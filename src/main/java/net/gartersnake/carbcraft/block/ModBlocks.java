package net.gartersnake.carbcraft.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.block.custom.FermentationTankBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static Block FERMENTATION_TANK;

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK,
                new Identifier(CarbCraft.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(CarbCraft.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        FERMENTATION_TANK = registerBlock("fermentation_tank",
                new FermentationTankBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
        CarbCraft.LOGGER.debug("Registering Mod Blocks for " + CarbCraft.MOD_ID);
    }
}
