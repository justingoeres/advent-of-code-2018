package org.jgoeres.adventofcode.Day13;

public class RunDay13 {
    //    static final String pathToInputs = "day13/input-example.txt";
    static final String pathToInputs = "day13/input.txt";
    static final boolean DEBUG_PRINT = false;

    static TrackNetwork trackNetwork = new TrackNetwork(pathToInputs);

    public static void problem13A() {
    /*
    After following their respective paths for a while, the carts eventually crash.
    To help prevent crashes, you'd like to know the location of the first crash.
    Locations are given in X,Y coordinates, where the furthest left column
    is X=0 and the furthest top row is Y=0:
    */
        System.out.println("=== DAY 13A ===");

        int timerTick = 0;

        Cart collidedCart;
        while (true) {
            String output = timerTick + ":\t";
            collidedCart = trackNetwork.doTimerTick();

            if (collidedCart != null) { // uh oh, we hit something.
                output += "Collision at ("
                        + collidedCart.getCurrentTrackPiece().getX()
                        + "," + collidedCart.getCurrentTrackPiece().getY()
                        + ")";
                System.out.println(output);
                break;
            } else if (DEBUG_PRINT) {
                System.out.println(output);
            }
            timerTick++;
        }
        // Answer:
        // 183:	Collision at (116,10)
    }

    public static void problem13B() {
    /*
    What is the location of the last cart at the end of the first tick where it is the only cart left?
    */
        System.out.println("=== DAY 13B ===");
    }
}
