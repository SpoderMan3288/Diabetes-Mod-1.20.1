package net.gartersnake.diabetesmod.item.custom;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InsulinSyringeItem extends Item {
    public InsulinSyringeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack stack = new ItemStack(this);
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("insulin", 0);

        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbt = stack.getOrCreateNbt();
        int insulin = 0;
        if (nbt != null) {
            insulin = nbt.getInt("insulin");
        } else {
            nbt.putInt("insulin", 0);
        }

        tooltip.add(Text.literal("Insulin: " + insulin + " / 40 Units").formatted(Formatting.BLUE));
    }
}
