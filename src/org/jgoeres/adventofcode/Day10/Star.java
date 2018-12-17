package org.jgoeres.adventofcode.Day10;

public class Star {
    private Integer Id;
    private XYPair initialPosition;
    private XYPair velocity;

    public Star(Integer x0, Integer y0, Integer vX, Integer vY, Integer id) {
        initialPosition = new XYPair(x0, y0);
        velocity = new XYPair(vX, vY);
        Id = id;
    }

    public XYPair getPositionAtTime(Integer time) {
        Integer xPos = initialPosition.getX() + velocity.getX() * time;
        Integer yPos = initialPosition.getY() + velocity.getY() * time;

        XYPair positionAtTime = new XYPair(xPos, yPos);
        return positionAtTime;
    }

    public XYPair getInitialPosition() {
        return initialPosition;
    }

    public Integer getId() {
        return Id;
    }
}
