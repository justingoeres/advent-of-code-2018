package org.jgoeres.adventofcode.Day13;

public class Cart {
    private TrackPiece currentTrackPiece;
    private Direction direction;

    public Cart(TrackPiece currentTrackPiece, Direction direction) {
        this.currentTrackPiece = currentTrackPiece;
        this.direction = direction;
    }
}
