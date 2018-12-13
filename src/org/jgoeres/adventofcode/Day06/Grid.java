package org.jgoeres.adventofcode.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grid {
    private ArrayList<GridPoint> gridPoints = new ArrayList<>();

    public Grid(String pathToFile) {
        loadGrid(pathToFile);
    }

    private void loadGrid(String pathToFile) {
        /* The file looks like
            275, 276
            176, 108
            270, 134
            192, 224
         */


        Pattern p = Pattern.compile("(\\d+), (\\d+)");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                Integer gridX = Integer.parseInt(m.group(1));
                Integer gridY = Integer.parseInt(m.group(2));

                GridPoint gridPoint = new GridPoint(gridX, gridY);

                System.out.println(gridPoint);

                // Put this event in the schedule.
                this.getGridPoints().add(gridPoint);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public ArrayList<GridPoint> getGridPoints() {
        return gridPoints;
    }

    public void setGridPoints(ArrayList<GridPoint> gridPoints) {
        this.gridPoints = gridPoints;
    }
}
