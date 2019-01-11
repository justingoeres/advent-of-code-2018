package org.jgoeres.adventofcode.Day20;

public class RunDay20 {
    static final String DEFAULT_PATH_TO_INPUTS = "day20/test.txt";

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = true;

    static Building building = new Building(DEFAULT_PATH_TO_INPUTS);

    public static void problem20A() {
        problem20A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem20A(String pathToInputs) {
        /*
        What is the largest number of doors you would be required 
        to pass through to reach a room? That is, find the room 
        for which the shortest path from your starting location 
        to that room would require passing through the most doors; 
        what is the fewest doors you can pass through to reach it?
        */
        System.out.println("=== DAY 20A ===");
        System.out.println(building);
        return 0;
    }

    public static void problem20B() {
        problem20B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem20B(String pathToInputs) {
        /*
        Problem description
        */
        System.out.println("=== DAY 20B ===");

        return 0;
    }
}
