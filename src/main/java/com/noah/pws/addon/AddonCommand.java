package com.noah.pws.addon;

import org.bukkit.entity.Player;

public abstract class AddonCommand {

    private String name;
    private String[] aliases;
    private String permission;

    public AddonCommand(String name, String permission, String... aliases) {
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
    }

    public abstract boolean onExecute(Player player, String[] args);

    public String getName() {
        return name;
    }

    public String getPermission() {
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }
}
