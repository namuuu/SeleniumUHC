package fr.namu.uhc;

import fr.namu.uhc.enums.StateUHC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public class InfoUHC {

    private StateUHC state;

    private World gameWorld;
    private World lobbyWorld;
    private Location spawnLoc;
    private final String GWName = "world_uhc";
    private final String LWName = "world";

    private UUID host = null;

    private int playerSize = 0;
    private int teamLeft = 99;
    private final int teamMax = 2;

    public static String prefix = "§9UHC §7» ";

    public InfoUHC(MainUHC main) {
    }


    public StateUHC getState() {
        return state;
    }
    public void setState(StateUHC state) {
        this.state = state;
    }

    public World getGameWorld() {
        return gameWorld;
    }
    public void setGameWorld(World gameWorld) {
        this.gameWorld = gameWorld;
    }

    public World getLobbyWorld() {
        return lobbyWorld;
    }
    public void setLobbyWorld(World lobbyWorld) {
        this.lobbyWorld = lobbyWorld;
    }

    public Location getSpawnLoc() {
        return spawnLoc;
    }
    public void setSpawnLoc(Location spawnLoc) {
        this.spawnLoc = spawnLoc;
    }

    public Player getHost() {
        if(host != null) {
            return Bukkit.getPlayer(host);
        }
        return null;
    }
    public void setHost(Player host) {
        if(this.host == null) {
            this.host = host.getUniqueId();
            return;
        }
        if(host == null) {
            this.host = null;
            return;
        }
    }

    public int getPlayerSize() {
        return playerSize;
    }
    public void setPlayerSize(int playerSize) {
        this.playerSize = playerSize;
    }
    public void addPlayerSize() {
        this.playerSize++;
    }
    public void decPlayerSize() {
        this.playerSize--;
    }

    public String getGWName() {
        return GWName;
    }
    public String getLWName() {
        return LWName;
    }

    public int getTeamLeft() {
        return teamLeft;
    }
    public void setTeamLeft(int teamLeft) {
        this.teamLeft = teamLeft;
    }

    public int getTeamMax() {
        return teamMax;
    }
}
