package fr.namu.uhc.menu;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.manager.ItemBuilder;
import fr.namu.uhc.stuff.GameStuff;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class HostMainMenu {

    private MenuManager menu;

    public HostMainMenu(MenuManager menu) {
        this.menu = menu;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Paramètres de la partie");

        inv.setItem(10, new ItemBuilder(Material.BANNER, 1).setName("§eConfigurer les Équipes").setDyeColor(DyeColor.RED).toItemStack());
        inv.setItem(12, new ItemBuilder(Material.CHEST, 1).setName("§eConfigurer le Stuff de Départ").toItemStack());
        inv.setItem(14, new ItemBuilder(Material.MAP, 1).setName("§eConfigurer les Scénarios").toItemStack());
        inv.setItem(16, new ItemBuilder(Material.WATCH, 1).setName("§eConfigurer les Timers").toItemStack());
        inv.setItem(20, new ItemBuilder(Material.BARRIER, 1).setName("§eModifier la bordure").toItemStack());

        inv.setItem(49, new ItemBuilder(Material.LAPIS_BLOCK, 1).setName("§aCommencer la Partie !").toItemStack());

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,50,51,52,53 };
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, ItemBuilder.glassPane(DyeColor.BLUE));

        player.openInventory(inv);
    }

    public void click(Player player, Material mat) {
        MainUHC main = menu.main;
        switch (mat) {
            case BANNER:
                menu.teamList.open(player);
            case LAPIS_BLOCK:
                main.start.start();
                return;
            case WATCH:
                menu.timer.open(player);
                return;
            case MAP:
                menu.scenario.open(player);
                return;
            case CHEST:
                main.GameStuff.edit(player);
                player.closeInventory();
                return;
            case BARRIER:
                menu.border.open(player);
                return;
        }
    }
}
