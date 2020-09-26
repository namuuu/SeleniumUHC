package fr.namu.uhc.manager;

import com.onarandombox.MultiverseCore.api.MVWorldManager;
import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.StateUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class SetupManager {

    private MainUHC main;
    private InfoUHC info;
    MVWorldManager mv;

    public SetupManager(MainUHC main) {
        this.main = main;
        this.info = main.info;
        this.mv = main.mv;
    }

    public void setup() {
        setWorlds();

        setScoreboard();
    }

    private void setWorlds() {
        if(!Bukkit.getWorlds().contains(Bukkit.getWorld("world_uhc"))) {
            this.main.mv.addWorld("world_uhc", World.Environment.NORMAL, null, WorldType.NORMAL, Boolean.valueOf(true), null);
        } else {
            this.main.mv.deleteWorld("world_uhc");
            this.main.mv.addWorld("world_uhc", World.Environment.NORMAL, null, WorldType.NORMAL, Boolean.valueOf(true), null);
        }

        info.setState(StateUHC.LOBBY);
        info.setLobbyWorld(Bukkit.getWorld(info.getLWName()));
        info.setGameWorld(Bukkit.getWorld(info.getGWName()));
        info.setSpawnLoc(new Location(info.getLobbyWorld(), 8.5, 43, 33.5));
    }

    private void setScoreboard() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

        for(Team team : sb.getTeams())
            team.unregister();

        sb.registerNewTeam("null");
        sb.getTeam("null").setPrefix("§7");
    }
}
