package org.jgoeres.adventofcode.Day13;

public class RunDay13 {
//    static final String pathToInputs = "day13/input-example.txt";
    static final String pathToInputs = "day13/input.txt";

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

        while(true){
            trackNetwork.doTimerTick();
            System.out.println(timerTick);
            timerTick++;
        }

    }

    public static void problem13B() {
    /*
    After 50 BILLION (50000000000) generations, what is the sum of the numbers of all pots which contain a plant?
    */
        System.out.println("=== DAY 13B ===");
    }
}
