package xyz.lotho.me.scoreboards;

import dev.jcsoftware.jscoreboards.JPerPlayerScoreboard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;

public class HubBoard {

    SkyHub instance;
    HubPlayer hubPlayer;

    public HubBoard(SkyHub instance, HubPlayer hubPlayer) {
        this.instance = instance;
        this.hubPlayer = hubPlayer;
    }

    public void create() {

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL);
        String formattedDate = formatter.format(LocalDate.now());

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
                "&7&oskycloud.gg",
                "&7&m----------------------"
        ));

        scoreboard.addPlayer(this.hubPlayer.getPlayer());
    }
}
