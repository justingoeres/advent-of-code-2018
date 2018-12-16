package org.jgoeres.adventofcode.Day10;

public class Star {
    private XYPair initialPosition;
    private XYPair velocity;

    public Star(Integer x0, Integer y0, Integer vX, Integer vY) {
        initialPosition = new XYPair(x0, y0);
        velocity = new XYPair(vX, vY);
    }

    public XYPair getPositionAtTime(Integer time) {
        Integer xPos = initialPosition.getX() + velocity.getX() * time;
        Integer yPos = initialPosition.getY() + velocity.getY() * time;

        XYPair positionAtTime = new XYPair(xPos, yPos);
        return positionAtTime;
    }


}
