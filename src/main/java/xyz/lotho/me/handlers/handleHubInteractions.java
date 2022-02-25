package xyz.lotho.me.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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
    }
}
