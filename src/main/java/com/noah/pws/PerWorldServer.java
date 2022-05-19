package com.noah.pws;

import com.noah.pws.addon.AddonManager;
import com.noah.pws.command.PerWorldServerCommand;
import com.noah.pws.command.PerWorldServerTabComplete;
import com.noah.pws.config.Config;
import com.noah.pws.config.ConfigLang;
import com.noah.pws.config.ConfigSettings;
import com.noah.pws.suite.SuiteListener;
import com.noah.pws.suite.SuiteManager;
import com.noah.pws.util.CloakUtil;
import com.noah.pws.util.FileUtil;
import com.noah.pws.util.UpdateUtil;
import com.noah.pws.util.versions.CloakUtil_1_8_BELOW;
import com.noah.pws.util.versions.CloakUtil_1_9_PLUS;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class PerWorldServer extends JavaPlugin {

    private SuiteManager suiteManager;
    private AddonManager addonManager;
    private CloakUtil cloakUtil;

    private ConfigSettings settings;
    //private ConfigLang language;

    @Override
    public void onEnable() {

        //another switch statement of course

        // managers
        this.suiteManager = new SuiteManager(this);
        this.suiteManager.loadAll();
        this.addonManager = new AddonManager(this, this.suiteManager);
        this.addonManager.loadAll();
        this.cloakUtil = new CloakUtil_1_9_PLUS(this);

        // config
        this.settings = new ConfigSettings(this);
        //this.language = new ConfigLang(this);
        Config.load(this.settings /*, this.language */);

        // listeners
        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new SuiteListener(this, this.suiteManager, this.cloakUtil), this);

        // commands
        PluginCommand mainCommand = getCommand("pws");
        mainCommand.setExecutor(new PerWorldServerCommand(this.suiteManager, this.addonManager, this.settings));
        mainCommand.setTabCompleter(new PerWorldServerTabComplete(this.suiteManager));

        String version = getVersion();
        String subVersion = version.substring(1, version.length() - 3);
        getLogger().info("[CloakUtil] Found version " + version + " of CraftBukkit.");
        switch (subVersion) {
            case "1_9":
            case "1_8":
                this.cloakUtil = new CloakUtil_1_8_BELOW(this);
                break;
            case "1_18":
            case "1_17":
            case "1_16":
            case "1_15":
            case "1_14":
            case "1_13":
            case "1_12":
            case "1_11":
            case "1_10":
                this.cloakUtil = new CloakUtil_1_9_PLUS(this);
                break;
            default:
                getLogger().info("[CloakUtil] You have an unsupported version of CraftBukkit, try updating!");
                pluginManager.disablePlugin(this);
                break;
        }

        UpdateUtil updateChecker = new UpdateUtil(this, this.settings.isCheckForUpdates());
        switch (updateChecker.check()) {
            case OUT_OF_DATE:
                getLogger().info("An update for PerWorldServer (" + updateChecker.getNewVersion() + ") was found. Please update at: https://www.spigotmc.org/resources/23989/");
                break;
            case UP_TO_DATE:
            default:
                break;
        }

    }

    @Override
    public void onDisable() { this.suiteManager.saveAll(); }

    public File getPluginFolder() {
        File directory = this.getDataFolder();
        if (!directory.exists()) directory.mkdirs();
        return new File(directory, FileUtil.PATH_SEPARATOR);
    }

    public String getVersion() {
        final String packageName = this.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }

}
