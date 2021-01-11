package uk.nstr.perworldserver.util.versions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import uk.nstr.perworldserver.util.CloakUtil;

public class CloakUtil_1_9_R1 extends CloakUtil {

    public CloakUtil_1_9_R1(JavaPlugin plugin) {
        super(plugin);
    }

    // deprecated, needs to be removed in the future
    @Override
    public void show(Player player, Player toShow) {
        player.showPlayer(toShow);
    }

    @Override
    public void hide(Player player, Player toHide) {
        player.hidePlayer(toHide);
    }
}
