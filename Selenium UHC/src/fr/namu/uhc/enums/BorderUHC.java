package fr.namu.uhc.enums;

public enum BorderUHC {
    BIG_BORDER("Bordure (Grande)", 2000),
    SMALL_BORDER("Bordure (Petite)", 500),
    ;

    private final String name;
    private int value;

    BorderUHC(String name, int value) {
        this.name = name;
        this.value = value;
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
    public void addValue(int value) {
        this.value += value;
    }
}
