package fr.namu.uhc.manager;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.TimerUHC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerManager {

    private MainUHC main;

    Integer timer = 0;

    public TimerManager(MainUHC main) {
        this.main = main;
    }

    public void addTimer() {
        timer++;
    }

    public int getTimer() {
        return timer;
    }

    public void startTimers() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.broadcastMessage(InfoUHC.prefix + "§eVotre période d'invicibilité est maintenant terminée !");
            }
        }.runTaskLater(this.main, 30*20);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player: Bukkit.getOnlinePlayers()) {
                    player.setHealth(player.getMaxHealth());
                    player.setFoodLevel(20);
                    player.setSaturation(20);
                    player.sendMessage(InfoUHC.prefix + "§aVous venez de recevoir le Final Heal !");
                }
            }
        }.runTaskLater(this.main, TimerUHC.FINAL_HEAL.getValue()*20);
    }

    public void enablePVP() {
        World world = this.main.info.getGameWorld();
        if(world.getPVP())
            return;
        world.setPVP(true);
        Bukkit.broadcastMessage(InfoUHC.prefix + "§cLe PVP est désormais activé, un combattant pourrait surgir à tout moment !");
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player.getLocation(), Sound.WOLF_GROWL, 1.0F, 1.0F);
        }
    }


    public String getTextTimer() {
        return conversion(timer);
    }

    public String conversion(int timer) {
        String valeur;
        if (timer % 60 > 9) {
            valeur = String.valueOf(timer % 60) + "s";
        } else {
            valeur = "0" + (timer % 60) + "s";
        }
        if (timer / 3600 > 0) {
            if (timer % 3600 / 60 > 9) {
                valeur = String.valueOf(timer / 3600) + "h" + (timer % 3600 / 60) + "m" + valeur;
            } else {
                valeur = String.valueOf(timer / 3600) + "h0" + (timer % 3600 / 60) + "m" + valeur;
            }
        } else if (timer / 60 > 0) {
            valeur = String.valueOf(timer / 60) + "m" + valeur;
        }
        return valeur;
    }
}
