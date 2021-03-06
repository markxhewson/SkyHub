package xyz.lotho.me.managers;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class HubManager {

    SkyHub instance;
    ArrayList<HubPlayer> hubPlayers = new ArrayList<>();
    ArrayList<HubPlayer> adminBypass = new ArrayList<>();

    public HashMap<Player, Long> pearlCooldown = new HashMap<>();
    public ArrayList<Player> pearlRiders = new ArrayList<>();

    public HashMap<String, Integer> data = new HashMap<>();
    public HashMap<String, Integer> serverCounts = new HashMap<>();

    public HubManager(SkyHub instance) {
        this.instance = instance;
    }

    public Location getHubSpawnLocation() {
        return new Location(
                this.instance.getServer().getWorld(Objects.requireNonNull(this.instance.config.getString("spawnpoint.world"))),
                this.instance.config.getDouble("spawnpoint.x"),
                this.instance.config.getDouble("spawnpoint.y"),
                this.instance.config.getDouble("spawnpoint.z"),
                this.instance.config.getInt("spawnpoint.yaw"),
                this.instance.config.getInt("spawnpoint.pitch")
        );
    }

    public void getServerCount(String serverName) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF("PlayerCount");
        out.writeUTF(serverName);

        this.instance.getServer().sendPluginMessage(this.instance, "BungeeCord", out.toByteArray());
    }

    public void addAdminBypass(HubPlayer hubPlayer) {
        this.adminBypass.add(hubPlayer);
    }

    public void removeAdminBypass(HubPlayer hubPlayer) {
        this.adminBypass.remove(hubPlayer);
    }

    public void addHubPlayer(Player player) {
        this.hubPlayers.add(new HubPlayer(this.instance, player));
    }

    public void removeHubPlayer(HubPlayer hubPlayer) {
        this.hubPlayers.remove(hubPlayer);
    }

    public ArrayList<HubPlayer> getAdminBypass() {
        return this.adminBypass;
    }

    public ArrayList<HubPlayer> getHubPlayers() {
        return this.hubPlayers;
    }


    public HubPlayer getHubPlayer(Player player) {
        HashMap<String, HubPlayer> hashMap = new HashMap<>();

        this.hubPlayers.forEach((hubPlayer) -> {
            if (player.getUniqueId().equals(hubPlayer.getPlayer().getUniqueId())) {
                hashMap.put("result", hubPlayer);
            }
        });

        return hashMap.get("result");
    }
}
