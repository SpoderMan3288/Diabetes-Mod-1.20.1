package net.gartersnake.diabetesmod.item.custom;

import net.gartersnake.diabetesmod.block.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

public class InsulinSyringeItem extends Item {
    public InsulinSyringeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            BlockState block = context.getWorld().getBlockState(positionClicked);

            if (isFermentationTank(block)) {
                player.sendMessage(Text.literal("Clicked Fermentation Tank"));
                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    private boolean isFermentationTank(BlockState block) {
        return block.isOf(ModBlocks.FERMENTATION_TANK);
    }
}
