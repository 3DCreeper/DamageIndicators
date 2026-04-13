package com.damageindicator.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerPreferenceManager {

    private final Map<UUID, Boolean> preferences;

    public PlayerPreferenceManager() {
        this.preferences = new HashMap<>();
    }

    public boolean isEnabled(Player player) {
        return preferences.getOrDefault(player.getUniqueId(), true);
    }

    public void setEnabled(Player player, boolean enabled) {
        preferences.put(player.getUniqueId(), enabled);
    }

    public void toggle(Player player) {
        boolean current = isEnabled(player);
        setEnabled(player, !current);
    }
}
