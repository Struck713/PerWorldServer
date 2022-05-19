package com.noah.pws.addon;

import com.noah.pws.suite.Suite;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class Addon {


    private String name;
    private String version;

    private File folder;
    private AddonManager addonManager;

    public Addon(String name, String version) {
        this.name = name;
        this.version = version;
    }

    public void onEnable() {}
    public void onDisable() {}

    public void onJoin(Suite suite, Player player) {}
    public void onQuit(Suite suite, Player player) {}

    public String getName() {
        return this.name;
    }

    public String getVersion() {
        return this.version;
    }

    public File getFolder() {
        return folder;
    }

    public AddonManager getAddonManager() {
        return addonManager;
    }

    // set protected
    protected void setFolder(File folder) { this.folder = folder; }
    protected void setAddonManager(AddonManager addonManager) { this.addonManager = addonManager; }

}
