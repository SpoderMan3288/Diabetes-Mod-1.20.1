package net.gartersnake.carbcraft.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.item.custom.InsulinSyringeItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item PANCREAS;
    public static Item INSULIN_SYRINGE;

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(CarbCraft.MOD_ID, name), item);
    }

    public static void registerModItems() {
        PANCREAS = registerItem("pancreas", new Item(new FabricItemSettings()));
        INSULIN_SYRINGE = registerItem("insulin_syringe", new InsulinSyringeItem(new FabricItemSettings().maxCount(1)));
        CarbCraft.LOGGER.debug("Registering Mod Items for " + CarbCraft.MOD_ID);
    }
}
