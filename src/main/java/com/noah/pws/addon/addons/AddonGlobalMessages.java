package com.noah.pws.addon.addons;

import com.noah.pws.addon.Addon;
import com.noah.pws.addon.AddonCommand;
import com.noah.pws.suite.Suite;
import com.noah.pws.util.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class AddonGlobalMessages extends Addon {

    private boolean enabled;
    private String messageEnabled;
    private String messageDisabled;
    private String format;

    private List<UUID> globalMode;

    public AddonGlobalMessages() {
        super("GlobalMessages", "1.0");
    }

    @Override
    public void onEnable() {

        this.globalMode = new ArrayList<>();

        File configFile = new File(this.getFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
        if (!configFile.exists()) {
            // defaults
            config.set("enabled", true);
            config.set("format", "&8[&cGLOBAL&8] &8[&3%suite% &a%world%&8] &f%player%&7: &f%message%");
            config.set("messages.enabled", "&8[&cGLOBAL&8] &7You have &aenabled &7global chat mode.");
            config.set("messages.disabled", "&8[&cGLOBAL&8] &7You have &cdisabled &7global chat mode.");
            try {
                config.save(configFile);
            } catch (IOException e) {}
        }

        this.enabled = config.getBoolean("enabled");
        this.format = StringUtil.colorize(config.getString("format"));
        this.messageEnabled = StringUtil.colorize(config.getString("messages.enabled"));
        this.messageDisabled = StringUtil.colorize(config.getString("messages.disabled"));

        this.getAddonManager().registerCommand(new AddonCommand("global", "pws.global") {

            @Override
            public boolean onExecute(Player player, String[] args) {
                UUID uuid = player.getUniqueId();
                if (!globalMode.contains(uuid)) {
                    globalMode.add(uuid);
                    player.sendMessage(messageEnabled);
                    return true;
                }

                globalMode.remove(uuid);
                player.sendMessage(messageDisabled);
                return true;
            }
        });

    }

    @Override
    public void onChat(Suite suite, Player player, String message) {
        String globalMessage = this.format
                .replaceAll("%suite%", suite.getName())
                .replaceAll("%world%", player.getWorld().getName())
                .replaceAll("%player%", player.getName())
                .replaceAll("%message%", message);

        globalMode.stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(receiver -> {
                    receiver.sendMessage(globalMessage);
                });
    }
}
