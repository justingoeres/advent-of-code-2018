package org.jgoeres.adventofcode.Day23;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Formation {
    HashSet<Nanobot> nanobots = new HashSet<>();

    public Formation(String pathToFile) {
        /*
        File looks like:
        pos=<85164684,17215793,33936340>, r=69982424
        pos=<99964562,33631002,26087091>, r=96905283
        pos=<95306439,38314904,31724758>, r=91293405
        pos=<47695076,18040111,-11868210>, r=77493042
        pos=<81341996,42208570,37604218>, r=75343137
        pos=<71660410,48428809,38684716>, r=70801124
         */

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
//                Pattern p = Pattern.compile("\\D+(\\d+),(\\d+),(\\d)\\D+(\\d+).*");
                Pattern p = Pattern.compile("\\D+(-?\\d+),(-?\\d+),(-?\\d+)\\D+(-?\\d+).*");
                Matcher m = p.matcher(line);
                m.matches();
                System.out.println(m.group(0));
                System.out.println(m.group(1));
                System.out.println(m.group(2));
                System.out.println(m.group(3));
                System.out.println(m.group(4));
                Nanobot newNanobot = new Nanobot(
                        Integer.parseInt(m.group(1)),
                        Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3)),
                        Integer.parseInt(m.group(4))
                );
                nanobots.add(newNanobot);
            }
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
