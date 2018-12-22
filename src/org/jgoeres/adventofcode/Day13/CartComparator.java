package org.jgoeres.adventofcode.Day13;

import java.util.Comparator;

public class CartComparator implements Comparator<Cart> {
    @Override
    public int compare(Cart o1, Cart o2) {
        int o1x = o1.getCurrentTrackPiece().getX();
        int o1y = o1.getCurrentTrackPiece().getY();

        int o2x = o2.getCurrentTrackPiece().getX();
        int o2y = o2.getCurrentTrackPiece().getY();

        if (o1y != o2y) {
            // if o1 & o2 are not in the same row
            return Integer.compare(o1y, o2y);
        } else {
            // They're in the same row, so
            // compare their x coords
            return Integer.compare(o1x, o2x);
        }
    }
}