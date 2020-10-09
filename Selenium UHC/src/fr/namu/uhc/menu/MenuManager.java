package fr.namu.uhc.menu;

import fr.namu.uhc.MainUHC;
import org.bukkit.scoreboard.Team;

public class MenuManager {

    public final TeamListMenu teamList = new TeamListMenu(this);
    public final HostMainMenu hostMain = new HostMainMenu(this);
    public final TimerMenu timer = new TimerMenu(this);
    public final ScenarioMenu scenario = new ScenarioMenu(this);
    public final BorderMenu border = new BorderMenu(this);

    public MainUHC main;


    public MenuManager(MainUHC main) {
        this.main = main;
    }
}
