package com.damageindicator.managers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TNTTracker {

    private static final long TRACKING_DURATION = 60000;
    private final Map<UUID, TNTInfo> tntMap;

    public TNTTracker() {
        this.tntMap = new HashMap<>();
    }

    public void trackTNT(TNTPrimed tnt, Player igniter) {
        UUID tntId = tnt.getUniqueId();
        long currentTime = System.currentTimeMillis();

        tntMap.put(tntId, new TNTInfo(igniter, currentTime));
    }

    public Player getIgniter(Entity explosion) {
        for (Map.Entry<UUID, TNTInfo> entry : tntMap.entrySet()) {
            TNTInfo info = entry.getValue();
            long currentTime = System.currentTimeMillis();

            if (currentTime - info.timestamp > TRACKING_DURATION) {
                tntMap.remove(entry.getKey());
                continue;
            }

            return info.igniter;
        }

        return null;
    }

    public void removeTNT(TNTPrimed tnt) {
        tntMap.remove(tnt.getUniqueId());
    }

    private static class TNTInfo {
        Player igniter;
        long timestamp;

        TNTInfo(Player igniter, long timestamp) {
            this.igniter = igniter;
            this.timestamp = timestamp;
        }
    }
}
