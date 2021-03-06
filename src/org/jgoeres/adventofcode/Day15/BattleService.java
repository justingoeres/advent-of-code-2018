package org.jgoeres.adventofcode.Day15;


import java.util.*;

public abstract class BattleService {

    private static final boolean DEBUG_PRINT_PATH_MAP = false;
    private static final boolean DEBUG_PRINT_ATTACKS = false;

    public static TreeSet<MapCell> identifyTargets(Unit unit, ArrayList<Unit> units, HashMap<String, MapCell> map) {
        TreeSet<MapCell> targetList = new TreeSet<>(new MapCellComparator());

        for (Unit targetUnit : units) {
            if (!(unit == targetUnit)) {    // skip ourselves
                TreeSet<MapCell> adjacentCells = findAdjacentCells(targetUnit.getCurrentCell());
                targetList.addAll(adjacentCells);
            }
        }
        return targetList;
    }

    public static MapCell findStepToTarget(Unit unit, TreeSet<MapCell> targetCells) {
        Integer minDistance = Integer.MAX_VALUE;
        MapCell closestCell = null;

        // Find the shortest path to this targetCell.
        TreeMap<MapCell, Integer> pathMapToTargets = pathMapToTargets(unit, targetCells);
        if (pathMapToTargets == null) {
            // If there was no path to any target.
            return null; // give up and return null.
        }

        if (DEBUG_PRINT_PATH_MAP) {
            printPathMap(pathMapToTargets);
        }

        // Now search through the pathMapToTargets to find the first target (in reading order)
        // in the map. That's the one we're going to move towards.
        MapCell destinationCell = null;
        for (MapCell pathTarget : pathMapToTargets.keySet()) {
            if (targetCells.contains(pathTarget)) {
                // if this cell in the pathMap is a targetCell, that's our destination!
                destinationCell = pathTarget;
                break;
            }
        }

        // Now trace the path from currentCell to our destinationCell to find the next step we need to take.
        MapCell nextStep = traceNextStepForward(unit.getCurrentCell(), destinationCell, pathMapToTargets);
        // When we get here, nextStep is the cell we need to move to!
        return nextStep;
    }

    private static MapCell traceNextStepForward(MapCell startCell, MapCell destinationCell, TreeMap<MapCell, Integer> pathMapToTargets) {
        int counter = 0;

        ArrayList<MapCell> pathSteps = new ArrayList<>();

        boolean foundPath = false;
        traceNextStepRecursive(startCell, destinationCell, pathMapToTargets, counter, pathSteps);

        return pathSteps.get(pathSteps.size() - 1); // last element of pathSteps is our next step on the path.
    }

    private static boolean traceNextStepRecursive(MapCell currentCell, MapCell destinationCell,
                                                  TreeMap<MapCell, Integer> pathMapToTargets,
                                                  int counter,
                                                  ArrayList<MapCell> pathSteps) {
        if (currentCell == destinationCell) {
            return true;
        }
        boolean foundPath = false;
        for (MapCell adjacentCell : findAdjacentCells(currentCell)) { // Check all the cells around the current one, in reading order.
            if ((pathMapToTargets.containsKey(adjacentCell))
                    && (pathMapToTargets.get(adjacentCell) == counter + 1)) { // if this adjacent cell is one step FURTHER from the start
                // then investigate that path.
                foundPath = traceNextStepRecursive(adjacentCell, destinationCell, pathMapToTargets, counter + 1, pathSteps);
                if (foundPath) {
                    pathSteps.add(adjacentCell);
                    break;
                }
            } else {
                // do nothing, this adjacentCell would be a step backward.
            }
        }
        return foundPath;

    }

    public static String keyFromXY(int x, int y) {
        String trackCoords = x + "," + y;
        return trackCoords;
    }

    public static String keyFromMapCell(MapCell mapCell) {
        return keyFromXY(mapCell.getX(), mapCell.getY());
    }

    private static String relativeKeyFromXY(int x0, int y0, Direction direction) {
        int x = 0;
        int y = 0;

        switch (direction) {
            case UP:
                x = x0;
                y = y0 - 1;
                break;
            case DOWN:
                x = x0;
                y = y0 + 1;
                break;
            case LEFT:
                x = x0 - 1;
                y = y0;
                break;
            case RIGHT:
                x = x0 + 1;
                y = y0;
                break;
        }
        String cellCoords = keyFromXY(x, y);
        return cellCoords;
    }

    public static MapCell findRelativeMapCell(MapCell mapCell, Direction
            direction, HashMap<String, MapCell> map) {
        String coords = relativeKeyFromXY(mapCell.getX(), mapCell.getY(), direction);
        if (map.containsKey(coords)) {
            MapCell relativeMapCell = map.get(coords);
            return relativeMapCell;
        } else { // if there's no cell in the specified direction, return null.
            return null;
        }
    }

    private static TreeMap<MapCell, Integer> pathMapToTargets(Unit unit, TreeSet<MapCell> targetCells) {
        // Calculate the ArrayList of steps (MapCells) that give the shortest path(s) to the target(s).
        // Algorithm Ref: https://en.scratch-wiki.info/wiki/Pathfinding

        TreeMap<MapCell, Integer> pathCells = new TreeMap<>(new MapCellComparator()); // TreeSet to store all the cells we've checked.
        //MapCell endPoint = targetCell;
        MapCell startPoint = unit.getCurrentCell(); // start at the current unit and work outward until we find something..
        // Go in circles working outward from the endpoint.
        // For each cell with counter value i, get all the adjacent cells.
        // Drop the ones that are already in our pathCells TreeSet,
        // then put counter value i+1 in the ones that remain.
        // Increment i, then repeat.

        boolean reachedEnd = false;
        // Initialize our pathCells list with the endPoint (although this may not be strictly necessary)
        pathCells.put(startPoint, 0);
        int counter = 0; // Start from 0
        while (!reachedEnd) {
            // For each PathCell with the current counter value...
            TreeSet<MapCell> edgeCells = new TreeSet<>(new MapCellComparator());
            for (Map.Entry<MapCell, Integer> pathCellEntry : pathCells.entrySet()) {
                if (pathCellEntry.getValue() == counter) {
                    // If the counter for this cell is the current counter, we're on an edge.
                    edgeCells.add(pathCellEntry.getKey());
                }
            }
            if (!edgeCells.isEmpty()) // If we have new edge cells to add.
            {
                // Now we've got all the edge cells.
                counter++; // increment counter because all the cells we're about to add are 1 further from the endPoint.

                // For each edge cell, get all of its adjacent cells, but drop the ones already in our TreeMap.
                for (MapCell edgeCell : edgeCells) {
                    for (MapCell newEdgeCandidate : findAdjacentCells(edgeCell)) {
                        if (!pathCells.containsKey(newEdgeCandidate)) {
                            // If this cell is NOT already in our pathCells...
                            pathCells.put(newEdgeCandidate, counter);
                        }
                    }
                }
                // Now we've got all the NEW edge cells added, with the new counter value.
                // Did we reach any endpoints yet?
                for (MapCell targetCell : targetCells) {
                    if (pathCells.containsKey(targetCell)) {
                        reachedEnd = true;
                    }
                }
            } else { // no new edge cells were found, so we've filled up everything we can get to.
                // No move available, so return null.
                return null;
            }
        }

        // Now we've got a whole TreeMap of PathCells that includes our startPoint and at least one targetCell!
        // We just need to find a path through it that obeys our "go in reading order" rules.
        return pathCells; // Return the entire pathCells map. Let the calling code decide what to do with it.
    }


    public static TreeSet<MapCell> findAdjacentCells(MapCell mapCell) {
        TreeSet<MapCell> adjacentCells = new TreeSet<>(new MapCellComparator());

        for (Direction direction : Direction.values()) {
            MapCell relativeCell = mapCell.getRelativeCell(direction);
            if ((relativeCell != null)  // relativeCell must exist (obviously)
                    && (relativeCell.getCurrentUnit() == null)) // ... and must be empty
            {
                adjacentCells.add(relativeCell);
            }
        }
        return adjacentCells;
    }

    public static TreeSet<MapCell> findAdjacentEnemies(MapCell mapCell, Race enemyRace) {
        TreeSet<MapCell> adjacentEnemies = new TreeSet<>(new MapCellComparator());

        for (Direction direction : Direction.values()) {
            MapCell relativeCell = mapCell.getRelativeCell(direction);
            if ((relativeCell != null)  // relativeCell must exist (obviously)
                    && (relativeCell.getCurrentUnit() != null)
                    && (relativeCell.getCurrentUnit().getRace() == enemyRace))// ... and must contain an enemy
            {
                adjacentEnemies.add(relativeCell);
            }
        }
        return adjacentEnemies;
    }

    public static void handleAttack(Unit attacker, Unit target) {
        if (DEBUG_PRINT_ATTACKS) {
            System.out.println(attacker.getRace() + " at (" + attacker.getCurrentCell().getX() + "," + attacker.getCurrentCell().getY() + ") attacks "
                    + target.getRace() + " at (" + target.getCurrentCell().getX() + "," + target.getCurrentCell().getY() + ")");
        }

        target.takeHit(attacker);
    }

    private static void printPathMap(TreeMap<MapCell, Integer> treeMap) {
        // The TreeMap is already sorted for printing, so we *should* be able to just itertate through it.
        int x = 0;
        int y = 0;
        for (Iterator itr = treeMap.keySet().iterator(); itr.hasNext(); ) { // for each MapCell in the map
            MapCell currentCell = (MapCell) itr.next();
            if (currentCell.getY() > y) {
                // If we're on a new row...
                // Start a new line
                System.out.println();
                // And update our row.
                y = currentCell.getY();
                x = 0; // and reset our column.
            }
            while (!((currentCell.getX() == x) && (currentCell.getY() == y))) { // keep going until we're at the screen coordinate for the currentCell
                System.out.print(" \t");
                x++; // and move forward one character.
            }
            // Then when we get to the current cell, print its counter value.
            System.out.print(treeMap.get(currentCell) + "\t");
            x++; // and move forward one character.
        }
        System.out.println(); // linefeed at the end
    }

    public static String formatUnitsInfo(ArrayList<Unit> units) {
        String result = "";
        Iterator<Unit> it = units.iterator();
        if (it.hasNext()) {
            result += it.next().formatUnitInfo();
        }
        while (it.hasNext()) {
            result += ", " + it.next().formatUnitInfo();
        }
        return result;
    }
}

