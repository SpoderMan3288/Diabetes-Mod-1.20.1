package net.gartersnake.carbcraft.util;

import com.google.common.base.Enums;
import net.minecraft.item.ItemStack;

public class CarbUtil {

    // Food Carbs
    private static String getItemName(ItemStack stack) {
        String itemID = stack.getTranslationKey().toUpperCase();
        if (itemID.startsWith("I")) {
            return itemID.replace("ITEM.MINECRAFT.", "");
        } else if (itemID.startsWith("B")) {
            return itemID.replace("BLOCK.MINECRAFT.", "");
        }
        return itemID;
    }

    public static boolean hasItemCarbs(ItemStack stack) {
        String itemName = getItemName(stack);
        return Enums.getIfPresent(FoodCarbs.class, itemName).isPresent();
    }

    public static int getItemCarbs(ItemStack stack) {
        String itemName = getItemName(stack);

        if (hasItemCarbs(stack)) {
            FoodCarbs enumKey = FoodCarbs.valueOf(itemName);
            return enumKey.carbs;
        }
        return 0;
    }
}
