package fr.namu.uhc.commands;

import fr.namu.uhc.InfoUHC;
import fr.namu.uhc.MainUHC;
import fr.namu.uhc.PlayerUHC;
import fr.namu.uhc.enums.StateUHC;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamCMD implements TabExecutor {

    private MainUHC main;

    public TeamCMD(MainUHC main) {
        this.main = main;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if(!(sender instanceof Player))
            return true;

        Player player = (Player)sender;
        PlayerUHC puhc = this.main.puhc.get(player.getUniqueId());

        if(args.length == 0) {
            player.sendMessage("§7§m----------------------");
            player.sendMessage("§6§lUHC: §r§bCommandes d'équipes");
            player.sendMessage("/team create <nom> §bCréer une équipe");
            player.sendMessage("/team leave §bQuitter ton équipe");
            player.sendMessage("/team prefix <prefix> §bChanger le préfix de l'équipe");
            player.sendMessage("/team disband §bDissoudre une équipe");
            player.sendMessage("/team rename <nom> §bChange le nom de l'équipe");
            player.sendMessage("/team color <couleur> §bChanger la couleur de l'équipe");
            player.sendMessage("§7§m----------------------");
            return true;
        }

        if(!this.main.info.getState().equals(StateUHC.LOBBY)) {
            player.sendMessage(InfoUHC.prefix + "§eTu ne peux plus réaliser ce genre de commande en pleine partie !");
            return true;
        }

        switch (args[0]) {
            case "create":
                if(args.length == 1) {
                    player.sendMessage(InfoUHC.prefix + "§eTu dois préciser un nom d'équipe !");
                    return true;
                }
                this.main.team.newTeam(player, args[1]);
                return true;
            case "invite":
                if(args.length == 1) {
                    player.sendMessage(InfoUHC.prefix + "§eTu dois préciser un joueur !");
                    return true;
                }
                for(Player players : Bukkit.getOnlinePlayers()) {
                    System.out.println(players.getName());
                    if(args[1].equalsIgnoreCase(players.getName())) {
                        PlayerUHC puhcs = this.main.puhc.get(players.getUniqueId());
                        if(puhcs.getTeam() != null) {
                            player.sendMessage(InfoUHC.prefix + "§eCe joueur possède déjà une équipe !");
                            return true;
                        }
                        player.sendMessage(InfoUHC.prefix + "§eVous avez invité §a" + players.getName() + "§e avec succès !");
                        players.sendMessage(InfoUHC.prefix + "§a" + player.getName() + " §evous a invité dans son équipe, voudriez-vous la rejoindre ?");
                        TextComponent invite = new TextComponent("§7[§aCliquez ici !§7]");
                        invite.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/team eihsaka " + player.getName()));
                        players.spigot().sendMessage(invite);
                        players.sendMessage(" ");
                        return true;
                    }
                }
                player.sendMessage(InfoUHC.prefix + "§cNous ne sommes pas parvenus à trouver le joueur en question... Etes-vous sûr qu'il soit connecté ?");
                return true;
            case "eihsaka":
                if(args.length == 1) {
                    player.sendMessage("§cHmmmm... Tu as trouvé une bien curieuse commande... Mais à quoi peut-elle bien servir ?");
                    return true;
                }
                if(puhc.getTeam() != null) {
                    return true;
                }
                for(Player players : Bukkit.getOnlinePlayers()) {
                    if(args[1].equalsIgnoreCase(players.getName())) {
                        PlayerUHC puhcs = this.main.puhc.get(players.getUniqueId());
                        if(puhcs.getTeam() == null) {
                            return true;
                        }
                        if(puhcs.getTeam().getCaptain() != players) {
                            return true;
                        }
                        this.main.team.joinTeam(player, puhcs.getTeam());
                        return true;
                    }
                }
                return true;
            case "leave":
                this.main.team.leaveTeam(player);
                return true;
            case "prefix":
                if(args.length == 1) {
                    player.sendMessage(InfoUHC.prefix + "§eTu dois préciser un préfixe !");
                    return true;
                }
                this.main.team.changeTeamPrefix(player, args[1]);
                return true;
            case "disband":
                this.main.team.disbandTeam(player);
                return true;
            case "rename":
                if(args.length == 1) {
                    player.sendMessage(InfoUHC.prefix + "§eTu dois préciser un préfixe !");
                    return true;
                }
                this.main.team.changeTeamName(player, args[1]);
                return true;
            case "color":
                if(args.length == 1) {
                    player.sendMessage(InfoUHC.prefix + "§eTu dois préciser un préfixe !");
                    return true;
                }
                this.main.team.changeColor(player, args[1]);
                return true;

        }





        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        return null;
    }
}
