package uk.nstr.perworldserver.util.versions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import uk.nstr.perworldserver.util.CloakUtil;

public class CloakUtil_1_16_R1 extends CloakUtil {

    public CloakUtil_1_16_R1(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void show(Player player, Player toShow) {
        player.showPlayer(this.getPlugin(), toShow);
    }

    @Override
    public void hide(Player player, Player toHide) {
        player.hidePlayer(this.getPlugin(), toHide);
    }
}
