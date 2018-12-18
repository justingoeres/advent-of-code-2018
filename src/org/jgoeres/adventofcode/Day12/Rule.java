package org.jgoeres.adventofcode.Day12;

public class Rule {
    private boolean prevPrevHasPlant;
    private boolean prevHasPlant;
    private boolean hasPlant;
    private boolean nextHasPlant;
    private boolean nextNextHasPlant;

    private boolean willHavePlant;

    public Rule(String plantsNow, String willHavePlant) {
        char[] plantsNowChars = plantsNow.toCharArray();
        this.prevPrevHasPlant = GreenhouseService.hasPlantCharToBool(plantsNowChars[0]);
        this.prevHasPlant = GreenhouseService.hasPlantCharToBool(plantsNowChars[1]);
        this.hasPlant = GreenhouseService.hasPlantCharToBool(plantsNowChars[2]);
        this.nextHasPlant = GreenhouseService.hasPlantCharToBool(plantsNowChars[3]);
        this.nextNextHasPlant = GreenhouseService.hasPlantCharToBool(plantsNowChars[4]);

        this.willHavePlant = GreenhouseService.hasPlantCharToBool(willHavePlant.charAt(0));
    }

    public boolean willHavePlant() {
        return willHavePlant;
    }
}
