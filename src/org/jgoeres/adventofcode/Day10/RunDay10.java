package org.jgoeres.adventofcode.Day10;

public class RunDay10 {
    static String pathToInputs = "day10/input.txt";

    static Sky sky = new Sky(pathToInputs);
    public static void problem10A() {
    /*
    You can see these points of light floating in the distance, and record
    their position in the sky and their velocity, the relative change in
    position per second (your puzzle input). The coordinates are all given
    from your perspective; given enough time, those positions and velocities
    will move the points into a cohesive message!

    Rather than wait, you decide to fast-forward the process and calculate
    what the points will eventually spell.

    */
        System.out.println("=== DAY 10A ===");

//        Approach:
//        If we assume the message will be in ALLCAPS, then when we're finished every
//        point will be adjacent to at least one other point (Manhattan Distance == 1).
//        There may be other moments when this happens, too, so we can't just bail out
//        but we can pause and output the sky view, and continue if necessary.
        System.out.println(sky);


    }

    public static void problem10B() {
    /*
    Problem Description
    */
        System.out.println("=== DAY 10B ===");

    }

}
