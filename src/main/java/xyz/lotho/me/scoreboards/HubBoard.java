package xyz.lotho.me.scoreboards;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import xyz.lotho.me.SkyHub;
import xyz.lotho.me.managers.HubPlayer;
import xyz.lotho.me.utils.Chat;

public class HubBoard {

    SkyHub instance;
    HubPlayer hubPlayer;

    public HubBoard(SkyHub instance, HubPlayer hubPlayer) {
        this.instance = instance;
        this.hubPlayer = hubPlayer;
    }

    private void handleTeamValidation(Team team, String line) {
        if (line.length() <= 16) {
            team.setPrefix(Chat.colorize(line));
            return;
        }

        if (line.length() > 32) {
            line = line.substring(0, 32);
        }

        team.setPrefix(Chat.colorize(line.substring(0, 16)));
        team.setSuffix(Chat.colorize(line.substring(16)));
    }

    public Scoreboard create() {
        Scoreboard scoreboard = this.instance.getServer().getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("SkyCloud", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score topBar = objective.getScore(Chat.colorize("&7&m-------------------"));
        topBar.setScore(15);

        Team player = scoreboard.registerNewTeam("player");
        player.addEntry(ChatColor.RED + "");
        this.handleTeamValidation(player, "&d&l" + this.hubPlayer.getPlayer().getDisplayName());
        objective.getScore(ChatColor.RED + "").setScore(14);

        Team rank = scoreboard.registerNewTeam("rank");
        rank.addEntry(ChatColor.BLUE + "");
        this.handleTeamValidation(rank, "&fRank: &cAdmin");
        objective.getScore(ChatColor.BLUE + "").setScore(13);

        Score spacer = objective.getScore(Chat.colorize(" "));
        spacer.setScore(11);

        Score serversBar = objective.getScore(Chat.colorize("&d&lRealms"));
        serversBar.setScore(10);


        this.instance.getServer().getScheduler().runTaskLaterAsynchronously(this.instance, () -> {
            Team networkCount = scoreboard.registerNewTeam("networkCount");
            networkCount.addEntry(ChatColor.GOLD + "");
            this.handleTeamValidation(networkCount, "&fGlobal: &a" + this.instance.hubManager.data.get("networkCount"));
            objective.getScore(ChatColor.GOLD + "").setScore(9);
        }, 20);

        Score bottomBar = objective.getScore(Chat.colorize("&7&m------------------- "));
        bottomBar.setScore(0);

        return scoreboard;
    }

    public void update() {

    }
}
