package fr.namu.uhc.manager;

import fr.namu.uhc.MainUHC;
import org.bukkit.DyeColor;
import org.bukkit.material.Dye;

public class ColorManager {


    public ColorManager(MainUHC main) {
    }

    public static String toChatColor(DyeColor color) {

        switch (color) {
            case RED:
                return "§c";
            case BLUE:
                return "§9";
            case CYAN:
                return "§3";
            case GRAY:
                return "§7";
            case LIGHT_BLUE:
                return "§b";
            case LIME:
                return "§a";
            case GREEN:
                return "§2";
            case PINK:
                return "§d";
            case ORANGE:
                return "§6";
            case WHITE:
                return "§f";
            case YELLOW:
                return "§e";
            case PURPLE:
                return "§5";

        }

        return "";
    }
    public DyeColor chatToDyeColor(String string) {
        switch (string) {
            case "rouge":
                return DyeColor.RED;
            case "bleu":
                return DyeColor.BLUE;
            case "cyan":
                return DyeColor.CYAN;
            case "gris":
                return DyeColor.GRAY;
            case "aqua":
                return DyeColor.LIGHT_BLUE;
            case "pomme":
                return DyeColor.LIME;
            case "vert":
                return DyeColor.GREEN;
            case "rose":
                return DyeColor.PINK;
            case "orange":
                return DyeColor.ORANGE;
            case "blanc":
                return DyeColor.WHITE;
            case "jaune":
                return DyeColor.YELLOW;
            case "mauve":
                return DyeColor.PURPLE;
        }

        return null;
    }
}
