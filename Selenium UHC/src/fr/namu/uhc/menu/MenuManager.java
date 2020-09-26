package fr.namu.uhc.menu;

import fr.namu.uhc.MainUHC;
import org.bukkit.scoreboard.Team;

public class MenuManager {

    public final TeamListMenu teamList = new TeamListMenu(this);

    public MainUHC main;


    public MenuManager(MainUHC main) {
        this.main = main;
    }
}
