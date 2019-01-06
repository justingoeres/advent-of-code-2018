package org.jgoeres.adventofcode.Day18;

public class RunDay18 {

    static final String DEFAULT_PATH_TO_INPUTS = "day18/input.txt";
    static final boolean DEBUG_PRINT_FOREST = false;

    public static void problem18A() {
        problem18A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem18A(String pathToInputs) {
        /*
        What will the total resource value of the lumber collection area be after 10 minutes?
        (value = wood acres * lumberyard acres)
        */
        System.out.println("=== DAY 18A ===");

        final int GENERATIONS_TO_RUN = 10;

        Forest forest = new Forest(pathToInputs);
        if (DEBUG_PRINT_FOREST) {
            forest.printForest();   // print initial state
        }

        for (int i = 0; i < GENERATIONS_TO_RUN; i++) {
            forest.doTimerTick();
            if (DEBUG_PRINT_FOREST) {
                System.out.println("Generation #" + i);
                forest.printForest();
            }
        }

        int finalValue = forest.calculateResourceValue();
        System.out.println("Final resource value:\t" + finalValue);
        return finalValue;
        // Answer:
        // Final resource value:	360720
    }

    public static void problem18B() {
        problem18B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem18B(String pathToInputs) {
        /*
        Problem description
        */
        System.out.println("=== DAY 18B ===");

        return 0;
        // Answer:
        // 
    }

}
