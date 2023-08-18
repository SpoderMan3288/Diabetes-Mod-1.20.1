package net.gartersnake.diabetesmod.damage_type;

import net.gartersnake.diabetesmod.DiabetesMod;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public interface ModDamageTypes {
    RegistryKey<DamageType> LOW_GLUCOSE = RegistryKey.of
            (RegistryKeys.DAMAGE_TYPE, new Identifier(DiabetesMod.MOD_ID, "low_glucose"));
    RegistryKey<DamageType> HIGH_GLUCOSE = RegistryKey.of
            (RegistryKeys.DAMAGE_TYPE, new Identifier(DiabetesMod.MOD_ID, "high_glucose"));
}
