package org.jgoeres.adventofcode.Day07;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class StepList {
    private ArrayList<Step> steps = new ArrayList<>();

    public StepList(String pathToFile) {
        loadStepList(pathToFile);
    }

    private void loadStepList(String pathToFile) {
    /* The file looks like
        Step L must be finished before step T can begin.
        Step B must be finished before step I can begin.
        Step A must be finished before step T can begin.
     */
        Pattern p = Pattern.compile("Step (\\w) must be finished before step (\\w) can begin");

    }
}
