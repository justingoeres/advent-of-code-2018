package org.jgoeres.adventofcode.Day09;

public class RunDay9 {
    static String pathToInputs = "day09/input.txt";

    public static void problem9A() {
    /*
    First, the marble numbered 0 is placed in the circle. At this point, while it contains only a single marble,
    it is still a circle: the marble is both clockwise from itself and counter-clockwise from itself. This marble is
    designated the current marble.

    Then, each Elf takes a turn placing the lowest-numbered remaining marble into the circle between the marbles that
    are 1 and 2 marbles clockwise of the current marble. (When the circle is large enough, this means that there is one
    marble between the marble that was just placed and the current marble.) The marble that was just placed then becomes
    the current marble.

    However, if the marble that is about to be placed has a number which is a multiple of 23, something entirely
    different happens. First, the current player keeps the marble they would have placed, adding it to their score.
    In addition, the marble 7 marbles counter-clockwise from the current marble is removed from the circle and also
    added to the current player's score. The marble located immediately clockwise of the marble that was removed
    becomes the new current marble.

    Here are a few more examples:
        10 players; last marble is worth 1618 points: high score is 8317
        13 players; last marble is worth 7999 points: high score is 146373
        17 players; last marble is worth 1104 points: high score is 2764
        21 players; last marble is worth 6111 points: high score is 54718
        30 players; last marble is worth 5807 points: high score is 37305

    What is the winning Elf's score?



     */
        System.out.println("=== DAY 9A ===");

//        MarbleGame marbleGame = new MarbleGame(pathToInputs);
        MarbleGame marbleGame = new MarbleGame(10, 1618);

        Integer currentMarble; // start at marble #1

        for (currentMarble = 1; currentMarble <= marbleGame.getLastMarbleValue(); currentMarble++) {
            // Play all N marbles.

        }


    }

    public static void problem9B() {
    /*
        Problem Description
    */
        System.out.println("=== DAY 9B ===");


    }
}
