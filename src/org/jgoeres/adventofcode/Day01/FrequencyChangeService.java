package org.jgoeres.adventofcode.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FrequencyChangeService {

    public int minFrequency = 0;
    public int maxFrequency = 0;

    public ArrayList<Integer> frequencyHistory = new ArrayList<>();
    public HashMap<Integer, Integer> frequencyHistogram = new HashMap<>();

    public FrequencyChangeService(String pathToFile) {
        loadFrequencyList(pathToFile);
    }

    public Integer calculateTotalFrequencyChange(int baseFrequency) {
        int totalFrequencyChange = baseFrequency;

        for (Integer thisFrequency : frequencyHistory) {
            totalFrequencyChange = totalFrequencyChange + thisFrequency;
            if (frequencyHistogram.containsKey(totalFrequencyChange)) {
                // Found a duplicate frequency!
                System.out.println("Duplicate frequency found: " + totalFrequencyChange);
                return null;
            } else {
                frequencyHistogram.put(totalFrequencyChange,1);
            }
        }
        return totalFrequencyChange;
    }

    private void loadFrequencyList(String pathToFile) {
        frequencyHistory.clear();
//        frequencyHistory.add(0);
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextFrequency = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                nextFrequency = Integer.parseInt(line);

                frequencyHistory.add(nextFrequency);
            }
        } catch (
                Exception e)

        {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public Integer findDuplicate(int frequencyIncrement) {
        for (int nextHistoryFrequency : getFrequencyHistory()) {
            int frequencyToCheck = nextHistoryFrequency + frequencyIncrement;
            if (getFrequencyHistory().contains(frequencyToCheck)) {
                return frequencyToCheck; // Found a duplicate!
            }
        }
        return null; // No duplicate found, return null.
    }

    public int getMinFrequency() {
        return minFrequency;
    }

    public int getMaxFrequency() {
        return maxFrequency;
    }

    private ArrayList<Integer> getFrequencyHistory() {
        return frequencyHistory;
    }

}
