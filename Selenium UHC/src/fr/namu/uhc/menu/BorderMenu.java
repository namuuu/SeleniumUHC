package fr.namu.uhc.menu;

import fr.namu.uhc.enums.BorderUHC;
import fr.namu.uhc.enums.ScenarioUHC;
import fr.namu.uhc.manager.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

import javax.swing.border.Border;

public class BorderMenu {


    public BorderMenu(MenuManager menu) {
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Configuration de la Bordure");

        int slot = 1;
        int line = 1;

        inv.setItem(11, new ItemBuilder(Material.LAPIS_BLOCK).setName("§f" + BorderUHC.BIG_BORDER.getName() + " : §9" + BorderUHC.BIG_BORDER.getValue()).toItemStack());
        inv.setItem(15, new ItemBuilder(Material.DIAMOND_BLOCK).setName("§f" + BorderUHC.SMALL_BORDER.getName() + " : §9" + BorderUHC.SMALL_BORDER.getValue()).toItemStack());

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, ItemBuilder.glassPane(DyeColor.RED));

        player.openInventory(inv);
    }

    public void click(Player player, String itemName, ClickType click) {
        for(BorderUHC border : BorderUHC.values()) {
            if(itemName.contains(border.getName())) {
                if(click.isLeftClick()) {
                    border.addValue(-100);
                } else if(click.isRightClick()) {
                    border.addValue(100);
                }
            }
        }
        open(player);
    }
}
