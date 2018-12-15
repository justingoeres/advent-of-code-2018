package org.jgoeres.adventofcode.Day07;

public class Worker {
    private Step doingStep;
    private Integer startTime;
    private String name;

    public Worker(String name) {
        this.name = name;
    }

    public boolean isIdle() {
        return (doingStep == null);
    }

    public void startWork(Step step, Integer startTime) {
        setDoingStep(step);
        setStartTime(startTime);
    }

    public Step currentStepComplete(Integer currentTime) {
        if ((doingStep != null)
                && (currentTime > (getStartTime() + StepService.duration(doingStep)))) {
            Step doneStep = doingStep;
            doingStep = null;
            return doneStep;
        }
        return null; // if we have no step, we're certainly not doing anything!
    }

    public Step getDoingStep() {
        return doingStep;
    }

    public void setDoingStep(Step doingStep) {
        this.doingStep = doingStep;
    }

    public Character getDoingStepId() {
        return (doingStep != null) ? doingStep.getId() : '.';
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
