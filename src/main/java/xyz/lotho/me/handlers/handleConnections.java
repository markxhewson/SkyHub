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

        hubPlayer.setScoreboard();
        player.teleport(this.instance.hubManager.getHubSpawnLocation());

        hubPlayer.setup();
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(event.getPlayer());
        this.instance.queueManager.removePlayerFromQueues(hubPlayer);

        this.instance.queueManager.getServersQueue().forEach((serverName, players) -> players.forEach(HubPlayer::setQueueScoreboard));
        event.setQuitMessage(null);
    }

}
