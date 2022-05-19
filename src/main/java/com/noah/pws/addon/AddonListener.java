package com.noah.pws.addon;

import com.noah.pws.suite.Suite;
import com.noah.pws.suite.SuiteManager;
import com.noah.pws.suite.event.SuiteChangeEvent;
import com.noah.pws.suite.event.SuiteChatEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;
import java.util.stream.Collectors;

public class AddonListener implements Listener {

    private AddonManager addonManager;
    private SuiteManager suiteManager;

    public AddonListener(AddonManager addonManager, SuiteManager suiteManager) {
        this.addonManager = addonManager;
        this.suiteManager = suiteManager;
    }

    @EventHandler
    public void onChange(SuiteChangeEvent event) {
        Player player = event.getPlayer();
        Suite from = event.getFrom();
        Suite to = event.getTo();

        if (from != null) this.addonManager.forEach(addon -> addon.onQuit(from, player));
        if (to != null) this.addonManager.forEach(addon -> addon.onJoin(to, player));
    }

    @EventHandler
    public void onChat(SuiteChatEvent event) {
        Suite suite = event.getSuite();
        Player player = event.getPlayer();
        String message = event.getMessage();

        this.addonManager.forEach(addon -> addon.onChat(suite, player, message));
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        int indexOfSpace = message.indexOf(' ');
        String commandString = message.substring(1, indexOfSpace == -1 ? message.length() : indexOfSpace);

        AddonCommand foundCommand = this.addonManager.getRegisteredCommands()
                .stream()
                .filter(command -> command.getName().equals(commandString))
                .filter(command -> player.hasPermission(command.getPermission()))
                .findFirst()
                .orElse(null);

        if (foundCommand == null) return;
        event.setCancelled(true);

        String[] args = indexOfSpace == -1 ? new String[0] : message.substring(message.indexOf(' ') + 1).split(" ");
        foundCommand.onExecute(player, args);
    }

}
