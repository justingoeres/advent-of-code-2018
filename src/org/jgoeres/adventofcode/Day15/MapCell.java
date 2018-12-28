package org.jgoeres.adventofcode.Day15;

public class MapCell {
    private int x, y;
    private MapCell aboveCell;
    private MapCell rightCell;
    private MapCell belowCell;
    private MapCell leftCell;

    private Unit currentUnit;

    public void setRelativeCell(MapCell relativeCell, Direction direction) {
        switch (direction) {
            case UP:
                aboveCell = relativeCell;
            case RIGHT:
                rightCell = relativeCell;
            case DOWN:
                belowCell = relativeCell;
            case LEFT:
                leftCell = relativeCell;
        }
    }

    public MapCell getRelativeCell(Direction direction) {
        switch (direction) {
            case UP:
                return aboveCell;
            case RIGHT:
                return rightCell;
            case DOWN:
                return belowCell;
            case LEFT:
                return leftCell;
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

    public void removeUnit(){
        setCurrentUnit(null);
    }

}
