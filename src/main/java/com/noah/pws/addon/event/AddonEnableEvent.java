package com.noah.pws.addon.event;

import com.noah.pws.addon.AddonAbstract;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddonEnableEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private AddonAbstract addon;

    public AddonEnableEvent(AddonAbstract addon) {
        this.addon = addon;
    }

    public AddonAbstract getAddon() {
        return addon;
    }

    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
