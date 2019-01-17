package org.jgoeres.adventofcode.Day24;

import java.util.TreeSet;

import static org.jgoeres.adventofcode.Day24.GroupType.IMMUNE;

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
        How many units would the winning army have?
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
        // INFECTION wins with a total of 10723 units remaining.
    }

    public static void problem24B() {
        problem24B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem24B(String pathToInputs) {
        /*
        How many units does the immune system have left after getting the smallest boost it needs to win?
        */
        System.out.println("=== DAY 24B ===");

        // Reload the battle to revive all the units.
        battle = new Battle(pathToInputs);

        int boost = 39;  //  boost per cycle
        while (true) { // Run forever, break below.
            // Reload the battle to revive all the units.
            battle = new Battle(pathToInputs);

            battle.boostImmune(boost);

            int prevTotalArmies = battle.totalAllArmies();
            int totalArmies = 0;
            TreeSet<Group> winner = null;
            int round = 1;
            System.out.println("\n=========== BOOST " + boost + "===========");
            while (winner == null) {
                winner = battle.doTimerTick();

//                System.out.println("Round " + round
//                        + ":\tImmune:\t" + battle.totalUnitCount(battle.immuneSystem)
//                        + "\tInfection:\t" + battle.totalUnitCount(battle.infection));
                round++;
                // Are we in a stalemate?
                totalArmies = battle.totalAllArmies();
                if (totalArmies != prevTotalArmies) {
                    // no. continue.
                    prevTotalArmies = totalArmies;
                } else {
                    // yes. stop and try the next boost.
                    break;
                }

            }

            if (winner != null) {
                // If there's a winner, check if it was IMMUNE.
                int result = battle.totalUnitCount(winner);
                String winnerName = winner.first().type.toString();

                System.out.println("Boost " + boost + ":\t" + winnerName + " wins with a total of " + result + " units remaining. (" + round + " rounds)");
                if (winner.first().type == IMMUNE) {
                    ;
                    return result;  // Stop if IMMUNE wins
                }
            } else {
                // Otherwise, it was a tie.
                System.out.println("Boost " + boost + ":\t" + "Result is a stalemate with " + totalArmies + " units remaining. (" + round + " rounds)");
                ;
            }
            boost++;    // Increment boost and continue

            // Answer:
            //  4964 (too low)
            //  5099 (too low)
            //  5892 (wrong; boost 40)
        }
    }
}
