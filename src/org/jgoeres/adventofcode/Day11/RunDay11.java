package org.jgoeres.adventofcode.Day11;

public class RunDay11 {
    static final Integer gridSN = 5034; // From problem webpage.
    static final Integer gridSize = 300; // 300x300
//    static final Integer gridSN = 18; // From problem webpage.
//    static final Integer gridSize = 100; // 300x300

    static final Integer tileSize = 3; // 3x3

    static final boolean DEBUG_PRINT_GRID = false;
    static final boolean DEBUG_PRINT_TOTALPOWER = false;

    public static void problem11A() {
    /*
    Each fuel cell has a coordinate ranging from 1 to 300 in both the X (horizontal)
    and Y (vertical) direction. In X,Y notation, the top-left cell is 1,1, and the
    top-right cell is 300,1.

    The interface lets you select any 3x3 square of fuel cells. To increase your
    chances of getting to your destination, you decide to choose the 3x3 square with
    the largest total power.

    The power level in a given fuel cell can be found through the following process:

        - Find the fuel cell's rack ID, which is its X coordinate plus 10.
        - Begin with a power level of the rack ID times the Y coordinate.
        - Increase the power level by the value of the grid serial number (your puzzle input).
        - Set the power level to itself multiplied by the rack ID.
        - Keep only the hundreds digit of the power level (so 12345 becomes 3;
            numbers with no hundreds digit become 0).
        - Subtract 5 from the power level.

    For example, to find the power level of the fuel cell at 3,5 in a grid
    with serial number 8:

        The rack ID is 3 + 10 = 13.
        The power level starts at 13 * 5 = 65.
        Adding the serial number produces 65 + 8 = 73.
        Multiplying by the rack ID produces 73 * 13 = 949.
        The hundreds digit of 949 is 9.
        Subtracting 5 produces 9 - 5 = 4.

    So, the power level of this fuel cell is 4.

    What is the X,Y coordinate of the top-left fuel cell of the
    3x3 square with the largest total power?
    */
        System.out.println("=== DAY 11A ===");

        FuelGrid fuelGrid = new FuelGrid(gridSN, gridSize, tileSize);

//        FuelGrid fuelGrid = new FuelGrid(8);
//        Integer temp = fuelGrid.calculateCellPowerLevel(3,5);

//        FuelGrid fuelGrid = new FuelGrid(57);
//        Integer temp = fuelGrid.calculateCellPowerLevel(122,79);

//        FuelGrid fuelGrid = new FuelGrid(39);
//        Integer temp = fuelGrid.calculateCellPowerLevel(217,196);

//        FuelGrid fuelGrid = new FuelGrid(71);
//        Integer temp = fuelGrid.calculateCellPowerLevel(101,153);

//        System.out.println(temp);

        if (DEBUG_PRINT_GRID) {
            for (int x = 1; x <= gridSize; x++) { // rows
                System.out.print(x + ":\t"); // print row #
                for (int y = 1; y <= gridSize; y++) { // columns
                    System.out.print(fuelGrid.getGridCell(x, y) + "\t"); // print cell data for this row
                }
                System.out.println(); // CR/LF at end of row
            }
            System.out.println(); // spaces before the next outputs.
            System.out.println();
        }

        // Iterate over the whole grid and find the maximum power tile.
        Integer maxPower = null;
        Integer maxX = null;
        Integer maxY = null;
        for (int x = 1; x <= gridSize; x++) { // rows

            if (DEBUG_PRINT_TOTALPOWER) {
                System.out.print(x + ":\t"); // print row #
            }

            for (int y = 1; y <= gridSize; y++) { // columns
                Integer tilePower = fuelGrid.getTileTotalPower(x, y);
                if ((maxPower == null) || (tilePower > maxPower)) {
                    maxPower = tilePower;
                    maxX = x;
                    maxY = y;
                }
                if (DEBUG_PRINT_TOTALPOWER) {
                    System.out.print(tilePower + "\t");// print cell data for this row
                }
            }
            if (DEBUG_PRINT_TOTALPOWER) {
                System.out.println(); // CR/LF at end of row
            }
        }
        // Done scanning the grid, print our results.
        System.out.println("Max tile power of " + maxPower + " at cell (" + maxX + "," + maxY + ")");
        System.out.println("Top left of this tile is (" + (maxX - 1) + "," + (maxY - 1) + ")");
        // Answer:
        // Max tile power of 29 at cell (236,64)
        // Top left of this tile is (235,63)
    }

    public static void problem11B() {
    /*
    Problem Description
    */
        System.out.println("=== DAY 11B ===");
    }

}
