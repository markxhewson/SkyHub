package xyz.lotho.me.utils;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Chat {
    public static String colorize(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendActionText(Player player, String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(colorize(message)), (byte)2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
