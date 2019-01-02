package org.jgoeres.adventofcode.Day17;

public class XYPair {
    private Integer x;
    private Integer y;

    public XYPair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
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
    public boolean equals(Object o)
    {
        System.out.println("equals");
        if(o instanceof XYPair
                && (x == ((XYPair) o).getX())
                && (y == ((XYPair) o).getY())
                )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode(); // Make the hashCode equivalent for XYPairs at the same coords.
    }
}
