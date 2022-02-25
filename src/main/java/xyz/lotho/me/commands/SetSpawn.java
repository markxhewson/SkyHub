package xyz.lotho.me.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.utils.Chat;

public class SetSpawn implements CommandExecutor {

    SkyHub instance;

    public SetSpawn(SkyHub instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        this.instance.config.set("spawnpoint.world", player.getWorld().getName());
        this.instance.config.set("spawnpoint.x", player.getLocation().getX());
        this.instance.config.set("spawnpoint.y", player.getLocation().getY());
        this.instance.config.set("spawnpoint.z", player.getLocation().getZ());
        this.instance.config.set("spawnpoint.yaw", player.getLocation().getYaw());
        this.instance.config.set("spawnpoint.pitch", player.getLocation().getPitch());

        this.instance.configManager.saveConfig();

        player.sendMessage(Chat.colorize("&aYou have successfully set the spawn location for players."));
        return false;
    }
}
