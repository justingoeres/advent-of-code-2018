package org.jgoeres.adventofcode.Day24;

public enum AttackType {
    BLUDGEONING("bludgeoning"),
    COLD("cold"),
    FIRE("fire"),
    RADIATION("radiation"),
    SLASHING("slashing");

    private final String asString;

    AttackType(String asString) {
        this.asString = asString;
    }

    public String asString() {
        return this.asString;
    }

    public static AttackType fromString(String text) {
        for (AttackType b : AttackType.values()) {
            if (b.asString.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
