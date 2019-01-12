package org.jgoeres.adventofcode.Day22;

public class RunDay22 {

    static final boolean DEBUG_PRINT_CAVE = false;
    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = false;

    /* Justin's Puzzle Inputs */
    public static final int DEPTH = 7863;
    public static final int TARGET_X = 14;
    public static final int TARGET_Y = 760;

    public static CaveSystem caveSystem;

    public static int problem22A() {
        return problem22A(TARGET_X, TARGET_Y, DEPTH);
    }

    public static int problem22A(int TARGET_X, int TARGET_Y, int DEPTH) {
        /*
        What is the total risk level for the smallest rectangle
        that includes 0,0 and the target's coordinates?
        */
        System.out.println("=== DAY 22A ===");
        caveSystem = new CaveSystem(TARGET_X, TARGET_Y, DEPTH);
        if (DEBUG_PRINT_CAVE) {
            caveSystem.printCaveSystem();
        }

        int totalRisk = caveSystem.calculateAreaRiskLevel(0, TARGET_X, 0, TARGET_Y);
        System.out.println("Total risk level of target area:\t" + totalRisk);

        return totalRisk;
        // Answer:
        // Total risk level of target area:	11462
    }

    public static void problem22B() {
        problem22B("");
    }

    public static int problem22B(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 22B ===");
        return 0;
    }
}
