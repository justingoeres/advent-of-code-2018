package org.jgoeres.adventofcode.Day11;

public class FuelGrid {
    private Integer gridSN;
    private Integer[][] grid = null; //new Integer[5][10];
    private Integer tileSize;

    public FuelGrid(Integer gridSN, Integer size, Integer tileSize) {
        this.setGridSN(gridSN);
        this.setGrid(new Integer[size][size]);
        this.setTileSize(tileSize);

        calculateGridPowerLevels();
    }

    private void calculateGridPowerLevels() {
        for (int x = 0; x < grid.length; x++) { // rows
            for (int y = 0; y < grid[0].length; y++) { // columns
                grid[x][y] = calculateCellPowerLevel(x + 1, y + 1);
            }
        }
    }

    private Integer calculateCellPowerLevel(Integer x, Integer y) {
        Integer powerLevel = 0;
        Integer powerLevelDigit = 0;

//        - Find the fuel cell's rack ID, which is its X coordinate plus 10.
        Integer rackId = rackId(x);

        powerLevel =
//        - Begin with a power level of the rack ID times the Y coordinate.
                ((rackId * y)
//        - Increase the power level by the value of the grid serial number (your puzzle input).
                        + gridSN)
//        - Set the power level to itself multiplied by the rack ID.
                        * rackId;
//        - Keep only the hundreds digit of the power level (so 12345 becomes 3;
//          numbers with no hundreds digit become 0).
        if (powerLevel < 100) {
            powerLevelDigit = 0;
        } else {
            // Convert the number to a string
            String powerLevelString = powerLevel.toString();
            // Reverse it
            powerLevelString = new StringBuilder(powerLevelString).reverse().toString();
            Character powerLevelChar = powerLevelString.charAt(2); // hundreds digit of a reversed number.
            powerLevelDigit = Integer.parseInt(powerLevelChar.toString());
        }
//        - Subtract 5 from the power level.
        powerLevel = powerLevelDigit - 5;

        return powerLevel;
    }

    public Integer getTileTotalPower(Integer x0, Integer y0) {
        // x0 and y0 are the upper left corner of the tile.
        // The tiles are 3x3

        Integer totalPower = 0;
        Integer tileCenterDistance = (tileSize - 1) / 2;
        for (int x = -tileCenterDistance; x <= tileCenterDistance; x++) { // rows
            for (int y = -tileCenterDistance; y <= tileCenterDistance; y++) { // columns
                Integer gridCellPower = getGridCell(x0 + x, y0 + y);
                if (gridCellPower != null) {
                    totalPower += gridCellPower; // add up the power in all these cells
                } else {
                    // at least one cell of the grid is out of bounds,
                    // so this isn't a valid power cell.
                    // Just return 0;
                    return 0;
                }
            }
        }
        return totalPower;
    }

    public Integer getGridCell(Integer x, Integer y) {
        Integer gridCell = null;
        try {
            gridCell = grid[x - 1][y - 1]; // cells are queried from 1, not 0
        } catch (ArrayIndexOutOfBoundsException e) {
            return null; // If we index out of bounds, just return null
        }
        return gridCell;
    }


    private Integer rackId(Integer x) {
        Integer rackId = x + 10;
        return rackId;
    }

    public void setGridSN(Integer gridSN) {
        this.gridSN = gridSN;
    }

    public void setGrid(Integer[][] grid) {
        this.grid = grid;
    }

    public void setTileSize(Integer tileSize) {
        this.tileSize = tileSize;
    }
}
