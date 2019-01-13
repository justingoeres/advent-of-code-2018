package org.jgoeres.adventofcode.Day23;

public class Nanobot {
    int x;
    int y;
    int z;
    int radius;

    public Nanobot(int x, int y, int z, int radius) {
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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
