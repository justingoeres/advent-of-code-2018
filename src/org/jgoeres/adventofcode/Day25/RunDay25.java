package org.jgoeres.adventofcode.Day25;

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

        return 0;
    }

    public static void problem25B() {
        problem25B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem25B(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 25B ===");

        return 0;
    }
}
