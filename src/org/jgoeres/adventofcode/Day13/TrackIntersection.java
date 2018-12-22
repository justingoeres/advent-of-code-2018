package org.jgoeres.adventofcode.Day13;

public class TrackIntersection extends TrackPiece {
    private TrackPiece trackConnection3;
    private TrackPiece trackConnection4;

    public TrackIntersection(Character trackGlyph, int x, int y, TrackPiece prevTrackPiece) {
        super(trackGlyph, x, y, prevTrackPiece);
    }

    public TrackPiece getTrackConnection3() {
        return trackConnection3;
    }

    public void setTrackConnection3(TrackPiece trackConnection3) {
        this.trackConnection3 = trackConnection3;
    }

    public TrackPiece getTrackConnection4() {
        return trackConnection4;
    }

    public void setTrackConnection4(TrackPiece trackConnection4) {
        this.trackConnection4 = trackConnection4;
    }

    @Override
    public TrackPiece getNextPiece(Direction direction) {
        switch (direction) {
            case LEFT:
                return getTrackConnection1();
            case UP:
                return getTrackConnection2();
            case RIGHT:
                return getTrackConnection3();
            case DOWN:
                return getTrackConnection4();
            default:
                return null;
        }
    }

    @Override
    public boolean anyConnectionsNull() {
        return (super.anyConnectionsNull() || (trackConnection3 == null) || (trackConnection4 == null));
    }
}
