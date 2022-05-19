package com.noah.pws.suite.event;

import com.noah.pws.addon.Addon;
import com.noah.pws.suite.Suite;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SuiteChangeEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Player player;
    private Suite from;
    private Suite to;

    public SuiteChangeEvent(Player player, Suite from, Suite to) {
        this.player = player;
        this.from  = from;
        this.to = to;
    }

    public Player getPlayer() {
        return player;
    }

    public Suite getFrom() {
        return from;
    }

    public Suite getTo() {
        return to;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
