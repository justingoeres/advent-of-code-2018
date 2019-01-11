package org.jgoeres.adventofcode.Day20;

public class Room {
    private Integer x;
    private Integer y;
    private Integer distance;

    private Room roomNorth = null;
    private Room roomEast = null;
    private Room roomSouth = null;
    private Room roomWest = null;

    public Room() {
    }

    public Room(Integer x, Integer y, Integer distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }


    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getDistance() {
        return distance;
    }

    public void connectToNorth(Room roomNorth) {
        setRoomNorth(roomNorth);
        roomNorth.setRoomSouth(this);
    }

    public void connectToSouth(Room roomSouth) {
        setRoomSouth(roomSouth);
        roomSouth.setRoomNorth(this);
    }

    public void connectToEast(Room roomEast) {
        setRoomEast(roomEast);
        roomEast.setRoomWest(this);
    }

    public void connectToWest(Room roomWest) {
        setRoomWest(roomWest);
        roomWest.setRoomEast(this);
    }

    public Room getRoomNorth() {
        return roomNorth;
    }

    public void setRoomNorth(Room roomNorth) {
        this.roomNorth = roomNorth;
    }

    public Room getRoomEast() {
        return roomEast;
    }

    public void setRoomEast(Room roomEast) {
        this.roomEast = roomEast;
    }

    public Room getRoomSouth() {
        return roomSouth;
    }

    public void setRoomSouth(Room roomSouth) {
        this.roomSouth = roomSouth;
    }

    public Room getRoomWest() {
        return roomWest;
    }

    public void setRoomWest(Room roomWest) {
        this.roomWest = roomWest;
    }

    @Override
    public String toString() {
        return ("(" + this.getX() + "," + getY() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Room
                && (((Room) o).getX().equals(x))
                && (((Room) o).getY().equals(y))
                ) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String hashString = this.toString();
        int hashCode = hashString.hashCode();
        return hashCode; // Make the hashCode equivalent for XYPairs at the same coords.
    }
}

