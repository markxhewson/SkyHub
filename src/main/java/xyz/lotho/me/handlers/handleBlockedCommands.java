package xyz.lotho.me.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.utils.Chat;

import java.util.List;

public class handleBlockedCommands implements Listener {

    SkyHub instance;

    public handleBlockedCommands(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        List<String> blockedCommands = this.instance.config.getStringList("blockedCommands");
        String command = event.getMessage().split(" ")[0].split("/")[1];

        if (!player.isOp() && blockedCommands.stream().anyMatch(command::contains)) {
            event.getPlayer().sendMessage(Chat.colorize(this.instance.config.getString("utils.noPerm")));
            event.setCancelled(true);
        }
    }
}
