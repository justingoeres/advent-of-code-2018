package org.jgoeres.adventofcode.Day18;

public enum Acre {
    OPEN('.'),
    TREES('|'),
    LUMBERYARD('#');

    public char asChar() {
        return asChar;
    }

    private final char asChar;

    Acre(char asChar) {
        this.asChar = asChar;
    }
}
