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

    public static int problem22A(int targetX, int targetY, int depth) {
        /*
        What is the total risk level for the smallest rectangle
        that includes 0,0 and the target's coordinates?
        */
        System.out.println("=== DAY 22A ===");
        caveSystem = new CaveSystem(targetX, targetY, depth);
        if (DEBUG_PRINT_CAVE) {
            caveSystem.printCaveSystem();
        }

        int totalRisk = caveSystem.calculateAreaRiskLevel(0, targetX, 0, targetY);
        System.out.println("Total risk level of target area:\t" + totalRisk);

        return totalRisk;
        // Answer:
        // Total risk level of target area:	11462
    }

    public static void problem22B() {
        problem22B(TARGET_X, TARGET_Y, DEPTH);
    }

    public static int problem22B(int targetX, int targetY, int depth) {
        /*
        - In rocky regions, you can use the climbing gear or the torch. You cannot use neither (you'll likely slip and fall).
        - In wet regions, you can use the climbing gear or neither tool. You cannot use the torch
          (if it gets wet, you won't have a light source).
        - In narrow regions, you can use the torch or neither tool. You cannot use the climbing gear (it's too bulky to fit).

        You start at 0,0 (the mouth of the cave) with the torch equipped

        What is the fewest number of minutes you can take to reach the target?
        */
        System.out.println("=== DAY 22B ===");
        return 0;
    }
}
