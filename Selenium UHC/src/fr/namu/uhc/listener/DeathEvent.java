package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.State;
import fr.namu.uhc.enums.StateUHC;
import fr.namu.uhc.enums.TeamUHC;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathEvent implements Listener {

    private MainUHC main;

    public DeathEvent(MainUHC main) {
        this.main = main;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        event.setDeathMessage(null);

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

        if(this.main.info.getState().equals(StateUHC.END)) {
            this.main.points.addPlacementScore(player.getKiller());
        }
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
