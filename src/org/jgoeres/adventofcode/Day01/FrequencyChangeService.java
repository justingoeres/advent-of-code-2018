package org.jgoeres.adventofcode.Day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class FrequencyChangeService {

    public ArrayList<Integer> frequencyHistory = new ArrayList<>();
    public HashMap<Integer, Integer> frequencyHistogram = new HashMap<>();

    public FrequencyChangeService(String pathToFile) {
        loadFrequencyList(pathToFile);
    }

    public Integer calculateTotalFrequencyChange(int baseFrequency) throws Exception {
        int totalFrequencyChange = baseFrequency;

        for (Integer thisFrequency : frequencyHistory) {
            totalFrequencyChange = totalFrequencyChange + thisFrequency;
            if (frequencyHistogram.containsKey(totalFrequencyChange)) {
                // Found a duplicate frequency!
                throw new Exception("Duplicate frequency found at " + String.valueOf(totalFrequencyChange));
            } else {
                frequencyHistogram.put(totalFrequencyChange, 1);
            }
        }
        return totalFrequencyChange;
    }

    private void loadFrequencyList(String pathToFile) {
        frequencyHistory.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Integer nextFrequency = 0;
            while ((line = br.readLine()) != null) {
                // process the line.
                nextFrequency = Integer.parseInt(line);

                frequencyHistory.add(nextFrequency);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private ArrayList<Integer> getFrequencyHistory() {
        return frequencyHistory;
    }

}
