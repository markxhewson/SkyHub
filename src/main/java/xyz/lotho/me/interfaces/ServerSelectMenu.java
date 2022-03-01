package xyz.lotho.me.interfaces;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.interfaces.utils.Menu;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;
import xyz.lotho.me.utils.Item;

import java.util.Arrays;
import java.util.List;

public class ServerSelectMenu extends Menu {

    protected final SkyHub instance;

    public ServerSelectMenu(SkyHub instance) {
        super("Server Selector", 27);

        this.instance = instance;

        setItems();
    }

    @Override
    public void setItems() {
        this.fillRemainingSlots();

        super.getInventory().setItem(11, Item.createItem(
                Material.GRASS,
                "&6&lOrbit",
                "",
                Chat.colorize("&7(Click to connect to Orbit)"))
        );

        super.getInventory().setItem(13, Item.createItem(
                Material.DIAMOND_PICKAXE,
                "&d&lAtlas",
                "",
                Chat.colorize("&7(Click to connect to Atlas)"))
        );

        super.getInventory().setItem(15, Item.createItem(
                Material.BARRIER,
                "&5&lMystery",
                "",
                Chat.colorize("&7(Click to connect to Mystery)"))
        );
    }

    public void handleClick(Player clicker, ItemStack clickedItem, int slot) {
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(clicker);

        switch(clickedItem.getType()) {
            case GRASS:
                if (!this.instance.queueManager.isPlayerInAnyQueue(hubPlayer)) {
                    hubPlayer.joinQueue("orbit-1");
                    hubPlayer.getPlayer().sendMessage(Chat.colorize("&aYou have successfully joined the queue for this realm!\n &7You will be transferred shortly, please be patient."));
                    hubPlayer.setQueueScoreboard();
                } else {
                    hubPlayer.getPlayer().sendMessage(Chat.colorize("&cYou are already in a queue to join a realm!\n &7You can type /leavequeue to remove yourself from the realm queue."));
                }
                break;

            case DIAMOND_PICKAXE:
                if (!this.instance.queueManager.isPlayerInAnyQueue(hubPlayer)) {
                    hubPlayer.joinQueue("atlas-1");
                    hubPlayer.getPlayer().sendMessage(Chat.colorize("&aYou have successfully joined the queue for this realm!\n &7You will be transferred shortly, please be patient."));
                    hubPlayer.setQueueScoreboard();
                } else {
                    hubPlayer.getPlayer().sendMessage(Chat.colorize("&cYou are already in a queue to join a realm!\n &7You can type /leavequeue to remove yourself from the realm queue."));
                }
                break;

            case BARRIER:
                hubPlayer.getPlayer().sendMessage(Chat.colorize("&cThis realm is currently closed."));
                break;

            default:
                break;
        }
    }
}
