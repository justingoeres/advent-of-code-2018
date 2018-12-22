package org.jgoeres.adventofcode.Day13;

public class Cart {
    private TrackPiece currentTrackPiece;
    private Direction direction;

    private enum IntersectionBehavior {LEFT, STRAIGHT, RIGHT}

    private IntersectionBehavior intersectionBehavior;

    public Cart(TrackPiece currentTrackPiece, Direction direction) {
        this.currentTrackPiece = currentTrackPiece;
        this.direction = direction;

        this.intersectionBehavior = IntersectionBehavior.LEFT; // turn left at first intersection.
    }

    public boolean doTimerTick() {
        boolean collision = false;

        // Update our location based on the direction we're headed.
        TrackPiece nextTrackPiece = currentTrackPiece.getNextPiece(direction);
        TrackPieceType nextTrackPieceType = nextTrackPiece.getTrackPieceType();

        // Move this cart off of the current TrackPiece.
        currentTrackPiece.setCart(null);
        // Move this cart onto the nextTrackPiece
        this.currentTrackPiece = nextTrackPiece;
        collision = currentTrackPiece.setCart(this); // check whether we just smashed into anything.

        // Update our direction based on the new piece type, and our current direction.
        if (nextTrackPieceType == TrackPieceType.CORNER_LD) {
            //entering a left & down corner
            if (direction == Direction.RIGHT) {
                // from the left
                turnRight();
            } else {
                // from the bottom
                turnLeft();
            }
        } else if (nextTrackPieceType == TrackPieceType.CORNER_LU) {
            //entering a left & up corner
            if (direction == Direction.RIGHT) {
                // from the left
                turnLeft();
            } else {
                // from the top
                turnRight();
            }
        } else if (nextTrackPieceType == TrackPieceType.CORNER_RD) {
            // entering a right & down corner
            if (direction == Direction.LEFT) {
                // from the right
                turnLeft();
            } else {
                // from the bottom
                turnRight();
            }
        } else if (nextTrackPieceType == TrackPieceType.CORNER_RU) {
            // entering a right & up corner
            if (direction == Direction.LEFT) {
                // from the right
                turnRight();
            } else {
                // from the top
                turnLeft();
            }
        } else if (nextTrackPieceType == TrackPieceType.INTERSECTION) {
            // entering an intersection.
            // Turn or go straight based on what we've done before
            switch (intersectionBehavior) {
                case LEFT:
                    turnLeft();
                    break;
                case STRAIGHT:
                    // do nothing; keep going
                    break;
                case RIGHT:
                    turnRight();
                    break;
            }
            // update the intersection behavior for next time.
            incrementIntersectionBehavior();
        }
        return collision;
    }

    private void turnRight() {
        int newOrdinal = (this.direction.ordinal() + 1) % (this.direction.values().length);
        this.direction = this.direction.values()[newOrdinal];
    }

    private void turnLeft() {
        int newOrdinal = (this.direction.ordinal() - 1) % (this.direction.values().length);
        if (newOrdinal < 0) {
            newOrdinal += this.direction.values().length; // wrap the negative end of the enum.
        }
        this.direction = this.direction.values()[newOrdinal];
    }

    private void incrementIntersectionBehavior() {
        int newOrdinal = (this.intersectionBehavior.ordinal() + 1) % (this.intersectionBehavior.values().length);
        this.intersectionBehavior = this.intersectionBehavior.values()[newOrdinal];
    }

    public TrackPiece getCurrentTrackPiece() {
        return currentTrackPiece;
    }
}

