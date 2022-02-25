package xyz.lotho.me.handlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import xyz.lotho.me.SkyHub;

public class handleBlockBreaking implements Listener {

    SkyHub instance;

    public handleBlockBreaking(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        event.setCancelled(true);
    }
}
