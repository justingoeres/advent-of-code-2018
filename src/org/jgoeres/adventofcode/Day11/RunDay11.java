package org.jgoeres.adventofcode.Day11;

public class RunDay11 {
    static final Integer gridSN = 5034; // << My input from problem webpage.
    static final Integer gridSize = 300; // 300x300
//    static final Integer gridSN = 42; // Example from problem webpage.
//    static final Integer gridSize = 100; // smaller grid for examples, to make them go faster.

    static final Integer tileSize = 3; // 3x3

    static FuelGrid fuelGrid = new FuelGrid(gridSN, gridSize, tileSize);

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
        CellPower maxCell = calculateMaxPowerTile(fuelGrid, tileSize);
        // Done scanning the grid, print our results.
        System.out.println("Max tile power of " + maxCell.getPower() + " at cell (" + maxCell.getX() + "," + maxCell.getY() + ")");
        // Answer:
        // Max tile power of 29 at cell (235,63)
    }

    public static void problem11B() {
    /*
    You discover a dial on the side of the device; it seems to let you select a square of any size,
    not just 3x3. Sizes from 1x1 to 300x300 are supported.

    Realizing this, you now must find the square of any size with the largest total power.
    Identify this square by including its size as a third parameter after the top-left coordinate:
    a 9x9 square with a top-left corner of 3,5 is identified as 3,5,9.

    For example:

        - For grid serial number 18, the largest total square (with a total power of 113) is 16x16
            and has a top-left corner of 90,269, so its identifier is 90,269,16.
        - For grid serial number 42, the largest total square (with a total power of 119) is 12x12
            and has a top-left corner of 232,251, so its identifier is 232,251,12.

    What is the X,Y,size identifier of the square with the largest total power?
    */
        System.out.println("=== DAY 11B ===");

        CellPower maxCell = new CellPower();
        // Try every tile size, and track the max cell!
        for (Integer tileSize = 1; tileSize <= 300; tileSize++) {
            CellPower tileSizeMaxCell = calculateMaxPowerTile(fuelGrid, tileSize);

            System.out.println("Tile Size: " + tileSize + "x" + tileSize + ":\t("
                    + tileSizeMaxCell.getX() + ","
                    + tileSizeMaxCell.getY() + ")\t@ "
                    + tileSizeMaxCell.getPower());
            if ((maxCell.getPower() == null) || tileSizeMaxCell.getPower() > maxCell.getPower()) {
                maxCell.setPower(tileSizeMaxCell.getPower());
                maxCell.setX(tileSizeMaxCell.getX());
                maxCell.setY(tileSizeMaxCell.getY());
                maxCell.setTileSize(tileSize);
            }
            if (tileSizeMaxCell.getPower() == 0) {
                // Does the problem degenerate and start returning 0's after a certain size??
                // WHY!?!
                break;
            }
        }
        System.out.println("Max tile power of " + maxCell.getPower()
                + " at cell (" + maxCell.getX() + "," + maxCell.getY() + ") with tile size "
                + maxCell.getTileSize());
        System.out.println("Answer entry: (" + maxCell.getX() + "," + maxCell.getY() + "," + maxCell.getTileSize() + ")");

        // Answer:
        // Max tile power of 109 at cell (229,251) with tile size 16
        // Answer entry: (229,251,109)
    }


    private static CellPower calculateMaxPowerTile(FuelGrid fuelGrid, Integer tileSize) {
        CellPower maxCell = new CellPower();
        for (int x = 1; x <= fuelGrid.getGridSize(); x++) { // rows

            if (DEBUG_PRINT_TOTALPOWER) {
                System.out.print(x + ":\t"); // print row #
            }

            for (int y = 1; y <= gridSize; y++) { // columns
                Integer tilePower = fuelGrid.getTileTotalPower(x, y, tileSize);
                if ((maxCell.getPower() == null) || (tilePower > maxCell.getPower())) {
                    maxCell.setPower(tilePower);
                    maxCell.setX(x);
                    maxCell.setY(y);
                }
                if (DEBUG_PRINT_TOTALPOWER) {
                    System.out.print(tilePower + "\t");// print cell data for this row
                }
            }
            if (DEBUG_PRINT_TOTALPOWER) {
                System.out.println(); // CR/LF at end of row
            }
        }
        return maxCell;
    }
}