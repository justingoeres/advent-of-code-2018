package org.jgoeres.adventofcode.Day15;

public class MapCell {
    private int x, y;
    private MapCell aboveCell;
    private MapCell leftCell;
    private MapCell rightCell;
    private MapCell belowCell;

    private Unit currentUnit;

    public void setRelativeCell(MapCell relativeCell, Direction direction) {
        switch (direction) {
            case UP:
                aboveCell = relativeCell;
                break;
            case LEFT:
                leftCell = relativeCell;
                break;
            case RIGHT:
                rightCell = relativeCell;
                break;
            case DOWN:
                belowCell = relativeCell;
                break;
        }
    }

    public MapCell getRelativeCell(Direction direction) {
        switch (direction) {
            case UP:
                return aboveCell;
            case LEFT:
                return leftCell;
            case RIGHT:
                return rightCell;
            case DOWN:
                return belowCell;
        }
        return null; // this should never happen
    }


    public MapCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void removeUnit() {
        setCurrentUnit(null);
    }

}
