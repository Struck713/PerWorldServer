package uk.nstr.perworldserver.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import uk.nstr.perworldserver.config.ConfigManager;
import uk.nstr.perworldserver.config.ConfigNodes;
import uk.nstr.perworldserver.config.ConfigPermissions;
import uk.nstr.perworldserver.manager.GlobalManager;
import uk.nstr.perworldserver.util.string.StringPlaceholder;
import uk.nstr.perworldserver.util.string.StringUtil;

import java.util.stream.Collectors;

public class PerWorldServerCommand implements CommandExecutor {

    private ConfigManager configManager;
    private GlobalManager globalManager;

    public PerWorldServerCommand(ConfigManager configManager, GlobalManager globalManager) {
        this.configManager = configManager;
        this.globalManager = globalManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        String commandName = command.getName();
        if (commandName.equalsIgnoreCase("pws") ||
                commandName.equalsIgnoreCase("perworldserver")) {
            if (!(commandSender instanceof Player)) {
                return true;
            }
            Player player = (Player)commandSender;
            if (!ConfigPermissions.hasPermission(player, ConfigPermissions.WILDCARD, ConfigPermissions.PWS_ADMIN)) {
                player.sendMessage(
                        configManager.getLanguageValue(ConfigNodes.NO_PERMISSION));
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(configManager.getLanguageList(ConfigNodes.HELP_LIST));
                return true;
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    configManager.reload();
                    player.sendMessage(configManager.getLanguageValue(ConfigNodes.RELOAD_MESSAGE));
                    return true;
                }
                if (args[0].equalsIgnoreCase("global")) {
                    boolean toggled = globalManager.toggle(player);
                    if (toggled) {
                        player.sendMessage(configManager.getLanguageValue(ConfigNodes.GLOBAL_ON_MESSAGE));
                        return true;
                    }
                    player.sendMessage(configManager.getLanguageValue(ConfigNodes.GLOBAL_OFF_MESSAGE));
                    return true;
                }
                if (args[0].equalsIgnoreCase("list")) {

                    player.sendMessage(configManager.getLanguageValue(ConfigNodes.LIST_HEADER));

                    String listItem = configManager.getLanguageValue(ConfigNodes.LIST_ITEM);
                    Bukkit.getWorlds().forEach(world -> {
                        String players = StringUtil.seperatedList(
                                        world.getPlayers()
                                        .stream()
                                        .map(Player::getName)
                                        .collect(Collectors.toList()));
                        player.sendMessage(StringUtil.placeholder(listItem,
                                new StringPlaceholder("%world%", world.getName()),
                                new StringPlaceholder("%players%", players)));
                    });
                    return true;
                }
            }
            player.sendMessage(configManager.getLanguageList(ConfigNodes.HELP_LIST));
            return true;
        }
        return false;
    }


}