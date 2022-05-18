package com.noah.pws.util;

import org.bukkit.ChatColor;

public class StringUtil {

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
