package xyz.lotho.me.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;

public class LeaveQueue implements CommandExecutor {

    SkyHub instance;

    public LeaveQueue(SkyHub instance) {
        this.instance = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        if (this.instance.queueManager.isPlayerInAnyQueue(hubPlayer)) {
            this.instance.queueManager.removePlayerFromQueues(hubPlayer);
            hubPlayer.getPlayer().sendMessage(Chat.colorize("&cYou have successfully left the realm queue!"));
            hubPlayer.setScoreboard();
        } else {
            player.sendMessage(Chat.colorize("&cYou are not currently in a realm queue."));
        }

        return true;
    }
}