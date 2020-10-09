package fr.namu.uhc.manager;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.State;
import fr.namu.uhc.enums.StateUHC;
import fr.namu.uhc.enums.TeamUHC;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TeamManager {

    private MainUHC main;

    public TeamManager(MainUHC main) {
        this.main = main;
    }

    public HashMap<UUID, TeamUHC> teams = new HashMap<>();

    public void newPlayer(Player player) {
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("null").addEntry(player.getName());
        player.setPlayerListName("§7" + player.getName());
    }

    public void newTeam(Player player, String teamName) {
        if(teamName.length() > 8) {
            player.sendMessage(InfoUHC.prefix + "§cTon équipe ne doit pas comporter plus de 8 caractères !");
            return;
        }
        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage("§eLa partie a déjà commencé !");
            return;
        }

        TeamUHC team = new TeamUHC(teamName, this.main.color.toChatColor(DyeColor.WHITE) + teamName + " §7| ", DyeColor.WHITE, player, new ArrayList<>());
        teams.put(team.getCaptain().getUniqueId(), team);

        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        sb.registerNewTeam(team.getCaptain().getName());

        team.setSBTeam(sb.getTeam(team.getCaptain().getName()));
        team.getSBTeam().setPrefix(team.getPrefix());

        this.main.LobbyStuff.give(player);

        joinTeam(team.getCaptain(), team);
    }

    public void joinTeam(Player player, TeamUHC team) {
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage("§eLa partie a déjà commencé !");
            return;
        }
        if(puhc.getTeam() != null) {
            player.sendMessage(InfoUHC.prefix + "§cTu possèdes déjà une équipe !");
            return;
        }
        if(team.getPlayers().size() == this.main.info.getTeamMax()) {
            player.sendMessage(InfoUHC.prefix + "§eCette équipe est pleine !");
        }
        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("null").removeEntry(player.getName());

        player.sendMessage(InfoUHC.prefix + "§7Tu viens de rejoindre l'équipe §e" + team.getTeamName());

        player.setPlayerListName("§7[" + this.main.color.toChatColor(team.getColor()) + team.getTeamName() + "§7] §e" + player.getName());
        puhc.setTeam(team);
        puhc.getTeam().addPlayer(player);
        team.getSBTeam().addEntry(player.getName());

        this.main.LobbyStuff.give(player);
    }

    public void leaveTeam(Player player) {
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage("§eLa partie a déjà commencé !");
            return;
        }
        if(puhc.getTeam() == null) {
            player.sendMessage(InfoUHC.prefix + "§cTu ne possèdes aucun équipe !");
            return;
        }
        if(puhc.getTeam().getCaptain() == player) {
            player.sendMessage(InfoUHC.prefix + "§cTu es la capitaine de cette équipe ! N'abandonne pas tes moussaillons !");
            return;
        }

        player.setPlayerListName("§7" + player.getName());
        puhc.getTeam().getSBTeam().removeEntry(player.getName());
        puhc.getTeam().remPlayer(player);
        puhc.setTeam(null);

        Bukkit.getScoreboardManager().getMainScoreboard().getTeam("null").addEntry(player.getName());
    }

    public void changeTeamPrefix(Player player, String prefix) {
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage("§eLa partie a déjà commencé !");
            return;
        }
        if(puhc.getTeam() == null) {
            player.sendMessage(InfoUHC.prefix + "§cTu ne possèdes aucun équipe !");
            return;
        }
        if(puhc.getTeam().getCaptain() != player) {
            player.sendMessage(InfoUHC.prefix + "§cSeul le capitaine a le droit de réaliser cette action !");
            return;
        }
        if(prefix.length() > 8) {
            player.sendMessage(InfoUHC.prefix + "§cTon prefix ne doit pas dépasser les 8 caractères !");
            return;
        }

        puhc.getTeam().setPrefix(this.main.color.toChatColor(puhc.getTeam().getColor()) + prefix + " §7| ");
        puhc.getTeam().getSBTeam().setPrefix(puhc.getTeam().getPrefix());

        for(Player players : puhc.getTeam().getPlayers()) {
            puhc.getTeam().getSBTeam().removeEntry(players.getName());
            puhc.getTeam().getSBTeam().addEntry(players.getName());
        }

        player.sendMessage(InfoUHC.prefix + "§aTon prefix d'équipe est dorénavant §e" + prefix);
    }

    public void changeTeamName(Player player, String teamName) {
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage("§eLa partie a déjà commencé !");
            return;
        }
        if(puhc.getTeam() == null) {
            player.sendMessage(InfoUHC.prefix + "§cTu ne possèdes aucun équipe !");
            return;
        }
        if(puhc.getTeam().getCaptain() != player) {
            player.sendMessage(InfoUHC.prefix + "§cSeul le capitaine a le droit de réaliser cette action !");
            return;
        }
        if(teamName.length() > 8) {
            player.sendMessage(InfoUHC.prefix + "§cTon nom d'équipe ne doit pas dépasser les 8 caractères !");
            return;
        }

        TeamUHC team = puhc.getTeam();
        puhc.getTeam().setPrefix(this.main.color.toChatColor(puhc.getTeam().getColor()) + teamName + " §7| ");
        puhc.getTeam().getSBTeam().setPrefix(puhc.getTeam().getPrefix());
        puhc.getTeam().setTeamName(teamName);

        for(Player players : puhc.getTeam().getPlayers()) {
            players.setPlayerListName("§7[" + this.main.color.toChatColor(team.getColor()) + team.getTeamName() + "§7] §e" + players.getName());
            puhc.getTeam().getSBTeam().removeEntry(players.getName());
            puhc.getTeam().getSBTeam().addEntry(players.getName());
        }

        player.sendMessage(InfoUHC.prefix + "§aTu viens de renommer ton équipe en §e" + teamName + " §a!");
    }

    public void disbandTeam(Player player) {
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage("§eLa partie a déjà commencé !");
            return;
        }
        if(puhc.getTeam() == null) {
            player.sendMessage(InfoUHC.prefix + "§cTu ne possèdes aucun équipe !");
            return;
        }
        if(puhc.getTeam().getCaptain() != player) {
            player.sendMessage(InfoUHC.prefix + "§cSeul le capitaine a le droit de réaliser cette action !");
            return;
        }

        TeamUHC team = puhc.getTeam();

        for(Player players : team.getPlayers()) {
            PlayerUHC puhcs = this.main.puhc.get(players.getUniqueId());
            players.sendMessage(InfoUHC.prefix + "§eTon équipe vient d'être dissoute !");
            players.setPlayerListName("§7" + players.getName());
            puhcs.getTeam().getSBTeam().removeEntry(players.getName());
            puhcs.setTeam(null);
            Bukkit.getScoreboardManager().getMainScoreboard().getTeam("null").addEntry(players.getName());
        }

        team.getPlayers().clear();

        teams.remove(team.getCaptain().getUniqueId());
    }

    public void changeColor(Player player, String color) {
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
        DyeColor dyecolor = this.main.color.chatToDyeColor(color);

        if(dyecolor == null) {
            player.sendMessage(InfoUHC.prefix + "§eNous ne sommes pas parvenus à trouver ta couleur ! Réessaie avec une autre !");
            return;
        }
        if(puhc.getTeam() == null) {
            player.sendMessage(InfoUHC.prefix + "§cTu ne possèdes aucun équipe !");
            return;
        }
        if(puhc.getTeam().getCaptain() != player) {
            player.sendMessage(InfoUHC.prefix + "§cSeul le capitaine a le droit de réaliser cette action !");
            return;
        }

        TeamUHC team = puhc.getTeam();
        String teamName = team.getTeamName();
        team.setColor(dyecolor);
        team.setPrefix(this.main.color.toChatColor(team.getColor()) + teamName + " §7| ");
        team.getSBTeam().setPrefix(team.getPrefix());
        team.setTeamName(teamName);

        for(Player players : team.getPlayers()) {
            players.setPlayerListName("§7[" + this.main.color.toChatColor(team.getColor()) + team.getTeamName() + "§7] §e" + players.getName());
            team.getSBTeam().removeEntry(players.getName());
            team.getSBTeam().addEntry(players.getName());
            this.main.LobbyStuff.give(players);
        }

        player.sendMessage(InfoUHC.prefix + "§eTu viens de changer la couleur de ton équipe à " + color);
    }

    public TeamUHC recalculateTeamsLeft() {

        List<TeamUHC> teamLeft = new ArrayList<>();

        for(TeamUHC team : teams.values()) {
            for(Player player : team.getPlayers()) {
                PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
                if(!teamLeft.contains(team) && puhc.getState().equals(State.ALIVE)) {
                    teamLeft.add(team);
                }
            }
        }

        this.main.info.setTeamLeft(teamLeft.size());

        if(teamLeft.size() == 1) {
            return teamLeft.get(0);
        }
        return null;
    }
}
