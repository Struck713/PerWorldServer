package com.noah.pws.util.versions;

import com.noah.pws.util.CloakUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CloakUtil_1_8_BELOW extends CloakUtil {

    public CloakUtil_1_8_BELOW(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void show(Player player, Player toShow) {
        player.showPlayer(toShow);
    }

    @Override
    public void hide(Player player, Player toHide) {
        player.hidePlayer(toHide);
    }
}
