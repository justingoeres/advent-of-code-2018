package org.jgoeres.adventofcode.Day06;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Centers extends Grid {


    public Centers(String pathToFile) {
        loadGridPoints(pathToFile);
    }

    private void loadGridPoints(String pathToFile) {
        /* The file looks like
            275, 276
            176, 108
            270, 134
            192, 224
         */
        Pattern p = Pattern.compile("(\\d+), (\\d+)");

        Character gridPointName = 44; // ',' (comma)
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                Integer gridX = Integer.parseInt(m.group(1));
                Integer gridY = Integer.parseInt(m.group(2));

                GridPoint gridPoint = new GridPoint(gridX, gridY, gridPointName);

//                System.out.println(gridPoint);

                // Put this event in the schedule.
                this.getGridPoints().add(gridPoint);

                gridPointName++; // Go to the next character for naming.
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

}
