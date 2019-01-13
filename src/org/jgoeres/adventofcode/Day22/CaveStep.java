package org.jgoeres.adventofcode.Day22;

public class CaveStep {
    private Integer x;
    private Integer y;
    private Tool tool;
    Integer distance;

    private CaveStep caveAbove = null;
    private CaveStep caveRight = null;
    private CaveStep caveBelow = null;
    private CaveStep caveLeft = null;
    private CaveStep toNothing = null;
    private CaveStep toTorch = null;
    private CaveStep toClimbingGear = null;


    public CaveStep() {
    }

    public CaveStep(Integer x, Integer y, Tool tool) {
        this.x = x;
        this.y = y;
        this.tool = tool;
    }


    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Tool getTool() {
        return tool;
    }

    public Integer getDistance() {
        return distance;
    }

    public void connectToAbove(CaveStep caveAbove) {
        setCaveAbove(caveAbove);
        caveAbove.setCaveBelow(this);
    }

    public void connectToBelow(CaveStep caveBelow) {
        setCaveBelow(caveBelow);
        caveBelow.setCaveAbove(this);
    }

    public void connectToRight(CaveStep caveRight) {
        setCaveRight(caveRight);
        caveRight.setCaveLeft(this);
    }

    public void connectToLeft(CaveStep caveLeft) {
        setCaveLeft(caveLeft);
        caveLeft.setCaveRight(this);
    }

    public void connectToTorch(CaveStep toTorch) {
        setToTorch(toTorch);
        switch (this.tool) {
            case NOTHING:
                toTorch.setToNothing(this);
                break;
            case CLIMBING_GEAR:
                toTorch.setToClimbingGear(this);
                break;
        }
    }

    public void connectToNothing(CaveStep toNothing) {
        setToNothing(toNothing);
        switch (this.tool) {
            case TORCH:
                toNothing.setToTorch(this);
                break;
            case CLIMBING_GEAR:
                toNothing.setToClimbingGear(this);
                break;
        }
    }

    public void connectToClimbingGear(CaveStep toClimbingGear) {
        setToClimbingGear(toClimbingGear);
        switch (this.tool) {
            case NOTHING:
                toClimbingGear.setToNothing(this);
                break;
            case TORCH:
                toClimbingGear.setToTorch(this);
                break;
        }
    }

    public CaveStep getCaveAbove() {
        return caveAbove;
    }

    public void setCaveAbove(CaveStep caveAbove) {
        this.caveAbove = caveAbove;
    }

    public CaveStep getCaveRight() {
        return caveRight;
    }

    public void setCaveRight(CaveStep caveRight) {
        this.caveRight = caveRight;
    }

    public CaveStep getCaveBelow() {
        return caveBelow;
    }

    public void setCaveBelow(CaveStep caveBelow) {
        this.caveBelow = caveBelow;
    }

    public CaveStep getCaveLeft() {
        return caveLeft;
    }

    public void setCaveLeft(CaveStep caveLeft) {
        this.caveLeft = caveLeft;
    }

    public CaveStep getToNothing() {
        return toNothing;
    }

    public void setToNothing(CaveStep toNothing) {
        this.toNothing = toNothing;
    }

    public CaveStep getToTorch() {
        return toTorch;
    }

    public void setToTorch(CaveStep toTorch) {
        this.toTorch = toTorch;
    }

    public CaveStep getToClimbingGear() {
        return toClimbingGear;
    }

    public void setToClimbingGear(CaveStep toClimbingGear) {
        this.toClimbingGear = toClimbingGear;
    }

    @Override
    public String toString() {
        return ("(" + this.getX() + "," + getY() + "," + getTool() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof CaveStep
                && (((CaveStep) o).getX().equals(x))
                && (((CaveStep) o).getY().equals(y))
                && (((CaveStep) o).getTool().equals(tool))
                ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String hashString = this.toString();
        int hashCode = hashString.hashCode();
        return hashCode; // Make the hashCode equivalent for XYPairs at the same coords.
    }
}

