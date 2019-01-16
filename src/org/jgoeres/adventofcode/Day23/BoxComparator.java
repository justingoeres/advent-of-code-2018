package org.jgoeres.adventofcode.Day23;

import java.util.Comparator;

public class BoxComparator implements Comparator<Box> {

    private Box ORIGIN = new Box(0, 0, 0, 0);

    @Override
    public int compare(Box o1, Box o2) {
        int o1x = o1.x;
        int o1y = o1.y;
        int o1z = o1.z;
        int o1r = o1.distanceTo(ORIGIN);
        int o1count = o1.reachableCountUpperBound;

        int o2x = o2.x;
        int o2y = o2.y;
        int o2z = o2.z;
        int o2r = o2.distanceTo(ORIGIN);
        int o2count = o2.reachableCountUpperBound;

        // First, compare how many bots each box is in range of. "More" is "earlier in the set".
        if (o1count != o2count) {
            return Integer.compare(o2count, o1count);  // Compare in reverse order so that "greater" sorts "earlier"
        }

        // Next, compare each bot's distance to the origin. Closer is "less than" further.
        if (o1r != o2r) {
            return Integer.compare(o1r, o2r);
        }

        // Next, compare each bot's radius. Smaller is "less than" larger.
        if (o1.radius != o2.radius) {
            return Integer.compare(o1.radius,o2.radius);
        }


        // Finally, if both are the same distance, compare z, then y, then x.
        if (o1z != o2z) {
            // if o1 and o2 are not at the same height
            if (o1y != o2y) {
                // if o1 & o2 are not in the same row
                return Integer.compare(o1y, o2y);
            } else {
                // They're in the same row, so
                // compare their x coords
                return Integer.compare(o1x, o2x);

            }
        } else {
            return Integer.compare(o1z, o2z);
        }
    }
}



