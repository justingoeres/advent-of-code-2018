package org.jgoeres.adventofcode.Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Building {
    private HashMap<String, Room> rooms = new HashMap<>();
    private HashMap<Integer, Jump> jumps = new HashMap<>();
    // A map for all the spiders everywhere. Key is the string index, and the HashSet is all the spiders at that index.
    private HashMap<Integer, HashSet<Spider>> spiderMap = new HashMap<>();

    private final boolean DEBUG_PRINT_SPIDERS = false;
    private final boolean DEBUG_PRINT_BUILDING_SUMMARY = false;

    public Building(String pathToFile) {
        loadBuilding(pathToFile);
    }

    private void loadBuilding(String pathToFile) {
        /*
        File looks like:
        ^WSWSSEEESEEENWWNEEEESESSENNNENENESSWSEENENWNNESESEESSWW(NEWS|)SSSESSEN...$
        (all one long line)
        */

        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            line = br.readLine(); // read the whole map into one long String

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        // Locate all the jumps
        ArrayList<Jump> jumpsList = findAllJumps(line);

        // Put all the jumps into a HashMap we can lookup later by index.
        for (Jump jump : jumpsList) {
            jumps.put(jump.openIndex, jump);
        }

        // Work through the string, spawning spiders as we go.
        // First spider starts at 0,0 and the front of the string.
        Room startingRoom = new Room(0, 0, 0);
        Spider firstSpider = new Spider(startingRoom, 0);
        rooms.put(startingRoom.toString(), startingRoom); // add it to our map.

        addSpiderAtSpiderIndex(firstSpider);

        for (int i = 0; i < line.length(); i++) {
            Character thisChar = line.charAt(i);
            HashSet<Spider> spidersHere = spiderMap.get(i);

            if (DEBUG_PRINT_SPIDERS) {
                System.out.println(i + ":\t" + thisChar + "\t# spiders here:\t" + spidersHere.size());
            }

            Room newRoom;

            for (Spider spider : spidersHere) {
                // Check every spider here
                Room currentRoom = spider.room;

                // Create & connect the current room to the next room.
                newRoom = doRoomConnection(currentRoom, thisChar);

                // Process the branches
                switch (thisChar) {
                    // Branches
                    case '(':   // New jump
                        // Look up this jump.
                        Jump jump = jumps.get(i);   // The jumps are keyed by the string position of the '('

                        // Create new spider(s) in this room.
                        spawnNewSpiders(spider, jump);

                        // Put the pipe-to-close jump on this spider's stack
                        spider.jumpStack.push(jump);
                        // Then step forward.
                        removeSpiderAtSpiderIndex(spider); // Move the spider out of this index's set
                        spider.stepForward();
                        addSpiderAtSpiderIndex(spider); // Add the spider to the set for the index it's now at.
                        break;

                    case '|':   // The pipe of a jump.
                        if (!spider.jumpStack.isEmpty()) {
                            // If we have a jump on the stack, this pipe means jump.
                            jump = spider.jumpStack.pop();
                            // Jump this spider to the close paren.
                            removeSpiderAtSpiderIndex(spider); // Move the spider out of this index's set
                            spider.index = jump.closeIndex;
                            addSpiderAtSpiderIndex(spider); // Add the spider to the set for the index it's now at.
                        } else {
                            // There's no jump no the stack because this is a brand new spider that just got fast-forwarded here.
                            // So just step forward.
                            removeSpiderAtSpiderIndex(spider); // Move the spider out of this index's set
                            spider.stepForward();
                            addSpiderAtSpiderIndex(spider);
                        }
                        break;

                    case ')':   // The close paren of a jump
                        // Nothing happens on a close paren, just step forward.
                        removeSpiderAtSpiderIndex(spider); // Move the spider out of this index's set
                        spider.stepForward();
                        addSpiderAtSpiderIndex(spider); // Add the spider to the set for the index it's now at.
                        break;

                    case '$':    // end of the line
                        break; // No need to move when we're done, so just return

                    default:
                        // Just a regular direction character, and we created the room earlier, so move the spider to it.
                        spider.room = newRoom;  // Move the spider into the new room we just made.
                        removeSpiderAtSpiderIndex(spider); // Move the spider out of this index's set
                        spider.stepForward();    // Move the spider to the next character.
                        addSpiderAtSpiderIndex(spider); // Add the spider to the set for the index it's now at.
                        break;
                }
            }
        }

        if (DEBUG_PRINT_BUILDING_SUMMARY) {
            // Find extents of the building.
            int minX = Integer.MAX_VALUE;
            int minY = Integer.MAX_VALUE;
            int maxX = Integer.MIN_VALUE;
            int maxY = Integer.MIN_VALUE;
            for (Map.Entry<String, Room> roomEntry : rooms.entrySet()) {
                Room room = roomEntry.getValue();
                int x = room.getX();
                if (x < minX) minX = x;
                if (x > maxX) maxX = x;
                int y = room.getY();
                if (y < minY) minY = y;
                if (y > maxY) maxY = x;
            }

            System.out.println("Number of rooms:\t" + rooms.size());
            System.out.println("Building width:\t(" + minX + ", " + maxX + ") = " + (maxX + 1 - minX));
            System.out.println("Building height:\t(" + minY + ", " + maxY + ") = " + (maxY + 1 - minY));
        }
    }

    private Room doRoomConnection(Room currentRoom, Character thisChar) {
        // Create and connect up a room in the specified direction.
        int newX = 0;
        int newY = 0;

        switch (thisChar) {
            // Beginning & End
            case '^':   // starting room
                // Don't need to do anything, the first spider is born at (0,0).
                break;
            case '$':   // End of the route.
            case '(':   // open paren
            case '|':   // pipe
            case ')':   // close
                return null; // No rooms to create. Just return.

            // Moves
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
                System.out.println("unhandled character: " + thisChar);
                return null;
        }

        // Look up the new room, or create it
        Room newRoom;
        String key = XYtoKey(newX, newY);
        if (!rooms.containsKey(key)) {
            // if this is a room we haven't seen before.
            // create it, one step further from the origin.
            newRoom = new Room(newX, newY, currentRoom.getDistance() + 1);
            rooms.put(newRoom.toString(), newRoom); // add it to our map.
        } else {
            // We've seen this room before!
            newRoom = rooms.get(key);
        }

        // Connect up to the new room.
        switch (thisChar) {
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
                // Skip unhandled characters (like ^ and $)
                //System.out.println("unhandled character: " + nextDirection);
        }
        return newRoom;
    }

    private void spawnNewSpiders(Spider parent, Jump jump) {
        // Create one or two new spiders, depending on how this jump works.

        // Create a new spider at this location.
        // Fast-forward it to ONE PAST the pipe.
        Spider spider1 = new Spider(parent.room, jump.pipeIndex1 + 1);
        // Duplicate parent's jump stack onto the new one.
        spider1.jumpStack = (Stack<Jump>) parent.jumpStack.clone();
        addSpiderAtSpiderIndex(spider1); // Add the spider to the set for the index it's now at.

        if (jump.pipeIndex2 != null) {
            // If there's a second pipe in this jump, spawn a second spider
            Spider spider2 = new Spider(parent.room, jump.pipeIndex2 + 1);
            // Duplicate parent's jump stack onto the new one.
            spider2.jumpStack = (Stack<Jump>) parent.jumpStack.clone();
            addSpiderAtSpiderIndex(spider2); // Add the spider to the set for the index it's now at.
        }
    }

    private ArrayList<Jump> findAllJumps(String string) {
        ArrayList<Jump> allJumps = new ArrayList<>();

        HashMap<Integer, Jump> jumps = new HashMap<>();

        int depth = 0;
        int maxDepth = Integer.MIN_VALUE;   // Track the max depth, for fun.
        for (int i = 0; i < string.length(); i++) {
            Character thisChar = string.charAt(i);
            switch (thisChar) {
                case '(':
                    // Open paren means beginning of a new jump
                    // Initialize it with this
                    depth++;
                    if (depth > maxDepth) maxDepth = depth; // Track the max depth.
                    Jump newJump = new Jump(i);
                    jumps.put(depth, newJump); // Add the new jump to the HashMap, indexed to its paren level.
                    break;
                case '|':
                    // Found the middle of a jump.
                    // There must be a jump in the HashMap with this depth, or we wouldn't be at this level.
                    if (jumps.get(depth).pipeIndex1 == null) {
                        // First pipe
                        jumps.get(depth).pipeIndex1 = i;   // Set the pipeIndex1 of this jump to the current string position.
                    } else {
                        // second pipe
                        jumps.get(depth).pipeIndex2 = i;
                    }
                    break;
                case ')':
                    // Found the end of a jump.
                    // Remove the jump from the HashMap since we're done with this level of nesting.
                    newJump = jumps.remove(depth);
                    newJump.closeIndex = i; // Set the closeIndex of this jump
                    allJumps.add(newJump);  // Add the completed jump to the overall jumps list.
                    depth--;
                    break;
            }
        }
        return allJumps;
    }

    private void addSpiderAtSpiderIndex(Spider spider) {
        addSpiderAtIndex(spider, spider.index);
    }

    private void addSpiderAtIndex(Spider spider, int index) {
        HashSet<Spider> spiderHashSet;
        if (spiderMap.containsKey(index)) {
            // If a map for this index exists, get it
            spiderHashSet = spiderMap.get(index);
        } else {
            // a map for this index doesn't exist; create it.
            spiderHashSet = new HashSet<>();
            // And put it in the big list of maps.
            spiderMap.put(index, spiderHashSet);
        }
        // Finally, add the current spider to the set at the specified index.
        spiderHashSet.add(spider);
    }

    private void removeSpiderAtSpiderIndex(Spider spider) {
        // This function is disabled because I couldn't get the removal to work, and the code is fast enough anyway.
/*        int index = spider.index;
        if (spiderMap.containsKey(index)) {
            // If a map for this index exists
            HashSet<Spider> spiderHashSet = spiderMap.get(index);
//            if (spiderHashSet.contains(spider)) {
            // AND it contains our spider
            spiderMap.get(index).remove(spider);    // Then remove this spider from it.

            // Finally, if the set of spiders for this index is empty, remove it from the map
            if (spiderHashSet.isEmpty()) {
                spiderMap.remove(index);
            }
            //  }

        }
*/
    }

    public Room findMostDistant() {
        // Find the most distant room.
        Room mostDistant = null;
        int maxDistance = Integer.MIN_VALUE;
        for (Map.Entry<String, Room> roomEntry : rooms.entrySet()) {
            Room room = roomEntry.getValue();
            if (room.getDistance() > maxDistance) {
                maxDistance = room.getDistance();
                mostDistant = room;
            }
        }
        return mostDistant;
    }

    private String XYtoKey(int x, int y) {
        return "(" + x + "," + y + ")";
    }

    public void printBuilding() {
        // Find the extents of the building.
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;
        for (Map.Entry<String, Room> roomEntry : rooms.entrySet()) {
            Room room = roomEntry.getValue();
            int x = room.getX();
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            int y = room.getY();
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }

        String WALL = "#";
        String OPEN = " ";
        String DOOR_NS = "-";
//        String DOOR_NS = " ";     // for cleaner output
        String DOOR_EW = "|";
//        String DOOR_EW = " ";     // for cleaner output
        String SPACE = ".";
        String START = "X";


        // Draw the top wall first
        String output = "" + WALL;  // upper left corner is a wall piece
        for (int col = minX; col <= maxX; col++) { // columns of rooms
            output += WALL + WALL;
        }
        System.out.println(output); // top wall.

        for (int row = maxY; row >= minY; row--) {    // rows of rooms, counting DOWN from the north.
            for (int k = 0; k < 2; k++) {   // center & bottom strings of each room.
                output = "";
                for (int col = minX; col <= maxX; col++) { // columns of rooms
                    if (col == minX) { // If we're in the leftmost column of rooms
                        // Draw the left edge.
                        output += WALL;
                    }

                    // Draw the center (EW) or bottom (S) strings of this row of rooms.
                    String key = XYtoKey(col, row);
                    if (k == 0) { // center
                        if (rooms.containsKey(key)) {  // this should always be TRUE because our map is full?
                            Room room = rooms.get(key);
                            output += ((key.equals("(0,0)") ? START : OPEN)  // Identify the starting room as 'X' otherwise open.
                                    + ((room.getRoomEast() != null) ? DOOR_EW : WALL)); // e.g. .# or .|
                        } else {
                            output += SPACE + WALL;
                        }
                    } else {
                        // k == 1
                        if (rooms.containsKey(key)) {  // this should always be TRUE because our map is full?
                            Room room = rooms.get(key);
                            output += ((room.getRoomSouth() != null) ? DOOR_NS : WALL) + WALL;    // e.g. -# or ##
                        } else {
                            output += WALL + WALL;
                        }

                    }
                }   // end of all rooms in this row
                System.out.println(output);
            }
        }

    }

    public static void swap(MapWrapper al1,
                            MapWrapper al2) {
        ArrayList<String> temp = al1.c;
        al1.c = al2.c;
        al2.c = temp;
    }

    // A Wrapper over class that is used for swapping
    class MapWrapper {
        ArrayList<String> c;

        // Constructor
        MapWrapper(ArrayList<String> c) {
            this.c = c;
        }
    }
}
