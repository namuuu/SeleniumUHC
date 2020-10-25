package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandEvent implements Listener {

    private MainUHC main;

    public CommandEvent(MainUHC main) {
        this.main = main;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String cmd = event.getMessage();

        if(cmd.contains("/tell") || cmd.contains("/me")) {
            event.setCancelled(true);
            return;
        }

        if(cmd.contains("/msg")) {

        }
    }
}
