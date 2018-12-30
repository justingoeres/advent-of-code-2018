package org.jgoeres.adventofcode.Day15;

public class RunDay15 {
//    static final String pathToInputs = "day15/input.txt";
//    static final String pathToInputs = "day15/input-example1.txt";
    // 47 rounds, 590 hp = 27730.

//    static final String pathToInputs = "day15/input-example2.txt";
    /*
    Combat ends after 37 full rounds
    Elves win with 982 total hit points left
    Outcome: 37 * 982 = 36334
     */

//    static final String pathToInputs = "day15/input-example3.txt";
    /*
    Combat ends after 46 full rounds
    Elves win with 859 total hit points left
    Outcome: 46 * 859 = 39514
     */

//    static final String pathToInputs = "day15/input-example4.txt";
    /*
    Combat ends after 35 full rounds
    Goblins win with 793 total hit points left
    Outcome: 35 * 793 = 27755
     */


//    static final String pathToInputs = "day15/input-example5.txt";
    /*
    Combat ends after 54 full rounds
    Goblins win with 536 total hit points left
    Outcome: 54 * 536 = 28944
     */

//    static final String pathToInputs = "day15/input-example6.txt";
    /*
    Combat ends after 20 full rounds
    Goblins win with 937 total hit points left
    Outcome: 20 * 937 = 18740
     */

//        static final String pathToInputs = "day15/examples/aoc-2018/15/input.txt";
    /*
    Outcome: 197025
     */

//    static final String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit1/input.txt";
    /*
    Outcome: 13400 (200 x 67) (PASS)
     */

    static String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit2/input.txt";
    /*
    Outcome: Part 1: 13987 (197 x 71) (PASS)
     */


    static final boolean DEBUG_PRINT_EACH_TURN = true;

    public static void problem15A() {
        problem15A(pathToInputs);
    }

    public static int problem15A(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 15A ===");

        Battle battle = new Battle(pathToInputs);

        int roundsComplete = 0;
        while (true) {
            boolean roundComplete = battle.doTimerTick();

            if (roundComplete) {
                roundsComplete++; // ... mark the round complete, then continue.
            }
            if (!battle.isOver()) { // If we're not done...
                if (DEBUG_PRINT_EACH_TURN) {
                    System.out.println("\n============ AFTER ROUND #" + roundsComplete + " ============");
                    battle.printBattle();
                }
            } else {
                break; // we're done.
            }
        }

        System.out.println("\n============ FINAL RESULT AFTER " + roundsComplete + " ROUNDS ============");
        battle.printBattle();

        Race winner = battle.getWinner();
        int totalScore = battle.calculateScore(winner);
        System.out.println("Rounds complete:\t" + roundsComplete);
        System.out.println("Winner:\t\t" + winner);
        System.out.println("Total Hitpoints:\t\t" + totalScore);
        System.out.println("Answer:\t" + roundsComplete + " x " + totalScore + " =\t" + (roundsComplete * totalScore));
        // Wrong Answer: 230989 (too low)
        // Wrong Answer: 234948 (too low)   (84 x 2797)
        // Wrong Answer: 237745 (too high)  (85 x 2797)
        return totalScore * roundsComplete;
    }

    public static void problem15B() {

        /*
        Problem Description
        */
        System.out.println("=== DAY 15B ===");

    }
}
