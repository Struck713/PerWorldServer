package uk.nstr.perworldserver.util.string;

import org.bukkit.ChatColor;

import java.util.List;

public class StringUtil
{
    public StringUtil() {
    }

    public static String color(final String toColor) {
        return color(toColor, '&');
    }

    public static String color(final String toColor, final char alt) {
        return ChatColor.translateAlternateColorCodes(alt, toColor);
    }

    public static String placeholder(String toFill, final StringPlaceholder... placeholders) {
        for (final StringPlaceholder placeholder : placeholders) {
            toFill = toFill.replaceAll(placeholder.getPlaceholder(), placeholder.getFill());
        }
        return toFill;
    }

    public static String seperatedList(List<String> list) {
        StringBuilder builder = new StringBuilder();
        if (list.size() <= 0) {
            return "None";
        }
        builder.append(list.get(0)); //append first item
        for (int i = 1; i<list.size(); i++) builder.append(", " + list.get(i));
        return builder.toString();
    }

}
