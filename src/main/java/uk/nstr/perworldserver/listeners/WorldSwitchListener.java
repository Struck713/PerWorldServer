package uk.nstr.perworldserver.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerLoadEvent;
import uk.nstr.perworldserver.config.ConfigManager;
import uk.nstr.perworldserver.config.ConfigNodes;
import uk.nstr.perworldserver.manager.GlobalManager;
import uk.nstr.perworldserver.util.CloakUtil;
import uk.nstr.perworldserver.util.string.StringUtil;

public class WorldSwitchListener implements Listener {

    private boolean enabled;
    private ConfigManager configManager;
    private GlobalManager globalManager;
    private CloakUtil cloakUtil;

    public WorldSwitchListener(ConfigManager configManager, GlobalManager globalManager, CloakUtil cloakUtil) {
        this.configManager = configManager;
        this.globalManager = globalManager;
        this.cloakUtil = cloakUtil;

        //again with the cool shorthand
        if (!(this.enabled = this.configManager.getBoolean(ConfigNodes.TAB_ENABLED))) {
            ConsoleCommandSender sender = Bukkit.getConsoleSender();
            sender.sendMessage(StringUtil.color("&r[PerWorldServer] &lNOTE:&r You have set per world tab to: &cdisabled&r."));
        }

    }

    @EventHandler
    public void onPluginLoad(ServerLoadEvent event) {

        if (!this.enabled) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            World currentWorld = player.getWorld();
            this.cloakUtil.showAWorld(player, currentWorld);
            Bukkit.getWorlds()
                    .stream()
                    .filter(world -> !world.equals(currentWorld))
                    .forEach(world -> this.cloakUtil.hideAWorld(player, world));
        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        if (!this.enabled) {
            return;
        }

        Player player = event.getPlayer();
        World currentWorld = player.getWorld();

        //take all the worlds but the current one
        Bukkit.getWorlds()
                .stream()
                .filter(world -> !world.equals(currentWorld))
                .forEach(world -> {
                    this.cloakUtil.hide(world, player); //hide player from those worlds
                });
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {

        if (!this.enabled) {
            return;
        }

        Player player = event.getPlayer();
        World from = event.getFrom();
        World to = player.getWorld();

        this.cloakUtil.hideAWorld(player, from);
        this.cloakUtil.showAWorld(player, to);
        this.cloakUtil.hide(from, player); //hide player in old world
        this.cloakUtil.show(to, player); //show player in new world
    }

}
