package com.noah.pws.config;

import com.noah.pws.PerWorldServer;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigSettings extends Config {

    private boolean checkForUpdates;

    public ConfigSettings(PerWorldServer plugin) {
        super(plugin, "config.yml");
    }

    @Override
    public void load() {
        YamlConfiguration conf = this.getConfiguration();
        this.checkForUpdates = conf.getBoolean("check-for-updates");
    }

    public boolean isCheckForUpdates() {
        return this.checkForUpdates;
    }
}
