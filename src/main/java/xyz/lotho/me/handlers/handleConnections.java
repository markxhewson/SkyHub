package xyz.lotho.me.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;

public class handleConnections implements Listener {

    SkyHub instance;

    public handleConnections(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        event.setJoinMessage(null);

        this.instance.hubManager.getHubPlayers().forEach((hubPlayer) -> {
            if (hubPlayer.playersHidden()) {
                hubPlayer.getPlayer().hidePlayer(player);
            }
        });

        this.instance.hubManager.addHubPlayer(player);
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        player.teleport(this.instance.hubManager.getHubSpawnLocation());

        hubPlayer.setupHubPlayer(player);
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

}
