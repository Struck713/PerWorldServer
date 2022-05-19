package com.noah.pws.addon.addons;

import com.noah.pws.addon.Addon;
import com.noah.pws.suite.Suite;
import com.noah.pws.util.StringUtil;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class AddonServerMessages extends Addon {

    private boolean enabled;
    private String joinMessage;
    private String quitMessage;

    public AddonServerMessages() {
        super("ServerMessages", "1.0");
    }

    @Override
    public void onEnable() {
        File configFile = new File(this.getFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            // defaults
            config.set("enabled", true);
            config.set("messages.join", "&e%player% joined the game.");
            config.set("messages.quit", "&e%player% left the game.");
            try {
                config.save(configFile);
            } catch (IOException e) {}
        }

        this.enabled = config.getBoolean("enabled");
        this.joinMessage = StringUtil.colorize(config.getString("messages.join"));
        this.quitMessage = StringUtil.colorize(config.getString("messages.quit"));
    }

    @Override
    public void onJoin(Suite suite, Player player) {
        if (!this.enabled) return;
        String placeholderJoinMessage = this.joinMessage.replaceAll("%player%", player.getName());
        player.sendMessage(placeholderJoinMessage); // player must see message
        suite.broadcast(placeholderJoinMessage); // broadcast!
    }

    @Override
    public void onQuit(Suite suite, Player player) {
        if (!this.enabled) return;
        suite.broadcast(this.quitMessage.replaceAll("%player%", player.getName()));
    }

}
