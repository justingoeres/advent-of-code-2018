package org.jgoeres.adventofcode.Day15;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.jgoeres.adventofcode.Day15.CharacterSet.*;
import static org.jgoeres.adventofcode.Day15.Race.RACE_ELF;
import static org.jgoeres.adventofcode.Day15.Race.RACE_GOBLIN;

public class Battle {
    HashMap<String, MapCell> map = new HashMap<>();
    HashSet<Unit> elves = new HashSet<>();
    HashSet<Unit> goblins = new HashSet<>();

    public Battle(String pathToFile) {
        loadBattle(pathToFile);
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
                        map.put(keyFromXY(x, y), newCell);

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
                MapCell relativeCell = findRelativeMapCell(referenceCell, direction);
                referenceCell.setRelativeCell(relativeCell,direction);
            }
        }
    }

    private String keyFromXY(int x, int y) {
        String trackCoords = x + "," + y;
        return trackCoords;
    }

    private String relativeKeyFromXY(int x0, int y0, Direction direction) {
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
        String cellCoords = x + "," + y;
        return cellCoords;
    }

    private MapCell findRelativeMapCell(MapCell mapCell, Direction direction) {
        String coords = relativeKeyFromXY(mapCell.getX(), mapCell.getY(), direction);
        if (map.containsKey(coords)) {
            MapCell relativeMapCell = map.get(coords);
            return relativeMapCell;
        } else { // if there's no cell in the specified direction, return null.
            return null;
        }
    }
}
