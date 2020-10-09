package fr.namu.uhc.stuff;

import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.ScenarioUHC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;

public class GameStuff {

    private MainUHC main;

    public HashMap<Integer, ItemStack> itemStarts = new HashMap<>();
    public ItemStack[] armorStart;

    public GameStuff(MainUHC main) {
        this.main = main;
    }

    public void set(Player player) {
        PlayerUHC ptg = this.main.puhc.get(player.getUniqueId());
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setHealth(20.0);
        player.setMaxHealth(20.0D);
        player.setHealth(20.0D);
        player.setExp(0.0F);
        player.setLevel(0);
        player.setFoodLevel(20);
        player.setSaturation(20.0F);
        player.setGameMode(GameMode.SURVIVAL);

        give(player);

        for (PotionEffect po : player.getActivePotionEffects())
            player.removePotionEffect(po.getType());
        if(ScenarioUHC.CAT_EYES.getValue())
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
    }

    public void give(Player player) {
        Inventory inv = player.getInventory();

        for(Integer ind : itemStarts.keySet()) {
            ItemStack is = itemStarts.get(ind);
            inv.setItem(ind, is);
        }

        player.getInventory().setArmorContents(armorStart);
    }

    public void save(Player player) {
        itemStarts.clear();
        Inventory inv = player.getInventory();
        Integer slot = 0;

        for(ItemStack item : inv.getContents()) {
            if(item != null) {
                itemStarts.put(slot, item);
            }
            slot++;
        }

        armorStart = player.getInventory().getArmorContents();
    }

    public void edit(Player player) {
        player.getInventory().clear();
        player.setGameMode(GameMode.CREATIVE);

        give(player);

        TextComponent msg = new TextComponent(ChatColor.GREEN + "Cliquez ici pour valider l'inventaire de départ !");
        msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/h startStuff"));
        player.sendMessage("§7§m----------------------");
        player.sendMessage(" ");
        player.spigot().sendMessage(msg);
        player.sendMessage("§7Vous êtes en créatif, faites-vous plaisir !");
        player.sendMessage(" ");
        player.sendMessage("§7§m----------------------");

    }
}
