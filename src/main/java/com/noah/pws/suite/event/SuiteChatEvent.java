package com.noah.pws.suite.event;

import com.noah.pws.suite.Suite;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SuiteChatEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Suite suite;
    private Player player;
    private String message;

    public SuiteChatEvent(Suite suite, Player player, String message) {
        this.suite = suite;
        this.player = player;
        this.message = message;
    }

    public Suite getSuite() {
        return suite;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
