package org.jgoeres.adventofcode.Day23;

import java.util.ArrayList;
import java.util.TreeSet;

public class RunDay23 {
    static final String DEFAULT_PATH_TO_INPUTS = "day23/input.txt";

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;

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

        // Let's use the "box and subdivide" method.
        // Start with a box large enough to contain all the nanobots.
        // Divide it into 9 equal sub-boxes.
        // Count the nanobots "in range of" each sub-box
        // Order the subboxes by count, then by distance to the origin.
        // Process the first (most/nearest) of those:
        //  Divide it into 9 sub-boxes.
        //  Count the nanobots "in range of" each sub-box.
        //  Add those sub-boxes (with counts) to the TreeSet of boxes to process.
        //  Loop.


        // Create the first (very large) box.
        Extents initialExtents = new Extents();
        initialExtents.findExtents(formation.nanobots);
        int initialX = initialExtents.xMin + (initialExtents.xMax - initialExtents.xMin) / 2;
        int initialY = initialExtents.yMin + (initialExtents.yMax - initialExtents.yMin) / 2;
        int initialZ = initialExtents.zMin + (initialExtents.zMax - initialExtents.zMin) / 2;

        // initial box size is the distance to the furthest nanobot
        int maxDistance = Integer.MIN_VALUE;
        for (Nanobot nanobot : formation.nanobots) {
            int distance = nanobot.distanceTo(initialX, initialY, initialZ);
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }
        int initialRadius = maxDistance;

        Box initialBox = new Box(initialX, initialY, initialZ, initialRadius);
        initialBox.reachableCountUpperBound = formation.nanobots.size();

        // Create the initial list of boxesToCheck – just the single huge one.
        TreeSet<Box> boxesToCheck = new TreeSet<>(new BoxComparator());
        boxesToCheck.add(initialBox);

        int boxesChecked = 0;
        Box finalBox;
        // There may be multiple boxes at the front of the set with equal distances from the origin. We need to process them all.
        while (true) { // run forever; we break below when we have an answer.
            int botCountToCheck = -1;
            TreeSet<Box> boxesToAdd = new TreeSet<>(new BoxComparator());
            Box box = boxesToCheck.first(); // Check just the FIRST (i.e. most -> closest -> smallest) box
            boxesToCheck.remove(box);
            if (box.radius == 0) {
                // As soon as we have a zero-radius box that is both "most in range" and "closest", we're done?
                finalBox = box;
                break;
            }
            // If this is the first element, initialize the bot count we're checking.
            if (botCountToCheck == -1) botCountToCheck = box.reachableCountUpperBound;

            if (box.reachableCountUpperBound >= botCountToCheck) { // If this box can have max # of bots we're interested in.
                // subdivide it and keep checking.
                ArrayList<Box> subBoxes = box.subdivide();
                // Calculate how many bots are in range of each new subBox.
                for (Box subBox : subBoxes) {
                    int reachableCount = 0;
                    for (Nanobot nanobot : formation.nanobots) {
                        if (subBox.distanceTo(nanobot) <= (subBox.radius + nanobot.radius)) {
                            // If this nanobot is reachable by this subBox.
                            reachableCount++;
                        }
                    }
                    // Store the final count as the upper bound of reachable bots for this subBox.
                    subBox.reachableCountUpperBound = reachableCount;
                    boxesChecked++;     // Count every box we check, for record-keeping.
                }
                // When we get here, we've subdivided this box and checked all the subBoxes.
                // Time to add them to our list of boxes to keep checking.
                boxesToAdd.addAll(subBoxes);
            }
            // When we're done iterating, add everything to the boxesToCheck, then loop.
            boxesToCheck.addAll(boxesToAdd);
        }

        System.out.println("Checked " + boxesChecked + " boxes, closest point (" + finalBox.x + ", " + finalBox.y + ", " + finalBox.z + ") " +
                "in range of " + finalBox.reachableCountUpperBound + " bots, " + finalBox.distanceToOrigin() + " from origin.");

        return finalBox.distanceToOrigin();

        // Answer:
        // Checked 19980 boxes, closest point (37629599, 24779842, 48818202) in range of 882 bots, 111227643 from origin.
    }
}
    
