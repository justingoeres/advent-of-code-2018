package org.jgoeres.adventofcode.Day05;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PolymerService {
    private String polymersString;
    private ArrayList<Character> polymersList = new ArrayList<>();

    public PolymerService(String pathToFile) {
        loadPolymerStream(pathToFile);
    }

    private void loadPolymerStream(String pathToFile) {
/*  The file looks like

        YvVIiMhHcwWCYymIuUiTteE...
*/
        // Just read the whole file into a String, we'll look at it character-by-character later.
        polymersString = readFile(pathToFile, Charset.defaultCharset());
        polymersString = polymersString.trim(); // remove the trailing \n from the file.

        // Put all the characters of the string into an ArrayList
        for (int i = 0; i < polymersString.length(); i++) {
            polymersList.add(polymersString.charAt(i));
        }
    }

    static String readFile(String path, Charset encoding) {
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            System.out.println("readFile exception occurred: " + e.getMessage());
        }
        return new String(encoded, encoding);
    }

    public ArrayList<Character> getPolymersList() {
        return polymersList;
    }

    public static boolean oppositeCase(Character c1, Character c2) {
        final int CASE_DISTANCE = 32;
        final int distance = Math.abs(c1 - c2);

        return (distance == CASE_DISTANCE);
    }
}
