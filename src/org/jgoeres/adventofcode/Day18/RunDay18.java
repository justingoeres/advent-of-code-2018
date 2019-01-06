package org.jgoeres.adventofcode.Day18;

import java.util.HashMap;

public class RunDay18 {

    static final String DEFAULT_PATH_TO_INPUTS = "day18/input.txt";

    public static void problem18A() {
        problem18A(DEFAULT_PATH_TO_INPUTS);
    }


    public static int problem18A(String pathToInputs) {
        /*
        What will the total resource value of the lumber collection area be after 10 minutes?
        (value = wood acres * lumberyard acres)
        */
        System.out.println("=== DAY 18A ===");
        final boolean DEBUG_PRINT_FOREST = false;

        final int GENERATIONS_TO_RUN = 10;

        Forest forest = new Forest(pathToInputs);
        if (DEBUG_PRINT_FOREST) {
            forest.printForest();   // print initial state
        }

        for (int i = 0; i < GENERATIONS_TO_RUN; i++) {
            forest.doTimerTick();
            if (DEBUG_PRINT_FOREST) {
                System.out.println("Generation #" + i + ":\t" + forest.calculateResourceValue());
                forest.printForest();
            }
//            System.out.println("Generation #" + i+":\t"+forest.calculateResourceValue());
        }

        int finalValue = forest.calculateResourceValue();
        System.out.println("Final resource value after "+GENERATIONS_TO_RUN+" generations:\t" + finalValue);
        return finalValue;
        // Answer:
        // Final resource value after 10 generations:	360720
    }

    public static void problem18B() {
        problem18B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem18B(String pathToInputs) {
        /*
        What will the total resource value of the
        lumber collection area be after 1,000,000,000 minutes?
        */
        System.out.println("=== DAY 18B ===");
        final boolean DEBUG_PRINT_FOREST = false;
        final boolean DEBUG_PRINT_PROGRESS = false;

        final int TARGET_GENERATIONS = 1000000000;  // From problem statement.

        Forest forest = new Forest(pathToInputs);
        int generationNum = 0;
        int generationLastSeen;
        String fingerprint;
        HashMap<String, Integer> resourceValueLog = new HashMap<>();
        while (true) {
            // Run a tick
            forest.doTimerTick();
            generationNum++;    // Mark this generation complete.
            // Log this result for later lookup
            fingerprint = forest.getCurrentAcresFingerprint();
            if (!resourceValueLog.containsKey(fingerprint)) {
                // If we have NOT seen this resource value before.
                // Add it to the log.
                resourceValueLog.put(fingerprint, generationNum);
            } else {
                // We've seen this one before!
                //
                generationLastSeen = resourceValueLog.get(fingerprint);
                System.out.println("Fingerprint " + fingerprint + " occurs at generations " + generationLastSeen + " and " + generationNum);
                if (DEBUG_PRINT_FOREST) {
                    forest.printForest();   // Print the first forest that repeats
                }
                break;  // Move on to figuring out how to get to the end.
            }
        }
        int generationInterval = generationNum - generationLastSeen;    // This is how often the pattern repeats.
//        System.out.println("Interval is " + generationInterval);

        int generationsLeft = TARGET_GENERATIONS - generationNum;   // How many left to the end.
        generationsLeft = generationsLeft % generationInterval;     // Fast forward through all the cycles.
        int resourceValue = 0;
        for (int i = 0; i < generationsLeft; i++) { // Run this many to get to the target.
            forest.doTimerTick();
            resourceValue = forest.calculateResourceValue();
            if (DEBUG_PRINT_PROGRESS) {
                System.out.println("Generations left:\t" + (generationsLeft - i - 1) + "\tResource value:\t" + resourceValue + "\tforest fingerprint:\t" + forest.getCurrentAcresFingerprint());
            }
        }
        if (DEBUG_PRINT_FOREST) {
            forest.printForest();   // Print final forest
        }
        System.out.println("Final resource value after " + TARGET_GENERATIONS + " generations:\t" + resourceValue);
        return resourceValue;
        // Answer:
        //  Final resource value after 1000000000 generations:	197276
    }
}
