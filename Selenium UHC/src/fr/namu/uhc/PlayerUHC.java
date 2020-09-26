package fr.namu.uhc;

import fr.namu.uhc.enums.TeamUHC;

public class PlayerUHC {

    private TeamUHC team = null;



    public PlayerUHC() {

    }


    public TeamUHC getTeam() {
        return team;
    }
    public void setTeam(TeamUHC team) {
        this.team = team;
    }
}
