package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.StateUHC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractEvent implements Listener {

    private MainUHC main;

    public InteractEvent(MainUHC main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(this.main.info.getState().equals(StateUHC.LOBBY) && event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
            return;
        }
        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            return;
        }

        ItemStack current = event.getItem();
        Player player = event.getPlayer();

        if(current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName()) {
            return;
        }

        switch (current.getItemMeta().getDisplayName()) {
            case "§eListe des équipes":
                event.setCancelled(true);
                this.main.menu.teamList.open(player);
                break;
            case "§eMenu du Host":
                event.setCancelled(true);
                break;
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            event.setCancelled(true);
        }
    }
}
