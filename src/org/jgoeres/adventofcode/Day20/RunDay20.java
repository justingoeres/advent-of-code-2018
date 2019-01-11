package org.jgoeres.adventofcode.Day20;

public class RunDay20 {
    static final String DEFAULT_PATH_TO_INPUTS = "day20/input.txt";

    static final boolean DEBUG_PART_A_PRINT_BUILDING = false;

    static Building building = new Building(DEFAULT_PATH_TO_INPUTS);

    public static void problem20A() {
        problem20A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem20A(String pathToInputs) {
        /*
        What is the largest number of doors you would be required 
        to pass through to reach a room? That is, find the room 
        for which the shortest path from your starting location 
        to that room would require passing through the most doors; 
        what is the fewest doors you can pass through to reach it?
        */
        System.out.println("=== DAY 20A ===");
        if (DEBUG_PART_A_PRINT_BUILDING) {
            building.printBuilding();
        }

        // Find the most distant room.
        Room furthestRoom = building.findMostDistant();
        int answer = furthestRoom.getDistance();
        System.out.println("Most distant room is at " + furthestRoom.toString() + ":\t" + answer + " steps");

        return answer;
        // Answer:
        // Most distant room is at (-39,22):	3971 steps
    }

    public static void problem20B() {
        problem20B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem20B(String pathToInputs) {
        /*
        How many rooms have a shortest path from your current location
        that pass through at least 1000 doors?
        */
        System.out.println("=== DAY 20B ===");
        final int THRESHOLD = 1000;   // per problem description
        int count = building.countDistantRooms(THRESHOLD);
        System.out.println(count + " rooms are at least " + THRESHOLD + " steps from the origin.");

        return count;
        // Answer:
        // 8578 rooms are at least 1000 steps from the origin.

    }
}
