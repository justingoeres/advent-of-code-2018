package org.jgoeres.adventofcode.Day13;

public class Cart {
    private TrackPiece currentTrackPiece;
    private Direction direction;

    public Cart(TrackPiece currentTrackPiece, Direction direction) {
        this.currentTrackPiece = currentTrackPiece;
        this.direction = direction;
    }

    public boolean doTimerTick(){
        boolean collision = false;

        // Update our location based on the direction we're headed.
        this.currentTrackPiece = currentTrackPiece.getNextPiece(direction);

        // Update our direction based on the new piece.

        // Did we collide with anyone?
        return collision;
    }
}
