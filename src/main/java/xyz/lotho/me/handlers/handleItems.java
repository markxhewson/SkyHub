package xyz.lotho.me.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import xyz.lotho.me.SkyHub;

public class handleItems implements Listener {

    SkyHub instance;

    public handleItems(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void itemDrop(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void itemPickup(PlayerPickupItemEvent event) {
        event.setCancelled(true);
    }
}
