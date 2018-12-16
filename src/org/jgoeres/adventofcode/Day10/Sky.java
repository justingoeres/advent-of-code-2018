package org.jgoeres.adventofcode.Day10;

import org.jgoeres.adventofcode.Day07.Step;
import org.jgoeres.adventofcode.Day07.StepService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sky {
    private ArrayList<Star> stars = new ArrayList<>();

    public Sky(String pathToFile) {
        loadSky(pathToFile);
    }

    private void loadSky(String pathToFile){
           /*
        File looks like:
            position=<-53868, -10684> velocity=< 5,  1>
            position=<-43043, -43128> velocity=< 4,  4>
            position=< 11010,  54188> velocity=<-1, -5>
         */
//        Pattern p = Pattern.compile("position=<([ -]?\\d+), ([ -]?\\d+)> velocity=< ([ -] ?\\d+), ([ -]\\d+)>");
        Pattern p = Pattern.compile("position=<([ -]?\\d+), ([ -]?\\d+)> velocity=< ([ -]?\\d+), ([ -]?\\d+)>");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                String x0 = (m.group(1));
                String y0 = (m.group(2));
                String vX = (m.group(3));
                String vY = (m.group(4));


                // Look up the child & parent steps in the HashMap, or create them.
//                Step parentStep = (steps.containsKey(parentId)) ? steps.get(parentId) : new Step(parentId);
//                Step childStep = (steps.containsKey(childId)) ? steps.get(childId) : new Step(childId);
//
//                // Add this parent to the child
//                steps.put(parentId,parentStep);
//                steps.put(childId,childStep);
//                StepService.addParent(childStep,parentStep,parentId);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }
}
