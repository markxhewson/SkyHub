package xyz.lotho.me.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.lotho.me.SkyHub;

public class MessagingChannel implements PluginMessageListener {

    SkyHub instance;

    public MessagingChannel(SkyHub instance) {
        this.instance = instance;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        // if (!channel.equals("BungeeCord")) return;

        System.out.println("HEYYYY");

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        System.out.println(subChannel);

        if (subChannel.equals("PlayerCount")) this.instance.hubManager.data.put("networkCount", in.readInt());
    }
}
