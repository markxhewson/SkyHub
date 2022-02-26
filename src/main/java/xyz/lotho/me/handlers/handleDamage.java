package xyz.lotho.me.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.lotho.me.SkyHub;

public class handleDamage implements Listener {

    SkyHub instance;

    public handleDamage(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }
}
