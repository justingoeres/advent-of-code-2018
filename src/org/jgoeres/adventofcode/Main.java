package org.jgoeres.adventofcode;

import org.jgoeres.adventofcode.Day01.FrequencyChangeService;

public class Main {

    public static void main(String[] args) {
        // Day 01
        //https://adventofcode.com/2018/day/1

        // Day 01A
        // Starting with a frequency of zero, what is the resulting frequency
        // after all of the changes in frequency have been applied?


        String pathToInputs = "day01/input.txt";
        FrequencyChangeService frequencyChangeService = new FrequencyChangeService(pathToInputs);

        Integer totalFrequencyChange = frequencyChangeService.calculateTotalFrequencyChange(0);

        System.out.println("Day 1A: Total Frequency Change = " + totalFrequencyChange);
        // Answer: 437

        System.out.println("Min: " + frequencyChangeService.getMinFrequency());
        System.out.println("Max: " + frequencyChangeService.getMaxFrequency());
        System.out.println("Width: " +
                (frequencyChangeService.getMaxFrequency() - frequencyChangeService.getMinFrequency()));

        // Day 02
        // You notice that the device repeats the same frequency change list over and over.
        // To calibrate the device, you need to find the first frequency it reaches twice.

        // If the total frequency change for each pass through the list is +totalFrequencyChange,
        // then each successive pass is +totalFrequency *above* the previous one.

        // The first frequency match has to match against the FIRST set of frequencies,
        // so we can just iteratively add +totalFrequencyChange to the frequencyHistory
        // and check point-by-point to see if each frequency exists in the original frequencyHistory


        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("Pass at totalFrequencyChange = " + totalFrequencyChange);
            totalFrequencyChange = frequencyChangeService.calculateTotalFrequencyChange(totalFrequencyChange);
            keepGoing = (totalFrequencyChange != null);
        }
    }
}
