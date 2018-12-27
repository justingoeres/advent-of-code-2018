package org.jgoeres.adventofcode.Day15;


import java.lang.reflect.Array;
import java.util.*;

public abstract class BattleService {

    public static TreeSet<MapCell> identifyTargets(Unit unit, TreeSet<Unit> units, HashMap<String, MapCell> map) {
        TreeSet<MapCell> targetList = new TreeSet<>(new MapCellComparator());

        for (Unit targetUnit : units) {
            if (!(unit == targetUnit)) {    // skip ourselves
                TreeSet<MapCell> adjacentCells = findAdjacentCells(targetUnit.getCurrentCell());
                targetList.addAll(adjacentCells);
            }
        }
        return targetList;
    }

    public static MapCell findClosestTarget(Unit unit, TreeSet<MapCell> targetCells) {
        Integer minDistance = Integer.MAX_VALUE;
        MapCell closestCell = null;

   //     for (MapCell mapCell : targetCells) {
            // Because the TreeSet already is in "reading" order, we don't have to worry
            // about ties. The first one we find will be the "first in reading order" one.

            // Find the shortest path to this targetCell.
            TreeMap<MapCell, Integer> shortestPathMap = shortestPathToTarget(unit, targetCells);
            //TODO: WORKING HERE!!! NEED TO FIND THE ACTUAL SHORTEST PATH.
            // Use the counter value of Unit.currentCell in the shortestPathMap to determine the
            // shortest path. That will give us the "closestTarget" MapCell.

            // Then (in the calling code?) get the minimum counter number from each "shortestPath" (there maybe more than one)
            // for each cell adjacent to Unit.currentCell. -- or maybe we calculate the first step here and return that.


            /*
            // Find the manhattan distance to each target cell,
            // keeping track of the closest one.
            // TODO: Account for pathfinding & reachability at some point.
            Integer distance = manhattanDistance(unit.getCurrentCell(), mapCell);
            if ((distance < minDistance)
                    || (closestCell == null)) {
                // If this is the closest cell we've seen.
                // Update the minimum.
                minDistance = distance;
                closestCell = mapCell;
            }
            */
//        }
        return closestCell;
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

    public static MapCell findRelativeMapCell(MapCell mapCell, Direction direction, HashMap<String, MapCell> map) {
        String coords = relativeKeyFromXY(mapCell.getX(), mapCell.getY(), direction);
        if (map.containsKey(coords)) {
            MapCell relativeMapCell = map.get(coords);
            return relativeMapCell;
        } else { // if there's no cell in the specified direction, return null.
            return null;
        }
    }

    private static Integer manhattanDistance(MapCell p1, MapCell p2) {
        Integer xDist = Math.abs(p2.getX() - p1.getX());
        Integer yDist = Math.abs(p2.getY() - p1.getY());

        return (xDist + yDist);
    }

    private static TreeMap<MapCell, Integer> shortestPathToTarget(Unit unit, TreeSet<MapCell> targetCells) {
        // Calculate the ArrayList of steps (MapCells) that give the shorted path to the target.
        // Algorithm Ref: https://en.scratch-wiki.info/wiki/Pathfinding

        TreeMap<MapCell, Integer> pathCells = new TreeMap<>(new MapCellComparator()); // TreeSet to store all the cells we've checked.
        //MapCell endPoint = targetCell;
        MapCell startPoint = unit.getCurrentCell(); // start at the current unit and work outward until we find something..
        // TODO: WORKING HERE. ADD CIRCULAR SCANNING FROM ENDPOINT
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
        }

        // Now we've got a whole TreeMap of PathCells that includes our startPoint!
        // We just need to find a path through it that obeys our "go in reading order" rules.

        //TODO: Working above on the TreeMap comparator, but overall need to somehow sort our PathCells.
        // I think.
        return pathCells; // Return the entire pathCells map. Let the calling code decide what to do with it.
    }


    private static TreeSet<MapCell> findAdjacentCells(MapCell mapCell) {
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
}
