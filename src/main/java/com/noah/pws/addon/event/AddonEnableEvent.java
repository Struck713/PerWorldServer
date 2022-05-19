package com.noah.pws.addon.event;

import com.noah.pws.addon.Addon;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddonEnableEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Addon addon;

    public AddonEnableEvent(Addon addon) {
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
