package fr.namu.uhc.enums;

import org.bukkit.DyeColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class TeamUHC {

    private String teamName;

    private String prefix;

    private DyeColor color;

    private List<Player> players;

    private final Player captain;

    private Team team;

    public TeamUHC(String teamName, String prefix, DyeColor color, Player captain, List<Player> players) {
        this.teamName = teamName;
        this.prefix = prefix;
        this.color = color;
        this.captain = captain;
        this.players = players;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
    public void remPlayer(Player player) {
        players.remove(player);
    }
    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getCaptain() {
        return captain;
    }

    public Team getSBTeam() {
        return team;
    }
    public void setSBTeam(Team team) {
        this.team = team;
    }

    public String getPrefix() {
        return prefix;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public DyeColor getColor() {
        return color;
    }
    public void setColor(DyeColor color) {
        this.color = color;
    }

    public String getTeamName() {
        return teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
