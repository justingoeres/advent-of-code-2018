package org.jgoeres.adventofcode.Day03;

import java.util.HashSet;

public abstract class RunDay3 {
    static String pathToInputs = "day03/input.txt";
    static FabricService fabricService = new FabricService(pathToInputs);

    static HashSet<String> mayOverlap = new HashSet<>();
    static HashSet<String> overlapSet = new HashSet<>();

    public static void problem3A() {
//    DAY 3A
//A claim like #123 @ 3,2: 5x4 means that claim ID 123 specifies
// a rectangle 3 inches from the left edge,
// 2 inches from the top edge, 5 inches wide, and 4 inches tall.
// Visually, it claims the square inches of fabric represented
// by # (and ignores the square inches of fabric represented by .)
// in the diagram below:
//
//            ...........
//            ...........
//            ...#####...
//            ...#####...
//            ...#####...
//            ...#####...
//            ...........
//            ...........
//            ...........
//
//    If the Elves all proceed with their own plans, none of them will have enough fabric.
//    How many square inches of fabric are within two or more claims?
        System.out.println("=== DAY 3A ===");

        // Approach:
        // Iterate through the area covered by each claim, inch-by-inch.
        // Keep two HashSets:
        //      1) Inches we've seen once.
        //      2) Inches we've seen more than once.
        // For each inch, try to add it to the first HashSet (i.e. is this the first time we've seen this?)
        //      If added successfully, continue on.
        //      If NOT added successfully (it already exists in the Set), add it to the second HashSet
        //          (i.e. this is the second-or-more time we've seen it).
        // When we've processed all the inches, count the size of Set #2!


        // Process each square.
        for (FabricSquare fabricSquare : fabricService.getFabricSquares()) {
            // Process each inch of each square.
            for (Integer i = 0; i < fabricSquare.getW(); i++) { // Iterate over the width
                for (Integer j = 0; j < fabricSquare.getH(); j++) { // Iterate over the height

                    // Construct the key (name) for this square inch.
                    String key = keyFromFabricSquare(fabricSquare, i, j);

                    if (mayOverlap.add(key)) {
                        // First time we've seen this inch, just continue.
                    } else {
                        // Second or more time we've seen this inch, add it to the
                        // second HashSet
                        overlapSet.add(key);
                    }

                } // iterate over height
            } // iterate over width
//            System.out.println("Done with claim #" + fabricSquare.getClaim());
        } // process all squares

        System.out.println("Total inches covered by two or more claims: " + overlapSet.size());

//        Answer:
//        Total inches covered by two or more claims: 101196
    }

    public static void problem3B() {
//          Amidst the chaos, you notice that exactly one claim doesn't overlap
//          by even a single square inch of fabric with any other claim. If you
//          can somehow draw attention to it, maybe the Elves will be able to make
//          Santa's suit after all!
//
//          What is the ID of the only claim that doesn't overlap?
        System.out.println("=== DAY 3B ===");

        // Approach:
        // The "easy" (kind of brute force-y) way to do this is to
        // iterate over all the inch-squares again, comparing them
        // to the overlapSet HashSet.
        //
        // When we fine a claim with *no* inch-squares in the overlapSet
        // set, we know it doesn't overlap anything!

        // Process each square.
        for (FabricSquare fabricSquare : fabricService.getFabricSquares()) {
            boolean overlap = false;
            // Process each inch of each square.
            for (Integer i = 0; i < fabricSquare.getW(); i++) { // Iterate over the width
                for (Integer j = 0; j < fabricSquare.getH(); j++) { // Iterate over the height

                    // Construct the key (name) for this square inch.
                    String key = keyFromFabricSquare(fabricSquare, i, j);

                    // This inch-square overlaps something else if it's in overlapSet
                    overlap = overlapSet.contains(key);

                    if (overlap) {
                        // This inch is in at least two squares (i.e. it overlaps something)
                        // so bail out and check the next inch-square.
                        break;
                    }
                } // iterate over height

                if (overlap) {
                    // Break out of BOTH for loops and go to the next whole square.
                    break;
                }
            } // iterate over width

            // If we get all the way through an entire fabricSquare
            // without breaking, that's our solution! We can stop checking.
            if (!overlap) {
                System.out.println("Non-overlapping fabric square is claim #" + fabricSquare.getClaim());
            }
        } // process all squares

        // Answer:
        // Non-overlapping fabric square is claim #243
    }

    private static String keyFromFabricSquare(FabricSquare fabricSquare, Integer i, Integer j) {
        // Construct the key (name) for this square inch.
        Integer xLoc = fabricSquare.getX() + i;
        Integer yLoc = fabricSquare.getY() + j;
        String xKey = xLoc.toString();
        String yKey = yLoc.toString();

        String key = xKey + "," + yKey;

        return key;
    }
}
