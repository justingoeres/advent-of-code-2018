package org.jgoeres.adventofcode.Day02;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class BoxChecksumService {

    private ArrayList<String> boxesList = new ArrayList<>();

    public BoxChecksumService(String pathToFile) {
        loadBoxesList(pathToFile);
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
}
