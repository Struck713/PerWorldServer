package com.noah.pws.addon.addons;

import com.noah.pws.addon.Addon;
import com.noah.pws.addon.AddonCommand;
import com.noah.pws.addon.AddonManager;
import com.noah.pws.util.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class AddonBungeeCommands extends Addon {

    private boolean enabled;

    public AddonBungeeCommands() {
        super("BungeeCommands", "1.0");
    }

    @Override
    public void onEnable() {
        File configFile = new File(this.getFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            // defaults
            config.set("enabled", true);
            try {
                config.save(configFile);
            } catch (IOException e) {}
        }

        this.enabled = config.getBoolean("enabled");

        if (!this.enabled) return; // do not register

        final AddonManager addonManager = this.getAddonManager();
        addonManager.registerCommand(new AddonCommand("server", "pws.bungee.server") {
            @Override
            public boolean onExecute(Player player, String[] args) {
                if (args.length == 1) {
                    String suite = args[0];
                    player.sendMessage("going to: " + suite);
                    return true;
                }

                player.sendMessage("Server list");
                return true;
            }
        });

    }

}
