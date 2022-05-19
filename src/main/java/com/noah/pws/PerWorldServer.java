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
        mainCommand.setExecutor(new PerWorldServerCommand(this.suiteManager, this.settings));
        mainCommand.setTabCompleter(new PerWorldServerTabComplete(this.suiteManager));
    }

    @Override
    public void onDisable() { this.suiteManager.saveAll(); }

    public File getPluginFolder() {
        File directory = this.getDataFolder();
        if (!directory.exists()) directory.mkdirs();
        return new File(directory, FileUtil.PATH_SEPARATOR);
    }

}
