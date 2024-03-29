package com.noah.pws.suite;

import com.noah.pws.util.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Suite {

    private File file;
    private String name;
    private String permission;
    private List<String> worlds;
    private Location spawn;

    public Suite(File file,
                 String name,
                 String permission,
                 List<String> worlds) {
        this.file = file;
        this.name = name;
        this.permission = permission;
        this.worlds = worlds;
    }

    public void save() {
        YamlConfiguration configuration = YamlConfiguration.loadConfiguration(this.file);
        configuration.set("name", this.name);
        configuration.set("permission", this.permission);
        configuration.set("worlds", this.worlds);
        if (this.spawn != null) LocationUtil.serialize(configuration.createSection("spawn"), this.spawn);

        try {
            configuration.save(this.file);
        } catch (IOException e) { System.out.println("FAILED TO SAVE FILE."); }

    }

    public void broadcast(String message) {
        this.getPlayers().forEach(player -> player.sendMessage(message));
        System.out.println("[" + this.name + "] " + ChatColor.stripColor(message));
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public void setSpawn(Location spawn) {
        this.spawn = spawn;
    }

    public boolean addWorld(World world) {
        if (world == null) return false;
        return this.worlds.add(world.getName());
    }

    public boolean removeWorld(World world) {
        if (world == null) return false;
        return this.worlds.remove(world.getName());
    }

    public boolean hasWorld(World world) {
        if (world == null) return false;
        return this.worlds.contains(world.getName());
    }

    public File getFile() { return this.file; }
    public String getName() { return this.name; }
    public String getPermission() { return this.permission; }
    public Location getSpawn() { return this.spawn; }

    public List<World> getWorlds() {
        return this.worlds.stream()
                .map(Bukkit::getWorld)
                .toList();
    }

    public List<Player> getPlayers() {
        return this.getWorlds().stream()
                .map(World::getPlayers)
                .flatMap(List::stream)
                .toList();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
