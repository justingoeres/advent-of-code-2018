package org.jgoeres.adventofcode.Day07;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RunDay7 {
    static String pathToInputs = "day07/input.txt";
//    static String pathToInputs = "day07/input-dnatt.txt";

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

                    // Execute it! (Just print it out)
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
        // Step execution order:	CQSWKZFJONPBEUMXADLYIGVRHT (DNatt)
    }

    public static void problem7B() {
        /*
        Now, you need to account for multiple people working on steps simultaneously.
        If multiple steps are available, workers should still begin them in
        alphabetical order.

        Each step takes 60 seconds plus an amount corresponding to its letter:
        A=1, B=2, C=3, and so on. So,
        step A takes 60+1=61 seconds, while
        step Z takes 60+26=86 seconds. No time is required between steps.

        Note that the order of the steps has changed; this is because steps
        now take time to finish and multiple workers can begin multiple steps
        simultaneously.

        With 5 workers and the 60+ second step durations described above,
        how long will it take to complete all of the steps?
        */
        System.out.println("=== DAY 7B ===");

        boolean PRINT_EACH_TIME_STEP = false;
        Integer NUMBER_OF_WORKERS = 5;

        // Set up our workers
        ArrayList<Worker> workers = new ArrayList<>();
//        String[] workerNames = new String[]{"Hamilton", "Laurens", "Mulligan", "Lafayette", "Burr"};
        String[] workerNames = new String[]{"Celia", "Emmett", "Dishita", "Makai", "Silas"};
        for (int i = 0; i < NUMBER_OF_WORKERS; i++) {
            workers.add(new Worker(workerNames[i]));
        }
        String stepsDone = "";

        // Reload our stepsList since problem 7A consumed it.
        StepList stepList = new StepList(pathToInputs);

        if (PRINT_EACH_TIME_STEP) {
            System.out.println("Step execution flow:");
            System.out.println("Time\t" + printWorkerNames(workers) + "\tDone");
        }

        Integer currentTime = 0; // Start at t = 0

        while (!stepList.getSteps().isEmpty() || !allWorkersIdle(workers)) {
            // Keep going until all steps are complete
            // Check each worker to see if their current step is done.
            for (Worker w : workers) {
                Step doneStep = w.currentStepComplete(currentTime);

                if (doneStep != null) {
                    // If this worker has just completed a step.
                    // Remove it from the lists of all parents.
                    for (Map.Entry<Character, Step> stepToCheck : stepList.getSteps().entrySet()) {
                        stepToCheck.getValue().getParents().remove(doneStep.getId());
                    }
                    // And add it to the String of steps completed.
                    stepsDone += doneStep.getId();
                }
            }
            for (Worker w : workers) {
                // Are any workers idle?
                if (w.isIdle()) {
                    // Find this idle worker a step to do, if one is executable.
                    for (Map.Entry<Character, Step> stepToExecute : stepList.getSteps().entrySet()) {
                        Step step = stepToExecute.getValue();
                        if (!StepService.hasParent(step)) {
                            // if this step has NO parents, then it can be executed!

                            // Remove it from the remaining list of steps
                            HashMap<Character, Step> modifiedSteps = stepList.getSteps();
                            modifiedSteps.remove(step.getId());
                            stepList.setSteps(modifiedSteps);

                            // Give it to this worker.
                            w.startWork(step, currentTime);

                            break;  // After we find and execute the FIRST AVAILABLE step,
                                    // go back and start looking for the next one.
                        }
                    }
                }
            }

            if (PRINT_EACH_TIME_STEP) {
                // Print out everyone's status.
                System.out.println(currentTime + "\t\t" + printWorkerStatuses(workers) + "\t" + stepsDone);
            }
            currentTime = currentTime + 1;  // Advance the clock before starting the next tick.
        }
        System.out.println("Number of workers:\t" + NUMBER_OF_WORKERS);
        System.out.println("Step execution order:\t" + stepsDone);
        System.out.println("Total execution time:\t" + currentTime + " seconds");

        // Answer:
//        Number of workers:	5
//        Step execution order:	ABLDNFWMCJVRHQITXKEUZOSYPG
//        Total execution time:	897 seconds
    }

    private static boolean allWorkersIdle(ArrayList<Worker> workers) {
        boolean result = true; // assume everybody is Idle to start
        for (Worker worker : workers) {
            result &= worker.isIdle();
        }
        return result;
    }

    private static String printWorkerNames(ArrayList<Worker> workers) {
        String result = "";
        for (Worker worker : workers) {
            result += worker.getName() + "\t";
        }
        result = result.trim();
        return result;
    }

    private static String printWorkerStatuses(ArrayList<Worker> workers) {
        String result = "";
        for (Worker worker : workers) {
            result += nameInSpaces(worker.getName()).substring(1) + worker.getDoingStepId() + "\t";
        }
        result = result.trim();
        return result;
    }

    private static String nameInSpaces(String name) {
        String spaces = name.replaceAll(".", " ");
        return spaces;
    }
}
