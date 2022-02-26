package xyz.lotho.me.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;

public class Spawn implements CommandExecutor {

    SkyHub instance;

    public Spawn(SkyHub instance) {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        player.teleport(this.instance.hubManager.getHubSpawnLocation());

        return true;
    }
}
