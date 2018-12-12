package org.jgoeres.adventofcode.Day05;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class PolymerList extends ArrayList<Character>{
    public PolymerList(String pathToFile) {
        loadPolymerStream(pathToFile);
    }

    private void loadPolymerStream(String pathToFile) {
/*  The file looks like

        YvVIiMhHcwWCYymIuUiTteE...
*/
        // Just read the whole file into a String, we'll look at it character-by-character later.
        String polymersString = readFile(pathToFile, Charset.defaultCharset());
        polymersString = polymersString.trim(); // remove the trailing \n from the file.

        // Put all the characters of the string into an ArrayList
        for (int i = 0; i < polymersString.length(); i++) {
            this.add(polymersString.charAt(i));
        }
    }

    private String readFile(String path, Charset encoding) {
        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(path));
        } catch (Exception e) {
            System.out.println("readFile exception occurred: " + e.getMessage());
        }
        return new String(encoded, encoding);
    }

}
