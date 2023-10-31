package net.gartersnake.carbcraft.fluid;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.fluid.custom.InsulinFluid;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModFluids {
    public static FlowableFluid STILL_INSULIN;
    public static FlowableFluid FLOWING_INSULIN;
    public static Block INSULIN_BLOCK;
    public static Item INSULIN_BUCKET;

    public static void register() {
        STILL_INSULIN = Registry.register(Registries.FLUID,
                new Identifier(CarbCraft.MOD_ID, "insulin"), new InsulinFluid.Still());

        FLOWING_INSULIN = Registry.register(Registries.FLUID,
                new Identifier(CarbCraft.MOD_ID, "flowing_insulin"), new InsulinFluid.Flowing());

        INSULIN_BLOCK = Registry.register(Registries.BLOCK,
                new Identifier(CarbCraft.MOD_ID, "insulin_block"),
                new FluidBlock(ModFluids.STILL_INSULIN, FabricBlockSettings.copyOf(Blocks.WATER)));

        INSULIN_BUCKET = Registry.register(Registries.ITEM,
                new Identifier(CarbCraft.MOD_ID, "insulin_bucket"),
                new BucketItem(ModFluids.STILL_INSULIN, new FabricItemSettings()
                        .maxCount(1).recipeRemainder(Items.BUCKET)));
    }
}
