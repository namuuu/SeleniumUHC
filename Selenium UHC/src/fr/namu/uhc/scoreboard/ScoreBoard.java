package fr.namu.uhc.scoreboard;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.StateUHC;
import fr.namu.uhc.enums.TeamUHC;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreBoard {

    private MainUHC main;

    private String title = "§7• §9SELENIUM UHC §7•";

    public int switchBoard = 0;


    public ScoreBoard(MainUHC main) {
        this.main = main;
    }

    public void updateBoard() {

        for(FastBoard board : this.main.boards.values()) {
            if(this.main.info.getState().equals(StateUHC.LOBBY)) {
                LobbyBoard(board);
            } else if (this.main.info.getState().equals(StateUHC.GAME)) {
                GameBoard(board);
            }
        }
    }

    private void LobbyBoard(FastBoard board) {
        String[] score = {
                "§7§m----------------------",
                "Joueurs: §9" + this.main.info.getPlayerSize(),
                "§fHost: §9" + "Libre",
                " ",
                "§7play.selenium-pvp.com",
                "§7§m----------------------"
        };

        if(this.main.info.getHost() != null) {
            score[2] = "§fHost: §9" + this.main.info.getHost().getName();
        }


        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    private void GameBoard(FastBoard board) {
        String[] score = {
                "§7§m----------------------",
                "Timer: §9" + this.main.timer.getTextTimer(),
                "",
                "Joueurs: §9" + this.main.info.getPlayerSize(),
                "Teams restantes: §9" + this.main.info.getTeamLeft(),
                " ",
                "§7play.selenium-pvp.com",
                "§7§m----------------------"
        };


        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    private void ScoresBoard(FastBoard board) {
        String[] score = {
                "§7§m----------------------",
                "",
                "",
                "",
                "",
                "",
                "",
                "§7§m----------------------"
        };

        Map<TeamUHC, Integer> top = getTop();

        Integer ind = 1;
        for(TeamUHC team : top.keySet()) {
            score[1] = team.getTeamName() + " §7: §b" + top.get(team);
            ind++;
            if(ind == 6) {
                break;
            }
        }

        for (int i = 0; i < score.length; i++) {
            StringBuilder sb = new StringBuilder();
            sb.append(score[i]);
            if (sb.length() > 30)
                sb.delete(29, sb.length() - 1);
            score[i] = sb.toString();
        }

        board.updateTitle(title);
        board.updateLines(score);
    }

    private Map<TeamUHC, Integer> getTop() {
        Map<TeamUHC, Integer> stats = new HashMap<>();

        for(TeamUHC team : this.main.points.scores.keySet()) {
            stats.put(team, this.main.points.scores.get(team));
        }

        return stats.entrySet()
                .stream()
                .sorted((Map.Entry.<TeamUHC, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
