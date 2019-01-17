package org.jgoeres.adventofcode.Day24;

import java.util.TreeSet;

public class RunDay24 {
    static final String DEFAULT_PATH_TO_INPUTS = "day24/input.txt";

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = false;

    static Battle battle;

    public static void problem24A() {
        problem24A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem24A(String pathToInputs) {
        /*
        Problem description
        */
        System.out.println("=== DAY 24A ===");

        battle = new Battle(pathToInputs);

        TreeSet<Group> winner = null;
        while (winner == null) {
            winner = battle.doTimerTick();
        }

        int result = battle.totalUnitCount(winner);
        String winnerName = winner.first().type.toString();

        System.out.println(winnerName + " wins with a total of " + result + " units remaining.");
        return result;

        // Answer:
        // 15186 (too high)
    }

    public static void problem24B() {
        problem24B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem24B(String pathToInputs) {
        /*
        Problem description
        */
        System.out.println("=== DAY 24B ===");

        return 0;
    }

}
