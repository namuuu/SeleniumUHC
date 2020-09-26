package fr.namu.uhc.stuff;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.manager.ItemBuilder;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class LobbyStuff {

    private MainUHC main;

    public LobbyStuff(MainUHC main) {
        this.main = main;
    }

    public void give(Player player) {
        Inventory inv = player.getInventory();
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setGameMode(GameMode.ADVENTURE);

        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());
        inv.setItem(4, new ItemBuilder(Material.BANNER, 1).setTeamBannerHotbar(puhc.getTeam()).toItemStack());
        if(puhc.getTeam() != null) {
            inv.setItem(4, new ItemBuilder(Material.BANNER, 1).setTeamBannerHotbar(puhc.getTeam()).toItemStack());
        }

        if(this.main.info.getHost() != null && this.main.info.getHost().equals(player)) {
            inv.setItem(7, new ItemBuilder(Material.NETHER_STAR, 1).setName("§eMenu du Host").toItemStack());
        }
    }
}
