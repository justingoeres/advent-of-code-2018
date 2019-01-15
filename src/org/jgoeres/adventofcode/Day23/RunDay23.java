package org.jgoeres.adventofcode.Day23;

import java.util.ArrayList;
import java.util.TreeSet;

public class RunDay23 {
    static final String DEFAULT_PATH_TO_INPUTS = "day23/input.txt";

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = false;

    static Formation formation;
    private static Nanobot ORIGIN = new Nanobot(0, 0, 0, 0);

    public static void problem23A() {
        problem23A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem23A(String pathToInputs) {
        /*
        Find the nanobot with the largest signal radius.
        How many nanobots are in range of its signals?
        */
        System.out.println("=== DAY 23A ===");
        formation = new Formation(pathToInputs);

        // Find the most powerful nanobot
        int maxRadius = Integer.MIN_VALUE;
        Nanobot mostPowerfulNanobot = null;
        for (Nanobot nanobot : formation.nanobots) {
            if (nanobot.radius > maxRadius) {
                mostPowerfulNanobot = nanobot;
                maxRadius = mostPowerfulNanobot.radius;
            }
        }

        // Now count up the nanobots inside that radius
        int counter = 0;
        for (Nanobot nanobot : formation.nanobots) {
            if (mostPowerfulNanobot.distanceTo(nanobot) <= mostPowerfulNanobot.radius) {
                counter++;
                if (DEBUG_PART_A_PRINT_PROGRESS) {
                    System.out.println(counter + ":\t" + nanobot.toString() + "\tis " + mostPowerfulNanobot.distanceTo(nanobot) + "\taway (IN RANGE).");
                }
            }
        }

        System.out.println("Total nanobots within " + mostPowerfulNanobot.radius
                + " of " + mostPowerfulNanobot.toString() + ":\t" + counter);
        return counter;
        // Answer:
        // Total nanobots within 99741094 of (110101452, 38159223, 37916432):	588
    }

    public static void problem23B() {
        problem23B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem23B(String pathToInputs) {
        /*
        Find the coordinates that are in range of the largest number of nanobots.
        What is the shortest manhattan distance between any of those points and 0,0,0?
        */
        System.out.println("=== DAY 23B ===");

        // The point(s) that are in range of the most nanobots
        // are also the point(s) where THE MOST NANOBOT RANGES OVERLAP.

        // Find how many ranges each bot overlaps with.
        int i = 0;
        int maxOverlapCount = Integer.MIN_VALUE;
        TreeSet<Nanobot> maxOverlapBots = new TreeSet<>(new NanobotComparator());
        for (Nanobot nanobot1 : formation.nanobots) {
            // Two nanobots are "in range" of each other if
            // the Manhattan Distance of their centers
            // is <= the sum of their ranges.

            // We only need to check FORWARD from the current
            // nanobot because when we find two bots in range we update them both.

            for (Nanobot nanobot2 : formation.nanobots.subList(i, formation.nanobots.size())) {  // why does size-1 not work here?
                if (nanobot1.distanceTo(nanobot2) <= (nanobot1.radius + nanobot2.radius)) {
                    // If these guys's ranges overlap.
                    // Add them to each other's list.
                    nanobot1.overlapWith(nanobot2); // connects both ways.
                }
            }
            if (DEBUG_PART_B_PRINT_PROGRESS) {
                System.out.println(i + ":\tNanobot " + nanobot1.toString() + ":\t" + nanobot1.overlapBots.size());
            }

            // As we go, keep track of the bot(s) in range of the most other bots.
            int overlapBotsCount = nanobot1.overlapBots.size();
            if (overlapBotsCount > maxOverlapCount) {
                // if this bot is in range of more bots than any before it
                // It's the new max. Set the new max and restart the list.
                maxOverlapCount = overlapBotsCount;
                maxOverlapBots.clear();
                maxOverlapBots.add(nanobot1);
            } else if (overlapBotsCount == maxOverlapCount) {
                // If this bot is in EXACTLY as many ranges as all other known maximum bots.
                // Add it to the list of max-in-range bots.
                maxOverlapBots.add(nanobot1);
            }
            i++;
        }

        // Now we have a list of bots (20 in my case?) that all overlap EXACTLY 989 other bots.
        // Do all of these bots overlap each other?
/*
        for (Nanobot maxOverlapBot : maxOverlapBots) {
            boolean overlapsAll = true;
            for (Nanobot otherOverlapBot : maxOverlapBots) {
                // Check each bot against all others.
                overlapsAll &= otherOverlapBot.overlapBots.contains(maxOverlapBot);
            }
            String result = (overlapsAll ? "overlaps all others" : "DOES NOT overlap all others");
            System.out.println(maxOverlapBot.toString() + " " + result);
        }
        // Result: they all overlap each other!
*/

        // OK so these all overlap each other. So the overall max-overlap point is somewhere
        // in the UNION of all of these (NOT the intersection!).

        // What if we subdivide the problem by dividing everything by 1,000,000?
        // That will give us things like:
        // (109, 26, 31) r = 93
        // (110, 30, 38) r = 91
        // (115, 26, 45) r = 86
        // Which we can just iterate over to find the point(s) with the most overlaps (which hopefully will be all 20??)
        // Then we take the closest of those points to the origin (there may be more than one :( ),
        // multiply everything by 10, and search again but ONLY IN THOSE AREAS.
        // Keep repeating that until we're back to the original resolution.

        int inRangeCellCount = 0;
        boolean firstLoop = true;
        ArrayList<Extents> searchAreaExtents = new ArrayList<>();
        TreeSet<Nanobot> searchAreaAdjacents = new TreeSet<>(new NanobotComparator());
        TreeSet<Nanobot> searchAreaInRange = new TreeSet<>(new NanobotComparator());    // Reuse the Nanobot to represent the search area, since it has X, Y, and Z components.
        for (int powerOfTen = 6; powerOfTen >= 0; powerOfTen--) {    // Repeat at increasing scale levels
            inRangeCellCount = 0;
            TreeSet<Nanobot> dividedBots = new TreeSet<>(new NanobotComparator());
            for (Nanobot toDivide : maxOverlapBots) {
                // Create a set of new nanobots, divided by the current scale.
                dividedBots.add(toDivide.newBotDividedByPowerOfTen(powerOfTen));
            }
            // The point of max overlap has to be inside all the centers somewhere (I think?)
            // Find the extents we have to search
            Extents newSearchArea = null;
            if (firstLoop) {
                // On the first time through, search one big area covered by all the bots.
                newSearchArea = new Extents();
                newSearchArea.findExtents(dividedBots);
                searchAreaExtents.add(newSearchArea);
                firstLoop = false;
            } else {
                // Search the area(s) found in the previous iteration
                searchAreaExtents.clear();  // clear the previous list.
                // Scale up the previous iteration's search areas by 10x, then add their extents to the areas to search.
//                for(Nanobot searchAreaCell : searchAreaInRange) {
                newSearchArea = new Extents();
                for (Nanobot searchAreaCell : searchAreaInRange) {
                    // For each cell, search the cell +/- one in all directions
                    for (int z = searchAreaCell.z - 1; z <= searchAreaCell.z + 2; z++) {
                        for (int y = searchAreaCell.y - 1; y <= searchAreaCell.y + 2; y++) {
                            for (int x = searchAreaCell.x - 1; x <= searchAreaCell.x + 2; x++) {
                                Nanobot searchAreaAdjacent = new Nanobot(x * 10, y * 10, z * 10, searchAreaCell.radius * 10);
                                searchAreaAdjacents.add(searchAreaAdjacent);
                            }
                        }
                    }
                }
                newSearchArea.findExtents(searchAreaAdjacents);
                searchAreaExtents.add(newSearchArea);
//                }
            }

            for (Extents searchArea : searchAreaExtents) {
                // Let's search every area
                inRangeCellCount = 0;
                int minDistanceToOrigin = Integer.MAX_VALUE;
                boolean inRange = false;
                for (int z = searchArea.zMin; z <= searchArea.zMax; z++) {
                    for (int y = searchArea.yMin; y <= searchArea.yMax; y++) {
                        for (int x = searchArea.xMin; x <= searchArea.xMax; x++) {
                            for (Nanobot nanobot : dividedBots) {
                                inRange = nanobot.pointInRange(x, y, z);
                                //                        System.out.println("(" + x + ", " + y + ", " + z + ") is " + (inRange ? "" : "NOT ") + "in range of " + nanobot.toString());
                                if (inRange) {
                                    Nanobot searchAreaBot = new Nanobot(x, y, z, 0);
                                    int distanceToOrigin = searchAreaBot.distanceTo(ORIGIN);
                                    if (distanceToOrigin < minDistanceToOrigin) {   // If this is the closest search area point we've seen so far.
                                        minDistanceToOrigin = distanceToOrigin;
                                        searchAreaInRange.clear();  // Clear the previous list of "closest search areas"
                                        searchAreaBot.radius = searchAreaBot.distanceTo(ORIGIN);
                                        searchAreaInRange.add(searchAreaBot);
                                    } else if (distanceToOrigin == minDistanceToOrigin) { // else if this area is just as close as the previous closest
                                        searchAreaBot.radius = searchAreaBot.distanceTo(ORIGIN);
                                        searchAreaInRange.add(searchAreaBot);
                                    }   // else do nothing; this area is in range there's already at least one closer.

                                    //                            System.out.println("(" + x + ", " + y + ", " + z + ") is " + (inRange ? "" : "NOT ") + "in range of " + nanobot.toString());
                                } else {
                                    break; // Stop scanning this point if it's out of range of anything.
                                }
                            }
                            if (inRange) {
                                inRangeCellCount++;
                            }
                        }
                    }
                }
            }
            // When we get here we've found all the cells at this detail level that are in range and closest to the origin.
            // So now we need to scale those up, turn them collectively into a set of Extents, then repeat
            // the evaluation process with dividedBots that are also scaled up.
        }


        System.out.println(inRangeCellCount);
        return 0;
    }
}
    
