package uk.nstr.perworldserver.config;

import org.bukkit.entity.Player;

public class ConfigPermissions {

    public static final String WILDCARD = "pws.*";
    public static final String PWS_ADMIN = "pws.admin";

    public static boolean hasPermission(Player player, String... permissions) {
        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return false;
            }
        }
        return true;
    }

}
