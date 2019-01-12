package org.jgoeres.adventofcode.Day22;


public class Region {
    RegionType type;
    int erosionLevel;

    public Region(RegionType type, int erosionLevel) {
        this.erosionLevel = erosionLevel;
        this.type = type;
    }
}
