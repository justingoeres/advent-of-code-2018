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

    public String compareBoxIDs(String reference, String candidate) {
        for (int i = 0; i < reference.length(); i++) { // Go character by character
            if (reference.charAt(i) != candidate.charAt(i)) {
                // We found a difference!
//                System.out.println("Difference at position " + i + " (" + reference.charAt(i) + " vs " + candidate.charAt(i) + "); matching substring: " + reference.substring(0, i));
                // Now check the REST of the two strings.
                String refRemaining = reference.substring(i+1);
                String candRemaning = candidate.substring(i+1);
                if (refRemaining.equals(candRemaning)) {
                    // The rest of the strings match! This is our solution!
                    String solution = reference.substring(0, i) + reference.substring(i + 1); // Cat the before & after.
                    return solution; // return our solution!
                } else {
                    return null; // The rest of the strings don't match, so no solution here. Give up.
                }
            }
            // Characters are identical, keep looking.
        }
        return null; // Nothing found. Give up.
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
