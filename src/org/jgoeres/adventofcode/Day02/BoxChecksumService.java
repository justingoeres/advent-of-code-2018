package org.jgoeres.adventofcode.Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class BoxChecksumService {

    private ArrayList<String> boxesList = new ArrayList<>();

    public BoxChecksumService(String pathToFile) {
        loadBoxesList(pathToFile);
    }

    public BoxChecksum calculateBoxChecksum(String boxID) {
        // Calculate the checksum for one box ID.
        HashMap<Character, Integer> characterHistogram = new HashMap<>();
//        String temp = "cccded";
        for (Character c : boxID.toCharArray()) {
//        for (Character c : temp.toCharArray()) {
            // Increment the histogram value for this character.
            characterHistogram.put(c,
                    (characterHistogram.getOrDefault(c, 0)) + 1
            );
        }
        BoxChecksum result = new BoxChecksum();
        result.setContainsTwo(characterHistogram.containsValue(2));
        result.setContainsThree(characterHistogram.containsValue(3));

        return result;
    }

    private void loadBoxesList(String pathToFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
                boxesList.add(line);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public ArrayList<String> getBoxesList() {
        return boxesList;
    }
}
