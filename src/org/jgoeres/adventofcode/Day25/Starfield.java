package org.jgoeres.adventofcode.Day25;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Starfield {
    HashSet<Star> allStars = new HashSet<>();

    public Starfield(String pathToFile) {
        loadStars(pathToFile);
    }

    private void loadStars(String pathToFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                Matcher matchStar = Pattern.compile("^(-?\\d+),(-?\\d+),(-?\\d+),(-?\\d+)$").matcher(line);
                if (matchStar.find()) {
                    Star newStar = new Star(
                            Integer.parseInt(matchStar.group(1)),
                            Integer.parseInt(matchStar.group(2)),
                            Integer.parseInt(matchStar.group(3)),
                            Integer.parseInt(matchStar.group(4)));
                    allStars.add(newStar);
                }

            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }
}
