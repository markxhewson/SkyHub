package xyz.lotho.me.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;

public class handleBlocks implements Listener {

    SkyHub instance;

    public handleBlocks(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        if (!hubPlayer.adminBypassEnabled()) event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        if (!hubPlayer.adminBypassEnabled()) event.setCancelled(true);
    }
}
