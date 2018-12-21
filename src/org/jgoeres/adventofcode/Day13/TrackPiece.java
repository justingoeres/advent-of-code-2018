package org.jgoeres.adventofcode.Day13;

public class TrackPiece {
    private TrackPieceType trackPieceType;
    private int x;
    private int y;

    private TrackPiece trackConnection1;
    private TrackPiece getTrackConnection2;

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
}
