package fr.namu.uhc.manager;

import fr.namu.uhc.MainUHC;

public class TimerManager {

    Integer timer = 0;

    public TimerManager(MainUHC main) {
    }

    public void addTimer() {
        timer++;
    }

    public int getTimer() {
        return timer;
    }
}
