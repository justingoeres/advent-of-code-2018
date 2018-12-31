package org.jgoeres.adventofcode.Day15;


import static org.jgoeres.adventofcode.Day15.CharacterSet.ELF;
import static org.jgoeres.adventofcode.Day15.CharacterSet.GOBLIN;
import static org.jgoeres.adventofcode.Day15.Race.RACE_ELF;
import static org.jgoeres.adventofcode.Day15.Race.RACE_GOBLIN;

public class Unit {
    private String name;
    private Race race;
    int hitPoints;
    MapCell currentCell;
    private int attackPower;

    public static final int STARTING_HIT_POINTS = 200;
    public static final int DEFAULT_ATTACK_POWER = 3;
    private static final MapCell NOWHERE = new MapCell(-1, -1);

    public Unit(Race race, MapCell currentCell, String name) {
        this.race = race;
        this.currentCell = currentCell;
        this.hitPoints = STARTING_HIT_POINTS; // all units start with this many.
        this.name = name;
        this.attackPower = DEFAULT_ATTACK_POWER; // create unit with default power, it can be changed later.
    }

    public void move(MapCell destination) {
        MapCell currentCell = getCurrentCell();

        // Move out of the current cell.
        currentCell.removeUnit();

        // Move into the new cell
        destination.setCurrentUnit(this);
        setCurrentCell(destination);
    }


    public Unit canAttackUnit() {
        Unit weakestEnemy = null;

        for (MapCell adjacentEnemyCell : BattleService.findAdjacentEnemies(getCurrentCell(), getOppositeRace())) {
            Unit adjacentEnemy = adjacentEnemyCell.getCurrentUnit();
            // TODO: Scan for the WEAKEST adjacent unit, not the *first* one.
            if ((adjacentEnemy != null) // if an adjacent unit exists...
                    && (adjacentEnemy.getRace() == getOppositeRace()) // and is an enemy
                    && (!adjacentEnemy.isDead())) { // and is alive
                if ((weakestEnemy == null)
                        || (adjacentEnemy.getHitPoints() < weakestEnemy.getHitPoints())) {
                    weakestEnemy = adjacentEnemy;
                }
            }
        }
        return weakestEnemy;
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

    public int getHitPoints() {
        return hitPoints;
    }

    public Race getOppositeRace() {
        return ((getRace() == RACE_ELF) ? RACE_GOBLIN : RACE_ELF);
    }

    public void takeHit() {
        // Reduce my strength by the attack power of the hit.
        hitPoints -= getAttackPower();

        if (isDead()) {
            // it's dead.
            die();
        }
    }

    private void die() {
        // Remove this unit from its cell.
        currentCell.removeUnit();
        // Remove the cell from this unit.
//        setCurrentCell(null);
        setCurrentCell(NOWHERE);
    }

    public boolean isDead() {
        return (hitPoints <= 0);
    }

    public String getName() {
        return name;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public String formatUnitInfo() {
        String result = String.format("%c(%d)",
                (getRace() == RACE_ELF ? ELF : GOBLIN),
                getHitPoints());
        return result;
    }
}


