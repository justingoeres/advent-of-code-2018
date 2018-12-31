package org.jgoeres.adventofcode.Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import static org.jgoeres.adventofcode.Day15.CharacterSet.*;
import static org.jgoeres.adventofcode.Day15.Race.RACE_ELF;
import static org.jgoeres.adventofcode.Day15.Race.RACE_GOBLIN;

public class Battle {
    HashMap<String, MapCell> map = new HashMap<>();
    //    TreeSet<Unit> elves = new TreeSet<>(new UnitComparator());
//    TreeSet<Unit> goblins = new TreeSet<>(new UnitComparator());
//    TreeSet<Unit> allUnits = new TreeSet<>(new UnitComparator());
    ArrayList<Unit> elves = new ArrayList<>();
    ArrayList<Unit> goblins = new ArrayList<>();
    ArrayList<Unit> allUnits = new ArrayList<>();
    private boolean isOver = false;
    private int roundsComplete = 0;

    private static final boolean DEBUG_PRINT_MAP = false;

    public Battle(String pathToFile) {
        loadBattle(pathToFile);
    }

    public void runBattle(boolean PRINT_EACH_TURN) {
        while (true) {
            boolean roundComplete = this.doTimerTick();

            if (roundComplete) {
                roundsComplete++; // ... mark the round complete, then continue.
            }
            if (!this.isOver()) { // If we're not done...
                if (PRINT_EACH_TURN) {
                    System.out.println("\n============ AFTER ROUND #" + roundsComplete + " ============");
                    this.printBattle();
                }
            } else {
                break; // we're done.
            }
        }
    }

    public boolean doTimerTick() {
        boolean roundComplete = true; // assume the round will complete.
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

        // Sort everything?
        allUnits.sort(new UnitComparator());
        elves.sort(new UnitComparator());
        goblins.sort(new UnitComparator());

        TreeSet<Unit> deadUnits = new TreeSet<>(new UnitComparator());
        // Process this tick for every (living) unit (in order x,y)
        for (Unit unit : allUnits) {
//        for (Iterator allUnitsIterator = allUnits.iterator(); allUnitsIterator.hasNext(); ) {
//            Unit unit = (Unit) allUnitsIterator.next();


            ArrayList<Unit> enemyUnits = (unit.getOppositeRace() == RACE_ELF ? elves : goblins);

            boolean allDead = allDead(enemyUnits);
//            if (enemyUnits.isEmpty()) {
            if (!unit.isDead()) { // if unit is alive
                if (allDead) {
                    // No enemies; nothing to do!
                    isOver = true;
                    roundComplete = false;
                    break;
                }
                Unit targetUnit = null;
                // Are we already next to an enemy?
                if ((targetUnit = unit.canAttackUnit()) != null) { // if there is a unit available for attack.
                    // Attack it.
                    BattleService.handleAttack(unit, targetUnit);

                    if (targetUnit.isDead()) { // Did it die?
//                    // If so, remove it from the lists.
//                    allUnits.remove(targetUnit); // from all units
//                    // from its race
//                    (targetUnit.getRace() == RACE_ELF ? elves : goblins).remove(targetUnit);
                        // do nothing, it obviously can't attack
                        deadUnits.add(targetUnit);
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
//                        System.out.println("no move available");
                    }

                    // After the move, try to attack.
                    if ((targetUnit = unit.canAttackUnit()) != null) { // if there is a unit available for attack.
                        // Attack it.
                        BattleService.handleAttack(unit, targetUnit);

                        if (targetUnit.isDead()) { // Did it die?
//                    // If so, remove it from the lists.
//                    allUnits.remove(targetUnit); // from all units
//                    // from its race
//                    (targetUnit.getRace() == RACE_ELF ? elves : goblins).remove(targetUnit);
                            // do nothing, it obviously can't attack
                            deadUnits.add(targetUnit);
                        }
                    }

                    // Print the battle after every move, if requested.
                    if (DEBUG_PRINT_MAP) {
                        printBattle();
                    }
                }
            } // end if unit is alive.
        }
        // Remove any dead bodies from the unit lists.
        for (Iterator deadUnitIterator = allUnits.iterator(); deadUnitIterator.hasNext(); ) {
            Unit unit = (Unit) deadUnitIterator.next();
            if (unit.isDead()) {
                deadUnitIterator.remove(); // remove from allUnits
                (unit.getRace() == RACE_ELF ? elves : goblins).remove(unit); // remove from its race.
            }
        }
       /* for (Unit deadUnit : deadUnits) {
            for (Unit unit : allUnits) {
                // remove if the name matches
                if (deadUnit.getName().equals(unit.getName())) {
                    allUnits.remove(unit); // remove from all units.
                    break;
                }
            }
            TreeSet<Unit> deadUnitRaceSet = (deadUnit.getRace() == RACE_ELF ? elves : goblins);
            for (Unit unit : deadUnitRaceSet) {
                // remove if the name matches
                if (deadUnit.getName().equals(unit.getName())) {
                    deadUnitRaceSet.remove(unit); // remove from race units.
                    break;
                }
            }

            //            boolean allUnitsRemoved = allUnits.remove(deadUnit);
//            Race deadUnitRace = deadUnit.getRace();
//            TreeSet<Unit> deadUnitRaceSet = (deadUnitRace == RACE_ELF ? elves : goblins);
//            boolean raceRemoved = deadUnitRaceSet.remove(deadUnit); // remove from its race.
//                        boolean raceRemoved = (deadUnit.getRace() == RACE_ELF ? elves : goblins).remove(deadUnit); // remove from its race.
//            System.out.println(allUnitsRemoved + "\t" + raceRemoved);
        }
        */
        return roundComplete;
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
                                newUnit = new Unit(RACE_ELF, newCell, "E" + x + "," + y);
                                elves.add(newUnit);
                            } else if (currentChar.equals(GOBLIN)) { // if this is a goblin
                                // Make a goblin
                                newUnit = new Unit(RACE_GOBLIN, newCell, "G" + x + "," + y);
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

    public void setRaceAttackPower(Race race, int attackPower) {
        ArrayList<Unit> unitsToModify = (race == RACE_ELF ? elves : goblins);
        for (Unit unit : unitsToModify) {
            unit.setAttackPower(attackPower);
        }
    }

    private boolean allDead(ArrayList<Unit> enemyUnits) {
        boolean allDead = true;
        for (Unit enemyUnit : enemyUnits) {
            allDead &= enemyUnit.isDead();
            if (!allDead) {
                break;
            }
        }
        return allDead;
    }

    public boolean isOver() {
//        boolean elvesDead = allDead(elves);
//        boolean goblinsDead = allDead(goblins);
//        return (elvesDead || goblinsDead);
        return isOver;
    }

    public Race getWinner() {
        if (!allDead(elves)) return RACE_ELF;
        return RACE_GOBLIN;
    }

    public int calculateScore(Race winner) {
        ArrayList<Unit> winningSet = (winner == RACE_ELF ? elves : goblins);
        int totalScore = 0;
        for (Unit unit : winningSet) {
            if (!unit.isDead()) {
                // Ignore dead guys
                totalScore += unit.getHitPoints();
            }
        }
        return totalScore;
    }

    public void printBattle() {
        // battlefield input is 32x32

        int xmax = 0;
        int ymax = 0;
        for (Map.Entry<String, MapCell> mapCellEntry : map.entrySet()) {
            MapCell mapCell = mapCellEntry.getValue();
            if (mapCell.getX() > xmax) xmax = mapCell.getX();
            if (mapCell.getY() > ymax) ymax = mapCell.getY();
        }

        for (int y = 0; y <= ymax; y++) {
            String battleLine = "";
            ArrayList<Unit> unitsInfo = new ArrayList<>();
            for (int x = 0; x <= xmax; x++) {
                MapCell cellToPrint;
                char charToPrint = '\0';
                if ((cellToPrint = map.get(BattleService.keyFromXY(x, y))) != null) {
                    // If this cell exists, see what it contains
                    if (cellToPrint.getCurrentUnit() != null) {
                        unitsInfo.add(cellToPrint.getCurrentUnit());
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
//                System.out.print(charToPrint);
                battleLine += charToPrint;
            }
//            System.out.println(); // go to the next line.
            System.out.println(battleLine + "\t" + BattleService.formatUnitsInfo(unitsInfo)); // go to the next line.
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

    public int getRoundsComplete() {
        return roundsComplete;
    }

    public int getNumberOfUnits(Race race) {
        int numberOfUnits = (race == RACE_ELF ? elves : goblins).size();
        return numberOfUnits;
    }
}


