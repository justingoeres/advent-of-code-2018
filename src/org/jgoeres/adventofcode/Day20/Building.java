package org.jgoeres.adventofcode.Day20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            line = br.readLine(); // read the whole map into one long String

        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        ArrayList<String> currentList = new ArrayList<>();
        ArrayList<String> nextList = new ArrayList<>();

        MapWrapper cW = new MapWrapper(currentList);
        MapWrapper nW = new MapWrapper(nextList);


        String root = null;    // Match the root
        String remainder = null;
        int routesProcessed = 0;

        currentList.add(line);
        while (true) {
            for (String map : cW.c) {
                root = "";
                remainder = "";
                Pattern p = Pattern.compile("([^(]*)(.*)"); // Match up to first parenthesis
                Matcher m = p.matcher(map);
                if (m.find()) {
                    root = m.group(1);
                    remainder = m.group(2);
                }

                BranchStringInfo branchStringInfo = new BranchStringInfo();
                boolean found = findMatchingParen(remainder, branchStringInfo);  // Find the next matching closing paren
                if (found) {
                    String branch1 = remainder.substring(branchStringInfo.openParenIndex + 1, branchStringInfo.pipeIndex);
                    String branch2 = remainder.substring(branchStringInfo.pipeIndex + 1, branchStringInfo.closeParenIndex);
                    remainder = remainder.substring(branchStringInfo.closeParenIndex + 1);

                    branch1 = root + branch1 + remainder;
                    branch2 = root + branch2 + remainder;

                    nW.c.add(branch1);
                    nW.c.add(branch2);
                } else {
//                    nW.c.add(map);
                    // When we get a map route with NO parens, that one is done and we can run it!
//                    System.out.println(map);
                    runRoute(map);
                    routesProcessed++;
                }
            }
            // After we've got the new maps, swap the pointers.
            if (nW.c.isEmpty()) { // If we have no more branched routes remaining to process
                // We're done!
                swap(cW, nW);
                break;
            } else {
                System.out.println("Rooms:\t" + rooms.size() + "\tRoutes Finalized:\t" + routesProcessed + "\tTo Process:\t" + nW.c.size());
                // continue
                swap(cW, nW);
                nW.c.clear(); // Empty nextList after we swap.
            }
        }
        System.out.println("here we are.");

//        System.out.println(root);
//        System.out.println(parenString);
//        System.out.println(remainder);
//
//

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

    private boolean findMatchingParen(String string, BranchStringInfo branchStringInfo) {

        int counter = 0;
        boolean foundOne = false;
        for (int i = 0; i < string.length(); i++) {
            Character thisChar = string.charAt(i);
            switch (thisChar) {
                case '(':
                    if (!foundOne) {
                        // if this is the first one we've seen
                        branchStringInfo.openParenIndex = i;
                        foundOne = true;
                    }
                    counter++;
                    break;
                case '|':
                    if (counter == 1) { // Find the parent that's at the root level
                        branchStringInfo.pipeIndex = i;
                    }
                    break;
                case ')':
                    counter--;
                    break;
            }
            if (foundOne && counter == 0) {
                // We found the matching paren!
                branchStringInfo.closeParenIndex = i;
                break;
            }
        }

        return foundOne;
    }

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
