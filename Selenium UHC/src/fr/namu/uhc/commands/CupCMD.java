package fr.namu.uhc.commands;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.manager.PointsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CupCMD implements TabExecutor {

    private MainUHC main;

    public CupCMD(MainUHC main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        PointsManager points = this.main.points;

        if(!(sender instanceof Player))
            return true;

        Player player = (Player)sender;
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(args.length == 0) {

            return true;
        }

        switch (args[0]) {
            case "points":
                if (args.length == 1) {
                    player.sendMessage(InfoUHC.prefix + "§eJusqu'alors, vous avez obtenu §b" + points.getScore(player) + " §e!");
                    return true;
                }
                for(Player players : Bukkit.getOnlinePlayers()) {
                    if (args[1].equalsIgnoreCase(players.getName())) {
                        player.sendMessage(InfoUHC.prefix + "§eCe joueur possède actuellement §b" + points.getScore(players) + " §e!");
                        return true;
                    }
                }
                player.sendMessage(InfoUHC.prefix + "§cNous ne sommes pas parvenus à trouver ton joueur...");
                return true;
            case "teampoints":
                if (args.length == 1) {
                    if(puhc.getTeam() == null) {
                        player.sendMessage("Tu n'as pas d'équipe, rejoins-en d'abord une pour ces informations !");
                        return true;
                    }
                    player.sendMessage(InfoUHC.prefix + "§eJusqu'alors, vous avez obtenu §b" + points.getTeamScore(puhc.getTeam()) + " §e!");
                    return true;
                }
                for(Player players : Bukkit.getOnlinePlayers()) {
                    if (args[1].equalsIgnoreCase(players.getName())) {
                        PlayerUHC puhcs = this.main.puhc.get(player.getUniqueId());
                        if(puhcs.getTeam() == null) {
                            player.sendMessage("Ce joueur n'a pas d'équipe, il doit d'abord en rejoindre une pour ces informations !");
                            return true;
                        }
                        player.sendMessage(InfoUHC.prefix + "§eCe joueur possède actuellement §b" + points.getTeamScore(puhcs.getTeam()) + " §e!");
                        return true;
                    }
                }
                player.sendMessage(InfoUHC.prefix + "§cNous ne sommes pas parvenus à trouver ton joueur...");
                return true;
            case "reset":
                if(!player.isOp()) {
                    player.sendMessage(InfoUHC.prefix + "§eTu n'as pas les permissions nécessaires pour réaliser cette commande !");
                    return true;
                }
                player.sendMessage(InfoUHC.prefix + "§eLes points ont été supprimés avec succès !");
                this.main.points.deleteFile();
                return true;
        }








        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        String[] tabe = {"points", "teampoints"};
        List<String> tab = new ArrayList<>(Arrays.asList(tabe));
        if (args.length == 0)
            return tab;
        if (args.length == 1) {
            for (int i = 0; i < tab.size(); i++) {
                for (int j = 0; j < tab.get(i).length() && j < args[0].length(); j++) {
                    if (tab.get(i).charAt(j) != args[0].charAt(j)) {
                        tab.remove(i);
                        i--;
                        break;
                    }
                }
            }
            return tab;
        }
        return null;
    }
}
