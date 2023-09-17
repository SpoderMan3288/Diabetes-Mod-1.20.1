package net.gartersnake.diabetesmod.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.gartersnake.diabetesmod.DiabetesMod;
import net.gartersnake.diabetesmod.item.custom.InsulinSyringeItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static Item PANCREAS;
    public static Item INSULIN_SYRINGE;

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(DiabetesMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        PANCREAS = registerItem("pancreas", new Item(new FabricItemSettings()));
        INSULIN_SYRINGE = registerItem("insulin_syringe", new InsulinSyringeItem(new FabricItemSettings().maxCount(1)));
        DiabetesMod.LOGGER.debug("Registering Mod Items for " + DiabetesMod.MOD_ID);
    }
}
