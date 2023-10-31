package net.gartersnake.carbcraft.damage_type;

import net.gartersnake.carbcraft.CarbCraft;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public interface ModDamageTypes {
    RegistryKey<DamageType> LOW_GLUCOSE = RegistryKey.of
            (RegistryKeys.DAMAGE_TYPE, new Identifier(CarbCraft.MOD_ID, "low_glucose"));
    RegistryKey<DamageType> HIGH_GLUCOSE = RegistryKey.of
            (RegistryKeys.DAMAGE_TYPE, new Identifier(CarbCraft.MOD_ID, "high_glucose"));
}
