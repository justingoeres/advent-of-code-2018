package org.jgoeres.adventofcode.Day09;

import java.io.File;
import java.util.Scanner;

public class MarbleGame {
    private Integer numPlayers;
    private Integer lastMarbleValue;
    private Integer currentMarbleIndex;

    private CircularArrayList<Player> players = new CircularArrayList<>();

    private CircularArrayList<Integer> marbleCircle = new CircularArrayList<>();

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

        setCurrentMarbleIndex(0);
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
        // Move one marble clockwise
        Integer originalSize = marbleCircle.size();

        Player currentPlayer = players.get(newMarbleValue);

        if ((newMarbleValue % 23) != 0) {
            // If the new marble is NOT a multiple of 23 (the usual case)
            if (originalSize == 1) {
                marbleCircle.add(newMarbleValue);
                currentMarbleIndex = 1;
            } else {
                currentMarbleIndex += 2;
                marbleCircle.add(currentMarbleIndex, newMarbleValue);
                currentMarbleIndex %= originalSize; // wrap the index.
            }
        } else {
            // If the new marble IS a multiple of 23
            currentPlayer.addToCurrentScore(newMarbleValue); // Add the current marble to the player's score.
            Integer newMarbleIndex = currentMarbleIndex - 7;
            if (newMarbleIndex < 0){
                newMarbleIndex += originalSize;
            }
            Integer removedMarble = marbleCircle.remove((newMarbleIndex) % originalSize); // Remove the marble 7 steps counter-clockwise.
            currentPlayer.addToCurrentScore(removedMarble); // Add the removed marble to the player's score.

            currentMarbleIndex = newMarbleIndex;
//            currentMarbleIndex -= 7; // Move the index to the marble one step clockwise from the removed marble.
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

    public Integer getCurrentMarbleIndex() {
        return currentMarbleIndex;
    }

    public void setCurrentMarbleIndex(Integer currentMarbleIndex) {
        this.currentMarbleIndex = currentMarbleIndex;
    }

    public CircularArrayList<Player> getPlayers() {
        return players;
    }
}
