package fr.namu.uhc.menu;

import fr.namu.uhc.enums.TeamUHC;
import fr.namu.uhc.manager.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TeamListMenu {

    private MenuManager menu;

    public TeamListMenu(MenuManager menu) {
        this.menu = menu;
    }

    public void open(Player player) {
        Inventory inv = Bukkit.createInventory(null, 6*9, "§7Liste des Équipes");

        int slot = 1;
        int line = 1;

        for(TeamUHC team : this.menu.main.team.teams.values()) {
            if(team.getPlayers().size() != 0) {
                inv.setItem(slot + line * 9, new ItemBuilder(Material.BANNER, 1).setTeamInfo(team).toItemStack());
                slot = slot + 1;
                if(slot >= 8) {
                    line = line + 1;
                    slot = 1;
                }
            }
        }

        int[] SlotWhiteGlass = {
                0,1,2,3,4,5,6,7,8,9,17,18,26,27,35,36,44,45,46,47,48,49,50,51};
        for (int slotGlass : SlotWhiteGlass)
            inv.setItem(slotGlass, ItemBuilder.glassPane(DyeColor.RED));

        player.openInventory(inv);
    }


}
