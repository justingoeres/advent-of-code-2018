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

            TreeSet<Unit> enemyUnits = (unit.getRace() == RACE_ELF ? goblins : elves);
            // targetCells is all empty cells adjacent to enemies.
            TreeSet<MapCell> targetCells = BattleService.identifyTargets(unit,
                    enemyUnits,
                    map);

            if (targetCells.isEmpty()) {
                // If there are no valid target cells, end turn here somehow.
            }

            // Find the closest of the target cells
            // TODO: Need to eventually account for reachability here.
            MapCell nextStep = BattleService.findStepToTarget(unit, targetCells);

            if (nextStep != null) { // Does this account for when there's no move available?
                unit.move(nextStep);
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

    private void printBattle(){
        
    }
}
