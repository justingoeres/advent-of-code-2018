package org.jgoeres.adventofcode.Day10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sky {
    private ArrayList<Star> stars = new ArrayList<>();

    public Sky() {
    }

    public Sky(String pathToFile) {
        loadSky(pathToFile);
    }

    private void loadSky(String pathToFile) {
           /*
        File looks like:
            position=<-53868, -10684> velocity=< 5,  1>
            position=<-43043, -43128> velocity=< 4,  4>
            position=< 11010,  54188> velocity=<-1, -5>
         */
        Pattern p = Pattern.compile("position=<([ -]?\\d+), ([ -]?\\d+)> velocity=<([ -]\\d+), ([ -]?\\d+)>");

        Integer starNum = 1; // Number stars starting at 1
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                Integer x0 = Integer.parseInt(m.group(1).trim());
                Integer y0 = Integer.parseInt(m.group(2).trim());
                Integer vX = Integer.parseInt(m.group(3).trim());
                Integer vY = Integer.parseInt(m.group(4).trim());

                Star newStar = new Star(x0, y0, vX, vY,starNum);
                stars.add(newStar);
                starNum++;
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public ArrayList<Star> getStars() {
        return stars;
    }
}
