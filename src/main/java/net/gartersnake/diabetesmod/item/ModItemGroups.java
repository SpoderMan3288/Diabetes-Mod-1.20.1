package net.gartersnake.diabetesmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.gartersnake.diabetesmod.DiabetesMod;
import net.gartersnake.diabetesmod.block.ModBlocks;
import net.gartersnake.diabetesmod.fluid.ModFluids;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {

    public static ItemGroup DIABETES_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(DiabetesMod.MOD_ID, "diabetes"), FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.diabetesmod.diabetes"))
                    .icon(() -> new ItemStack(ModItems.PANCREAS)).entries((displayContext, entries) -> {

                        entries.add(ModItems.PANCREAS);
                        entries.add(ModItems.INSULIN_SYRINGE);
                        entries.add(ModFluids.INSULIN_BUCKET);
                        entries.add(ModBlocks.FERMENTATION_TANK);

                    }).build());

    public static void registerModItemGroups() {
        DiabetesMod.LOGGER.debug("Registering mod Item Groups for " + DiabetesMod.MOD_ID);
    }
}
