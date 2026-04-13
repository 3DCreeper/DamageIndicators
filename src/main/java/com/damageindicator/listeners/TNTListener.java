package com.damageindicator.listeners;

import com.damageindicator.managers.TNTTracker;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class TNTListener implements Listener {

    private final TNTTracker tntTracker;

    public TNTListener(TNTTracker tntTracker) {
        this.tntTracker = tntTracker;
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() != BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL &&
            event.getCause() != BlockIgniteEvent.IgniteCause.EXPLOSION) {
            return;
        }

        Player player = event.getPlayer();
        if (player == null) {
            return;
        }

        Entity entity = event.getIgnitingEntity();
        if (!(entity instanceof TNTPrimed)) {
            return;
        }

        TNTPrimed tnt = (TNTPrimed) entity;
        tntTracker.trackTNT(tnt, player);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof TNTPrimed) {
            tntTracker.removeTNT((TNTPrimed) entity);
        }
    }
}
