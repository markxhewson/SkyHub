package xyz.lotho.me;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.lotho.me.commands.SetSpawn;
import xyz.lotho.me.commands.SetVoidLimit;
import xyz.lotho.me.handlers.*;
import xyz.lotho.me.interfaces.ServerSelectMenu;
import xyz.lotho.me.managers.HubManager;
import xyz.lotho.me.utils.Config;

public final class SkyHub extends JavaPlugin {

    public Config configManager = new Config(this, "config.yml");
    public YamlConfiguration config = configManager.getConfig();

    public HubManager hubManager = new HubManager(this);

    public ServerSelectMenu serverSelectMenu = new ServerSelectMenu(this);

    PluginManager pluginManager;

    @Override
    public void onEnable() {
        pluginManager = this.getServer().getPluginManager();

        loadCommands();
        loadListeners();

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    @Override
    public void onDisable() {
        super.onDisable();

        this.getServer().getMessenger().unregisterOutgoingPluginChannel(this, "BungeeCord");
    }

    public void loadCommands() {
        this.getCommand("setspawn").setExecutor(new SetSpawn(this));
        this.getCommand("setvoidlimit").setExecutor(new SetVoidLimit(this));
    }

    public void loadListeners() {
        pluginManager.registerEvents(new handleVoid(this), this);
        pluginManager.registerEvents(new handleConnections(this), this);
        pluginManager.registerEvents(new handleInventoryMovement(this), this);
        pluginManager.registerEvents(new handleHubInteractions(this), this);
        pluginManager.registerEvents(new handleBlockBreaking(this), this);
        pluginManager.registerEvents(new handleBlockedCommands(this), this);
    }
}
