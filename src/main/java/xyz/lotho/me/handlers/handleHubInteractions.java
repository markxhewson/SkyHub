package xyz.lotho.me.handlers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;
import xyz.lotho.me.utils.Item;

import java.text.DecimalFormat;

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
        if (item.getType() == Material.WATCH) {
            this.instance.hubSelectMenu.open(player);
        }

        if (player.getItemInHand().getType() == Material.ENDER_PEARL) {
            if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (!this.instance.hubManager.pearlCooldown.containsKey(player)) {
                    this.instance.hubManager.pearlCooldown.put(player, System.currentTimeMillis() - 50);
                }
                long time = this.instance.hubManager.pearlCooldown.get(player);
                if (time < System.currentTimeMillis()) {
                    event.setCancelled(true);

                    this.instance.hubManager.pearlCooldown.put(player, System.currentTimeMillis() + 2000);

                    EnderPearl enderPearl = player.launchProjectile(EnderPearl.class);
                    player.spigot().setCollidesWithEntities(false);

                    Vector velocity = player.getLocation().getDirection();
                    Vector multiply = velocity.multiply(2.0);

                    enderPearl.setVelocity(multiply);

                    player.updateInventory();
                    enderPearl.setPassenger(player);

                    if (this.instance.hubManager.pearlRiders.contains(player)) {
                        this.instance.hubManager.pearlRiders.add(player);
                    }
                } else {
                    DecimalFormat format = new DecimalFormat("#.#");
                    String timeLeft = format.format((float) (time - System.currentTimeMillis()) / 1000);

                    Chat.sendActionText(player, "&aYou can use this again in &f" + timeLeft + " &aseconds.");

                    event.setCancelled(true);
                    this.instance.getServer().getScheduler().runTaskLaterAsynchronously(this.instance, () -> {
                        player.getInventory().setItem(1, Item.createItem(Material.ENDER_PEARL, "&5&lEnderbutt", Chat.colorize("&7Click to ride the enderbutt!")));
                    }, 1);
                }
            }
        }

    }
}
