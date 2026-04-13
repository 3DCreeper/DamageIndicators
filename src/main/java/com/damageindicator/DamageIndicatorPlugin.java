package com.damageindicator;

import org.bukkit.plugin.java.JavaPlugin;
import com.damageindicator.listeners.DamageListener;
import com.damageindicator.listeners.TNTListener;
import com.damageindicator.commands.DamageIndicatorCommand;
import com.damageindicator.managers.PlayerPreferenceManager;
import com.damageindicator.managers.TNTTracker;

public class DamageIndicatorPlugin extends JavaPlugin {

    private PlayerPreferenceManager preferenceManager;
    private TNTTracker tntTracker;

    @Override
    public void onEnable() {
        preferenceManager = new PlayerPreferenceManager();
        tntTracker = new TNTTracker();

        getServer().getPluginManager().registerEvents(new DamageListener(this, tntTracker), this);
        getServer().getPluginManager().registerEvents(new TNTListener(tntTracker), this);

        getCommand("damageindicator").setExecutor(new DamageIndicatorCommand(this));

        getLogger().info("DamageIndicator plugin has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("DamageIndicator plugin has been disabled!");
    }

    public PlayerPreferenceManager getPreferenceManager() {
        return preferenceManager;
    }
}
