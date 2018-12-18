package org.jgoeres.adventofcode.Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Greenhouse {

    private ArrayList<Pot> pots = new ArrayList<>();
    private ArrayList<Rule> rules = new ArrayList<>();

    public Greenhouse(String pathToFile) {
        loadPotsAndRules(pathToFile);
    }

    private void loadPotsAndRules(String pathToFile) {
           /*
        File looks like:
            initial state: #..######..#....#####..###.##..#######.####...####.##..#....#.##.....########.#...#.####........#.#.

            #...# => .
            ##.## => .
            ###.. => .
            .#### => .
            #.#.# => .
            ##..# => #
            ..#.# => #
            .##.. => #
         */

//        Pattern p = Pattern.compile("position=<([ -]?\\d+), ([ -]?\\d+)> velocity=<([ -]\\d+), ([ -]?\\d+)>");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            String initialPots;


            // First line of the file has pots.
            line = br.readLine(); // read the first line ("initial state:...")
            initialPots = line.substring(15); // "initial string: " is 15 characters long, so jump past that.

            // Create all the pots.
            Integer potId = 0; // potIds start at 0.
            for (Character potHasPlant : initialPots.toCharArray()) {
                boolean hasPlant = GreenhouseService.hasPlantCharToBool(potHasPlant);

                Pot pot = new Pot(potId, hasPlant);
                pots.add(pot); // Add the new pot to our list.
                potId++; // next potId!
            }

            br.readLine(); // Skip the next line (it's blank).

            Pattern p = Pattern.compile("([#\\.]+) => ([#\\.])");

            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                Rule rule = new Rule(m.group(1), m.group(2));
                rules.add(rule);


//                System.out.println(m.group(1));
//                System.out.println(m.group(2));

            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

}
