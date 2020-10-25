package fr.namu.uhc.runnable;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.BorderUHC;
import fr.namu.uhc.enums.TimerUHC;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;
import org.bukkit.scheduler.BukkitRunnable;

public class GameRun extends BukkitRunnable {

    private MainUHC main;

    public GameRun(MainUHC main) {
        this.main = main;
    }

    @Override
    public void run() {
        this.main.timer.addTimer();
        this.main.score.updateBoard();
        this.main.points.refreshPoints();

        if(this.main.timer.getTimer() == TimerUHC.PVP.getValue()) {
            this.main.timer.enablePVP();
        }

        if (this.main.timer.getTimer() >= TimerUHC.BORDER_SHRINK.getValue()) {
            WorldBorder wb = this.main.info.getGameWorld().getWorldBorder();
            if (wb.getSize() == BorderUHC.BIG_BORDER.getValue()) {
                Bukkit.broadcastMessage(InfoUHC.prefix + "§eLa §6Bordure §ecommence à se rétrécir, dirigez-vous vers le centre de la Map !");
            }
            if (wb.getSize() > BorderUHC.SMALL_BORDER.getValue()) {
                wb.setSize(wb.getSize() - 3D);
                wb.setWarningDistance((int)(wb.getSize() / 7.0D));
            }
        }

        if(this.main.timer.getTimer() % 10 == 0) {
            if(this.main.score.switchBoard == 1) {
                this.main.score.switchBoard = 0;
            } else {
                this.main.score.switchBoard = 1;
            }
        }
    }


}
