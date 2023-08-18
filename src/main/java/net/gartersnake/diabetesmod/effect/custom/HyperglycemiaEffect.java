package net.gartersnake.diabetesmod.effect.custom;

import net.gartersnake.diabetesmod.damage_type.ModDamageTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;

public class HyperglycemiaEffect extends StatusEffect {

    public HyperglycemiaEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (entity instanceof PlayerEntity) {
            if (amplifier >= 0) {
                entity.addStatusEffect(new StatusEffectInstance
                        (StatusEffects.WEAKNESS, 10, amplifier));
            }
            if (amplifier >= 1) {
                entity.addStatusEffect(new StatusEffectInstance
                        (StatusEffects.SPEED, 10, amplifier -1));
            }
            if (amplifier >= 2) {
                entity.addStatusEffect(new StatusEffectInstance
                        (StatusEffects.NAUSEA, 10, amplifier -1));
            }
            if (amplifier >= 4) {
                entity.damage(entity.getDamageSources().create
                        (ModDamageTypes.HIGH_GLUCOSE), 3);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}