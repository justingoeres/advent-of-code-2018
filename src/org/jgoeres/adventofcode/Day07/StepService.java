package org.jgoeres.adventofcode.Day07;

public class StepService {

    public boolean addParent(Step step, Step newParent){
        step.getParents().add(newParent);
    }

}
