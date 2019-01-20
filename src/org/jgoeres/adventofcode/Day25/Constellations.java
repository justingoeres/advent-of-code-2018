package org.jgoeres.adventofcode.Day25;

import java.util.HashSet;

public class Constellations extends HashSet<HashSet<Star>> {

    public Constellations() {
    }

    public HashSet<Star> newConstellation(Star firstStar) {
        HashSet<Star> newConstellation = new HashSet<>();

        // Put this star in a new constellation
        newConstellation.add(firstStar);
        // Add the new constellation to the list of all constellations
        this.add(newConstellation);
        // Return the newly created constellation.
        return newConstellation;
    }

    public boolean containsStar(Star star) {
        // Check every constellation to see if any contain this star.
        for (HashSet<Star> constellation : this) {
            if (constellation.contains(star)) {
                // If this star is in this constellation, stop looking and return true.
                return true;
            }
        }
        // Not found in any constellation.
        return false;
    }
}
