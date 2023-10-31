package net.gartersnake.carbcraft.event;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.gartersnake.carbcraft.util.CarbUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class FoodTooltipHandler implements ItemTooltipCallback {

    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if (CarbUtil.hasItemCarbs(stack)) {
            lines.add(Text.literal("Carbs: " + CarbUtil.getItemCarbs(stack)).formatted(Formatting.BLUE));
        }
    }
}
