package org.jgoeres.adventofcode.Day07;

public abstract class StepService {

    private static Integer ASCII_VALUE_A = 65;
    private static Integer BASE_STEP_DURATION_SEC = 60;

    public static Integer duration(Character stepID) {
        Integer duration = (stepID - ASCII_VALUE_A) + BASE_STEP_DURATION_SEC;
        return duration;
    }


    public static void addParent(Step step, Step parentToAdd, Character parentId){
        step.getParents().put(parentId,parentToAdd);
    }

    public static boolean hasParent(Step step){
        boolean hasParent = !step.getParents().isEmpty();
        return hasParent;
    }
}
