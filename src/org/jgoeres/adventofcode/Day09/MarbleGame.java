package org.jgoeres.adventofcode.Day09;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class MarbleGame {
    Integer numPlayers;
    Integer lastMarbleValue;

    CircularArrayList<Player> players = new CircularArrayList<>();

    CircularArrayList<Integer> marbleCircle = new CircularArrayList<>();

    public MarbleGame(String pathToFile) {
        loadMarbleGame(pathToFile);
        initMarbleCircle();
        initPlayers();
    }

    public MarbleGame(Integer numPlayers, Integer lastMarbleValue) {
        this.numPlayers = numPlayers;
        this.lastMarbleValue = lastMarbleValue;
        initMarbleCircle();
        initPlayers();
    }

    private void initMarbleCircle() {
        marbleCircle.clear();

        marbleCircle.add(0); // Initialize the circle with a single marble number "0"
    }

    private void initPlayers() {
        players.clear(); // Empty the current players array if there is one.

        for (int i = 0; i < numPlayers; i++) { // Create N new players.
            players.add(new Player());
        }
    }

    private void loadMarbleGame(String pathToFile) {
        /*
        File looks like:
            493 players; last marble is worth 71863 points
         */
        try (Scanner sc = new Scanner(new File(pathToFile))) {
            numPlayers = sc.nextInt();
            sc.skip("[a-zA-Z ;]+");
            lastMarbleValue = sc.nextInt();
            System.out.println(numPlayers + "\t" + lastMarbleValue);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public Integer getLastMarbleValue() {
        return lastMarbleValue;
    }

    public CircularArrayList<Integer> getMarbleCircle() {
        return marbleCircle;
    }
}
