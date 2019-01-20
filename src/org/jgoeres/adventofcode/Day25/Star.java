package org.jgoeres.adventofcode.Day25;

import java.util.ArrayList;
import java.util.HashSet;

public class Star {
    int x;
    int y;
    int z;
    int t;

    ArrayList<Star> neighbors = new ArrayList<>();

    public Star(int x, int y, int z, int t) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.t = t;
    }

    public int distanceTo(Star otherStar) {
        return distanceTo(otherStar.x, otherStar.y, otherStar.z, otherStar.t);
    }

    public int distanceTo(int x, int y, int z, int t) {
        // Manhattan distance
        int xDist = Math.abs(this.x - x);
        int yDist = Math.abs(this.y - y);
        int zDist = Math.abs(this.z - z);
        int tDist = Math.abs(this.t - t);
        return (xDist + yDist + zDist + tDist);
    }

    public void findNeighbors(Starfield starfield, HashSet<Star> constellation) {
        // Locate all the neighboring stars to this one.
        for (Star point : pointsAround()) {    // For each point around this star.
            // Does it match a star in the starfield?
            if (starfield.allStars.contains(point)) {
                // If this point matches a star.
                // Add it to our list of neighbors, in case we need that later.
                neighbors.add(point);

                if (!constellation.contains(point)) {
                    // And if this constellation DOESN'T already contain it.
                    // Add it to the constellation
                    constellation.add(point);
                    // Then find its neighbors
                    point.findNeighbors(starfield, constellation);
                }
            }
        }
    }

    private ArrayList<Star> pointsAround() {
        // Generate a list of all the points in a size-3 arrangement around this Star in all 4 dimensions.
        ArrayList<Star> pointsAround = new ArrayList<>();

        // Structure the for loops so we don't generate any points we don't need.
        // This means each subsequent loop depends on the previous.
        for (int x = -3; x <= 3; x++) {
            for (int y = -3 + Math.abs(x); y <= 3 - Math.abs(x); y++) {
                for (int z = -3 + (Math.abs(x) + Math.abs(y)); z <= 3 - (Math.abs(x) + Math.abs(y)); z++) {
                    for (int t = -3 + (Math.abs(x) + Math.abs(y) + Math.abs(z)); t <= 3 - (Math.abs(x) + Math.abs(y) + Math.abs(z)); t++) {
//                        System.out.println(x + "\t" + y + "\t" + z + "\t" + t);
                        // Skip the 0,0,0,0 point (that's this star).
                        if (x == 0 && y == 0 && z == 0 && t == 0) continue;

                        // For all others, create a star and add it to the list.
                        Star pointAround = new Star(this.x + x, this.y + y, this.z + z, this.t + t);
                        pointsAround.add(pointAround);
                    }

                }
            }
        }
        return pointsAround;
    }

    @Override
    public String toString() {
        return ("(" + this.x + "," + y + "," + z + "," + t + ")");
    }


    @Override
    public boolean equals(Object o) {
        if (o instanceof Star
                && (((Star) o).x == this.x)
                && (((Star) o).y == this.y)
                && (((Star) o).z == this.z)
                && (((Star) o).t == this.t)
                ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String hashString = this.toString();
        int hashCode = hashString.hashCode();
        return hashCode; // Make the hashCode equivalent for Stars at the same coords.
    }
}
