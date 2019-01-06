package org.jgoeres.adventofcode.Day18;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import static org.jgoeres.adventofcode.Day18.Acre.LUMBERYARD;
import static org.jgoeres.adventofcode.Day18.Acre.OPEN;
import static org.jgoeres.adventofcode.Day18.Acre.TREES;

public class Forest {
    //    Acre[][] currentAcres, nextAcres;
    int forestSize;
    AcresWrapper currentAcresWrapper;
    AcresWrapper nextAcresWrapper;


    public Forest(String pathToFile) {
        loadForest(pathToFile);
    }

    private void loadForest(String pathToFile) {
        /*
        File looks like:
        .#....|###
        #.|.|.||.#
        ..|#...#||
         */

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            int i = 0;  // rows
            Acre nextAcreType = null;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                if (i == 0) {
                    // Use the length of the first line to figure out the dimensions of the forest.
                    forestSize = line.length();
                    Acre[][] currentAcres = new Acre[forestSize][forestSize];
                    Acre[][] nextAcres = new Acre[forestSize][forestSize];

                    currentAcresWrapper = new AcresWrapper(currentAcres);
                    nextAcresWrapper = new AcresWrapper(nextAcres);
                }
                try (Scanner sc = new Scanner(line)) {

                    sc.useDelimiter("");    // Scan the line character by character.
                    int j = 0;  // columns
                    while (sc.hasNext()) {
                        Character nextAcre = sc.next().charAt(0);    // Get the next character.
                        switch (nextAcre) { // What kind of acre is this?
                            case '.':   // Why can't I use OPEN.asChar() here??
                                nextAcreType = OPEN;
                                break;
                            case '|':
                                nextAcreType = TREES;
                                break;
                            case '#':
                                nextAcreType = LUMBERYARD;
                                break;
                        }
                        currentAcresWrapper.c[i][j] = nextAcreType;  // Stick it in the forest.
                        j++;    // go to next column of Acres.
                    }
                }
                i++; // go to next row of Acres.
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void doTimerTick() {
        calculateNextAcres();
        swap(currentAcresWrapper, nextAcresWrapper); // swap current & next

    }

    public void calculateNextAcres() {
        for (int i = 0; i < forestSize; i++) {  // down the rows
            for (int j = 0; j < forestSize; j++) {  // across the columns
                // Calculate the value of this acre on the next tick.
                Acre nextAcreType = calculateNextAcreType(i, j);
                nextAcresWrapper.c[i][j] = nextAcreType;
            }
        }
    }

    private Acre calculateNextAcreType(int i, int j) {
        Acre nextAcreType = null;
        switch (currentAcresWrapper.c[i][j]) {
            case OPEN:
                nextAcreType = calculateNextFromOpen(i, j);
                break;
            case TREES:
                nextAcreType = calculateNextFromTrees(i, j);
                break;
            case LUMBERYARD:
                nextAcreType = calculateNextFromLumberyard(i, j);
                break;
        }
        return nextAcreType;
    }

    private Acre calculateNextFromOpen(int i, int j) {
        /*
        An open acre will become filled with trees if three or
        more adjacent acres contained trees. Otherwise, nothing happens.
        */

        ArrayList<Acre> surroundingAcres = getSurroundingAcres(i, j);
        int treeCount = 0;
        for (Acre acre : surroundingAcres) {
            if (acre.equals(TREES)) {   // count the trees
                treeCount++;
            }
            if (treeCount >= 3) {
                // As soon as we have enough trees, we turn into one.
                return TREES;
            }
        }
        // If we're here, there weren't enough trees. Stay OPEN.
        return OPEN;
    }

    private Acre calculateNextFromTrees(int i, int j) {
        /*
        An acre filled with trees will become a lumberyard if three or
        more adjacent acres were lumberyards. Otherwise, nothing happens.
        */

        ArrayList<Acre> surroundingAcres = getSurroundingAcres(i, j);
        int lumberCount = 0;
        for (Acre acre : surroundingAcres) {
            if (acre.equals(LUMBERYARD)) {   // count the trees
                lumberCount++;
            }
            if (lumberCount >= 3) {
                // As soon as we have enough lumberyard, we turn into one.
                return LUMBERYARD;
            }
        }
        // If we're here, there weren't enough trees. Stay TREES.
        return TREES;
    }

    private Acre calculateNextFromLumberyard(int i, int j) {
        /*
        An acre containing a lumberyard will remain a lumberyard if it was
        adjacent to at least one other lumberyard and at least one acre
        containing trees. Otherwise, it becomes open.
        */

        ArrayList<Acre> surroundingAcres = getSurroundingAcres(i, j);
        int treeCount = 0;
        int lumberCount = 0;
        for (Acre acre : surroundingAcres) {
            if (acre.equals(TREES)) {   // count the trees
                treeCount++;
            }
            if (acre.equals(LUMBERYARD)) {   // count the trees
                lumberCount++;
            }
            if (treeCount >= 1 && lumberCount >= 1) {
                // As soon as we have enough lumberyards & trees, stay a lumberyard.
                return LUMBERYARD;
            }
        }
        // If we're here, there weren't enough trees & lumberyards. Become OPEN..
        return OPEN;
    }

    private ArrayList<Acre> getSurroundingAcres(int i, int j) {
        int[][] relativeCoords = {{-1, -1},
                {0, -1},
                {1, -1},
                {-1, 0},
                {1, 0},
                {-1, 1},
                {0, 1},
                {1, 1}};

        ArrayList<Acre> surroundingAcres = new ArrayList<>();

        for (int[] relative : relativeCoords) {
            // for each relative coordinate
            Acre thisAcre;
            try {
                thisAcre = currentAcresWrapper.c[i + relative[0]][j + relative[1]];
                surroundingAcres.add(thisAcre);
            } catch (IndexOutOfBoundsException e) {
                // If we try to index out of bounds, don't add anything to the ArrayList.
            }
        }
        return surroundingAcres;
    }

    public int calculateResourceValue() {
        /*
        Multiplying the number of wooded acres by the number of lumberyards gives the total resource value.
         */
        int resourceValue;

        int numTrees = 0;
        int numLumberYards = 0;
        for (int i = 0; i < forestSize; i++) {  // down the rows
            for (int j = 0; j < forestSize; j++) {  // across the columns
                if (currentAcresWrapper.c[i][j] == TREES) {
                    numTrees++;
                } else if (currentAcresWrapper.c[i][j] == LUMBERYARD) {
                    numLumberYards++;
                }
            }
        }
        resourceValue = numTrees * numLumberYards;
        return resourceValue;
    }

    public void printForest() {
        for (int i = 0; i < forestSize; i++) {  // down the rows
            String output = "";
            for (int j = 0; j < forestSize; j++) {  // across the columns
                output += currentAcresWrapper.c[i][j].asChar();
            }
            // print the line.
            System.out.println(output);
        }
        System.out.println(); // blank line after
    }

    public static void swap(AcresWrapper aw1,
                            AcresWrapper aw2) {
        Acre[][] temp = aw1.c;
        aw1.c = aw2.c;
        aw2.c = temp;
    }


    // A Wrapper over class that is used for swapping
    class AcresWrapper {
        Acre[][] c;

        // Constructor
        AcresWrapper(Acre[][] c) {
            this.c = c;
        }
    }
}

