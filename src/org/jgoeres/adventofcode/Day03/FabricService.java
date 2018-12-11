package org.jgoeres.adventofcode.Day03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

public class FabricService {
    private ArrayList<FabricSquare> fabricSquares = new ArrayList<>();

    public FabricService(String pathToFile) {
        loadFabricSquares(pathToFile);
    }

    private void loadFabricSquares(String pathToFile) {

//      Match lines like
//      #1 @ 257,829: 10x23
        Pattern p = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

//                EXAMPLE
//                #1 @ 257,829: 10x23
//                #2 @ 902,685: 10x20
//                #3 @ 107,733: 20x25

                FabricSquare fabricSquare = new FabricSquare();

                Matcher m = p.matcher(line);
                m.matches();
//                System.out.println(m.group(1) + " " + m.group(2)+ " " + m.group(3)+ " " + m.group(4)+ " " + m.group(5));

                fabricSquare.setClaim(parseInt(m.group(1)));
                fabricSquare.setX(parseInt(m.group(2)));
                fabricSquare.setY(parseInt(m.group(3)));
                fabricSquare.setW(parseInt(m.group(4)));
                fabricSquare.setH(parseInt(m.group(5)));

                // Put this square into our local set of squares to process.
                fabricSquares.add(fabricSquare);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public ArrayList<FabricSquare> getFabricSquares() {
        return fabricSquares;
    }
}
