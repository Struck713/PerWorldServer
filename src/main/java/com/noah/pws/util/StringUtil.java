package com.noah.pws.util;

import com.noah.pws.util.tuple.Pair;
import org.bukkit.ChatColor;

public class StringUtil {

    public static final String DIVIDER = StringUtil.colorize("&7&m----------------------\n");
    public static final String SPACER = "  \n";

    public static String colorize(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String makeMenu(String header, String subheader, Pair<String, String>... entries) {
        StringBuilder builder = new StringBuilder();
        builder.append(StringUtil.DIVIDER);

        if (subheader == null) builder.append(StringUtil.colorize("&3&l" + header + "\n"));
        else builder.append(StringUtil.colorize("&3&l" + header + " &7: &f&l" + subheader + "\n"));

        builder.append(StringUtil.SPACER);

        for (Pair<String, String> pair : entries) {
            if (pair == null) builder.append("  \n");
            else builder.append(StringUtil.colorize("&7 - &3" + pair.getLeft() + "  &f" + pair.getRight() + "\n"));
        }

        builder.append(StringUtil.SPACER);
        builder.append(StringUtil.DIVIDER);
        return builder.toString();
    }

}
