package xyz.lotho.me.managers;

import net.luckperms.api.model.user.User;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.scoreboards.HubBoard;
import xyz.lotho.me.scoreboards.QueueBoard;
import xyz.lotho.me.utils.Chat;
import xyz.lotho.me.utils.Item;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
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
        new HubBoard(this.instance, this).create();
    }

    public void setQueueScoreboard() {
        new QueueBoard(this.instance, this).create();
    }

    public void setArmor() {
        this.getPlayer().getInventory().setHelmet(Item.createItem(Material.GLASS, "&cSpace Helmet"));
        this.getPlayer().getInventory().setChestplate(Item.createColouredItem(Material.LEATHER_CHESTPLATE, Color.WHITE, "&cSpace Suit"));
        this.getPlayer().getInventory().setLeggings(Item.createColouredItem(Material.LEATHER_LEGGINGS, Color.WHITE, "&cSpace Suit"));
        this.getPlayer().getInventory().setBoots(Item.createColouredItem(Material.LEATHER_BOOTS, Color.WHITE, "&cSpace Suit"));
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

    public String getRank() {
        User user = this.instance.luckPermsAPI.getUserManager().getUser(this.getPlayer().getUniqueId());

        assert user != null;
        return user.getCachedData().getMetaData().getMeta().get("tag").get(0);
    }

    public boolean adminBypassEnabled() {
        return this.instance.hubManager.getAdminBypass().contains(this);
    }

    public void setItems() {
        this.getPlayer().getInventory().clear();

        this.getPlayer().getInventory().setItem(1, Item.createItem(Material.ENDER_PEARL, "&5&lEnderbutt", Chat.colorize("&7Click to ride the enderbutt!")));
        this.getPlayer().getInventory().setItem(3, Item.createItemShort(Material.INK_SACK, 10, "&3&lHide Players", Chat.colorize("&7Click to hide players.")));
        this.getPlayer().getInventory().setItem(5, Item.createItem(Material.COMPASS, "&c&lServer Selector", Chat.colorize("&7Click to navigate across the network!")));
        this.getPlayer().getInventory().setItem(7, Item.createItem(Material.WATCH, "&d&lHubs", Chat.colorize("&7Click to move to a separate lobby.")));
    }

    public HashMap<String, String> getQueueData() {
        HashMap<String, String> result = new HashMap<>();

        this.instance.queueManager.getServersQueue().forEach((serverName, players) -> {
            players.forEach((player) -> {
                if (this.instance.queueManager.isPlayerInQueue(serverName, player) && player.getPlayer().getUniqueId() == this.getPlayer().getUniqueId()) {
                    result.put("position", String.valueOf(this.instance.queueManager.getServersQueue().get(serverName).indexOf(this)));
                    result.put("total", String.valueOf(this.instance.queueManager.getServersQueue().get(serverName).size()));
                    result.put("serverName", serverName);
                }
            });
        });

        result.putIfAbsent("serverName", "null");
        result.putIfAbsent("position", "0");
        result.putIfAbsent("total", "0");

        return result;
    }

    public boolean isInQueue(String serverName) {
        return this.instance.queueManager.isPlayerInQueue(serverName, this);
    }

    public void joinQueue(String serverName) {
        this.instance.queueManager.addPlayerToQueue(serverName, this);
    }

    public void setup() {
        setArmor();
        setItems();
        sendConnectMessage();

        this.getPlayer().setGameMode(GameMode.ADVENTURE);
        this.getPlayer().setWalkSpeed((float) this.instance.config.getDouble("walkSpeed"));
    }

    public void sendConnectMessage() {
        List<String> message = this.instance.config.getStringList("connectMessage");
        for (String s : message) this.getPlayer().sendMessage(Chat.colorize(s));
    }


}
