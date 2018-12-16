package org.jgoeres.adventofcode.Day09;

public class RunDay9 {
    static String pathToInputs = "day09/input.txt";
    static boolean PRINT_PLAYER_SCORES = false;

    static Integer winningPlayerNum = null;
    static Player winningPlayer = null;


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
        10 players; last marble is worth 1618 points: high score is 8317    OK
        13 players; last marble is worth 7999 points: high score is 146373  OK
        17 players; last marble is worth 1104 points: high score is 2764    OK
        21 players; last marble is worth 6111 points: high score is 54718
        30 players; last marble is worth 5807 points: high score is 37305

    What is the winning Elf's score?
     */
        System.out.println("=== DAY 9A ===");
// EXAMPLE GAMES
//        MarbleGame marbleGame = new MarbleGame(10, 1618);
//        MarbleGame marbleGame = new MarbleGame(13, 7999);
//        MarbleGame marbleGame = new MarbleGame(17, 1104);
//        MarbleGame marbleGame = new MarbleGame(21, 6111);
//        MarbleGame marbleGame = new MarbleGame(30, 5807);
////////////////

        MarbleGame marbleGame = new MarbleGame(pathToInputs);

        runGame(marbleGame);
//        for (Integer currentMarble = 1; currentMarble <= marbleGame.getLastMarbleValue(); currentMarble++) {
//            // Play all N marbles.
//            marbleGame.playMarble(currentMarble);
//        }
//
//        // Game is over. Figure out who won
//        Integer playerNum = 1;
//        Integer winningPlayerNum = null;
//        Player winningPlayer = null;
//
//        for (Player player : marbleGame.getPlayers()) {
//            if (PRINT_PLAYER_SCORES) {
//                System.out.println("Player " + playerNum + ":\t" + player.getCurrentScore());
//            }
//            if ((winningPlayer == null) || player.getCurrentScore() > winningPlayer.getCurrentScore()) {
//                winningPlayer = player;
//                winningPlayerNum = playerNum;
//            }
//            playerNum++;
//        }
//        System.out.println("WINNER:\t Player " + winningPlayerNum + " with " + winningPlayer.getCurrentScore() + " points.");

        // Answer:
        // WINNER:	 Player 230 with 367802 points.
    }

/*  EXAMPLE GAME TREE
[-] (0)
[1]  0 (1)
[2]  0 (2) 1
[3]  0  2  1 (3)
[4]  0 (4) 2  1  3
[5]  0  4  2 (5) 1  3
[6]  0  4  2  5  1 (6) 3
[7]  0  4  2  5  1  6  3 (7)
[8]  0 (8) 4  2  5  1  6  3  7
[9]  0  8  4 (9) 2  5  1  6  3  7
[1]  0  8  4  9  2(10) 5  1  6  3  7
[2]  0  8  4  9  2 10  5(11) 1  6  3  7
[3]  0  8  4  9  2 10  5 11  1(12) 6  3  7
[4]  0  8  4  9  2 10  5 11  1 12  6(13) 3  7
[5]  0  8  4  9  2 10  5 11  1 12  6 13  3(14) 7
[6]  0  8  4  9  2 10  5 11  1 12  6 13  3 14  7(15)
[7]  0(16) 8  4  9  2 10  5 11  1 12  6 13  3 14  7 15
[8]  0 16  8(17) 4  9  2 10  5 11  1 12  6 13  3 14  7 15
[9]  0 16  8 17  4(18) 9  2 10  5 11  1 12  6 13  3 14  7 15
[1]  0 16  8 17  4 18  9(19) 2 10  5 11  1 12  6 13  3 14  7 15
[2]  0 16  8 17  4 18  9 19  2(20)10  5 11  1 12  6 13  3 14  7 15
[3]  0 16  8 17  4 18  9 19  2 20 10(21) 5 11  1 12  6 13  3 14  7 15
[4]  0 16  8 17  4 18  9 19  2 20 10 21  5(22)11  1 12  6 13  3 14  7 15
[5]  0 16  8 17  4 18(19) 2 20 10 21  5 22 11  1 12  6 13  3 14  7 15
[6]  0 16  8 17  4 18 19  2(24)20 10 21  5 22 11  1 12  6 13  3 14  7 15
[7]  0 16  8 17  4 18 19  2 24 20(25)10 21  5 22 11  1 12  6 13  3 14  7 15
 */

    public static void problem9B() {
    /*
        Amused by the speed of your answer, the Elves are curious:

        What would the new winning Elf's score be if the number of
        the last marble were 100 times larger?
    */
        System.out.println("=== DAY 9B ===");

        MarbleGame marbleGame = new MarbleGame(pathToInputs); // Load the game again.

        marbleGame = new MarbleGame(marbleGame.getNumPlayers(),
                marbleGame.getLastMarbleValue() * 100);    // Re-set the game with a larger lastMarbleValue

        runGame(marbleGame);

    }

    private static void runGame(MarbleGame marbleGame) {
        System.out.println("Playing game with " + marbleGame.getNumPlayers() + " players; last marble is #" + marbleGame.getLastMarbleValue());

        for (Integer currentMarble = 1; currentMarble <= marbleGame.getLastMarbleValue(); currentMarble++) {
            if (currentMarble % 25000 == 0) {
                System.out.print("\rPlaying marble #" + currentMarble);
            }
            // Play all N marbles.
            marbleGame.playMarble(currentMarble);
        }
        System.out.println(); // linefeed on the status output.

        // Game is over. Figure out who won
        Integer playerNum = 1;

        for (Player player : marbleGame.getPlayers()) {
            if (PRINT_PLAYER_SCORES) {
                System.out.println("Player " + playerNum + ":\t" + player.getCurrentScore());
            }
            if ((winningPlayer == null) || player.getCurrentScore() > winningPlayer.getCurrentScore()) {
                winningPlayer = player;
                winningPlayerNum = playerNum;
            }
            playerNum++;
        }

        System.out.println("WINNER:\t Player " + winningPlayerNum + " with " + winningPlayer.getCurrentScore() + " points.");

    }
}
