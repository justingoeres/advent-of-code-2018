package org.jgoeres.adventofcode.Day23;

import java.util.ArrayList;

public class RunDay23 {
    static final String DEFAULT_PATH_TO_INPUTS = "day23/input.txt";

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = false;

    static Formation formation;

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
        ArrayList<Nanobot> maxOverlapBots = new ArrayList<>();
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

        ArrayList<Nanobot> dividedBots = new ArrayList<>();
        for (Nanobot toDivide : maxOverlapBots) {
            // Create a set of new nanobots, divided by a million.
            dividedBots.add(toDivide.newBotDividedByMillion());
        }
        // The point of max overlap has to be inside all the centers somewhere (I think?)
        // Find the extents we have to search
        Extents searchArea = new Extents();
        searchArea.findExtents(dividedBots);

        // Let's search the whole area
        for (int z = searchArea.zMin; z <= searchArea.zMax; z++) {
            for (int y = searchArea.yMin; y <= searchArea.yMax; y++) {
                for (int x = searchArea.xMin; x <= searchArea.xMax; x++) {
                    for (Nanobot nanobot : dividedBots) {
                        boolean inRange = nanobot.pointInRange(x, y, z);
                        System.out.println("(" + x + ", " + y + ", " + z + ") is " + (inRange ? "" : "NOT ") + "in range of " + nanobot.toString());
                        if (!inRange) {
                            System.out.println("hi");
                        }
                        // Everything seems to be in range? Is that OK? Will it be different when we multiply everything?
                        // The next step I think is to find the point we just scanned which is
                        // (1) most overlapping, and
                        // (2) closest to the origin.
                        // (there may be more than one).
                        // ... and then multiply those and scan them.

                        // NO NO NO this is wrong. Our "extents" have to include the RADIUS, not just the centers!
                    }
                }

            }

        }
        return 0;
    }
}
    
