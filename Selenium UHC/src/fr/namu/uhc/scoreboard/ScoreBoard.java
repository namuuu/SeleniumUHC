package fr.namu.uhc.scoreboard;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.StateUHC;

public class ScoreBoard {

    private MainUHC main;

    private String title = "§7• §9SELENIUM UHC §7•";

    public ScoreBoard(MainUHC main) {
        this.main = main;
    }

    public void updateBoard() {

        for(FastBoard board : this.main.boards.values()) {
            if(this.main.info.getState().equals(StateUHC.LOBBY)) {
                LobbyBoard(board);
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
            score[2] = "§fHost: §c" + this.main.info.getHost().getName();
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
}
