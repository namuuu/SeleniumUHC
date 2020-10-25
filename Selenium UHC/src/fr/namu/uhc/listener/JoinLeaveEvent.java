package fr.namu.uhc.listener;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.*;
import fr.namu.uhc.scoreboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
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
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        event.setQuitMessage("§c- §7» §e"+ event.getPlayer().getName());

        this.main.info.setPlayerSize(this.main.info.getPlayerSize() - 1);
        this.main.boards.remove(player);

        if(this.main.info.getHost() != null && this.main.info.getHost().equals(player)) {
            this.main.info.setHost(null);
        }

        if(player.getGameMode().equals(GameMode.SPECTATOR)) {
            return;
        }

        if(this.main.info.getState().equals(StateUHC.LOBBY) || this.main.info.getState().equals(StateUHC.END)) {
            if(puhc.getTeam() != null) {
                this.main.team.leaveTeam(player);
                this.main.team.disbandTeam(player);
            }

        } else {
            if(this.main.timer.getTimer() >= TimerUHC.PVP.getValue()) {
                World world = this.main.info.getGameWorld();

                for(ItemStack item : player.getInventory().getContents()) {
                    if(item != null && item.getType() != Material.AIR) {
                        world.dropItemNaturally(player.getLocation(), item);
                    }
                }
                for(ItemStack item : player.getInventory().getArmorContents()) {
                    if(item != null && item.getType() != Material.AIR) {
                        world.dropItemNaturally(player.getLocation(), item);
                    }
                }

                player.getInventory().clear();
                player.setHealth(player.getMaxHealth());
                player.setGameMode(GameMode.SPECTATOR);
                calculPlacement(player, puhc.getTeam());
                puhc.setState(State.DEAD);
                this.main.info.getGameWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);

                if(player.getKiller() == null) {
                    Bukkit.broadcastMessage("§7[" + puhc.getTeam().getTeamName() + "§7] §6" + player.getName() + "§e est mort.");
                } else {
                    Player killer = player.getKiller();
                    PlayerUHC kuhc = this.main.puhc.get(killer.getUniqueId());

                    kuhc.addKill();
                    this.main.points.addKillScore(killer);
                    Bukkit.broadcastMessage("§7[" + puhc.getTeam().getTeamName() + "§7] §6" + player.getName() + "§e s'est fait tuer par " + "§7[" + kuhc.getTeam().getTeamName() + "§7] §6" + killer.getName());
                }

                this.main.team.recalculateTeamsLeft();
                this.main.win.verifyWin();
            } else {
                Bukkit.broadcastMessage(InfoUHC.prefix + "§e" + player.getName() + " §7possède jusqu'à l'activation du PVP pour se reconnecter. Autrement, il mourra.");
            }
        }

        this.main.score.updateBoard();
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

        if(!this.main.puhc.containsKey(player.getUniqueId())) {
            this.main.puhc.put(player.getUniqueId(), new PlayerUHC());
        } else {
            if(this.main.timer.getTimer() >= TimerUHC.PVP.getValue()) {
                PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
                if(puhc.getState().equals(State.ALIVE)) {
                    puhc.setState(State.DEAD);
                } else {
                    puhc.setState(State.SPEC);
                }
            }
        }

        this.main.boards.put(player.getUniqueId(), new FastBoard(player));
        this.main.info.addPlayerSize();

        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.teleport(this.main.info.getSpawnLoc());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            this.main.LobbyStuff.give(player);
            for (PotionEffect po : player.getActivePotionEffects())
                player.removePotionEffect(po.getType());

            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 0, false, false));
            this.main.team.newPlayer(player);

            puhc.setState(State.ALIVE);

            Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                @Override
                public void run(){
                    player.setGameMode(GameMode.ADVENTURE);
                }
            }, 3L);
        } else {
            if(puhc.getState().equals(State.DEAD)) {
                World world = this.main.info.getGameWorld();

                for(ItemStack item : player.getInventory().getContents()) {
                    if(item != null && item.getType() != Material.AIR) {
                        world.dropItemNaturally(player.getLocation(), item);
                    }
                }
                for(ItemStack item : player.getInventory().getArmorContents()) {
                    if(item != null && item.getType() != Material.AIR) {
                        world.dropItemNaturally(player.getLocation(), item);
                    }
                }

                player.getInventory().clear();
                player.setHealth(player.getMaxHealth());
                Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                    @Override
                    public void run(){
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                }, 3L);
                puhc.setState(State.DEAD);
                this.main.info.getGameWorld().spawnEntity(player.getLocation(), EntityType.EXPERIENCE_ORB);

                Bukkit.broadcastMessage("§7[" + puhc.getTeam().getTeamName() + "§7] §6" + player.getName() + "§e est mort.");

                this.main.team.recalculateTeamsLeft();
                this.main.win.verifyWin();

                if(this.main.info.getState().equals(StateUHC.END)) {
                    this.main.points.addPlacementScore(player.getKiller());
                }
            } else if (puhc.getState().equals(State.SPEC)) {
                Bukkit.getScheduler().runTaskLater(this.main, new Runnable(){
                    @Override
                    public void run(){
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                }, 3L);
            }
        }

        this.main.score.updateBoard();
    }

    private void calculPlacement(Player player, TeamUHC team) {
        Integer ind = 0;

        for (Player teamates : team.getPlayers()) {
            PlayerUHC tuhc = this.main.puhc.get(player.getUniqueId());
            if(tuhc.getState().equals(State.ALIVE))
                ind++;
        }

        if(ind == 1)
            this.main.points.addPlacementScore(player);
    }
}
