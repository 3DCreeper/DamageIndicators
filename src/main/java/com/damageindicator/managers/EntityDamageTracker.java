package com.damageindicator.managers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityDamageTracker {

    private static final long TRACKING_DURATION = 8000;
    private final Map<UUID, DamageInfo> damageMap;

    public EntityDamageTracker() {
        this.damageMap = new HashMap<>();
    }

    public void trackDamage(Entity entity, Player damager, double damage) {
        UUID entityId = entity.getUniqueId();
        long currentTime = System.currentTimeMillis();

        damageMap.put(entityId, new DamageInfo(damager, currentTime));
    }

    public Player getDamager(Entity entity) {
        UUID entityId = entity.getUniqueId();
        DamageInfo info = damageMap.get(entityId);

        if (info == null) {
            return null;
        }

        long currentTime = System.currentTimeMillis();
        if (currentTime - info.timestamp > TRACKING_DURATION) {
            damageMap.remove(entityId);
            return null;
        }

        return info.damager;
    }

    public void removeDamage(Entity entity) {
        damageMap.remove(entity.getUniqueId());
    }

    private static class DamageInfo {
        Player damager;
        long timestamp;

        DamageInfo(Player damager, long timestamp) {
            this.damager = damager;
            this.timestamp = timestamp;
        }
    }
}
