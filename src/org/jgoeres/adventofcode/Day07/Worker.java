package org.jgoeres.adventofcode.Day07;

public class Worker {
    private Step doingStep;
    private String doingStepId;
    private Integer startTime;

    public boolean isIdle(Integer currentTime) {
        if ((doingStep != null)
                && (currentTime >= (getStartTime() + StepService.duration(doingStepId)))) {
            // If we're done with the current step, go Idle.
            doingStep = null;
        }

        // If we're not doing a step (i.e. doingStep is null) we're idle!
        return (doingStep == null);
    }

    public void startWork(Step step, Integer startTime) {
        setDoingStep(step);
        setStartTime(startTime);
    }


    public Step getDoingStep() {
        return doingStep;
    }

    public void setDoingStep(Step doingStep) {
        this.doingStep = doingStep;
    }

    public String getDoingStepId() {
        return doingStepId;
    }

    public void setDoingStepId(String doingStepId) {
        this.doingStepId = doingStepId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }
}
