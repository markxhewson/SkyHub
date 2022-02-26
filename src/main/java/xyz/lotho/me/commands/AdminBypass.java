package xyz.lotho.me.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;

public class AdminBypass implements CommandExecutor {

    SkyHub instance;

    public AdminBypass(SkyHub instance) {
        this.instance = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;
        HubPlayer hubPlayer = this.instance.hubManager.getHubPlayer(player);

        if (!player.hasPermission(this.instance.config.getString("utils.permission"))) {
            player.sendMessage(Chat.colorize(this.instance.config.getString("utils.noPerm")));
            return false;
        }

        if (hubPlayer.adminBypassEnabled()) {
            this.instance.hubManager.removeAdminBypass(hubPlayer);
            player.sendMessage(Chat.colorize("&aYou have &fdisabled &aadmin bypass mode, you can no longer edit the world."));
        }
        else {
            this.instance.hubManager.addAdminBypass(hubPlayer);
            player.sendMessage(Chat.colorize("&aYou have &fenabled &aadmin bypass mode, you can now edit the world."));
        }

        return false;
    }
}
