package com.noah.pws.addon.event;

import com.noah.pws.addon.Addon;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddonDisableEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Addon addon;

    public AddonDisableEvent(Addon addon) {
        this.addon = addon;
    }

    public Addon getAddon() {
        return addon;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
