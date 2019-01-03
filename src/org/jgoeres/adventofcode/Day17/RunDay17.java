package org.jgoeres.adventofcode.Day17;

public class RunDay17 {
    static final String DEFAULT_PATH_TO_INPUTS = "day17/input.txt";

    public static void problem17A() {
        problem17A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem17A(String pathToInputs) {
        /*
        How many tiles can the water reach within the range of y values in your scan?
        */
        System.out.println("=== DAY 17A ===");

        Reservoir reservoir =  new Reservoir(pathToInputs);

        reservoir.getWaterSource().doNextFlow(reservoir);
        reservoir.printReservoir();
        return 0;
    }

    public static void problem17B() {
        problem17B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem17B(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 17B ===");

        return 0;
    }
}
