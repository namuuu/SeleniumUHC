package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.StateUHC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatEvent implements Listener {

    private MainUHC main;

    public ChatEvent(MainUHC main) {
        this.main = main;
    }

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
        String playername = player.getName();
        String message = event.getMessage();

        if(this.main.info.getState().equals(StateUHC.LOBBY)) {
            event.setFormat("§e" + playername + " §7» §f" + message);
            return;
        }

        if(puhc.getTeam() != null) {
            event.setFormat("§7[" + puhc.getTeam().getTeamName() + "§7] §e" + playername + " §7» §f" + event.getMessage());
        } else {
            event.setFormat("§7[SPEC] §e" + playername + " §7» §f" + event.getMessage());
        }
    }
}
