package net.gartersnake.diabetesmod.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.gartersnake.diabetesmod.effect.ModEffects;
import net.gartersnake.diabetesmod.util.DataUtil;
import net.gartersnake.diabetesmod.util.IEntityDataSaver;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.concurrent.ThreadLocalRandom;

public class PlayerTickHandler implements ServerTickEvents.StartTick {

    private int cooldown = 200;

    public int getRandom(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max +1);
    }

    private void applyHypo(ServerPlayerEntity player, int amplifier) {
        player.addStatusEffect(new StatusEffectInstance(ModEffects.HYPOGLYCEMIA, 10, amplifier));
    }
    private void applyHyper(ServerPlayerEntity player, int amplifier) {
        player.addStatusEffect(new StatusEffectInstance(ModEffects.HYPERGLYCEMIA, 10, amplifier));
    }

    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if (player.isCreative() || player.isSpectator()) {
                continue;
            }
            IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
            int glucose = DataUtil.getValue(dataPlayer, "glucose");
            int activeCarbs = DataUtil.getValue(dataPlayer, "active_carbs");
            int activeInsulin = DataUtil.getValue(dataPlayer, "active_insulin");

            Formatting color = Formatting.GREEN;

            if (activeCarbs > 0) {
                --cooldown;
                if (cooldown <= 0) {
                    DataUtil.increaseValue(dataPlayer, "glucose", getRandom(3,5));
                    DataUtil.decreaseValue(dataPlayer, "active_carbs", 1);
                    cooldown = 200;
                }
            } else if (activeCarbs == 0 && activeInsulin == 0) {
                --cooldown;
                if (cooldown <= 0) {
                    DataUtil.increaseValue(dataPlayer, "glucose", getRandom(-3,3));
                    cooldown = 200;
                }
            }

            // Low
            if (glucose <= 80) {
                color = Formatting.YELLOW;
            }

            if (glucose <= 70) {
                applyHypo(player, 0);
                color = Formatting.RED;
            }
            if (glucose <= 60) {
                applyHypo(player, 1);
            }
            if (glucose <= 50) {
                applyHypo(player, 2);
                color = Formatting.DARK_RED;
            }
            if (glucose <= 45) {
                applyHypo(player, 3);
            }
            if (glucose <= 40) {
                applyHypo(player, 4);
            }

            // High
            if (glucose >= 250) {
                applyHyper(player, 0);
                color = Formatting.YELLOW;
            }

            if (glucose >= 300) {
                applyHyper(player, 1);
                color = Formatting.RED;
            }
            if (glucose >= 350) {
                applyHyper(player, 2);
            }
            if (glucose >= 400) {
                applyHyper(player, 3);
                color = Formatting.DARK_RED;
            }
            if (glucose >= 450) {
                applyHyper(player, 4);
            }

            // Display Values
            Style style1 = Style.EMPTY.withColor(color);
            Style style2 = Style.EMPTY.withColor(Formatting.YELLOW);
            Style style3 = Style.EMPTY.withColor((Formatting.AQUA ));

            Text text = Text.literal("Blood Glucose: " + glucose).setStyle(style1)
                    .append(Text.literal(" Active Carbs: " + activeCarbs).setStyle(style2)
                            .append(Text.literal(" Active Insulin: " + activeCarbs).setStyle(style3)));

            player.sendMessage(text, true);
        }
    }
}
