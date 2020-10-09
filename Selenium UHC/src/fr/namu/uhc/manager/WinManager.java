package fr.namu.uhc.manager;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.StateUHC;
import fr.namu.uhc.enums.TeamUHC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class WinManager {

    private MainUHC main;

    public WinManager(MainUHC main) {
        this.main = main;
    }

    public void verifyWin() {
        TeamUHC team = this.main.team.recalculateTeamsLeft();

        if(team != null) {
            win(team);
        }

    }

    private void win(TeamUHC team) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(this.main.info.getSpawnLoc());
        }
        ItemBuilder.spawnFireWork(this.main.info.getSpawnLoc());

        this.main.info.setState(StateUHC.END);
        displayKills();
        this.main.score.updateBoard();
        Bukkit.broadcastMessage(InfoUHC.prefix + "§eL'équipe " + team.getTeamName() + " §eremportent cette partie d'§6UHC §Eavec un succès foudroyant ! Félicitations à eux !");


        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.shutdown();
            }
        }.runTaskLater(this.main, 20*10);
    }

    private void displayKills() {
        List<UUID> uuids = new ArrayList<>();
        for(Player player : Bukkit.getOnlinePlayers()) {
            uuids.add(player.getUniqueId());
        }
        for(UUID uuid : this.main.puhc.keySet()) {
            if(!uuids.contains(uuid))
                this.main.puhc.remove(uuid);
        }

        Bukkit.broadcastMessage(" ");
        for (Map.Entry<String, Integer> entry : getTop().entrySet()) {
            Player player = Bukkit.getPlayer(entry.getKey());
            PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
            Bukkit.broadcastMessage(ColorManager.toChatColor(puhc.getTeam().getColor()) + puhc.getTeam().getTeamName() + "§7 | §6" + player.getName() + "§7 » §6" + puhc.getKills() + "§e kills");
        }
        Bukkit.broadcastMessage(" ");
    }

    private Map<String, Integer> getTop() {
        Map<String, Integer> stats = new HashMap<>();
        this.main.puhc.keySet().forEach(UUID -> stats.put(Bukkit.getPlayer(UUID).getName(), this.main.puhc.get(UUID).getKills()));

        return stats.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
