package org.jgoeres.adventofcode.Day13;

import javax.sound.midi.Track;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class TrackNetwork {
    HashMap<String, TrackPiece> trackPieces = new HashMap<>();
    ArrayList<Cart> carts = new ArrayList<>();

    public TrackNetwork(String pathToFile) {
        loadNetwork(pathToFile);
    }

    private void loadNetwork(String pathToFile) {
        /*
        File looks like:
        /->-\
        |   |  /----\
        | /-+--+-\  |
        | | |  | v  |
        \-+-/  \-+--/
          \------/

        XY coordinates start in upper left at (0,0).
         */


        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            Character currentChar;
            int y = 0; // start at line 0
            TrackPiece newTrackPiece = null;
            TrackPiece prevTrackPiece = null;
            while ((line = br.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    currentChar = line.charAt(x); // get the next glyph

                    if (!currentChar.equals(' ')) { // skip spaces
                        newTrackPiece = new TrackPiece(currentChar, x, y, prevTrackPiece);
                    }
                    if (newTrackPiece != null) { // if this character yielded a track piece
                        String trackCoords = x + "," + y;

                        trackPieces.put(trackCoords, newTrackPiece);
                    }
                }
                y++; // increment the line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        // After we've loaded everything, find all the connections and wire them up.
    }

    private void findAllConnections(){
        // Look up any existing
    }
}

