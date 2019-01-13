package org.jgoeres.adventofcode.Day23;

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
        Problem Description
        */
        System.out.println("=== DAY 23B ===");
        return 0;
    }
}
    
