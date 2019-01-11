package org.jgoeres.adventofcode.Day20;

import java.util.Stack;

public class Spider {
    Room room;
    int index;
    Stack<Jump> jumpStack = new Stack<>();

    public Spider(Room room, int index) {
        this.room = room;
        this.index = index;
    }

    public void stepForward() {
        this.index++;
    }

    @Override
    public String toString() {
        return (index+": (" + room.getX() + "," + room.getY() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Room
                && (((Spider) o).room.getX().equals(room.getX()))
                && (((Spider) o).room.getY().equals(room.getY()))
                && (((Spider) o).index == index)
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
        return hashCode; // Make the hashCode equivalent for Spider at the same coords and index..
    }
}
