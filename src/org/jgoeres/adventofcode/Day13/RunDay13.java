package org.jgoeres.adventofcode.Day13;

import java.util.ArrayList;

public class RunDay13 {
    //    static final String pathToInputs = "day13/input-example.txt";
    //    static final String pathToInputs = "day13/input-fromReddit.txt";
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


        final String pathToInputs = "day13/input.txt";

        TrackNetwork trackNetwork = new TrackNetwork(pathToInputs);
        int timerTick = 0;

        Cart collidedCart;
        while (true) {
            String output = "";
            collidedCart = trackNetwork.doTimerTick();

            if (collidedCart != null) { // uh oh, we hit something.
                output += timerTick + ":\tCollision at ("
                        + collidedCart.getCurrentTrackPiece().getX()
                        + "," + collidedCart.getCurrentTrackPiece().getY()
                        + ")";
                System.out.println(output);

                // Remove any crashed carts.
                ArrayList<Cart> cartsToRemove = new ArrayList<>();
                for (Cart cart : trackNetwork.carts) {
                    if (cart.isCrashed()) {
                        // Remove this cart from the trackPiece that's holding it
                        cart.getCurrentTrackPiece().clearCart();
                        cartsToRemove.add(cart);
                    }
                }
                trackNetwork.carts.removeAll(cartsToRemove); // Remove all crashed carts.
//                System.out.println("Remaining carts: " + trackNetwork.carts.size());
            }
            if (DEBUG_PRINT) {
                output = timerTick + ":";

                // Print all the cart locations
                for (Cart cart : trackNetwork.carts) {
                    output += "\t" + cart.getCartNum() + ":("
                            + cart.getCurrentTrackPiece().getX()
                            + "," + cart.getCurrentTrackPiece().getY() + ")";
                }

                System.out.println(output);
            }

            if (trackNetwork.carts.size() == 1) { // are we down to just one cart?
                // If so, we're done!

                // Do one more tick, because that's what the problem asks for.
//                trackNetwork.doTimerTick();

                // Then find our cart.
                System.out.println(timerTick + ":\tFinal cart location:\t("
                        + trackNetwork.carts.get(0).getCurrentTrackPiece().getX()
                        + "," + trackNetwork.carts.get(0).getCurrentTrackPiece().getY()
                        + ")");
                break;

                // Answer:
                // 10746:	Final cart location:	(116,25)
            }

            timerTick++;
        }
    }
}
