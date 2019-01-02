package org.jgoeres.adventofcode.Day17;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Reservoir {
    HashSet<XYPair> wallCells = new HashSet<>();
    XYPair TopLeft = new XYPair(Integer.MAX_VALUE, Integer.MAX_VALUE);
    XYPair BottomRight = new XYPair(Integer.MIN_VALUE, Integer.MIN_VALUE);
    XYPair waterSource = new XYPair(500, 0); // per problem statement

    XYPair one = new XYPair(1, 1);
    XYPair two = new XYPair(1, 1);

    private static final boolean DEBUG_PRINT_RESERVOIR = false;

    public Reservoir(String pathToFile) {

        wallCells.add(one);
        System.out.println(wallCells.contains(two));

        loadReservoir(pathToFile);
    }

    private void loadReservoir(String pathToFile) {
        /*
        File looks like:
            x=399, y=453..458
            x=557, y=590..599
            y=1182, x=364..369
         */

        Pattern hPattern = Pattern.compile("^y.+"); // y is constant, run is in x.
        Pattern vPattern = Pattern.compile("^x.+"); // x is constant, run is in y.

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                Matcher hMatcher = hPattern.matcher(line);
                Matcher vMatcher = vPattern.matcher(line);

                if (hMatcher.matches()) { // If this is a HORIZONTAL wall.
                    try (Scanner sc = new Scanner(line)) {
                        sc.useDelimiter("\\D+");
                        int y = sc.nextInt();
                        int xMin = sc.nextInt();
                        int xMax = sc.nextInt();

                        for (int x = xMin; x <= xMax; x++) {
                            addAndCheckExtents(x, y);
                        }

                    }
                }
                if (vMatcher.matches()) { // If this is a VERTICAL wall.
                    try (Scanner sc = new Scanner(line)) {
                        sc.useDelimiter("\\D+");
                        int x = sc.nextInt();
                        int yMin = sc.nextInt();
                        int yMax = sc.nextInt();

                        for (int y = yMin; y <= yMax; y++) {
                            addAndCheckExtents(x, y);

                        }
                    }
                }

            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        if (DEBUG_PRINT_RESERVOIR) {
            printReservoir();
        }
        return;
    }

    private void addAndCheckExtents(int x, int y) {
        wallCells.add(new XYPair(x, y));

        // Check the x extents
        if (x < TopLeft.getX()) {
            TopLeft.setX(x);
        }
        if (x > BottomRight.getX()) {
            BottomRight.setX(x);
        }

        // Check the y extents
        if (y < TopLeft.getY()) {
            TopLeft.setY(y);
        }
        if (y > BottomRight.getY()) {
            BottomRight.setY(y);
        }

    }

    private void printReservoir() {
        for (int y = 0; y <= BottomRight.getY(); y++) { // start at y=0 so we print the water source.
            String output = "";
            for (int x = TopLeft.getX(); x <= BottomRight.getX(); x++) {
                if (wallCells.contains(new XYPair(x, y))) {
                    output += "#";
                } else if ((x == waterSource.getX() && y == waterSource.getY())) {
                    output += "+";

                } else {
                    output += ".";
                }
            }
            System.out.println(output);
        }
    }
}
