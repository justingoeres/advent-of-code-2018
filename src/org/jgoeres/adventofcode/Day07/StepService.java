package org.jgoeres.adventofcode.Day07;

public abstract class StepService {

    public static void addParent(Step step, Step parentToAdd, Character parentId){
        step.getParents().put(parentId,parentToAdd);
    }

    public static boolean hasParent(Step step){
        boolean hasParent = !step.getParents().isEmpty();
        return hasParent;
    }
}
