package com.noah.pws.addon;

import com.noah.pws.addon.event.AddonDisableEvent;
import com.noah.pws.addon.event.AddonEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.util.LinkedList;

public class AddonManager {

    private LinkedList<AddonAbstract> addons;

    public AddonManager() {
        this.addons = new LinkedList<>();
    }

    public void load(AddonAbstract addon) {
        this.addons.add(addon);
        addon.onLoad(); // initialize addon

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.callEvent(new AddonEnableEvent(addon));
    }

    public void unload(AddonAbstract addon) {
        this.addons.remove(addon);

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.callEvent(new AddonDisableEvent(addon));
    }

}
