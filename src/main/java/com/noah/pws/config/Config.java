package com.noah.pws.config;

import com.noah.pws.PerWorldServer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public abstract class Config {

    public static void load(Config... configs) {
        for (Config config : configs) config.load();
    }

    private PerWorldServer plugin;
    private String name;
    private YamlConfiguration configuration;

    public Config(PerWorldServer plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        this.reload(false);
    }

    public abstract void load();

    public void reload(boolean load) {
        File file = new File(plugin.getPluginFolder(), this.name);
        if (!file.exists()) plugin.saveResource(this.name, false);
        this.configuration = YamlConfiguration.loadConfiguration(file);
        if (load) this.load();
    }

    public String getName() {
        return this.name;
    }

    public YamlConfiguration getConfiguration() {
        return this.configuration;
    }
}
