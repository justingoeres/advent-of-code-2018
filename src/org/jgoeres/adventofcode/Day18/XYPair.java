package org.jgoeres.adventofcode.Day18;

public class XYPair {
    private Integer x;
    private Integer y;


    public XYPair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }


    public XYPair cellAbove() {
        return new XYPair(getX(), getY() - 1);
    }

    public XYPair cellBelow() {
        return new XYPair(getX(), getY() + 1);
    }

    public XYPair cellLeft() {
        return new XYPair(getX() - 1, getY());
    }

    public XYPair cellRight() {
        return new XYPair(getX() + 1, getY());
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

    @Override
    public String toString() {
        return ("(" + this.getX() + "," + getY() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof XYPair
                && (((XYPair) o).getX().equals(x))
                && (((XYPair) o).getY().equals(y))
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

