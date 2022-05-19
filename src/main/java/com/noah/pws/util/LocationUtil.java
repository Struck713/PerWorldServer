package com.noah.pws.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class LocationUtil {

    public static void serialize(ConfigurationSection section, Location location) {
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("pitch", location.getPitch());
        section.set("yaw", location.getPitch());
        section.set("world", location.getWorld().getName());
    }

    public static Location deserialize(ConfigurationSection section) {
        if (!(section.isDouble("x") && section.isDouble("y") && section.isDouble("z") && section.isString("world"))) return null;

        World world = Bukkit.getWorld(section.getString("world"));
        if (world == null) return null;

        Location location = new Location(world, section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
        if (section.isDouble("pitch")) location.setPitch((float) section.getDouble("pitch"));
        if (section.isDouble("yaw")) location.setYaw((float) section.getDouble("yaw"));

        return  location;
    }

    public static String toString(Location location) {
        return location.getX() + ", " + location.getY() + ", " + location.getZ();
    }

}
