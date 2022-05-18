package com.noah.pws.util;

import com.noah.pws.suite.Suite;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CloakUtil {

    private JavaPlugin plugin;

    public CloakUtil(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void show(Player player, Player toShow);
    public abstract void hide(Player player, Player toHide);

    // world based
//    public void show(World world, Player toShow) {
//        world.getPlayers()
//                .stream()
//                .filter(player -> !player.equals(toShow))
//                .forEach(player -> show(player, toShow));
//    }
//
//    public void hide(World world, Player toHide) {
//        world.getPlayers()
//                .stream()
//                .filter(player -> !player.equals(toHide))
//                .forEach(player -> hide(player, toHide));
//    }
//
//    public void showAWorld(Player player, World toShow) {
//        toShow.getPlayers()
//                .stream()
//                .filter(streamedPlayer -> !streamedPlayer.equals(player))
//                .forEach(streamedPlayer -> show(player, streamedPlayer));
//    }
//
//    public void hideAWorld(Player player, World toHide) {
//        toHide.getPlayers()
//                .stream()
//                .filter(streamedPlayer -> !streamedPlayer.equals(player))
//                .forEach(streamedPlayer -> hide(player, streamedPlayer));
//    }

    public void show(Suite suite, Player toShow) {
        suite.getPlayers()
                .stream()
                .filter(player -> !player.equals(toShow))
                .forEach(player -> show(player, toShow));
    }

    public void hide(Suite suite, Player toHide) {
        suite.getPlayers()
                .stream()
                .filter(player -> !player.equals(toHide))
                .forEach(player -> hide(player, toHide));
    }

    public void showASuite(Player player, Suite toShow) {
        toShow.getPlayers()
                .stream()
                .filter(streamedPlayer -> !streamedPlayer.equals(player))
                .forEach(streamedPlayer -> show(player, streamedPlayer));
    }

    public void hideASuite(Player player, Suite toHide) {
        toHide.getPlayers()
                .stream()
                .filter(streamedPlayer -> !streamedPlayer.equals(player))
                .forEach(streamedPlayer -> hide(player, streamedPlayer));
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
