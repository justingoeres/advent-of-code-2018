package org.jgoeres.adventofcode.Day23;

import java.util.ArrayList;

public class Nanobot {
    int x;
    int y;
    int z;
    int radius;
    ArrayList<Nanobot> overlapBots = new ArrayList<>();

    public Nanobot(int x, int y, int z, int radius) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.radius = radius;
    }

    public int distanceTo(Nanobot other) {
        // calculate the 3D Manhattan distance to the other bot.
        return distanceTo(other.x, other.y, other.z);
    }

    public int distanceTo(int x, int y, int z) {
        // calculate the 3D Manhattan distance to the other bot.
        int xDist = Math.abs(this.x - x);
        int yDist = Math.abs(this.y - y);
        int zDist = Math.abs(this.z - z);

        return (xDist + yDist + zDist);
    }

    public boolean pointInRange(int x, int y, int z) {
        // calculate the 3D Manhattan distance to the specified point.
        int xDist = Math.abs(this.x - x);
        int yDist = Math.abs(this.y - y);
        int zDist = Math.abs(this.z - z);

        boolean inRange = ((xDist + yDist + zDist) < radius);
        return inRange;
    }

    public void overlapWith(Nanobot other) {
        this.overlapBots.add(other);
        if (this != other) {
            // Don't connect a bot to itself twice (but once is fine)
            other.overlapBots.add(this);
        }
    }

    public Nanobot newBotDividedByPowerOfTen(int powerOfTen) {
        final int divisor = (int) Math.pow(10, powerOfTen);
        Nanobot dividedByPowerOfTen = new Nanobot(x / divisor, y / divisor, z / divisor, radius / divisor);
        // We are NOT setting the overlap list here, that's not necessary at this point.
        return dividedByPowerOfTen;
    }

    public void multiplyByPowerOfTen(int powerOfTen) {
        final int factor = (int) Math.pow(10, powerOfTen);
        x = this.x * factor;
        y = this.y * factor;
        z = this.z * factor;
        radius = this.radius * factor;
//        Nanobot multipliedByPowerOfTen = new Nanobot(x * factor, y * factor, z * factor, radius * factor);
        // We are NOT setting the overlap list here, that's not necessary at this point.
//        return multipliedByPowerOfTen;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ") r = " + radius;
    }
}
