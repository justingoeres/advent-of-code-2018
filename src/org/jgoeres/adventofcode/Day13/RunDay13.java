package org.jgoeres.adventofcode.Day13;

public class RunDay13 {
    //    static final String pathToInputs = "day13/input-example.txt";
    static final String pathToInputs = "day13/input.txt";
    static final boolean DEBUG_PRINT = false;


    public static void problem13A() {
    /*
    After following their respective paths for a while, the carts eventually crash.
    To help prevent crashes, you'd like to know the location of the first crash.
    Locations are given in X,Y coordinates, where the furthest left column
    is X=0 and the furthest top row is Y=0:
    */
        System.out.println("=== DAY 13A ===");

        TrackNetwork trackNetwork = new TrackNetwork(pathToInputs);
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

        TrackNetwork trackNetwork = new TrackNetwork(pathToInputs);
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
                // Remove the collided carts.
                // Figure out which carts are both on the same track piece.
                for (int i = 0; i < trackNetwork.carts.size(); i++) {
                    // check each cart
                    Cart cart1 = trackNetwork.carts.get(i);
                    for (int j = (i + 1); j < trackNetwork.carts.size(); j++) {
                        // ...against all the carts we haven't checked yet.
                        Cart cart2 = trackNetwork.carts.get(j);

                        if (cart1.getCurrentTrackPiece() == cart2.getCurrentTrackPiece()) {
                            // Are these carts both on the same track piece?
                            // If so, they've collided. Remove them.
                            trackNetwork.carts.get(i).getCurrentTrackPiece().setCart(null); // Remove these carts from the track piece they've collided on.
                            trackNetwork.carts.get(j).getCurrentTrackPiece().setCart(null);
                            trackNetwork.carts.remove(cart1);
                            trackNetwork.carts.remove(cart2);
                            if (DEBUG_PRINT) {
                                System.out.println("Removed carts:\t" + cart1 + ",\t" + cart2);
                            }
                        }
                    }
                }
            } else if (DEBUG_PRINT) {
                System.out.println(output);
            }

            timerTick++;

            if (trackNetwork.carts.size() == 1) { // are we down to just one cart?
                // If so, we're done!

                // Do one more tick, because that's what the problem asks for.
                trackNetwork.doTimerTick();

                // Then find our cart.
                System.out.println(timerTick + ":\tFinal cart location:\t("
                        + trackNetwork.carts.get(0).getCurrentTrackPiece().getX()
                        + "," + trackNetwork.carts.get(0).getCurrentTrackPiece().getY()
                        + ")");
                break;
            }
        }
    }
}
