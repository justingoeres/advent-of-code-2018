package org.jgoeres.adventofcode.Day22;

public enum RegionType {
    ROCKY('.'),
    WET('='),
    NARROW('|');

    public char asChar() {
        return asChar;
    }

    private final char asChar;

    RegionType(char asChar) {
        this.asChar = asChar;
    }
}
