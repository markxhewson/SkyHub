package xyz.lotho.me.handlers;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.utils.Config;

import java.util.Objects;

public class handleVoid implements Listener {

    SkyHub instance;

    public handleVoid(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getY() < this.instance.config.getInt("voidLimit")) {
            player.teleport(this.instance.hubManager.getHubSpawnLocation());
        }
    }
}
