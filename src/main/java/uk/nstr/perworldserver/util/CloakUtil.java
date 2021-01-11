package uk.nstr.perworldserver.util;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CloakUtil {

    private JavaPlugin plugin;

    public CloakUtil(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void show(Player player, Player toShow);
    public abstract void hide(Player player, Player toHide);

    public void show(World world, Player toShow) {
        world.getPlayers()
                .stream()
                .filter(player -> !player.equals(toShow))
                .forEach(player -> show(player, toShow));
    }

    public void hide(World world, Player toHide) {
        world.getPlayers()
                .stream()
                .filter(player -> !player.equals(toHide))
                .forEach(player -> hide(player, toHide));
    }

    public void showAWorld(Player player, World toShow) {
        toShow.getPlayers()
                .stream()
                .filter(streamedPlayer -> !streamedPlayer.equals(player))
                .forEach(streamedPlayer -> show(player, streamedPlayer));
    }

    public void hideAWorld(Player player, World toHide) {
        toHide.getPlayers()
                .stream()
                .filter(streamedPlayer -> !streamedPlayer.equals(player))
                .forEach(streamedPlayer -> hide(player, streamedPlayer));
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }
}
