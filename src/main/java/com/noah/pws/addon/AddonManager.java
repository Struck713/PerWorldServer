package com.noah.pws.addon;

import com.noah.pws.PerWorldServer;
import com.noah.pws.addon.addons.AddonGlobalMessages;
import com.noah.pws.addon.addons.AddonServerMessages;
import com.noah.pws.addon.event.AddonDisableEvent;
import com.noah.pws.addon.event.AddonEnableEvent;
import com.noah.pws.suite.SuiteManager;
import com.noah.pws.util.FileUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class AddonManager {

    private PerWorldServer plugin;
    private SuiteManager suiteManager;
    private File folder;
    private LinkedList<Addon> registeredAddons;
    private LinkedList<AddonCommand> registeredCommands;

    public AddonManager(PerWorldServer plugin, SuiteManager suiteManager) {
        this.plugin = plugin;
        this.suiteManager = suiteManager;
        this.folder = new File(this.plugin.getPluginFolder(), "addons" + FileUtil.PATH_SEPARATOR);
        this.registeredAddons = new LinkedList<>();
        this.registeredCommands = new LinkedList<>();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new AddonListener(this, this.suiteManager), this.plugin);
    }

    public void loadAll() {
        // init our addons
        this.load(new AddonServerMessages());
        this.load(new AddonGlobalMessages());
        //this.load(new AddonBungeeCommands());

        // addon lookup
//        for (File file : folder.listFiles()) {
//            if (!(file.isFile() && file.getName().endsWith(".jar"))) continue;
//            try {
//                JarFile jarFile = new JarFile(file);
//                jarFile.
//            } catch (IOException e) {
//                plugin.getLogger().info("Failed to load addon: " + file.getName());
//            }
//        }
    }

    public void load(Addon addon) {
        this.registeredAddons.add(addon);

        // init protected variables
        File folder = new File(this.folder, addon.getName() + FileUtil.PATH_SEPARATOR);
        if (!folder.exists()) folder.mkdirs();
        addon.setFolder(folder);
        addon.setAddonManager(this);

        addon.onEnable(); // initialize addon

        // remove all commands registered
        this.registeredCommands.removeIf(command -> command.getAddon().equals(addon));

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.callEvent(new AddonEnableEvent(addon));

        this.plugin.getLogger().info("[AddonManager] Loaded addon, " + addon.getName() + " v" + addon.getVersion() + ".");
    }

    public void unload(Addon addon, boolean remove) {
        if (remove) this.registeredAddons.remove(addon);

        addon.onDisable();

        final PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.callEvent(new AddonDisableEvent(addon));
    }

    public void reload() {
        this.registeredAddons.forEach(addon -> this.unload(addon, false));
        this.registeredAddons.clear();
        this.loadAll();
    }

    public void registerCommand(AddonCommand command) {
        this.registeredCommands.add(command);
    }

    public LinkedList<AddonCommand> getRegisteredCommands() {
        return registeredCommands;
    }

    public LinkedList<Addon> getRegisteredAddons() {
        return registeredAddons;
    }

    public int size() {
        return this.registeredAddons.size();
    }

    public void forEach(Consumer<Addon> consumer) {
        this.registeredAddons.forEach(consumer);
    }
}
