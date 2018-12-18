package org.jgoeres.adventofcode.Day10;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class RunDay10 {
    static String pathToInputs = "day10/input.txt";
    static final Integer STAR_DISTANCE_LIMIT = 0; // WHY DOES THE CODE WORK WHEN THIS IS ZERO BUT NOT 1?!?

    static Sky sky = new Sky(pathToInputs);

    public static void problem10Aand10B() {
    /*
    You can see these points of light floating in the distance, and record
    their position in the sky and their velocity, the relative change in
    position per second (your puzzle input). The coordinates are all given
    from your perspective; given enough time, those positions and velocities
    will move the points into a cohesive message!

    Rather than wait, you decide to fast-forward the process and calculate
    what the points will eventually spell.
n
    */
        System.out.println("=== DAY 10A ===");

//        Approach:
//        If we assume the message will be in ALLCAPS, then when we're finished every
//        point will be adjacent to at least one other point (Manhattan Distance == 1).
//        There may be other moments when this happens, too, so we can't just bail out
//        but we can pause and output the sky view, and continue if necessary.

        Extents extents = null;
        boolean allStarsAreClose = false;
        Integer currentTime = 0;
        while (!allStarsAreClose) {
//            System.out.println("\rTime:\t" + currentTime);

            // Find the positions of all the stars at currentTime
            Sky skyNow = SkyService.skyAtTimeT(sky, currentTime);

            // Star by assuming all the stars ARE close at this time step
            allStarsAreClose = true;

            // See if all the stars are close enough together to say it's time to stop.
            for (Star star : skyNow.getStars()) {
                allStarsAreClose &= (SkyService.starIsCloseToAnother(star, skyNow, STAR_DISTANCE_LIMIT));

                // If even one star is "far" from all neighbors, stop looking in this time step.
                if (!allStarsAreClose) {
                    currentTime++; // increment the clock
                    break;
                }
                System.out.println("Time:\t" + currentTime);
//                extents = SkyService.findExtents(skyNow);
//                System.out.println("Width: " + extents.width() + "\tHeight: " + extents.height());
                SkyService.printSky(skyNow);

                // Ask the user if we're done.
                Scanner scanner = new Scanner(System.in);
                try {
                    System.out.print("Continue? (y/n) ");
                    String input = scanner.nextLine();
                    if (input.toUpperCase().startsWith("N")) {
                        return; // We're done with the problem!
                    }
                } catch (IllegalStateException | NoSuchElementException e) {
                    // System.in has been closed
                    System.out.println("System.in was closed; exiting");
                }

            }
        }
        // Answer:
        /*
            Time:	10813    <<< Part B Answer
            Width: 61	Height: 9
            ######..#####....####...#....#..#.........##.......###..#.....
            #.......#....#..#....#..#....#..#........#..#.......#...#.....
            #.......#....#..#........#..#...#.......#....#......#...#.....
            #.......#....#..#........#..#...#.......#....#......#...#.....
            #####...#####...#.........##....#.......#....#......#...#.....
            #.......#..#....#.........##....#.......######......#...#.....
            #.......#...#...#........#..#...#.......#....#......#...#.....
            #.......#...#...#........#..#...#.......#....#..#...#...#.....
            #.......#....#..#....#..#....#..#.......#....#..#...#...#.....
            ######..#....#...####...#....#..######..#....#...###....######
         */
    }

}
