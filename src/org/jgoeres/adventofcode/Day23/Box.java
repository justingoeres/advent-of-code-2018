package org.jgoeres.adventofcode.Day23;

import java.util.ArrayList;

public class Box {
    int x;
    int y;
    int z;
    int radius;

    int reachableCountUpperBound;

    public Box(int x, int y, int z, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public int distanceTo(Nanobot other) {
        // calculate the 3D Manhattan distance to the other bot.
        int xDist = Math.abs(this.x - other.x);
        int yDist = Math.abs(this.y - other.y);
        int zDist = Math.abs(this.z - other.z);

        return (xDist + yDist + zDist);
    }

    public int distanceTo(Box other) {
        // calculate the 3D Manhattan distance to the other bot.
        int xDist = Math.abs(this.x - other.x);
        int yDist = Math.abs(this.y - other.y);
        int zDist = Math.abs(this.z - other.z);

        return (xDist + yDist + zDist);
    }

    public int distanceToOrigin() {
        int xDist = Math.abs(this.x);
        int yDist = Math.abs(this.y);
        int zDist = Math.abs(this.z);

        return (xDist + yDist + zDist);

    }

    public ArrayList<Box> subdivide() {
        // Subdivide into 9 equal boxes.
        ArrayList<Box> subdividedBoxes = new ArrayList<>();
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    Box subBox = new Box(this.x + x * radius, this.y + y * radius, this.z + z * radius, (radius + 1) / 3);
                    subdividedBoxes.add(subBox);
                }
            }
        }
        return subdividedBoxes;
    }

    @Override
    public String toString() {
        return "count = " + reachableCountUpperBound + " (" + x + ", " + y + ", " + z + ") r = " + radius + " d = " + distanceToOrigin();
    }
}
