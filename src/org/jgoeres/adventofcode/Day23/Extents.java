package org.jgoeres.adventofcode.Day23;

import java.util.ArrayList;

public class Extents {
    int xMin,xMax;
    int yMin,yMax;
    int zMin,zMax;

    public Extents() {
        xMin = Integer.MAX_VALUE;
        yMin = Integer.MAX_VALUE;
        zMin = Integer.MAX_VALUE;

        xMax = Integer.MIN_VALUE;
        yMax = Integer.MIN_VALUE;
        zMax = Integer.MIN_VALUE;
    }
    
    public void findExtents(ArrayList<Nanobot> nanobots){
        for (Nanobot nanobot:nanobots){
            xMin = Math.min(xMin,nanobot.x);
            yMin = Math.min(yMin,nanobot.y);
            zMin = Math.min(zMin,nanobot.z);

            xMax = Math.max(xMax,nanobot.x);
            yMax = Math.max(yMax,nanobot.y);
            zMax = Math.max(zMax,nanobot.z);
        }
    }
}
