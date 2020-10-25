package fr.namu.uhc.manager;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.*;
import fr.namu.uhc.runnable.GameRun;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StartManager {

    private MainUHC main;

    public StartManager(MainUHC main) {
        this.main = main;
    }

    public void start() {


        if(!verifySettings())
            return;


        setBorder();
        this.main.info.setState(StateUHC.TP);
        teleportPlayers();
        setSpectators();
        this.main.team.recalculateTeamsLeft();

        this.main.info.setState(StateUHC.GAME);
        this.main.score.updateBoard();

        GameRun startGame = new GameRun(this.main);
        startGame.runTaskTimer(this.main, 0L, 20L);

        new BukkitRunnable() {
            @Override
            public void run() {
                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.setHealth(player.getMaxHealth());
                    player.setFoodLevel(20);
                    player.sendMessage("§aLe Final Heal a été effectuée !");
                }
            }
        }.runTaskLater(this.main, TimerUHC.FINAL_HEAL.getValue() * 20);
    }

    private Boolean verifySettings() {
        if(this.main.team.teams.size() == 0) {
            return false;
        }
        return true;
    }

    private void teleportPlayers() {
        Integer ind = 0;
        for (TeamUHC team : this.main.team.teams.values()) {
            World world = this.main.info.getGameWorld();
            WorldBorder wb = world.getWorldBorder();
            double a = ind * 2.0D * Math.PI / this.main.team.teams.size();
            int x = (int) Math.round(wb.getSize() / 3.0D * Math.cos(a) + world.getSpawnLocation().getX());
            int z = (int) Math.round(wb.getSize() / 3.0D * Math.sin(a) + world.getSpawnLocation().getZ());
            Location loc = new Location(world, x, (world.getHighestBlockYAt(x, z) + 100), z);
            for (Integer i = 0; i < team.getPlayers().size(); i++) {
                Player player = team.getPlayers().get(i);
                this.main.GameStuff.set(player);
                player.teleport(loc);
                System.out.println(loc);
            }
            System.out.println("ind augmenté : " + ind);
            ind++;
        }
    }

    private void setSpectators() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if(player.getWorld().equals(this.main.info.getLobbyWorld())) {
                Bukkit.getScheduler().runTaskLater(this.main, new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                }, 3L);
                player.setGameMode(GameMode.SPECTATOR);
                this.main.info.decPlayerSize();
                player.teleport(new Location(this.main.info.getGameWorld(), 0, 90, 0));
                PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
                puhc.setState(State.SPEC);
            }
        }
    }

    private void setBorder() {
        World world = this.main.info.getGameWorld();
        world.setSpawnLocation(0, world.getHighestBlockYAt(0, 0), 0);
        world.setPVP(false);
        world.setWeatherDuration(0);
        world.setThunderDuration(0);
        world.setTime(1000L);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRuleValue("doDayLightCycle", "true");
        world.setTime(0L);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(world.getSpawnLocation().getX(), world.getSpawnLocation().getZ());
        worldBorder.setSize(BorderUHC.BIG_BORDER.getValue());
        worldBorder.setWarningDistance((int)(worldBorder.getSize() / 7.0D));
    }
}
