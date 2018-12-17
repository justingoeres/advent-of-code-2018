package org.jgoeres.adventofcode.Day10;

public abstract class SkyService {

    public static Sky skyAtTimeT(Sky sky, Integer currentTime) {
        Sky skyNow = new Sky(); // We'll return a copy of the original sky, fast-forwarded to currentTime.

        for (Star star : sky.getStars()) {
            XYPair posNow = star.getPositionAtTime(currentTime); // get the position of this star at currentTime
            Star starNow = new Star(posNow.getX(), posNow.getY(), 0, 0, star.getId()); // create a new star at that position
            skyNow.getStars().add(starNow); // add the new star to our image of the sky at currentTime
        }
        return skyNow;
    }

    public static boolean starIsCloseToAnother(final Star star, final Sky sky, final Integer distanceLimit) {
        Integer minDistance = Integer.MAX_VALUE;
        Integer maxDistance = Integer.MIN_VALUE;

        for (Star referenceStar : sky.getStars()) {
            // check star against every reference star
            if (star.equals(referenceStar)) {
                // if this star IS reference star
                // do nothing
            } else {
                Integer distance = manhattanDistance(star, referenceStar);

                if (distance > maxDistance) {
                    maxDistance = distance;
                }
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }

        }
        if (minDistance <= distanceLimit) {
            // star is close to this referenceStar, so we're done
            return true;
        }
        return false; // this star is not close to any other star in this sky.
    }

    private static Integer manhattanDistance(Star p1, Star p2) {
        Integer xDist = Math.abs(p2.getInitialPosition().getX() - p1.getInitialPosition().getX());
        Integer yDist = Math.abs(p2.getInitialPosition().getY() - p1.getInitialPosition().getY());

        return (xDist + yDist);
    }

    public static Extents findExtents(Sky sky) {
        // Find the extents of the sky.
        Integer xMin = Integer.MAX_VALUE;
        Integer xMax = Integer.MIN_VALUE;

        Integer yMin = Integer.MAX_VALUE;
        Integer yMax = Integer.MIN_VALUE;

        for (Star star : sky.getStars()) {
            Integer x = star.getInitialPosition().getX();
            Integer y = star.getInitialPosition().getY();

            if (x > xMax) xMax = x;
            if (x < xMin) xMin = x;
            if (y > yMax) yMax = y;
            if (y < yMin) yMin = y;
        }

        Extents skyExtents = new Extents(xMin, xMax, yMin, yMax);
        return skyExtents;
    }

    public static void printSky(Sky sky) {
        Extents extents = findExtents(sky);
        Integer xOffset = extents.xMin;
        Integer yOffset = extents.yMin;

        String output = "";
        // Graph all the stars
        for (int y = 0; y <= extents.height(); y++) {
            for (int x = 0; x <= extents.width(); x++) {
                boolean found = false;
                for (Star star : sky.getStars()) {
                    // Is this star living at the current x,y point in the sky?
                    if (((star.getInitialPosition().getX() - xOffset) == x)
                            && (star.getInitialPosition().getY() - yOffset == y)) {
                        // Draw it!
//                        System.out.print("#");
                        output += "#";
                        found = true;
                        break; // and move to the next point
                    }
                }
                if (!found) {
                    // No star here.
//                    System.out.print(".");
                    output += ".";
                }
            }
//            System.out.println(); // go to the next line
            output+="\n";
        }
        System.out.println(output);
    }
}
