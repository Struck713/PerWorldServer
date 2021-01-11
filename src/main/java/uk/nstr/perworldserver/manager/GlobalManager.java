package uk.nstr.perworldserver.manager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import uk.nstr.perworldserver.config.ConfigManager;
import uk.nstr.perworldserver.config.ConfigNodes;
import uk.nstr.perworldserver.util.string.StringPlaceholder;
import uk.nstr.perworldserver.util.string.StringUtil;

import java.util.ArrayList;
import java.util.UUID;

public class GlobalManager {

    private ConfigManager configManager;
    private ArrayList<UUID> toggled;

    public GlobalManager(ConfigManager configManager) {
        this.configManager = configManager;
        this.toggled = new ArrayList<>();
    }

    public void broadcast(Player player, String message) {
        World world = player.getWorld();
        String global = StringUtil.placeholder(this.configManager.getLanguageValue(ConfigNodes.GLOBAL_FORMAT),
                new StringPlaceholder("%player%", player.getName()),
                new StringPlaceholder("%message%", message),
                new StringPlaceholder("%world%", world.getName()));

        this.toggled.forEach(uuid -> {
            Player toPlayer = Bukkit.getPlayer(uuid);
            World toWorld = toPlayer.getWorld();
            if (toPlayer == null || toWorld.equals(world)) {
                return;
            }
            toPlayer.sendMessage(global);
        });

    }

    public boolean toggle(Player player) {
        UUID uuid = player.getUniqueId();
        if (this.toggled.contains(uuid)) {
            this.toggled.remove(uuid);
            return false;
        }
        this.toggled.add(uuid);
        return true;
    }

    public boolean toggled(Player player) {
        UUID uuid = player.getUniqueId();
        return this.toggled.contains(uuid);
    }

    public ArrayList<UUID> getToggled() {
        return this.toggled;
    }

}