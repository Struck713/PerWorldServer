package com.noah.pws.suite;

import com.noah.pws.suite.event.SuiteChangeEvent;
import com.noah.pws.suite.event.SuiteChatEvent;
import com.noah.pws.util.CloakUtil;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerLoadEvent;

public class SuiteListener implements Listener {

    private SuiteManager suiteManager;
    private CloakUtil cloakUtil;

    public SuiteListener(SuiteManager suiteManager, CloakUtil cloakUtil) {
        this.suiteManager = suiteManager;
        this.cloakUtil = cloakUtil;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();
        String message = event.getMessage();

        event.getRecipients().clear();

        Suite suite = this.suiteManager.getSuiteByWorld(world);
        event.getRecipients().addAll(suite.getPlayers());

        Bukkit.getPluginManager().callEvent(new SuiteChatEvent(suite, player, message));

    }

    @EventHandler
    public void onPluginLoad(ServerLoadEvent event) {
//        Bukkit.getOnlinePlayers().forEach(player -> {
//            Suite currentSuite = this.suiteManager.getSuiteByWorld(player.getWorld());
//            this.cloakUtil.showASuite(player, currentSuite);
//            this.suiteManager.getAllSuites()
//                    .stream()
//                    .filter(suite -> !suite.equals(currentSuite))
//                    .forEach(suite -> this.cloakUtil.hideASuite(player, suite));
//        });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Suite currentSuite = this.suiteManager.getSuiteByWorld(player.getWorld());

        //take all the worlds but the current one
        this.suiteManager.getAllSuites()
                .stream()
                .filter(suite -> !suite.equals(currentSuite))
                .forEach(suite -> {
                    this.cloakUtil.hide(suite, player); //hide player from those worlds
                });

        Bukkit.getPluginManager().callEvent(new SuiteChangeEvent(player, null, currentSuite));

        // always disable join message
        event.setJoinMessage(null);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Suite currentSuite = this.suiteManager.getSuiteByWorld(player.getWorld());

        Bukkit.getPluginManager().callEvent(new SuiteChangeEvent(player, currentSuite, null));

        // always disable leave message
        event.setQuitMessage(null);
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        Suite from = this.suiteManager.getSuiteByWorld(event.getFrom());
        Suite to = this.suiteManager.getSuiteByWorld(player.getWorld());

        this.cloakUtil.hideASuite(player, from);
        this.cloakUtil.showASuite(player, to);
        this.cloakUtil.hide(from, player); //hide player in old world
        this.cloakUtil.show(to, player); //show player in new world

        Bukkit.getPluginManager().callEvent(new SuiteChangeEvent(player, from, to));

    }

}
