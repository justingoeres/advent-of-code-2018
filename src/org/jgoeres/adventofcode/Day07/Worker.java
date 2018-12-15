package org.jgoeres.adventofcode.Day07;

public class Worker {
    private Step doingStep;
    private Character doingStepId;
    private Integer startTime;
    private String name;

    public Worker(String name) {
        this.name = name;
    }

    public boolean isIdle(Integer currentTime) {
        if ((doingStep != null)
                && (currentTime >= (getStartTime() + StepService.duration(doingStepId)))) {
            // If we're done with the current step, go Idle.
            doingStep = null;
            doingStepId = null;
        }

        // If we're not doing a step (i.e. doingStep is null) we're idle!
        return (doingStep == null);
    }

    public void startWork(Step step, Character stepId, Integer startTime) {
        setDoingStep(step);
        setDoingStepId(stepId);
        setStartTime(startTime);

    }


    public Step getDoingStep() {
        return doingStep;
    }

    public void setDoingStep(Step doingStep) {
        this.doingStep = doingStep;
    }

    public Character getDoingStepId() {
        return (doingStepId != null) ? doingStepId : '.';
    }

    public void setDoingStepId(Character doingStepId) {
        this.doingStepId = doingStepId;
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
