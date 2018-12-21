package org.jgoeres.adventofcode.Day13;

public class TrackPiece {
    private TrackPieceType trackPieceType;
    private int x;
    private int y;

    private TrackPiece trackConnection1;
    private TrackPiece trackConnection2;

    public TrackPiece(Character trackGlyph, int x, int y, TrackPiece prevTrackPiece) {
        this.x = x;
        this.y = y;
        switch (trackGlyph) {
            case '/':
                if (prevTrackPiece != null) { // if there was a piece to the left of this one.
                    this.trackPieceType = TrackPieceType.CORNER_LU;
                } else {
                    this.trackPieceType = TrackPieceType.CORNER_RD;
                }
                break;
            case '\\':
                if (prevTrackPiece != null) { // if there was a piece to the left of this one.
                    this.trackPieceType = TrackPieceType.CORNER_LD;
                } else {
                    this.trackPieceType = TrackPieceType.CORNER_RU;
                }
                break;
            case '-':
            case '>':   // right-moving cart
            case '<':   // left-moving cart
                this.trackPieceType = TrackPieceType.HORIZONTAL;
                break;
            case '|':
            case '^':   // up-moving cart
            case 'v':   // down-moving cart
                this.trackPieceType = TrackPieceType.VERTICAL;
                break;
            case '+':
                this.trackPieceType = TrackPieceType.INTERSECTION;
                break;
        }

    }

    public TrackPieceType getTrackPieceType() {
        return trackPieceType;
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
}
