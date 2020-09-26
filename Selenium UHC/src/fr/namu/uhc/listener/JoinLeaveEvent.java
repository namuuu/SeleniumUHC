package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.StateUHC;
import fr.namu.uhc.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class JoinLeaveEvent implements Listener {

    private MainUHC main;

    public JoinLeaveEvent(MainUHC main) {
        this.main = main;
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("§a+ §7» §e"+ event.getPlayer().getName());

        newPlayer(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage("§c- §7» §e"+ event.getPlayer().getName());

        this.main.info.setPlayerSize(this.main.info.getPlayerSize() - 1);
    }

    public void resetPlayers() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            newPlayer(player);
        }
    }


    private void newPlayer(Player player) {

        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(0);

        this.main.puhc.put(player.getUniqueId(), new PlayerUHC());
        this.main.boards.put(player.getUniqueId(), new FastBoard(player));
        this.main.info.addPlayerSize();

        if(this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.teleport(this.main.info.getSpawnLoc());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            this.main.LobbyStuff.give(player);
            for (PotionEffect po : player.getActivePotionEffects())
                player.removePotionEffect(po.getType());

            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
            this.main.team.newPlayer(player);

            Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                @Override
                public void run(){
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }, 3L);
        }

        this.main.score.updateBoard();
    }
}
