package org.jgoeres.adventofcode.Day15;

public class RunDay15 {
    static final String pathToInputs = "day15/input.txt";

    static final boolean DEBUG_PRINT_EACH_TURN = false;

    public static void problem15A() {
        problem15A(pathToInputs);
    }

    public static int problem15A(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 15A ===");

        Battle battle = new Battle(pathToInputs);

        // Run the entire battle to completion
        battle.runBattle(DEBUG_PRINT_EACH_TURN);
        int roundsComplete = battle.getRoundsComplete();

        System.out.println("\n============ FINAL RESULT AFTER " + roundsComplete + " ROUNDS ============");
        battle.printBattle();

        Race winner = battle.getWinner();
        int totalScore = battle.calculateScore(winner);
        System.out.println("Rounds complete:\t" + roundsComplete);
        System.out.println("Winner:\t\t" + winner);
        System.out.println("Total Hitpoints:\t\t" + totalScore);
        System.out.println("Answer:\t" + roundsComplete + " x " + totalScore + " =\t" + (roundsComplete * totalScore));
        /* Answer:
        Rounds complete:	85
        Winner:		RACE_GOBLIN
        Total Hitpoints:		2794
        Answer:	85 x 2794 =	237490
         */

        return totalScore * roundsComplete;
    }

    public static void problem15B() {
        problem15B(pathToInputs);
    }

    public static void problem15B(String pathToInputs) {

        /*
        find the outcome of the battle in which the Elves have the lowest integer attack power
        (at least 4) that allows them to win without a single death.
        The Goblins always have an attack power of 3.
        */
        System.out.println("=== DAY 15B ===");

        // load the battle from scratch each time through (surely there's a faster way to do this)
        Battle battle = new Battle(pathToInputs);

        // Set the elves' attack power
        battle.setRaceAttackPower(Race.RACE_ELF, 4); // TODO: Iterate attack power.


    }
}
