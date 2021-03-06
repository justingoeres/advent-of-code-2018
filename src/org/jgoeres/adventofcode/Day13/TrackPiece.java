package org.jgoeres.adventofcode.Day13;

public class TrackPiece {
    private TrackPieceType trackPieceType;
    private int x;
    private int y;

    private TrackPiece trackConnection1;
    private TrackPiece trackConnection2;

    private Cart cart;

    public TrackPiece(Character trackGlyph, int x, int y, TrackPiece prevTrackPiece) {
        this.x = x;
        this.y = y;
        switch (trackGlyph) {
            case Renders.CHAR_CORNER_LU:
                if ((prevTrackPiece != null)
                        && (prevTrackPiece.getTrackPieceType() != TrackPieceType.VERTICAL)) { // if there was a piece to the left of this one.
                    this.trackPieceType = TrackPieceType.CORNER_LU;
                } else {
                    this.trackPieceType = TrackPieceType.CORNER_RD;
                }
                break;
            case Renders.CHAR_CORNER_RU:
                if ((prevTrackPiece != null)
                        && (prevTrackPiece.getTrackPieceType() != TrackPieceType.VERTICAL)) { // if there was a piece to the left of this one.
                    this.trackPieceType = TrackPieceType.CORNER_LD;
                } else {
                    this.trackPieceType = TrackPieceType.CORNER_RU;
                }
                break;
            case Renders.CHAR_HORIZONTAL:
            case Renders.CHAR_CART_RIGHT:   // right-moving cart
            case Renders.CHAR_CART_LEFT:   // left-moving cart
                this.trackPieceType = TrackPieceType.HORIZONTAL;
                break;
            case Renders.CHAR_VERTICAL:
            case Renders.CHAR_CART_UP:   // up-moving cart
            case Renders.CHAR_CART_DOWN:   // down-moving cart
                this.trackPieceType = TrackPieceType.VERTICAL;
                break;
            case Renders.CHAR_INTERSECTION:
                this.trackPieceType = TrackPieceType.INTERSECTION;
                break;
        }

    }

    public TrackPieceType getTrackPieceType() {
        return trackPieceType;
    }

    public void setTrackPieceType(TrackPieceType trackPieceType) {
        this.trackPieceType = trackPieceType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TrackPiece getTrackConnection1() {
        return trackConnection1;
    }

    public TrackPiece getTrackConnection2() {
        return trackConnection2;
    }

    public void setTrackConnection1(TrackPiece trackConnection1) {
        this.trackConnection1 = trackConnection1;
    }

    public void setTrackConnection2(TrackPiece trackConnection2) {
        this.trackConnection2 = trackConnection2;
    }

    public Cart getCart() {
        return cart;
    }

    public void clearCart() {
        this.cart = null;
    }

    public boolean setCart(Cart cart) {
        boolean collision = false;
        if (this.cart != null) {
            collision = true;

            // If there's already a cart on this piece.
            // Set it to crashed.
            this.cart.setCrashed(true);
            // Also set the new cart to crashed.
            cart.setCrashed(true);
        }
        this.cart = cart; // But move the new cart onto here anyway.

        return collision;
    }

    public TrackPiece getNextPiece(Direction direction) {

        switch (this.getTrackPieceType()) {
            case VERTICAL:
                // Connection 1 is up
                // Connection 2 is down
                return (direction == Direction.UP ? trackConnection1 : trackConnection2);
            case HORIZONTAL:
                // Connection 1 is left
                // Connection 2 is right
                return (direction == Direction.LEFT ? trackConnection1 : trackConnection2);
            case CORNER_LD:
                // Connection 1 is left
                // Connection 2 is down
                return (direction == Direction.LEFT ? trackConnection1 : trackConnection2);
            case CORNER_RD:
                // Connection 1 is right
                // Connection 2 is down
                return (direction == Direction.RIGHT ? trackConnection1 : trackConnection2);
            case CORNER_LU:
                // Connection 1 is left
                // Connection 2 is up
                return (direction == Direction.LEFT ? trackConnection1 : trackConnection2);
            case CORNER_RU:
                // Connection 1 is right
                // Connection 2 is up
                return (direction == Direction.RIGHT ? trackConnection1 : trackConnection2);
            default:
                return null;
        }
    }

    public boolean anyConnectionsNull() {
        // Check all connection pieces and return TRUE if any are null.
        return ((trackConnection1 == null) || trackConnection2 == null);
    }

    public boolean isHorizontalConnector() {
        return ((trackPieceType == TrackPieceType.HORIZONTAL)
                || (trackPieceType == TrackPieceType.INTERSECTION));
    }

    public boolean isVerticalConnector() {
        return ((trackPieceType == TrackPieceType.VERTICAL)
                || (trackPieceType == TrackPieceType.INTERSECTION));
    }
}
