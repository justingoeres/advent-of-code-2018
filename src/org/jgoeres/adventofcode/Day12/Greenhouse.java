package org.jgoeres.adventofcode.Day12;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Greenhouse {

    private ArrayList<Pot> pots = new ArrayList<>();
    private ArrayList<Rule> rules = new ArrayList<>();
    private BigInteger potsBitfield = BigInteger.ZERO;
    private ArrayList<BigInteger> rulesAsBitfield = new ArrayList<>();

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

                if (hasPlant){
                    potsBitfield = potsBitfield.add(BigInteger.ONE.shiftLeft(potId));
                }
                potId++; // next potId!
            }
            // Shift the bitfield two more to make room for pot #s -2 and -1.
            potsBitfield = potsBitfield.shiftLeft(2);


            br.readLine(); // Skip the next line (it's blank).

            // Read all the rules.
            Pattern p = Pattern.compile("([#\\.]+) => ([#\\.])");

            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                Rule rule = new Rule(m.group(1), m.group(2));
                rules.add(rule);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        for (Rule rule : rules) {
            // For each "PLANT WILL BE TRUE" rule, convert it to a bitfield (int) we can use
            // for binary math. It will only be 5 bits wide.
            if (rule.willHavePlant()) {
                // if this is a PLANT WILL BE TRUE
                BigInteger plantMask = BigInteger.ZERO;
                // take the leftmost bit as lowest.
                int shift = 0;
                for (boolean hasPlant : rule.getHasPlants()) {
                    if (hasPlant) {
                        BigInteger newMaskBit = BigInteger.ONE.shiftLeft(shift);
                        plantMask = plantMask.add(newMaskBit); // add 1 shifted by the position of this digit in the field.
                    }
                    shift++;
                }

                // Now that we've got the 5-bit-wide bitfield, copy it out to be 100 bits' worth
                BigInteger filledPlantMask = BigInteger.ZERO;
                for (int i = 0; i < 20; i++) { // 20 copies of the 5-wide bitfield = 100 bits
                    BigInteger toAdd = plantMask.shiftLeft(5 * i);
                    filledPlantMask = filledPlantMask.add(toAdd);
                }
                rulesAsBitfield.add(filledPlantMask);
            }
        }
    }

    public ArrayList<Pot> getPots() {
        return pots;
    }

    public ArrayList<Rule> getRules() {
        return rules;
    }

    public ArrayList<BigInteger> getRulesAsBitfield() {
        return rulesAsBitfield;
    }
}
