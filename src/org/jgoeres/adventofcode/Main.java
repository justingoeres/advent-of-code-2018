package org.jgoeres.adventofcode;

import org.jgoeres.adventofcode.Day01.FrequencyChangeService;

public class Main {

    public static void main(String[] args) {
        // Day 01
        //https://adventofcode.com/2018/day/1

        // Day 01A
        // Starting with a frequency of zero, what is the resulting frequency
        // after all of the changes in frequency have been applied?
        System.out.println("=== DAY 1A ===");

        String pathToInputs = "day01/input.txt";
        FrequencyChangeService frequencyChangeService = new FrequencyChangeService(pathToInputs);

        Integer totalFrequencyChange = null;
        try {
            totalFrequencyChange = frequencyChangeService.calculateTotalFrequencyChange(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Day 1A: Total Frequency Change = " + totalFrequencyChange);
        // Answer: 437

        // Day 01B
        // You notice that the device repeats the same frequency change list over and over.
        // To calibrate the device, you need to find the first frequency it reaches twice.
        System.out.println("=== DAY 1B ===");

        System.out.println("Scanning for duplicates...");
        System.out.print("Pass: ");
        int pass = 1;
        while (true) {
//            System.out.println("Pass at totalFrequencyChange = " + totalFrequencyChange);
            System.out.print(pass);
            try {
                totalFrequencyChange = frequencyChangeService.calculateTotalFrequencyChange(totalFrequencyChange);
            } catch (Exception e) {
                System.out.println("\nException: " + e.getMessage());

                break;
            }
            System.out.print(", ");
            pass = pass + 1;
        }
    }
}
