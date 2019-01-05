package org.jgoeres.adventofcode.Day17;

public class RunDay17 {
    static final String DEFAULT_PATH_TO_INPUTS = "day17/input.txt";
    static final boolean PRINT_RESERVOIR_WHEN_DONE = false;

    static Reservoir reservoir;

    public static void problem17A() {
        problem17A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem17A(String pathToInputs) {
        /*
        How many tiles can the water reach within the range of y values in your scan?
        */
        System.out.println("=== DAY 17A ===");

        reservoir = new Reservoir(pathToInputs);

        while (!reservoir.getWaterCellStack().isEmpty()) {
            // As long as there's still cells to process.
            reservoir.processWaterStack();
        }

        if (PRINT_RESERVOIR_WHEN_DONE) {
            reservoir.printReservoir();
        }

        int numWaterTiles = 0;

        for (XYPair cell : reservoir.waterCells) { // Count up the tiles that are within our scan area.
            numWaterTiles += (reservoir.isInBounds(cell) ? 1 : 0);
        }

        System.out.println("Number of water tiles:\t" + numWaterTiles);
        return numWaterTiles;
        // Answer:
        // Number of water tiles:	34541
    }

    public static void problem17B() {
        problem17B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem17B(String pathToInputs) {
        /*
        How many water tiles are left after the water spring stops
        producing water and all remaining water not at rest has drained?
        */
        System.out.println("=== DAY 17B ===");

        int numContainedWaterTiles = 0;
        // This is as simple as counting up our "solid" water.
        for (XYPair cell : reservoir.waterCells) { // Count up the tiles that are within our scan area.
            numContainedWaterTiles += (reservoir.isSolidWater(cell) ? 1 : 0);
        }

        System.out.println("Number of contained water tiles:\t" + numContainedWaterTiles);
        return numContainedWaterTiles;
        // Answer:
        // Number of contained water tiles:	28000  (first try club!)
    }
}
