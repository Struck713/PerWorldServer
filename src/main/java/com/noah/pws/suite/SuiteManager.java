package com.noah.pws.suite;

import com.noah.pws.PerWorldServer;
import com.noah.pws.util.FileUtil;
import com.noah.pws.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SuiteManager {

    private PerWorldServer plugin;
    private LinkedList<Suite> suites;

    public SuiteManager(PerWorldServer plugin) {
        this.plugin = plugin;
        this.suites = new LinkedList<>();
    }

    public void loadAll() {

        this.suites.clear(); // reset list

        for (File suiteFile : this.getSuitesFolder().listFiles()) {
            if (!suiteFile.isFile() || !suiteFile.getName().endsWith(".yml")) continue; //skip non-yaml files

            // check if suite file is correct
            YamlConfiguration suiteConf = YamlConfiguration.loadConfiguration(suiteFile);
            if (!(suiteConf.isString("name")
                    || suiteConf.isString("permission")
                    || suiteConf.isList("worlds"))) continue;

            String name = suiteConf.getString("name");
            String permission = suiteConf.getString("permission");
            List<String> worlds = suiteConf.getStringList("worlds");
            worlds.removeIf(worldName -> Bukkit.getWorld(worldName) == null); // clean up worlds that don't exist

            Suite suite = new Suite(suiteFile, name, permission, worlds);

            if (suiteConf.isConfigurationSection("spawn")) {
                ConfigurationSection spawnSection = suiteConf.getConfigurationSection("spawn");
                suite.setSpawn(LocationUtil.deserialize(spawnSection));
            }

            this.suites.add(suite);
            plugin.getLogger().info("Loaded suite, " + name + " with " + worlds.size() + " worlds.");
        }

        // if we build and have no suites, we need to init
        if (!this.suites.isEmpty()) return;
        Suite suite = this.create("default");
        Bukkit.getWorlds().forEach(suite::addWorld); // add all worlds

    }

    public void saveAll() {
        this.suites.forEach(Suite::save);
    }

    public Suite create(String name) {
        File file = new File(this.getSuitesFolder(), name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                plugin.getLogger().info("Failed to create file for suite, " + name + ".");
            }
        }

        Suite suite = new Suite(file, name, "", new LinkedList<>());
        this.suites.add(suite);
        return suite;
    }

    public void destroy(Suite suite) {
        suite.getFile().delete();
        suites.remove(suite);
    }

    public boolean send(Player player, Suite suite) {
        Location spawn = suite.getSpawn();
        if (spawn == null) return false;
        player.teleport(spawn);
        return true;
    }

    public Suite getSuiteByName(String name) {
        return suites.stream()
                .filter(suite -> suite.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Suite getSuiteByWorld(World world) {
        return this.suites.stream()
                .filter(suite -> suite.hasWorld(world))
                .findFirst()
                .orElse(null);
    }

    public List<Suite> getAllSuites() {
        return this.suites;
    }

    public int size() {
        return this.suites.size();
    }

    private File getSuitesFolder() {
        File file = new File(plugin.getPluginFolder(), "suites" + FileUtil.PATH_SEPARATOR);
        if (!file.exists()) file.mkdirs();
        return file;
    }

}
