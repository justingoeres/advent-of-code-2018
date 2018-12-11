package org.jgoeres.adventofcode.Day01;

public abstract class RunDay1 {
    static String pathToInputs = "day01/input.txt";
    static FrequencyChangeService frequencyChangeService = new FrequencyChangeService(pathToInputs);

    static Integer totalFrequencyChange = null;

    public static void problem1A() {
        // Day 01A
        // Starting with a frequency of zero, what is the resulting frequency
        // after all of the changes in frequency have been applied?
        System.out.println("=== DAY 1A ===");

        try {
            totalFrequencyChange = frequencyChangeService.calculateTotalFrequencyChange(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Day 1A: Total Frequency Change = " + totalFrequencyChange);
        // Answer: 437
    }

    public static void problem1B() {
        // Day 01B
        // You notice that the device repeats the same frequency change list over and over.
        // To calibrate the device, you need to find the first frequency it reaches twice.
        System.out.println("=== DAY 1B ===");

        // This MUST run after problem1A() above, or the frequencyHistogram in
        // frequencyChangeService won't be initialized.
        System.out.println("Scanning for duplicates...");
//        System.out.print("Pass: ");
        int pass = 1;
        while (true) {
//            System.out.println("Pass at totalFrequencyChange = " + totalFrequencyChange);
//            System.out.print(pass);
            try {
                totalFrequencyChange = frequencyChangeService.calculateTotalFrequencyChange(totalFrequencyChange);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage() + " on pass #" + pass);

                break;
            }
//            System.out.print(", ");
            pass = pass + 1;
        }
        // Answer: 655 after 138 passes
    }
}
