package com.damageindicator.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class DamageDisplay {

    public static void showDamage(Player damager, Entity victim, double damage) {
        String victimName = getEntityName(victim);
        String damageText = formatDamage(damage);

        String healthInfo = "";
        if (victim instanceof LivingEntity) {
            LivingEntity livingVictim = (LivingEntity) victim;
            double remainingHealth = Math.max(0, livingVictim.getHealth() - damage);
            double maxHealth = livingVictim.getMaxHealth();
            healthInfo = String.format(" %s%.1f❤%s/%.1f❤",
                ChatColor.RED, remainingHealth, ChatColor.GRAY, maxHealth);
        }

        String message = String.format("%s%s %s-%s%s%s",
            ChatColor.YELLOW, victimName,
            ChatColor.GRAY, ChatColor.RED, damageText, healthInfo);

        damager.spigot().sendMessage(ChatMessageType.ACTION_BAR,
            new TextComponent(message));
    }

    private static String getEntityName(Entity entity) {
        if (entity instanceof Player) {
            return ((Player) entity).getName();
        }

        String name = entity.getType().name().toLowerCase().replace("_", " ");
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static String formatDamage(double damage) {
        if (damage >= 10) {
            return String.format("%.1f", damage);
        } else {
            return String.format("%.2f", damage);
        }
    }
}
