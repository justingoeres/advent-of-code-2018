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
}
