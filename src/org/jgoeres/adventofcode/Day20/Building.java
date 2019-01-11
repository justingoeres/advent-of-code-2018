package org.jgoeres.adventofcode.Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Building {
    private HashMap<String, Room> rooms = new HashMap<>();
    private Room startingRoom;
    private HashMap<Integer, Jump> jumps = new HashMap<>();
    // A map for all the spiders everywhere. Key is the string index, and the HashSet is all the spiders at that index.
    private HashMap<Integer, HashSet<Spider>> spiderMap = new HashMap<>();

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
        Room startingRoom = new Room(0, 0);
        Spider firstSpider = new Spider(startingRoom, 0);

        addSpiderAtSpiderIndex(firstSpider);

        for (int i = 0; i < line.length(); i++) {
            Character thisChar = line.charAt(i);
            HashSet<Spider> spidersHere = spiderMap.get(i);

            System.out.println(i + ":\t" + thisChar + "\t# spiders here:\t" + spidersHere.size());

            int newX = 0;
            int newY = 0;

            Room newRoom;

            for (Spider spider : spidersHere) {
                // Check every spider here
                Room currentRoom = spider.room;

                // Create & connect the current room to the next room.
                newRoom = doRoomConnection(currentRoom, thisChar);

                // Process the branches
                switch (thisChar) {
//                    // Beginning & End
//                    case '^':   // starting room
//                        // Don't need to do anything, the first spider is born at (0,0).
//                        break;
//                    case '$':   // End of the route.
//                        return; // We're done. Just return.
//
//                    // Moves
//                    case 'N':   // connect to the north.
//                        newX = currentRoom.getX();
//                        newY = currentRoom.getY() + 1; // y is positive to the north.
//                        break;
//                    case 'E':   // connect to the east.
//                        newX = currentRoom.getX() + 1; // x is positive to the east.
//                        newY = currentRoom.getY();
//                        break;
//                    case 'S':   // connect to the south.
//                        newX = currentRoom.getX();
//                        newY = currentRoom.getY() - 1; // y is negative to the south.
//                        break;
//                    case 'W':   // connect to the west.
//                        newX = currentRoom.getX() - 1; // x is negative to the west.
//                        newY = currentRoom.getY();
//                        break;

                    // Branches
                    case '(':   // New jump
                        // Look up this jump.
                        Jump jump = jumps.get(i);   // The jumps are keyed by the string position of the '('

                        // Create a new spider at this location.
                        // Fast-forward it to ONE PAST the pipe.
                        Spider spider2 = new Spider(currentRoom, jump.pipeIndex + 1);
                        // Duplicate spider's jump stack onto the new one.
                        spider2.jumpStack = (Stack<Jump>) spider.jumpStack.clone();
                        addSpiderAtSpiderIndex(spider2); // Add the spider to the set for the index it's now at.

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

//                // Look up the new room, or create it.
//                Room newRoom = new Room(newX, newY);
//                if (!rooms.containsKey(XYtoKey(newX, newY))) {
//                    // if this is a room we haven't seen before.
//                    rooms.put(newRoom.toString(), newRoom); // add it to our map.
//                } else {
//                    // We've seen this room before!
//                    newRoom = rooms.get(newRoom.toString());
//                }
//
//                // Connect up to the new room.
//                switch (thisChar) {
//                    case 'N':   // connect to the north.
//                        currentRoom.connectToNorth(newRoom);
//                        break;
//
//                    case 'E':   // connect to the east.
//                        currentRoom.connectToEast(newRoom);
//                        break;
//
//                    case 'S':   // connect to the south.
//                        currentRoom.connectToSouth(newRoom);
//                        break;
//
//                    case 'W':   // connect to the west.
//                        currentRoom.connectToWest(newRoom);
//                        break;
//                    default:
//                        // Skip unhandled characters (like ^ and $)
//                        //System.out.println("unhandled character: " + nextDirection);
//                }

//                spider.room = newRoom;  // Move the spider into the new room we just made.
//                removeSpiderAtSpiderIndex(spider); // Move the spider out of this index's set
//                spider.stepForward();    // Move the spider to the next character.
//                addSpiderAtSpiderIndex(spider); // Add the spider to the set for the index it's now at.
            }

        }

//        ArrayList<String> currentList = new ArrayList<>();
//        ArrayList<String> nextList = new ArrayList<>();
//
//        MapWrapper cW = new MapWrapper(currentList);
//        MapWrapper nW = new MapWrapper(nextList);
//
//
//        String root = null;    // Match the root
//        String remainder = null;
//        int routesProcessed = 0;
//
//        currentList.add(line);
//        while (true) {
//            for (String map : cW.c) {
//                root = "";
//                remainder = "";
//                Pattern p = Pattern.compile("([^(]*)(.*)"); // Match up to first parenthesis
//                Matcher m = p.matcher(map);
//                if (m.find()) {
//                    root = m.group(1);
//                    remainder = m.group(2);
//                }
//
//                BranchStringInfo branchStringInfo = new BranchStringInfo();
//                // boolean found = findMatchingParen(remainder, branchStringInfo);  // Find the next matching closing paren
//                boolean found = false;
//                if (found) {
//                    String branch1 = remainder.substring(branchStringInfo.openParenIndex + 1, branchStringInfo.pipeIndex);
//                    String branch2 = remainder.substring(branchStringInfo.pipeIndex + 1, branchStringInfo.closeParenIndex);
//                    remainder = remainder.substring(branchStringInfo.closeParenIndex + 1);
//
//                    branch1 = root + branch1 + remainder;
//                    branch2 = root + branch2 + remainder;
//
//                    nW.c.add(branch1);
//                    nW.c.add(branch2);
//                } else {
////                    nW.c.add(map);
//                    // When we get a map route with NO parens, that one is done and we can run it!
////                    System.out.println(map);
//                    runRoute(map);
//                    routesProcessed++;
//                }
//            }
//            // After we've got the new maps, swap the pointers.
//            if (nW.c.isEmpty()) { // If we have no more branched routes remaining to process
//                // We're done!
//                swap(cW, nW);
//                break;
//            } else {
//                System.out.println("Rooms:\t" + rooms.size() + "\tRoutes Finalized:\t" + routesProcessed + "\tTo Process:\t" + nW.c.size());
//                // continue
//                swap(cW, nW);
//                nW.c.clear(); // Empty nextList after we swap.
//            }
//        }
//        System.out.println("here we are.");

//        System.out.println(root);
//        System.out.println(parenString);
//        System.out.println(remainder);
//
//

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

        // Look up the new room, or create it.
        Room newRoom = new Room(newX, newY);
        if (!rooms.containsKey(XYtoKey(newX, newY))) {
            // if this is a room we haven't seen before.
            rooms.put(newRoom.toString(), newRoom); // add it to our map.
        } else {
            // We've seen this room before!
            newRoom = rooms.get(newRoom.toString());
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
                    jumps.get(depth).pipeIndex = i;   // Set the pipeIndex of this jump to the current string position.
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

    private void runRoute(String route) {
        try (Scanner sc = new Scanner(route)) {
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

                    case '$':   // End of the route.
                        return; // We're done. Just return.

                    default:
                        System.out.println("unhandled character: " + nextDirection);
                }

                // Look up the new room, or create it.
                newRoom = new Room(newX, newY);
                if (!rooms.containsKey(XYtoKey(newX, newY))) {
                    // if this is a room we haven't seen before.
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
                        // Skip unhandled characters (like ^ and $)
                        //System.out.println("unhandled character: " + nextDirection);
                }

                currentRoom = newRoom;  // Move into the new room we just made.

            }

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

//    private boolean findMatchingParen(String string, BranchStringInfo branchStringInfo) {
//
//        int counter = 0;
//        boolean foundOne = false;
//        for (int i = 0; i < string.length(); i++) {
//            Character thisChar = string.charAt(i);
//            switch (thisChar) {
//                case '(':
//                    if (!foundOne) {
//                        // if this is the first one we've seen
//                        branchStringInfo.openParenIndex = i;
//                        foundOne = true;
//                    }
//                    counter++;
//                    break;
//                case '|':
//                    if (counter == 1) { // Find the parent that's at the root level
//                        branchStringInfo.pipeIndex = i;
//                    }
//                    break;
//                case ')':
//                    counter--;
//                    break;
//            }
//            if (foundOne && counter == 0) {
//                // We found the matching paren!
//                branchStringInfo.closeParenIndex = i;
//                break;
//            }
//        }
//
//        return foundOne;
//    }

    private ArrayList<String> expandMaps(String root, String paren, String remainder) {
        ArrayList<String> output = new ArrayList<>();

        // Search backward through paren until we find the pipe.
        int pipeIndex = paren.lastIndexOf("|");
        String[] branches = {
                paren.substring(1, pipeIndex),
                paren.substring(pipeIndex + 1, paren.length() - 1)    // and this is the rest.
        };

        for (String branch : branches) {
            output.add(root + branch + remainder);
        }
        return output;
    }

    private String XYtoKey(int x, int y) {
        return "(" + x + "," + y + ")";
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
