package net.gartersnake.carbcraft.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.block.ModBlocks;
import net.gartersnake.carbcraft.fluid.ModFluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static ItemGroup DIABETES_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(CarbCraft.MOD_ID, "diabetes"), FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.carbcraft.diabetes"))
                    .icon(() -> new ItemStack(ModItems.PANCREAS)).entries((displayContext, entries) -> {

                        entries.add(ModItems.PANCREAS);
                        entries.add(ModItems.INSULIN_SYRINGE);
                        entries.add(ModFluids.INSULIN_BUCKET);
                        entries.add(ModBlocks.FERMENTATION_TANK);

                    }).build());

    public static void registerModItemGroups() {
        CarbCraft.LOGGER.debug("Registering mod Item Groups for " + CarbCraft.MOD_ID);
    }
}
