package xyz.lotho.me;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.luckperms.api.LuckPerms;
import org.bukkit.Difficulty;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.lotho.me.commands.*;
import xyz.lotho.me.handlers.*;
import xyz.lotho.me.interfaces.HubSelectMenu;
import xyz.lotho.me.interfaces.ServerSelectMenu;
import xyz.lotho.me.managers.HubManager;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.queues.Queue;
import xyz.lotho.me.queues.QueueManager;
import xyz.lotho.me.utils.Config;

public final class SkyHub extends JavaPlugin implements PluginMessageListener {

    public Config configManager = new Config(this, "config.yml");
    public YamlConfiguration config = configManager.getConfig();

    public HubManager hubManager = new HubManager(this);
    public QueueManager queueManager = new QueueManager(this);

    public ServerSelectMenu serverSelectMenu = new ServerSelectMenu(this);
    public HubSelectMenu hubSelectMenu = new HubSelectMenu(this);

    public String serverName = this.config.getString("utils.thisServer");
    public LuckPerms luckPermsAPI;

    private final Queue queue = new Queue(this);
    private PluginManager pluginManager;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if (subChannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int count = in.readInt();

            this.hubManager.serverCounts.put(server, count);
        }
    }

    @Override
    public void onEnable() {
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);

        pluginManager = this.getServer().getPluginManager();
        this.config.getStringList("servers").forEach((serverName) -> this.queueManager.addServerToQueue(serverName));

        loadCommands();
        loadListeners();

        RegisteredServiceProvider<LuckPerms> provider = this.getServer().getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            luckPermsAPI = provider.getProvider();
        }

        this.getServer().getScheduler().runTaskTimerAsynchronously(this, () -> {
            this.hubManager.getServerCount("ALL");
            this.hubManager.getServerCount("orbit-1");
            this.hubManager.getServerCount("atlas-1");

            this.getServer().getOnlinePlayers().forEach((player) -> {
                HubPlayer hubPlayer = this.hubManager.getHubPlayer(player);
                hubPlayer.getScoreboard().updateScoreboard();
            });
        }, 100, 100);

        this.getServer().getOnlinePlayers().forEach((player) -> {
            this.hubManager.addHubPlayer(player);

            HubPlayer hubPlayer = this.hubManager.getHubPlayer(player);
            hubPlayer.setup();
        });

        this.getServer().getScheduler().runTaskTimer(this, queue::attemptQueue, 200, 200);
        this.getServer().getWorlds().forEach((world) -> world.setDifficulty(Difficulty.PEACEFUL));
    }

    @Override
    public void onDisable() {
        super.onDisable();

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
    }

    public void loadCommands() {
        this.getCommand("setspawn").setExecutor(new SetSpawn(this));
        this.getCommand("setvoidlimit").setExecutor(new SetVoidLimit(this));
        this.getCommand("adminbypass").setExecutor(new AdminBypass(this));
        this.getCommand("spawn").setExecutor(new Spawn(this));
        this.getCommand("leavequeue").setExecutor(new LeaveQueue(this));
    }

    public void loadListeners() {
        pluginManager.registerEvents(new handlePlayerMovement(this), this);
        pluginManager.registerEvents(new handleConnections(this), this);
        pluginManager.registerEvents(new handleInventoryMovement(this), this);
        pluginManager.registerEvents(new handleHubInteractions(this), this);
        pluginManager.registerEvents(new handleBlocks(this), this);
        pluginManager.registerEvents(new handleBlockedCommands(this), this);
        pluginManager.registerEvents(new handleDamage(this), this);
        pluginManager.registerEvents(new handleItems(this), this);
    }

    public String getServerName() {
        return this.serverName;
    }
}
