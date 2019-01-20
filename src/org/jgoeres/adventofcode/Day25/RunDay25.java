package org.jgoeres.adventofcode.Day25;

import java.util.HashSet;

public class RunDay25 {
    static final String DEFAULT_PATH_TO_INPUTS = "day25/input.txt";

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = false;

    static Starfield starfield;

    public static void problem25A() {
        problem25A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem25A(String pathToInputs) {
        /*
        How many constellations are formed by the fixed points in spacetime?
        */
        System.out.println("=== DAY 25A ===");
        starfield = new Starfield(pathToInputs);

        Constellations constellations = new Constellations();

        // Check each star in the starfield.
        // Check if this star is already in a constellation. If so, skip ahead.
        // If not, create a new constellation, add it, and continue.
        // Look at all points around the star within 3 distance (pointsAround)
        // If the starfield contains any of those points,
        //      For each contained point
        //      Add the point to the current constellation.
        //      Add it as a "connection" for this star.  (?)
        //      Recursively process this point.

        for (Star star : starfield.allStars) {
            // For each star in the starfield
            if (!constellations.containsStar(star)) {   // If this star is NOT already in a constellation.
                // Add it to a new one.
                HashSet<Star> constellation = constellations.newConstellation(star);

                // Then process all the "close" stars to this one.
                star.findNeighbors(starfield, constellation);
            }
        }

        // Now we've mapped all the sets of stars that connect in constellations.
        // The answer is the number of constellations we found!

        int result = constellations.size();
        System.out.println("Found " + result + " constellations.");

        return result;
        // Answer:
        // Found 314 constellations.
    }
}