package org.jgoeres.adventofcode.Day15;

import java.util.Comparator;

public class MapCellComparator implements Comparator<MapCell> {
    @Override
    public int compare(MapCell o1, MapCell o2) {
        int o1x = o1.getX();
        int o1y = o1.getY();

        int o2x = o2.getX();
        int o2y = o2.getY();

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