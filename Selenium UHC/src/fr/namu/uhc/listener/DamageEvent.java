package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.StateUHC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    private MainUHC main;

    public DamageEvent(MainUHC main) {
        this.main = main;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            return;
        }

        if(!this.main.info.getState().equals(StateUHC.GAME)) {
            event.setCancelled(true);
        }
    }
}
