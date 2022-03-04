package xyz.lotho.me.interfaces;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.interfaces.utils.Menu;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;
import xyz.lotho.me.utils.Item;

import java.util.ArrayList;
import java.util.Locale;

public class HubSelectMenu extends Menu {

    protected final SkyHub instance;

    public HubSelectMenu(SkyHub instance) {
        super("Hub Selector", 27);

        this.instance = instance;

        setItems();
    }

    @Override
    public void setItems() {
        this.fillRemainingSlots();

        super.getInventory().setItem(11, Item.createItem(
                Material.QUARTZ_BLOCK,
                "&e&lHub-1",
                Chat.colorize("&7&m----------------------"),
                Chat.colorize("&7&oJourney to our first waiting station."),
                "",
                Chat.colorize("&7(Click to connect to Hub 1)"),
                Chat.colorize("&7&m----------------------")
                )
        );

        super.getInventory().setItem(15, Item.createItem(
                Material.QUARTZ_BLOCK,
                "&e&lHub-2",
                Chat.colorize("&7&m----------------------"),
                Chat.colorize("&7&oJourney to our second waiting station."),
                "",
                Chat.colorize("&7(Click to connect to Hub 2)"),
                Chat.colorize("&7&m----------------------")
            )
        );
    }

    public void handleClick(Player clicker, ItemStack clickedItem, int slot) {
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(clicker);
        String serverName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).toLowerCase();

        if (serverName.equals(this.instance.getServerName())) {
            hubPlayer.getPlayer().sendMessage(Chat.colorize("&cYou are already connected to this hub."));
            return;
        }

        if (serverName.contains("hub")) {
            if (!this.instance.queueManager.isPlayerInQueue(serverName, hubPlayer)) {
                hubPlayer.joinQueue(serverName);
                hubPlayer.getPlayer().sendMessage(Chat.colorize("&aYou have successfully joined the queue for this hub!\n &7You will be transferred shortly, please be patient."));
                hubPlayer.setQueueScoreboard();
            } else {
                hubPlayer.getPlayer().sendMessage(Chat.colorize("&cYou are already in queue to join this hub!\n &7Please wait patiently for your position to change."));
            }
        }
    }
}
