package net.gartersnake.carbcraft.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlockEntities {
    public static BlockEntityType<FermentationTankBlockEntity> FERMENTATION_TANK;

    public static void registerModBlockEntities() {
        FERMENTATION_TANK = Registry.register(Registries.BLOCK_ENTITY_TYPE,
                new Identifier(CarbCraft.MOD_ID, "fermentation_tank"),
                FabricBlockEntityTypeBuilder.create(FermentationTankBlockEntity::new,
                        ModBlocks.FERMENTATION_TANK).build(null));


        FluidStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.insulinStorage, FERMENTATION_TANK);
        CarbCraft.LOGGER.debug("Registering Mod Block Entities for " + CarbCraft.MOD_ID);
    }
}
