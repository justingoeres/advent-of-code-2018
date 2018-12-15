package org.jgoeres.adventofcode.Day07;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jgoeres.adventofcode.Day07.StepService;

public class StepList {
    private HashMap<Character,Step> steps = new HashMap<>();

    public StepList(String pathToFile) {
        loadStepList(pathToFile);
    }

    private void loadStepList(String pathToFile) {
    /* The file looks like
        Step L must be finished before step T can begin.
        Step B must be finished before step I can begin.
        Step A must be finished before step T can begin.
     */
        Pattern p = Pattern.compile("Step (\\w) must be finished before step (\\w) can begin.");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                Character parentId = (m.group(1)).charAt(0);
                Character childId = (m.group(2)).charAt(0);

                // Look up the child & parent steps in the HashMap, or create them.
                Step parentStep = (steps.containsKey(parentId)) ? steps.get(parentId) : new Step();
                Step childStep = (steps.containsKey(childId)) ? steps.get(childId) : new Step();

                // Add this parent to the child
                steps.put(parentId,parentStep);
                steps.put(childId,childStep);
                StepService.addParent(childStep,parentStep,parentId);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public HashMap<Character, Step> getSteps() {
        return steps;
    }

    public void setSteps(HashMap<Character, Step> steps) {
        this.steps = steps;
    }
}
