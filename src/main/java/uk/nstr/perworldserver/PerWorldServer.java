package uk.nstr.perworldserver;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import uk.nstr.perworldserver.commands.PerWorldServerCommand;
import uk.nstr.perworldserver.config.ConfigManager;
import uk.nstr.perworldserver.config.ConfigNodes;
import uk.nstr.perworldserver.listeners.WorldChatListener;
import uk.nstr.perworldserver.listeners.WorldSwitchListener;
import uk.nstr.perworldserver.manager.GlobalManager;
import uk.nstr.perworldserver.util.CloakUtil;
import uk.nstr.perworldserver.util.UpdateUtil;
import uk.nstr.perworldserver.util.string.StringUtil;
import uk.nstr.perworldserver.util.versions.CloakUtil_1_16_R1;
import uk.nstr.perworldserver.util.versions.CloakUtil_1_9_R1;

public class PerWorldServer extends JavaPlugin {

    private CloakUtil cloakUtil;

    @Override
    public void onEnable() {

        final ConsoleCommandSender sender = getServer().getConsoleSender();
        final PluginManager pluginManager = Bukkit.getPluginManager();

        ConfigManager configManager = new ConfigManager(this, "config.yml");
        configManager.defaults();

        GlobalManager globalManager = new GlobalManager(configManager);

        //version support (switch statement looks nice)
        String version = getVersion();
        String subVersion = version.substring(1, version.length() - 3);
        sender.sendMessage(StringUtil.color("&r[PerWorldServer] Found version &3" + version + "&r of CraftBukkit."));
        switch (subVersion) {
            case "1_9":
            case "1_8":
                this.cloakUtil = new CloakUtil_1_9_R1(this);
                break;
            case "1_16":
            case "1_15":
            case "1_14":
            case "1_13":
            case "1_12":
            case "1_11":
            case "1_10":
                this.cloakUtil = new CloakUtil_1_16_R1(this);
                break;
            default:
                sender.sendMessage(StringUtil.color("&r[PerWorldServer] &cYou have an unsupported version of CraftBukkit, try updating."));
                pluginManager.disablePlugin(this);
                break;
        }

        pluginManager.registerEvents(new WorldSwitchListener(configManager, globalManager, this.cloakUtil), this);
        pluginManager.registerEvents(new WorldChatListener(configManager, globalManager), this);

        final PluginCommand pwsCommand = this.getCommand("perworldserver");
        pwsCommand.setExecutor(new PerWorldServerCommand(configManager, globalManager));

        //another switch statement of course
        UpdateUtil updateChecker = new UpdateUtil(this, configManager.getBoolean(ConfigNodes.UPDATES_ENABLED));
        switch (updateChecker.check()) {
            case OUT_OF_DATE:
                sender.sendMessage(
                        StringUtil.color("&r[PerWorldServer] An update for &3PerWorldServer (" + updateChecker.getNewVersion() + ") &rwas found. Please update at: https://www.spigotmc.org/resources/23989/"));
                break;
            case UP_TO_DATE:
                sender.sendMessage(
                        StringUtil.color("&r[PerWorldServer] Your version of PerWorldServer is &3up-to-date&r!"));
                break;
            default:
                break;
        }

    }

    public String getVersion() {
        final String packageName = this.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

}
