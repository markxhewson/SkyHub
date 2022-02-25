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

public class handleHidingPlayers implements Listener {

    SkyHub instance;

    public handleHidingPlayers(SkyHub instance) {
        this.instance = instance;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        ItemStack dye = player.getItemInHand();

        if (dye.getType() == Material.INK_SACK && ChatColor.stripColor(dye.getItemMeta().getDisplayName()).contains("Hide")) {
            hubPlayer.hidePlayers();
        } else if (dye.getType() == Material.INK_SACK && ChatColor.stripColor(dye.getItemMeta().getDisplayName()).contains("Show")) {
            hubPlayer.showPlayers();
        }
    }
}
