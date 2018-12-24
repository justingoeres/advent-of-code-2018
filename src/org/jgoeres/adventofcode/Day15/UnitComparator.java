package org.jgoeres.adventofcode.Day15;

import java.util.Comparator;

public class UnitComparator implements Comparator<Unit> {
    @Override
    public int compare(Unit o1, Unit o2) {
        int o1x = o1.getCurrentCell().getX();
        int o1y = o1.getCurrentCell().getY();

        int o2x = o2.getCurrentCell().getX();
        int o2y = o2.getCurrentCell().getY();

        if (o1y != o2y) {
            // if o1 & o2 are not in the same row
            return Integer.compare(o1y, o2y);
        } else {
            // They're in the same row, so
            // compare their x coords
            int result = Integer.compare(o1x,o2x);
            return result;
        }
    }
}