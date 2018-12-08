package org.jgoeres.adventofcode.Day01;

import java.io.BufferedReader;
import java.io.FileReader;

public class FrequencyChangeService {
    public int calculateFrequencyChangeFromFile(String pathToFile) {
        int totalFrequencyChange = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                totalFrequencyChange = totalFrequencyChange + Integer.parseInt(line);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
        return totalFrequencyChange;
    }
}
