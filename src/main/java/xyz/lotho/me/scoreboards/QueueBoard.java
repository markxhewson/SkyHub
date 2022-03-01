package xyz.lotho.me.scoreboards;

import dev.jcsoftware.jscoreboards.JPerPlayerScoreboard;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.HashMap;

public class QueueBoard {

    SkyHub instance;
    HubPlayer hubPlayer;

    public QueueBoard(SkyHub instance, HubPlayer hubPlayer) {
        this.instance = instance;
        this.hubPlayer = hubPlayer;
    }

    public void create() {
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        String formattedDate = formatter.format(LocalDate.now());

        HashMap<String, String> queueData = this.hubPlayer.getQueueData();

        JPerPlayerScoreboard scoreboard = new JPerPlayerScoreboard((player) -> "&c&lSkyCloud &e2.0",
                (player) -> Arrays.asList(
                        "&7&m----------------------",
                        "&7&o" + formattedDate,
                        "",
                        "&d&lRealms &7(0 globally)",
                        "  &6&lOrbit &f[0/400]",
                        "  &c&lAtlas &f[0/400]",
                        "  &5&lMystery &f[0/400]",
                        "",
                        "&d&lQueue &7(/leavequeue)",
                        "  &fRealm: &e" + queueData.get("serverName"),
                        "  &fPosition: &b" + ((Integer.parseInt(queueData.get("position"))) + 1) + "&7/&b" + Integer.valueOf(queueData.get("total")),
                        "",
                        "&7&oskycloud.gg",
                        "&7&m----------------------"
                ));

        scoreboard.addPlayer(this.hubPlayer.getPlayer());
    }

}
