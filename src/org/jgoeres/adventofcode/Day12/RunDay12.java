package org.jgoeres.adventofcode.Day12;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import static org.jgoeres.adventofcode.Day12.GreenhouseService.printPots;

public class RunDay12 {
    static String pathToInputs = "day12/input.txt";

    static Greenhouse greenhouse = new Greenhouse(pathToInputs);

    static boolean DEBUG_PRINT_GREENHOUSE = false;
    static String SPACE = " ";

    public static void problem12A() {
    /*
    After 20 generations, what is the sum of the numbers of all pots which contain a plant?
    */
        System.out.println("=== DAY 12A ===");

        Integer GENERATIONS_TO_RUN = 20;

        ArrayList<Pot> currentPots = greenhouse.getPots(); // set the currentPots to be the initial state of the greenhouse
        ArrayList<Rule> rules = greenhouse.getRules(); // use the greenhouse's rules.

        for (int i = 0; i < 6; i++) {
            GreenhouseService.addPotToLeftEnd(false, currentPots);
        }
        if (DEBUG_PRINT_GREENHOUSE) {
            System.out.println("S:\t" + /*addSpaces(currentPots, GENERATIONS_TO_RUN) +*/ printPots(currentPots, false));
        }

        for (int generation = 1; generation <= GENERATIONS_TO_RUN; generation++) {
            ArrayList<Pot> nextPots = GreenhouseService.calculateNextPots(currentPots, rules);
            currentPots = nextPots;
            if (DEBUG_PRINT_GREENHOUSE) {
                System.out.println(generation + ":\t" +/* addSpaces(currentPots, GENERATIONS_TO_RUN) + */printPots(currentPots, false));
            }
        }

        // Now count up the indices of the pots with plants in them. That's our answer.
        Integer potTotal = 0;
        for (Pot pot : currentPots) {
            if (pot.isHasPlant()) {
                potTotal += pot.getPotId();
            }
        }
        System.out.println("Total sum of all occupied Pot IDs:\t" + potTotal);

        // Answer:
        // Total sum of all occupied Pot IDs:	2736
    }

    public static void problem12B() {
    /*
    After 50 BILLION (50000000000) generations, what is the sum of the numbers of all pots which contain a plant?
    */
        System.out.println("=== DAY 12B ===");

//        Integer GENERATIONS_TO_RUN = 50000000000;
        BigInteger GENERATIONS_TO_RUN = new BigInteger("50000000000");
        ArrayList<Pot> currentPots = greenhouse.getPots(); // set the currentPots to be the initial state of the greenhouse
        ArrayList<Rule> rules = greenhouse.getRules(); // use the greenhouse's rules.
        String lastPotsRendered = null;

        if (DEBUG_PRINT_GREENHOUSE) {
            System.out.println("S:\t" /* + addSpaces(currentPots, GENERATIONS_TO_RUN)*/ + printPots(currentPots, true));
        }

        BigInteger generation = BigInteger.ONE;
        Integer prevFirstFullPotId = null;
        Integer firstFullPotId = 0;
        while (true) { // This runs until we break below.
            currentPots = GreenhouseService.calculateNextPots(currentPots, rules);

            // Find the ID of the first full pot
            for (Pot pot : currentPots) {
                if (pot.isHasPlant()) {
                    firstFullPotId = pot.getPotId();
                    break;
                }
            }

            String potsRendered = printPots(currentPots, true);

            if (DEBUG_PRINT_GREENHOUSE) {
                System.out.println(generation + ":\t" + potsRendered);
            }

            if (potsRendered.equals(lastPotsRendered)) {
                // If the output string is the same twice in a row, we're done!
                break;
            } else {
                // Cache this output string to check next time.
                lastPotsRendered = potsRendered;
                prevFirstFullPotId = firstFullPotId;
            }
            generation = generation.add(BigInteger.ONE);
        }

        // Now count up the indices of the pots with plants in them. That's our answer.
        Integer potTotal = 0;
        for (Pot pot : currentPots) {
            if (pot.isHasPlant()) {
                potTotal += pot.getPotId();
            }
        }

        Integer step = firstFullPotId - prevFirstFullPotId;

        System.out.println("Total sum of all occupied Pot IDs after " + generation + " generations:\t" + potTotal);
        System.out.println("First pot migrated by " + step + " steps from previous step.");

        // Figure out where we'd be after the total target number of generations.
        BigInteger totalShift = GENERATIONS_TO_RUN.subtract(generation); // How many more steps to the target.

        // Now count up the indices of the pots with plants in them AFTER WE SHIFT THEM. That's our final answer.
        BigInteger potTotalBigInt = BigInteger.ZERO;
        for (Pot pot : currentPots) {
            if (pot.isHasPlant()) {
                potTotalBigInt = potTotalBigInt.add((BigInteger.valueOf(pot.getPotId()).add(totalShift)));
            }
        }

        System.out.println("Total sum of all occupied Pot IDs after " + GENERATIONS_TO_RUN + " generations:\t" + potTotalBigInt);

        // Answer:
//        Total sum of all occupied Pot IDs after 125 generations:	8780
//        First pot migrated by 1 steps from previous step.
//        Total sum of all occupied Pot IDs after 50000000000 generations:	3150000000905
    }

    private static String addSpaces(ArrayList<Pot> pots, Integer generations) {
        Integer numPots = pots.size();

        Integer numSpaces = generations * 2 - (numPots - 100) / 2 + 3;
        String output = String.join("", Collections.nCopies(numSpaces, SPACE));
        return output;

    }
}



