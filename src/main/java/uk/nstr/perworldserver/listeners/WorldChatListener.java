package uk.nstr.perworldserver.listeners;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import uk.nstr.perworldserver.config.ConfigManager;
import uk.nstr.perworldserver.config.ConfigNodes;
import uk.nstr.perworldserver.manager.GlobalManager;
import uk.nstr.perworldserver.util.string.StringUtil;

public class WorldChatListener implements Listener {

    private boolean enabled;
    private ConfigManager configManager;
    private GlobalManager globalManager;

    public WorldChatListener(ConfigManager configManager, GlobalManager globalManager) {
        this.configManager = configManager;
        this.globalManager = globalManager;

        //execising some cool shorthand here
        if (!(this.enabled = this.configManager.getBoolean(ConfigNodes.CHAT_ENABLED))) {
            ConsoleCommandSender sender = Bukkit.getConsoleSender();
            sender.sendMessage(StringUtil.color("&r[PerWorldServer] &lNOTE:&r You have set per world chat to: &cdisabled&r."));
        }

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        /*
            This event can get problematic easily: it is async
            so most BukkitAPI interaction will be unavailable.
         */

        if (!this.enabled) {
            return;
        }

        Player player = event.getPlayer();
        World world = player.getWorld();
        String message = event.getMessage();

        // modify the recipients, this preserves the chat format set by other plugins
        event.getRecipients().clear();
        event.getRecipients().addAll(world.getPlayers()); //simple, heh

        globalManager.broadcast(player, message);

    }

}
