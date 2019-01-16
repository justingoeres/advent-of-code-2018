package org.jgoeres.adventofcode.Day23;

import java.util.TreeSet;

public class Extents {
    int xMin, xMax;
    int yMin, yMax;
    int zMin, zMax;

    public Extents() {
        xMin = Integer.MAX_VALUE;
        yMin = Integer.MAX_VALUE;
        zMin = Integer.MAX_VALUE;

        xMax = Integer.MIN_VALUE;
        yMax = Integer.MIN_VALUE;
        zMax = Integer.MIN_VALUE;
    }

    public void findExtents(TreeSet<Nanobot> nanobots) {
        for (Nanobot nanobot : nanobots) {
            xMin = Math.min(xMin, nanobot.x - nanobot.radius);
            yMin = Math.min(yMin, nanobot.y - nanobot.radius);
            zMin = Math.min(zMin, nanobot.z - nanobot.radius);

            xMax = Math.max(xMax, nanobot.x + nanobot.radius);
            yMax = Math.max(yMax, nanobot.y + nanobot.radius);
            zMax = Math.max(zMax, nanobot.z + nanobot.radius);
        }
    }
}
