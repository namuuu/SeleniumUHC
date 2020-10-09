package fr.namu.uhc;

import fr.namu.uhc.enums.State;
import fr.namu.uhc.enums.TeamUHC;

public class PlayerUHC {

    private TeamUHC team = null;

    private int kills = 0;

    private State state;


    public PlayerUHC() {

    }


    public TeamUHC getTeam() {
        return team;
    }
    public void setTeam(TeamUHC team) {
        this.team = team;
    }

    public int getKills() {
        return kills;
    }
    public void setKills(int kills) {
        this.kills = kills;
    }
    public void addKill() {
        this.kills++;
    }

    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
}
