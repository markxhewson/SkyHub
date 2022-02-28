package xyz.lotho.me.managers;

import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.scoreboards.HubBoard;
import xyz.lotho.me.utils.Chat;
import xyz.lotho.me.utils.Item;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class HubPlayer {

    SkyHub instance;
    Player player;

    boolean playersHidden;

    public HubPlayer(SkyHub instance, Player player) {
        this.instance = instance;
        this.player = player;

        this.playersHidden = false;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean playersHidden() {
        return this.playersHidden;
    }

    public void hidePlayers() {
        this.playersHidden = true;

        this.instance.getServer().getOnlinePlayers().forEach((online) -> {
            this.player.hidePlayer(online);
        });

        player.getInventory().setItem(3, Item.createItemShort(Material.INK_SACK, 8, "&3&lShow Players", Chat.colorize("&7Click to hide players.")));
    }

    public void showPlayers() {
        this.playersHidden = false;

        this.instance.getServer().getOnlinePlayers().forEach((online) -> {
            this.player.showPlayer(online);
        });

        player.getInventory().setItem(3, Item.createItemShort(Material.INK_SACK, 10, "&3&lHide Players", Chat.colorize("&7Click to hide players.")));
    }

    public void setScoreboard() {
        this.getPlayer().setScoreboard(new HubBoard(this.instance, this).create());
    }

    public void setArmor(Player player) {
        player.getInventory().setHelmet(Item.createItem(Material.GLASS, "&cSpace Helmet"));
        player.getInventory().setChestplate(Item.createColouredItem(Material.LEATHER_CHESTPLATE, Color.WHITE, "&cSpace Suit"));
        player.getInventory().setLeggings(Item.createColouredItem(Material.LEATHER_LEGGINGS, Color.WHITE, "&cSpace Suit"));
        player.getInventory().setBoots(Item.createColouredItem(Material.LEATHER_BOOTS, Color.WHITE, "&cSpace Suit"));
    }

    public void connect(String serverName) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);

        try {
            dataOutputStream.writeUTF("Connect");
            dataOutputStream.writeUTF(serverName);
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.player.sendPluginMessage(this.instance, "BungeeCord", byteArrayOutputStream.toByteArray());
    }

    public boolean adminBypassEnabled() {
        return this.instance.hubManager.getAdminBypass().contains(this);
    }

    public void setItems(Player player) {
        player.getInventory().clear();

        player.getInventory().setItem(1, Item.createItem(Material.ENDER_PEARL, "&5&lEnderbutt", Chat.colorize("&7Click to ride the enderbutt!")));
        player.getInventory().setItem(3, Item.createItemShort(Material.INK_SACK, 10, "&3&lHide Players", Chat.colorize("&7Click to hide players.")));
        player.getInventory().setItem(5, Item.createItem(Material.COMPASS, "&c&lServer Selector", Chat.colorize("&7Click to navigate across the network!")));
        player.getInventory().setItem(7, Item.createItem(Material.WATCH, "&d&lHubs", Chat.colorize("&7Click to move to a separate lobby.")));
    }

    public void setup(Player player) {
        setArmor(player);
        setItems(player);
        sendConnectMessage(player);

        player.setGameMode(GameMode.ADVENTURE);
        player.setWalkSpeed((float) this.instance.config.getDouble("walkSpeed"));
    }

    public void sendConnectMessage(Player player) {
        List<String> message = this.instance.config.getStringList("connectMessage");

        for (String s : message) {
            player.sendMessage(Chat.colorize(s));
        }
    }


}
