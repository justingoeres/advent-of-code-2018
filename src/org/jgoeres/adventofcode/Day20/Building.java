package org.jgoeres.adventofcode.Day20;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Scanner;

public class Building {
    private HashMap<String, Room> rooms = new HashMap<>();
    private Room startingRoom;

    public Building(String pathToFile) {
        loadBuilding(pathToFile);
    }

    private void loadBuilding(String pathToFile) {
        /*
        File looks like:
        ^WSWSSEEESEEENWWNEEEESESSENNNENENESSWSEENENWNNESESEESSWW(NEWS|)SSSESSEN...$
        (all one long line)
        */

        try (Scanner sc = new Scanner(new FileReader(pathToFile))) {
            sc.useDelimiter("");    // Scan the line character by character.
            Room currentRoom = null;
            while (sc.hasNext()) {
                Character nextDirection = sc.next().charAt(0);  // Get the next character.

                Room newRoom = null;   // create a new room
                int newX = 0;
                int newY = 0;

                switch (nextDirection) {
                    case '^':   // starting room
                        // Don't need to do anything, the default newX & newY will give us a room at 0,0.
                        break;

                    case 'N':   // connect to the north.
                        newX = currentRoom.getX();
                        newY = currentRoom.getY() + 1; // y is positive to the north.
                        break;

                    case 'E':   // connect to the east.
                        newX = currentRoom.getX() + 1; // x is positive to the east.
                        newY = currentRoom.getY();
                        break;

                    case 'S':   // connect to the south.
                        newX = currentRoom.getX();
                        newY = currentRoom.getY() - 1; // y is negative to the south.
                        break;

                    case 'W':   // connect to the west.
                        newX = currentRoom.getX() - 1; // x is negative to the west.
                        newY = currentRoom.getY();
                        break;
                    default:
                        System.out.println("unhandled character: " + nextDirection);
                }

                // Look up the new room, or create it.
                if (!rooms.containsKey(XYtoKey(newX, newY))) {
                    // if this is a room we haven't seen before.
                    newRoom = new Room(newX, newY);
                    rooms.put(newRoom.toString(), newRoom); // add it to our map.
                } else {
                    // We've seen this room before!
                    newRoom = rooms.get(newRoom.toString());
                }

                // Connect up to the new room.
                switch (nextDirection) {
                    case 'N':   // connect to the north.
                        currentRoom.connectToNorth(newRoom);
                        break;

                    case 'E':   // connect to the east.
                        currentRoom.connectToEast(newRoom);
                        break;

                    case 'S':   // connect to the south.
                        currentRoom.connectToSouth(newRoom);
                        break;

                    case 'W':   // connect to the west.
                        currentRoom.connectToWest(newRoom);
                        break;
                    default:
                        System.out.println("unhandled character: " + nextDirection);
                }

                currentRoom = newRoom;  // Move into the new room we just made.

            }

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    private String XYtoKey(int x, int y) {
        return "(" + x + "," + y + ")";
    }
}
