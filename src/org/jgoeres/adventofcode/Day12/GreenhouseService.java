package org.jgoeres.adventofcode.Day12;

import java.util.ArrayList;

public abstract class GreenhouseService {
    public static boolean hasPlantCharToBool(Character hasPlantChar) {
        return (hasPlantChar == '#');
    }

    private static boolean DEBUG_PRINT = false;

    public static ArrayList<Pot> calculateNextPots(ArrayList<Pot> currentPots, ArrayList<Rule> rules) {
        ArrayList<Pot> nextPots = new ArrayList<>();

        // First, add two empty pots on the left & right ends of our current list so we
        // can use them to check rules.
        GreenhouseService.addPotToLeftEnd(false, currentPots);
        GreenhouseService.addPotToLeftEnd(false, currentPots);

        GreenhouseService.addPotToRightEnd(false, currentPots);
        GreenhouseService.addPotToRightEnd(false, currentPots);

        // Now check all the pots and see if they have a plant next generation!
        for (Pot pot : currentPots) {
            Pot nextPot = applyRulesToPot(pot, currentPots, rules);
            nextPots.add(nextPot);
        }

        return nextPots;
    }

    public static void addPotToRightEnd(boolean hasPlant, ArrayList<Pot> pots) {
        Integer endPotId = pots.get(pots.size() - 1).getPotId(); // get the ID of the rightmost pot

        // add a new pot on the end of the 'pots' list, with an ID one higher than the current end.
        Pot newPot = new Pot(endPotId + 1, hasPlant);
        pots.add(newPot);
    }

    public static void addPotToLeftEnd(boolean hasPlant, ArrayList<Pot> pots) {
        Integer beginPotId = pots.get(0).getPotId(); // get the ID of the leftmost pot

        // add a new pot at the beginning of the 'pots' list, with an ID one lower than the current end.
        Pot newPot = new Pot(beginPotId - 1, hasPlant);
        pots.add(0, newPot);
    }

    public static Pot applyRulesToPot(Pot pot, ArrayList<Pot> pots, ArrayList<Rule> rules) {
        // Apply each rule in turn to this pot.
        // If a rule applies, calculate its result (probably "hasPlant=TRUE")
        // and stop looking.
        //
        // If no rule matches, return an empty pot (hasPlant=FALSE")

        if (DEBUG_PRINT) {
            System.out.println("-------------------------------------");
        }
        boolean willHavePlant = false;
        for (Rule rule : rules) {
            if (potMatchesRule(pot, pots, rule)) {
                willHavePlant = true;
                break;
            }
        }

        Pot resultPot = new Pot(pot.getPotId(), willHavePlant); // create a new pot with the same ID, but the "next" hasPlant.
        return resultPot;
    }

    private static boolean potMatchesRule(Pot pot, ArrayList<Pot> pots, Rule rule) {
        boolean matchesRules = true; // start out assuming the pot WILL match all rules. Stop testing and return if it doesn't.

        boolean currentHasPlant = false;
        Integer potId = pot.getPotId();

        String potsString = null;            // DEBUG PRINT
        String ruleString = null;          // DEBUG PRINT
        String willHavePlantString = null;   // DEBUG PRINT

        if (DEBUG_PRINT) {
            potsString = "";
            ruleString = "";
        }
        for (int i = 0; i < 5; i++) {
            // There are always 5 pots in a rule. Check each current pot against the rule requirements.
            try {
                currentHasPlant = potByPotId((potId - 2) + i, pots).isHasPlant(); // start from the pot two (2) to our left
            } catch (IndexOutOfBoundsException e) {
                // If the pot we're trying to check is outside the bounds of our pots, it's empty.
                currentHasPlant = false;
            }

            boolean currentMustHavePlant = rule.getHasPlants().get(i); // get the requirement for this pot.

            if (DEBUG_PRINT) {
                potsString += currentHasPlant ? "#" : ".";             // DEBUG PRINT
                ruleString += currentMustHavePlant ? "#" : ".";         // DEBUG PRINT
            }

            matchesRules &= (currentHasPlant == currentMustHavePlant);

            if (!matchesRules) {
                return false;   // If we found a rule requirement the pots DON'T meet,
                // then there will be no plant in this pot!
            }
        }
        boolean willHavePlant = rule.willHavePlant(); // All rules matched, so this plant will or won't have a plant based on the rule.
        if (DEBUG_PRINT) {
            willHavePlantString = (willHavePlant ? "#" : ".");
            System.out.println(potsString + "\t" + ruleString + "\t" + willHavePlantString);
        }


        return willHavePlant;
    }

    private static Pot potByPotId(Integer potId, ArrayList<Pot> pots) {
        for (Pot pot : pots) {
            if (pot.getPotId() == potId) {
                return pot;
            }
        }
        return new Pot(potId,false); // If we don't find a pot with this ID, fake an empty one.
    }

    public static String printPots(ArrayList<Pot> pots) {
        String output = "";
        for (Pot pot : pots) {
            output += (pot.isHasPlant() ? "#" : ".");
        }
        return output;
    }

    private static String printRule(Rule rule) {
        String output = "";
        for (boolean hasPlant : rule.getHasPlants()) {
            output += (hasPlant ? "#" : ".");
        }
        return output;
    }
}

