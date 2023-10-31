package net.gartersnake.carbcraft.effect;

import net.gartersnake.carbcraft.CarbCraft;
import net.gartersnake.carbcraft.effect.custom.HyperglycemiaEffect;
import net.gartersnake.carbcraft.effect.custom.HypoglycemiaEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEffects {

    public static StatusEffect HYPOGLYCEMIA;
    public static StatusEffect HYPERGLYCEMIA;

    private static StatusEffect registerEffect(String name, StatusEffect effect) {
        return Registry.register(Registries.STATUS_EFFECT, new Identifier(CarbCraft.MOD_ID, name), effect);
    }

    public static void registerModEffects() {
        HYPOGLYCEMIA = registerEffect("hypoglycemia", new HypoglycemiaEffect(StatusEffectCategory.HARMFUL, 0x484D48));
        HYPERGLYCEMIA = registerEffect("hyperglycemia", new HyperglycemiaEffect(StatusEffectCategory.HARMFUL, 0x484D48));
        CarbCraft.LOGGER.debug("Registering Mod Effects for " + CarbCraft.MOD_ID);
    }
}
