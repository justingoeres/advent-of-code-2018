package org.jgoeres.adventofcode.Day12;

import java.util.ArrayList;

public class Rule {
    private ArrayList<Boolean> hasPlants = new ArrayList<>();

    private boolean willHavePlant;

    public Rule(String plantsNow, String willHavePlant) {
        char[] plantsNowChars = plantsNow.toCharArray();

        for (int i = 0; i < 5; i++) {
            hasPlants.add(GreenhouseService.hasPlantCharToBool(plantsNowChars[i]));
        }

        this.willHavePlant = GreenhouseService.hasPlantCharToBool(willHavePlant.charAt(0));
    }

    public ArrayList<Boolean> getHasPlants() {
        return hasPlants;
    }

    public boolean willHavePlant() {
        return willHavePlant;
    }
}
