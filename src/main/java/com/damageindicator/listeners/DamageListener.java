package com.damageindicator.listeners;

import com.damageindicator.DamageIndicatorPlugin;
import com.damageindicator.managers.EntityDamageTracker;
import com.damageindicator.managers.TNTTracker;
import com.damageindicator.utils.DamageDisplay;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.projectiles.ProjectileSource;

public class DamageListener implements Listener {

    private final DamageIndicatorPlugin plugin;
    private final EntityDamageTracker damageTracker;
    private final TNTTracker tntTracker;

    public DamageListener(DamageIndicatorPlugin plugin, TNTTracker tntTracker) {
        this.plugin = plugin;
        this.damageTracker = new EntityDamageTracker();
        this.tntTracker = tntTracker;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Player damager = getDamager(event.getDamager());

        if (damager == null) {
            return;
        }

        if (!plugin.getPreferenceManager().isEnabled(damager)) {
            return;
        }

        double damage = event.getFinalDamage();
        Entity victim = event.getEntity();

        damageTracker.trackDamage(victim, damager, damage);
        DamageDisplay.showDamage(damager, victim, damage);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDamage(EntityDamageEvent event) {
        if (event instanceof EntityDamageByEntityEvent) {
            return;
        }

        Entity victim = event.getEntity();
        Player damager = null;
        EntityDamageEvent.DamageCause cause = event.getCause();

        if (isIndirectFireDamage(cause)) {
            damager = damageTracker.getDamager(victim);
        } else if (isExplosionDamage(cause)) {
            damager = tntTracker.getIgniter(null);
        } else if (isKnockbackRelatedDamage(cause)) {
            damager = damageTracker.getDamager(victim);
        }

        if (damager == null) {
            return;
        }

        if (!plugin.getPreferenceManager().isEnabled(damager)) {
            return;
        }

        double damage = event.getFinalDamage();
        DamageDisplay.showDamage(damager, victim, damage);
    }

    private boolean isIndirectFireDamage(EntityDamageEvent.DamageCause cause) {
        return cause == EntityDamageEvent.DamageCause.FIRE ||
               cause == EntityDamageEvent.DamageCause.FIRE_TICK ||
               cause == EntityDamageEvent.DamageCause.LAVA ||
               cause == EntityDamageEvent.DamageCause.CONTACT;
    }

    private boolean isExplosionDamage(EntityDamageEvent.DamageCause cause) {
        return cause == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION ||
               cause == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
    }

    private boolean isKnockbackRelatedDamage(EntityDamageEvent.DamageCause cause) {
        return cause == EntityDamageEvent.DamageCause.FALL ||
               cause == EntityDamageEvent.DamageCause.DROWNING ||
               cause == EntityDamageEvent.DamageCause.SUFFOCATION ||
               cause == EntityDamageEvent.DamageCause.WITHER;
    }

    private Player getDamager(Entity damager) {
        if (damager instanceof Player) {
            return (Player) damager;
        }

        if (damager instanceof Projectile) {
            Projectile projectile = (Projectile) damager;
            ProjectileSource shooter = projectile.getShooter();

            if (shooter instanceof Player) {
                return (Player) shooter;
            }
        }

        return null;
    }
}
