package org.jgoeres.adventofcode.Day11;

public class FuelGridService {
    private Integer gridSN;

    public FuelGridService(Integer gridSN) {
        this.gridSN = gridSN;
    }

    public Integer calculateCellPowerLevel(Integer x, Integer y) {
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

    private Integer rackId(Integer x) {
        Integer rackId = x + 10;
        return rackId;
    }
}
