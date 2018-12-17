package org.jgoeres.adventofcode.Day11;

public class RunDay11 {
    static final Integer gridSN = 5034; // From problem webpage.

    public static void problem11A() {
    /*
    Each fuel cell has a coordinate ranging from 1 to 300 in both the X (horizontal)
    and Y (vertical) direction. In X,Y notation, the top-left cell is 1,1, and the
    top-right cell is 300,1.

    The interface lets you select any 3x3 square of fuel cells. To increase your c
    hances of getting to your destination, you decide to choose the 3x3 square with
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

//        FuelGridService fuelGridService = new FuelGridService(gridSN);

//        FuelGridService fuelGridService = new FuelGridService(8);
//        Integer temp = fuelGridService.calculateCellPowerLevel(3,5);

//        FuelGridService fuelGridService = new FuelGridService(57);
//        Integer temp = fuelGridService.calculateCellPowerLevel(122,79);

//        FuelGridService fuelGridService = new FuelGridService(39);
//        Integer temp = fuelGridService.calculateCellPowerLevel(217,196);

        FuelGridService fuelGridService = new FuelGridService(71);
        Integer temp = fuelGridService.calculateCellPowerLevel(101,153);

        System.out.println(temp);
    }

    public static void problem11B() {
    /*
    Problem Description
    */
        System.out.println("=== DAY 11B ===");
    }

}
