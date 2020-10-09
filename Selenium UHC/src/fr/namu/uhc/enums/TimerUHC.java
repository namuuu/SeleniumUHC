package fr.namu.uhc.enums;

import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;

public enum TimerUHC {

    PVP("PVP", 60, Material.IRON_SWORD),
    FINAL_HEAL("FinalHeal", 60, Material.EMERALD),
    BORDER_SHRINK("Réduction de la Bordure", 60*35, Material.BARRIER),

    ;

    private final String name;
    private int value;
    private final Material mat;

    TimerUHC(String name, int value, Material mat) {
        this.name = name;
        this.value = value;
        this.mat = mat;
    }


    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public Material getMat() {
        return mat;
    }

    public void click(ClickType click) {
        if(click.equals(ClickType.LEFT)) {
            if(this.value != 0)
                value -= 30;
        } else if (click.equals(ClickType.RIGHT)) {
            value += 30;
        }
    }
}
