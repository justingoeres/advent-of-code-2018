package org.jgoeres.adventofcode.Day15;

public class RunDay15 {
//    static final String pathToInputs = "day15/input.txt";
//static final String pathToInputs = "day15/input-example1.txt";
    static final String pathToInputs = "day15/input-example2.txt";
    static final boolean DEBUG_PRINT_EACH_TURN = false;

    public static void problem15A() {
        /*
        Problem Description
        */
        System.out.println("=== DAY 15A ===");

        Battle battle = new Battle(pathToInputs);

        int roundsComplete = 0;
        while (!battle.isOver()) {
            System.out.println("\n============ TURN #" + roundsComplete + " ============");
            battle.doTimerTick();
            if (DEBUG_PRINT_EACH_TURN) {
                battle.printBattle();
                battle.printArmies();
            }
            roundsComplete++;
        }

        battle.printArmies();

        Race winner = battle.getWinner();
        int totalScore = battle.calculateScore(winner);
        System.out.println("Rounds complete:\t" + roundsComplete);
        System.out.println("Winner:\t" + winner);
        System.out.println("Total Score:\t" + totalScore);
        System.out.println("Answer:\t" + roundsComplete + " x " + totalScore + " = " + (roundsComplete * totalScore));
    // Wrong Answer: 230989 (too low)
    }

    public static void problem15B() {

        /*
        Problem Description
        */
        System.out.println("=== DAY 15B ===");

    }
}
