package org.jgoeres.adventofcode.Day13;

public class GridPoint {
    private Integer x;
    private Integer y;
    private Character name;

    public GridPoint() {
    }

    public GridPoint(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public GridPoint(Integer x, Integer y, Character name) {
        this.x = x;
        this.y = y;
        this.name = name;
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

    public Character getName() {
        return name;
    }

    public void setName(Character name) {
        this.name = name;
    }
}
