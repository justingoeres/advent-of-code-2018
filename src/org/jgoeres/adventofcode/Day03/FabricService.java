package org.jgoeres.adventofcode.Day03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FabricService {
    private ArrayList<FabricSquare> fabricSquares = new ArrayList<>();

    public FabricService(String pathToFile) {
        loadFabricSquares(pathToFile);
    }

    private void loadFabricSquares(String pathToFile) {

//      Match lines like
//      #1 @ 257,829: 10x23

//        Pattern p = Pattern.compile("(\\d+)");

//        File file = new File(pathToFile);
//        FabricSquare fabricSquare = new FabricSquare();
//        System.out.println(file.getAbsolutePath());
//        try {
//            Scanner sc = new Scanner(file);
//
//            while (sc.hasNextLine()) {
//                // Parse this fabric square's info
//                System.out.println(sc.nextLine());
//                int x = sc.nextInt();
//                System.out.println(x);
//                fabricSquare.setX(sc.nextInt());
//                fabricSquare.setY(sc.nextInt());
//                fabricSquare.setW(sc.nextInt());
//                fabricSquare.setH(sc.nextInt());
//                // Add this square to our set of squares to process
//                fabricSquares.add(fabricSquare);
//            }
//        } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.getMessage());
//        }

        Pattern p = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
//        Pattern p = Pattern.compile("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)");
//        Pattern p = Pattern.compile("\\d+");
//        Pattern p = Pattern.compile("#(\\d+)");

//        String patternString1 = ".*2.*";
//        String text1 = "123";
//        Pattern p1 = Pattern.compile(patternString1);
//        Matcher m1 = p1.matcher(text1);
//        String d = m1.group(0);
//        System.out.println(d);


        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

//                String text    =
//                        "This is the text to be searched " +
//                                "for occurrences of the http:// pattern.";
//
//                String patternString = ".*(http://).*";
//
//                Pattern pattern = Pattern.compile(patternString);
//
//                Matcher matcher = pattern.matcher(text);
////                boolean matches = matcher.matches();
//                System.out.println("HELLO WORLD: " + matcher.group(0));
//                System.out.println("HELLO AGAIN: " + matcher.group(1));


//                EXAMPLE
//                #1 @ 257,829: 10x23
//                #2 @ 902,685: 10x20
//                #3 @ 107,733: 20x25

//                String patternString = ".*(2).*";
//                line = "#1 @ 257,829: 10x23";
             //   patternString = "(2)";
//                line = "123";
                //p = Pattern.compile("(\\d+)");
//                p = Pattern.compile(patternString);
                Matcher m = p.matcher(line);
                m.matches();
//                String d0 = m.group(0);
//                String d1 = m.group(1);
//                fabricSquares.add(line);
//                System.out.println(d0);
//                System.out.println(d1);
                System.out.println(m.group(1) + " " + m.group(2)+ " " + m.group(3)+ " " + m.group(4)+ " " + m.group(5));
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
