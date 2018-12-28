package org.jgoeres.adventofcode.Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import static org.jgoeres.adventofcode.Day15.CharacterSet.*;
import static org.jgoeres.adventofcode.Day15.Race.RACE_ELF;
import static org.jgoeres.adventofcode.Day15.Race.RACE_GOBLIN;

public class Battle {
    HashMap<String, MapCell> map = new HashMap<>();
    TreeSet<Unit> elves = new TreeSet<>(new UnitComparator());
    TreeSet<Unit> goblins = new TreeSet<>(new UnitComparator());
    TreeSet<Unit> allUnits = new TreeSet<>(new UnitComparator());

    private static final boolean DEBUG_PRINT_MAP = false;
    private static final boolean DEBUG_PRINT_ARMIES = false;


    public Battle(String pathToFile) {
        loadBattle(pathToFile);
    }

    public void doTimerTick() {
        /*
        All units are very disciplined and always follow very strict combat rules.
        Units never move or attack diagonally, as doing so would be dishonorable.
        When multiple choices are equally valid, ties are broken in reading order:
        top-to-bottom, then left-to-right. For instance, the order in which units
        take their turns within a round is the reading order of their starting
        positions in that round, regardless of the type of unit or whether other
        units have moved after the round started. For example:

                         would take their
        These units:   turns in this order:
          #######           #######
          #.G.E.#           #.1.2.#
          #E.G.E#           #3.4.5#
          #.G.E.#           #.6.7.#
          #######           #######

         Each unit begins its turn by identifying all possible targets (enemy units).
         If no targets remain, combat ends.

        Then, the unit identifies all of the open squares (.) that are in range of each
        target; these are the squares which are adjacent (immediately up, down, left, or
        right) to any target and which aren't already occupied by a wall or another unit.
        Alternatively, the unit might already be in range of a target. If the unit is not
        already in range of a target, and there are no open squares which are in range of
        a target, the unit ends its turn
         */


        // Process this tick for every (living) unit (in order x,y)
        for (Unit unit : allUnits) {

            TreeSet<Unit> enemyUnits = (unit.getOppositeRace() == RACE_ELF ? elves : goblins);
            // TODO: If enemyUnits is empty, the battle is over!

            if (enemyUnits.isEmpty()) {
                // No enemies; nothing to do!
                break;
            }

            Unit targetUnit = null;
            // Are we already next to an enemy?
            if ((targetUnit = unit.canAttackUnit()) != null) { // if there is a unit available for attack.
                // Attack it.
                BattleService.handleAttack(unit, targetUnit);

                if (targetUnit.isDead()) { // Did it die?
                    // If so, remove it from the lists.
                    allUnits.remove(targetUnit); // from all units
                    // from its race
                    (targetUnit.getRace() == RACE_ELF ? elves : goblins).remove(targetUnit);
                }
            } else { // No attack available, so try to move
                // targetCells is all empty cells adjacent to enemies.
                TreeSet<MapCell> targetCells = BattleService.identifyTargets(unit,
                        enemyUnits,
                        map);

                if (targetCells.isEmpty()) {
                    // If there are no valid target cells, end turn here somehow.
                }

                MapCell nextStep = BattleService.findStepToTarget(unit, targetCells);

                if (nextStep != null) { // Does this account for when there's no move available?
                    unit.move(nextStep);
                } else {
                    System.out.println("no move available");
                }

                // Print the battle after every move, if requested.
                if (DEBUG_PRINT_MAP) {
                    printBattle();
                }
            }
            if (DEBUG_PRINT_ARMIES) {
                printArmies();
            }

        }
    }

    private void loadBattle(String pathToFile) {
        /*
        File looks like:
        ########.G................######
        #######........G.....E....######
        ########GE.................#####

        XY coordinates start in upper left at (0,0).

        NOTE:   File reading SKIPS walls, just treats them as cells that don't exist
                (and are null for connections)
         */

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Character currentChar;
            int y = 0; // start at line 0
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    currentChar = line.charAt(x); // get the next glyph

                    if (!currentChar.equals(SPACE) && !currentChar.equals(WALL)) { // skip spaces & walls
                        // If we're here, this is an open cavern, maybe with a unit on it.
                        MapCell newCell = new MapCell(x, y);
                        map.put(BattleService.keyFromXY(x, y), newCell);

                        if (!currentChar.equals(OPEN)) { // if this is NOT an open cell, there's something here!
                            Unit newUnit = null;
                            if (currentChar.equals(ELF)) { // if this is an elf
                                // Make an elf
                                newUnit = new Unit(RACE_ELF, newCell);
                                elves.add(newUnit);
                            } else if (currentChar.equals(GOBLIN)) { // if this is a goblin
                                // Make a goblin
                                newUnit = new Unit(RACE_GOBLIN, newCell);
                                goblins.add(newUnit);
                            }
                            if (newUnit != null) {
                                newCell.setCurrentUnit(newUnit); // assign this unit to the new cell.
                                allUnits.add(newUnit);
                            }
                        }
                    }
                }
                y++; // increment the line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        // Done loading, now wire up all the cells.
        findAllConnections();

        if (DEBUG_PRINT_MAP) {
            printBattle();
        }
    }

    private void findAllConnections() {
        // Every map cell is connected to others, find them by relative coordinates.

        for (Map.Entry<String, MapCell> mapCellEntry : map.entrySet()) {
            MapCell referenceCell = mapCellEntry.getValue();
            for (Direction direction : Direction.values()) { // process every direction from this cell
                MapCell relativeCell = BattleService.findRelativeMapCell(referenceCell, direction, map);
                referenceCell.setRelativeCell(relativeCell, direction);
            }
        }
    }

    public boolean isOver() {
        boolean elvesDead = elves.isEmpty();
        boolean goblinsDead = goblins.isEmpty();
        return (elvesDead || goblinsDead);
    }

    public void printBattle() {
        // battlefield input is 32x32
        for (int y = 0; y < 32; y++) {
            for (int x = 0; x < 32; x++) {
                MapCell cellToPrint;
                char charToPrint = '\0';
                if ((cellToPrint = map.get(BattleService.keyFromXY(x, y))) != null) {
                    // If this cell exists, see what it contains
                    if (cellToPrint.getCurrentUnit() != null) {
                        switch (cellToPrint.getCurrentUnit().getRace()) {
                            case RACE_ELF:
                                charToPrint = ELF;
                                break;
                            case RACE_GOBLIN:
                                charToPrint = GOBLIN;
                                break;
                        }
                    } else {
                        charToPrint = OPEN;
                    }
                } else {
                    // this cell doesn't exist (is a wall)
                    charToPrint = WALL;
                }
                System.out.print(charToPrint);
            }
            System.out.println(); // go to the next line.
        }
    }

    public void printArmies() {
        for (Unit elf : elves) {
            System.out.println("Elf at (" + elf.getCurrentCell().getX() + ","
                    + elf.getCurrentCell().getY() + "):\t"
                    + elf.getHitPoints());
        }
        for (Unit goblin : goblins) {
            System.out.println("Goblin at (" + goblin.getCurrentCell().getX() + ","
                    + goblin.getCurrentCell().getY() + "):\t"
                    + goblin.getHitPoints());
        }
    }
}


