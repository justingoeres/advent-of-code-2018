package org.jgoeres.adventofcode.Day15;


public class Unit {
    private Race race;
    int hitPoints;
    MapCell currentCell;

    public static final int STARTING_HIT_POINTS = 200;

    public Unit(Race race, MapCell currentCell) {
        this.race = race;
        this.currentCell = currentCell;
        this.hitPoints = STARTING_HIT_POINTS; // all units start with this many.
    }

    public void move(MapCell destination) {
        MapCell currentCell = getCurrentCell();

        // Move out of the current cell.
        currentCell.removeUnit();

        // Move into the new cell
        destination.setCurrentUnit(this);
        setCurrentCell(destination);
    }

    public Race getRace() {
        return race;
    }

    public MapCell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(MapCell currentCell) {
        this.currentCell = currentCell;
    }
}
