package xyz.lotho.me.handlers;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import xyz.lotho.me.SkyHub;

public class handlePlayerMovement implements Listener {

    SkyHub instance;

    public handlePlayerMovement(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onMovement(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getY() < this.instance.config.getInt("voidLimit")) {
            player.teleport(this.instance.hubManager.getHubSpawnLocation());
        }

        if (this.instance.hubManager.pearlRiders.contains(player)) {
            if (player.getLocation().subtract(0.0D, 0.5D, 0.0D).getBlock().getType() != Material.AIR) {
                this.instance.hubManager.pearlRiders.remove(player);
                player.teleport(player.getLocation().add(0.0D, 2.5D, 0.0D));
            }
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) event.setCancelled(true);
    }
}
