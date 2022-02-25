package xyz.lotho.me.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.utils.Chat;

public class SetVoidLimit implements CommandExecutor {

    SkyHub instance;

    public SetVoidLimit(SkyHub instance) {
        this.instance = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (!player.hasPermission(this.instance.config.getString("utils.permission"))) {
            player.sendMessage(Chat.colorize(this.instance.config.getString("utils.noPerm")));
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(Chat.colorize("&cUsage: /setvoidlimit <Y: int>"));
            return false;
        }

        try {
            int y = Integer.parseInt(args[0]);
            this.instance.config.set("voidLimit", y);
            this.instance.configManager.saveConfig();
            player.sendMessage(Chat.colorize("&aYou have successfully set &f" + y + " &aas the new void limit."));
            return true;
        } catch (NumberFormatException exception) {
            player.sendMessage(Chat.colorize("&cUsage: /setvoidlimit <Y: int>"));
            return false;
        }
    }
}
