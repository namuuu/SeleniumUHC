package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.ScenarioUHC;
import fr.namu.uhc.manager.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class CraftItemEvent implements Listener {

    public CraftItemEvent(MainUHC main) {
    }

    @EventHandler
    public void onCraftItem(PrepareItemCraftEvent event) {

        Material mat = event.getInventory().getResult().getType();

        if(ScenarioUHC.RODLESS.getValue()) {
            if(mat.equals(Material.FISHING_ROD))
                event.getInventory().setResult(new ItemBuilder(Material.AIR, 1).toItemStack());
        }

    }
}
