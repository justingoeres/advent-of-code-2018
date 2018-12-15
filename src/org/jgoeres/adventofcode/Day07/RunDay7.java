package org.jgoeres.adventofcode.Day07;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RunDay7 {
    static String pathToInputs = "day07/input.txt";

    public static void problem7A() {
        /*
        The instructions specify a series of steps and requirements about
        which steps must be finished before others can begin (your puzzle
        input). Each step is designated by a single letter. For example,
        suppose you have the following instructions:

        Step C must be finished before step A can begin.
        Step C must be finished before step F can begin.
        Step A must be finished before step B can begin.
        Step A must be finished before step D can begin.
        Step B must be finished before step E can begin.
        Step D must be finished before step E can begin.
        Step F must be finished before step E can begin.
        Visually, these requirements look like this:

          -->A--->B--
         /    \      \
        C      -->D----->E
         \           /
          ---->F-----

        Your first goal is to determine the order in which the steps should be
        completed. If more than one step is ready, choose the step which is
        first alphabetically. In this example, the steps would be completed as
        follows:

        Only C is available, and so it is done first. Next, both A and F are
        available. A is first alphabetically, so it is done next. Then, even
        though F was available earlier, steps B and D are now also available,
        and B is the first alphabetically of the three. After that, only D and
        F are available. E is not available because only some of its
        prerequisites are complete. Therefore, D is completed next. F is the
        only choice, so it is done next. Finally, E is completed. So, in this
        example, the correct order is CABDFE.
        */
        System.out.println("=== DAY 7A ===");

        StepList stepList = new StepList(pathToInputs);

        System.out.print("Step execution order:\t");
        while (!stepList.getSteps().isEmpty()) {
            // As long as there are steps we haven't processed yet.

            // Find the first step (alphabetically) with no parents.
            for (Map.Entry<Character, Step> stepToCheck : stepList.getSteps().entrySet()) {
                if (!StepService.hasParent(stepToCheck.getValue())) {
                    // if this step has NO parents.

                    // Remove it from the remaining list of steps
                    HashMap<Character, Step> modifiedSteps = stepList.getSteps();
                    modifiedSteps.remove(stepToCheck.getKey());
                    stepList.setSteps(modifiedSteps);

                    // Execute it!
                    System.out.print(stepToCheck.getKey().toString());

                    // Finally, remove it from everyone's parent list.
                    for (Map.Entry<Character, Step> step : stepList.getSteps().entrySet()) {
                        step.getValue().getParents().remove(stepToCheck.getKey());
                    }
                    break;  // After we find and execute the FIRST AVAILABLE step,
                            // go back and start looking for the next one.
                }
            }
        }
        System.out.println(); // linefeed when we're all done.

        // Answer:
        // Step execution order:	ABDCJLFMNVQWHIRKTEUXOZSYPG
    }

    public static void problem7B() {
        /*
        Problem Description
        */
        System.out.println("=== DAY 7B ===");

    }
}
