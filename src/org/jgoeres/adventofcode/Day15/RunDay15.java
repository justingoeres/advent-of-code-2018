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

    public static int problem15B(String pathToInputs) {
        /*
        find the outcome of the battle in which the Elves have the lowest integer attack power
        (at least 4) that allows them to win without a single death.
        The Goblins always have an attack power of 3.
        */
        int roundsComplete;

        System.out.println("=== DAY 15B ===");
        Battle15B battle;

        int elvesAttackPower = 4; // start at 4.

        while (true) { // continue until NO elves die in battle
            // load the battle from scratch each time through (surely there's a faster way to do this)
            battle = new Battle15B(pathToInputs);

            // Set the elves' attack power
            battle.setRaceAttackPower(Race.RACE_ELF, elvesAttackPower); // TODO: Iterate attack power.

            System.out.println("Running battle with elves attack power:\t" + elvesAttackPower);
            // Run the entire battle to completion
            battle.runBattle(DEBUG_PRINT_EACH_TURN);
            roundsComplete = battle.getRoundsComplete();

            System.out.println("\n============ FINAL RESULT AFTER " + roundsComplete + " ROUNDS ============");
            battle.printBattle();

            System.out.println("Number of elves remaining:\t" + battle.getNumberOfUnits(Race.RACE_ELF));
            if (!(battle.elvesLostSomeone())) {
                break;  // If the elves DIDN'T lose anyone, we're done.
            } else {
                // The elves lost someone, so increase power and keep trying.
                elvesAttackPower++;
            }
        }

        Race winner = battle.getWinner();
        int totalScore = battle.calculateScore(winner);
        System.out.println("Rounds complete:\t" + roundsComplete);
        System.out.println("Winner:\t\t" + winner);
        System.out.println("Elves Attack Power:\t" + elvesAttackPower);
        System.out.println("Total Hitpoints:\t\t" + totalScore);
        System.out.println("Answer:\t" + roundsComplete + " x " + totalScore + " =\t" + (roundsComplete * totalScore));
        /* Answer:
        Number of elves remaining:	10
        Rounds complete:	24
        Winner:		RACE_ELF
        Elves Attack Power:	40
        Total Hitpoints:		1601
        Answer:	24 x 1601 =	38424
         */
        return totalScore * roundsComplete;

    }
}
