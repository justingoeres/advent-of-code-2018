package org.jgoeres.adventofcode.Day15;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public abstract class BattleService {

    public static TreeSet<MapCell> identifyTargets(Unit unit, TreeSet<Unit> units, HashMap<String, MapCell> map) {
        TreeSet<MapCell> targetList = new TreeSet<>(new MapCellComparator());

        for (Unit targetUnit : units) {
            if (!(unit == targetUnit)) {    // skip ourselves
                for (Direction direction : Direction.values()) {
                    MapCell relativeCell = findRelativeMapCell(targetUnit.getCurrentCell(), direction, map);
                    if ((relativeCell != null)  // relativeCell must exist (obviously)
                            && (relativeCell.getCurrentUnit() == null)) // ... and must be empty
                    {
                        targetList.add(relativeCell);
                    }
                }
            }
        }
        return targetList;

    }

    public static String keyFromXY(int x, int y) {
        String trackCoords = x + "," + y;
        return trackCoords;
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
}
