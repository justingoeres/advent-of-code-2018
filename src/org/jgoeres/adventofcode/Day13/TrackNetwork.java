package org.jgoeres.adventofcode.Day13;

import javax.sound.midi.Track;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
                        if (currentChar.equals('+')) { // if this is an intersection
                            newTrackPiece = new TrackIntersection(currentChar,x,y,prevTrackPiece);
                        } else { // just a regular old piece
                            newTrackPiece = new TrackPiece(currentChar, x, y, prevTrackPiece);
                        }
                        String trackCoords = keyFromXY(x, y);

                        trackPieces.put(trackCoords, newTrackPiece);
                        prevTrackPiece = newTrackPiece; // keep track of this piece so we can tell what kind of corner the next piece might be.
                    }
                }
                y++; // increment the line
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

        // After we've loaded everything, find all the connections and wire them up.
        findAllConnections();
    }

    private void findAllConnections() {
        // Every TrackPiece is connected to others. We can figure out what the connections are by
        // 1) The type of TrackPiece, and
        // 2) Its X & Y coordinates relative to other pieces.

        // Check every trackPiece
        for (Map.Entry<String, TrackPiece> trackPieceEntry : trackPieces.entrySet()) {
            // Find its connections based on type.
            TrackPiece trackPiece = trackPieceEntry.getValue();

            TrackPiece conn1Piece;
            TrackPiece conn2Piece;
            TrackPiece conn3Piece;
            TrackPiece conn4Piece;
            Direction direction1 = null;
            Direction direction2 = null;
            Direction direction3 = null;
            Direction direction4 = null;
            switch (trackPiece.getTrackPieceType()) {
                case VERTICAL:
                    // Connection 1 is up
                    direction1 = Direction.UP;
                    // Connection 2 is down
                    direction2 = Direction.DOWN;
                    break;
                case HORIZONTAL:
                    // Connection 1 is left
                    direction1 = Direction.LEFT;
                    // Connection 2 is right
                    direction2 = Direction.RIGHT;
                    break;
                case CORNER_LD:
                    // Connection 1 is left
                    direction1 = Direction.LEFT;
                    // Connection 2 is down
                    direction2 = Direction.DOWN;
                    break;
                case CORNER_RD:
                    // Connection 1 is right
                    direction1 = Direction.RIGHT;
                    // Connection 2 is right
                    direction2 = Direction.DOWN;
                    break;
                case CORNER_LU:
                    // Connection 1 is left
                    direction1 = Direction.LEFT;
                    // Connection 2 is up
                    direction2 = Direction.UP;
                    break;
                case CORNER_RU:
                    // Connection 1 is right
                    direction1 = Direction.RIGHT;
                    // Connection 2 is up
                    direction2 = Direction.UP;
                    break;
                case INTERSECTION:
                    // Connection 1 is left
                    direction1 = Direction.LEFT;
                    // Connection 2 is up
                    direction2 = Direction.UP;
                    // Connection 1 is right
                    direction3 = Direction.RIGHT;
                    // Connection 2 is down
                    direction4 = Direction.DOWN;
                    break;

            }

            // Make the connections in the specified directions
            conn1Piece = findRelativeTrackPiece(trackPiece, direction1);
            trackPiece.setTrackConnection1(conn1Piece);

            conn2Piece = findRelativeTrackPiece(trackPiece, direction2);
            trackPiece.setTrackConnection2(conn2Piece);

            if ((direction3 != null) && (direction4 != null)) {
                // trackPiece must be an Intersection, or we wouldn't be here.

                // Make the connections in the remaining directions
                conn3Piece = findRelativeTrackPiece(trackPiece, direction3);
                ((TrackIntersection) trackPiece).setTrackConnection3(conn3Piece);

                conn4Piece = findRelativeTrackPiece(trackPiece, direction4);
                ((TrackIntersection) trackPiece).setTrackConnection4(conn4Piece);
            }
        }
    }

    private String keyFromXY(int x, int y) {
        String trackCoords = x + "," + y;
        return trackCoords;
    }

    private String relativeKeyFromXY(int x0, int y0, Direction direction) {
        int x = 0;
        int y = 0;

        switch (direction) {
            case UP:
                x = x0;
                y = y0 - 1;
                break;
            case DOWN:
                x = x0;
                y = y0 + 1;
                break;
            case LEFT:
                x = x0 - 1;
                y = y0;
                break;
            case RIGHT:
                x = x0 + 1;
                y = y0;
                break;
        }
        String trackCoords = x + "," + y;
        return trackCoords;
    }

    private TrackPiece findRelativeTrackPiece(TrackPiece trackPiece, Direction direction) {
        String coords = relativeKeyFromXY(trackPiece.getX(), trackPiece.getY(), direction);
        TrackPiece relativeTrackPiece = trackPieces.get(coords);

        return relativeTrackPiece;
    }
}

