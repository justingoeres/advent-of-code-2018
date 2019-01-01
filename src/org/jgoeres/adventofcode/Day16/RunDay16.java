package org.jgoeres.adventofcode.Day16;

public class RunDay16 {
    static final String DEFAULT_PATH_TO_INPUTS = "day16/input.txt";

    public static void problem16A() {
        problem16A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem16A(String pathToInputs) {
        /*
        Ignoring the opcode numbers, how many codeSamples in your puzzle input behave like three or more opcodes?
        */
        System.out.println("=== DAY 16A ===");

        Samples samples = new Samples(pathToInputs);
        // TODO: Write comparisons to test each sample against every opcode type and count the number of matches.
        System.out.println(samples);
        return 0;
    }


    public static void problem16B() {
        problem16B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem16B(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 16B ===");

        return 0;
    }
}
