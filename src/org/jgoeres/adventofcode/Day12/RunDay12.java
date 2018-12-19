package org.jgoeres.adventofcode.Day12;


import java.util.ArrayList;
import java.util.Collections;

import static org.jgoeres.adventofcode.Day12.GreenhouseService.printPots;

public class RunDay12 {
    static String pathToInputs = "day12/input.txt";

    static Greenhouse greenhouse = new Greenhouse(pathToInputs);

    static boolean DEBUG_PRINT_GREENHOUSE = true;
    static String SPACE = " ";

    public static void problem12A() {
    /*
    After 20 generations, what is the sum of the numbers of all pots which contain a plant?
    */
        System.out.println("=== DAY 12A ===");

        Integer GENERATIONS_TO_RUN = 20;

        ArrayList<Pot> currentPots = greenhouse.getPots(); // set the currentPots to be the initial state of the greenhouse
        ArrayList<Rule> rules = greenhouse.getRules(); // use the greenhouse's rules.

        if (DEBUG_PRINT_GREENHOUSE) {
            System.out.println("S:\t" + addSpaces(currentPots) + printPots(currentPots));
        }

        for (int generation = 1; generation <= GENERATIONS_TO_RUN; generation++) {
            currentPots = GreenhouseService.calculateNextPots(currentPots, rules);

            if (DEBUG_PRINT_GREENHOUSE) {
                System.out.println(generation + ":\t" + addSpaces(currentPots) + printPots(currentPots));
            }
        }

    // Now count up the indices of the pots with plants in them. That's our answer.
        Integer potTotal = 0;
        for(Pot pot:currentPots) {
            if(pot.isHasPlant()) {
                potTotal += pot.getPotId();
            }
        }
        System.out.println("Total sum of all occupied Pot IDs:\t" + potTotal);

        // Answer:
        // Total sum of all occupied Pot IDs:	2736
    }

    public static void problem12B() {
    /*
    Problem description
    */
        System.out.println("=== DAY 12B ===");

    }

    private static String addSpaces(ArrayList<Pot> pots) {
        Integer numPots = pots.size();

        Integer numSpaces = 40 - (numPots - 100) / 2;
        String output = String.join("", Collections.nCopies(numSpaces, SPACE));
        return output;

    }
}



