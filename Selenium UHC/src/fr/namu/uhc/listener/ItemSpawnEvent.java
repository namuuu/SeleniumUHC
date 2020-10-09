package fr.namu.uhc.listener;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.enums.ScenarioUHC;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ItemSpawnEvent implements Listener {

    public ItemSpawnEvent(MainUHC main) {
    }

    @EventHandler
    public void onItemSpawn(org.bukkit.event.entity.ItemSpawnEvent event) {
        if(ScenarioUHC.CUT_CLEAN.getValue()) {
            ItemStack item = event.getEntity().getItemStack();
            Material mat = item.getType();
            switch (mat) {
                case COAL:
                    item.setType(Material.TORCH);
                    break;
                case IRON_ORE:
                    item.setType(Material.IRON_INGOT);
                    break;
                case GOLD_ORE:
                    item.setType(Material.GOLD_INGOT);
                    break;
                case GRAVEL:
                    item.setType(Material.FLINT);
                    break;
                case PORK:
                    item.setType(Material.GRILLED_PORK);
                    break;
                case RAW_BEEF:
                    item.setType(Material.COOKED_BEEF);
                    break;
                case RAW_CHICKEN:
                    item.setType(Material.COOKED_CHICKEN);
                    break;
                case MUTTON:
                    item.setType(Material.COOKED_MUTTON);
                    break;
                case RABBIT:
                    item.setType(Material.COOKED_RABBIT);
            }
        }
    }
}
