package org.jgoeres.adventofcode.Day09;

import java.io.File;
import java.util.Scanner;

public class MarbleGame {
    private Integer numPlayers;
    private Integer lastMarbleValue;

    private CircularArrayList<Player> players = new CircularArrayList<>();

    private CircularDoubleLinkedList marbleCircle = null;

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
        marbleCircle = new CircularDoubleLinkedList(0);
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
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public void playMarble(Integer newMarbleValue) {

        Player currentPlayer = players.get(newMarbleValue);

        if ((newMarbleValue % 23) != 0) {
            // Move one marble clockwise
            marbleCircle.next();

            // If the new marble is NOT a multiple of 23 (the usual case)
            marbleCircle.insertToRight(newMarbleValue);
        } else {
            // If the new marble IS a multiple of 23
            // Add this marble to the player's score instead of placing it.
            currentPlayer.addToCurrentScore(newMarbleValue); // Add the current marble to the player's score.

            // Jump counterclockwise by 7.
            marbleCircle.prevByN(7);
            // Remove this marble and add it to the player's score.
            currentPlayer.addToCurrentScore(marbleCircle.removeCurrentNode());
        }
    }

    public Integer getNumPlayers() {
        return numPlayers;
    }

    public Integer getLastMarbleValue() {
        return lastMarbleValue;
    }

    public CircularDoubleLinkedList getMarbleCircle() {
        return marbleCircle;
    }

    public CircularArrayList<Player> getPlayers() {
        return players;
    }
}
