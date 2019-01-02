package org.jgoeres.adventofcode.Day17;

import java.util.HashSet;

public class Reservoir {
    HashSet<XYPair> wallCells = new HashSet<>();

    public Reservoir() {
        XYPair one = new XYPair(1, 1);
        XYPair two = new XYPair(1, 1);

        wallCells.add(one);
        System.out.println(wallCells.contains(two));
    }
}
