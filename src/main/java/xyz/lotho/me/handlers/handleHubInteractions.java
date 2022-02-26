package xyz.lotho.me.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;

public class handleHubInteractions implements Listener {

    SkyHub instance;

    public handleHubInteractions(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        ItemStack item = player.getItemInHand();

        if (item.getType() == Material.INK_SACK && ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Hide")) {
            hubPlayer.hidePlayers();
        } else if (item.getType() == Material.INK_SACK && ChatColor.stripColor(item.getItemMeta().getDisplayName()).contains("Show")) {
            hubPlayer.showPlayers();
        }

        if (item.getType() == Material.COMPASS) {
            this.instance.serverSelectMenu.open(player);
        }

        if (player.getItemInHand().getType() == Material.ENDER_PEARL) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                event.setCancelled(true);

                EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
                player.spigot().setCollidesWithEntities(false);

                Vector velocity = player.getLocation().getDirection();
                Vector multiply = velocity.multiply(2.0);

                enderPearl.setVelocity(multiply);

                player.updateInventory();
                enderPearl.setPassenger(player);

                if (this.instance.hubManager.riders.contains(player)) {
                    this.instance.hubManager.riders.add(player);
                }
            }
        }

    }
}
