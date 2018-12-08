package org.jgoeres.adventofcode;

import org.jgoeres.adventofcode.Day01.FrequencyChangeService;

public class Main {

    public static void main(String[] args) {
    	// Day 01
        //https://adventofcode.com/2018/day/1

        //Starting with a frequency of zero, what is the resulting frequency
        // after all of the changes in frequency have been applied?

        FrequencyChangeService frequencyChangeService = new FrequencyChangeService();

        String pathToInputs = "day01/input.txt";

        int totalFrequency = 0;
        totalFrequency += frequencyChangeService.calculateFrequencyChangeFromFile(pathToInputs);

        System.out.println("Day 1: Total Frequency Change = " + totalFrequency);
        // Answer: 437
    }
}
