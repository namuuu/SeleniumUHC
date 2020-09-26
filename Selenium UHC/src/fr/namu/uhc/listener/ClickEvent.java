package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.StateUHC;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ClickEvent implements Listener {

    private MainUHC main;

    public ClickEvent(MainUHC main) {
        this.main = main;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();
        ItemStack current = event.getCurrentItem();

        if (this.main.info.getState().equals(StateUHC.LOBBY)) {
            if (inv.getName() == "container.crafting") {
                event.setCancelled(true);
                return;
            }
        }

        if (inv.getName().contains("container")) {
            return;
        }
        if (current == null || !current.hasItemMeta() || !current.getItemMeta().hasDisplayName()) {
            return;
        }

        String invname = inv.getName();
        Material mat = current.getType();
        String currentName = current.getItemMeta().getDisplayName();
        ClickType click = event.getClick();

        switch (invname) {
            case "§7Liste des Équipes":
                event.setCancelled(true);
                return;
        }
    }
}
