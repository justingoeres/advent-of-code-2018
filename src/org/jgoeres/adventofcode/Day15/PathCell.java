package org.jgoeres.adventofcode.Day15;

public class PathCell extends MapCell {
    int counter;

    public PathCell(int x, int y, Integer counter) {
        super(x, y);
        this.counter = counter;
    }

    public PathCell(MapCell mapCell, Integer counter) {
        super(mapCell.getX(), mapCell.getY());
        this.counter = counter;
    }


    public int getCounter() {
        return counter;
    }
}
