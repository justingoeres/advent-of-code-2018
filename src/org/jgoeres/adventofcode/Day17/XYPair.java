package org.jgoeres.adventofcode.Day17;

import java.util.ArrayList;

public class XYPair {
    private Integer x;
    private Integer y;

    private final boolean DEBUG_PRINT_CELL_COORDS = false;

    public XYPair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<XYPair> nextFlowCells(Reservoir reservoir) {
        ArrayList<XYPair> nextFlowCells = new ArrayList<>();
        // Identify which cells this cell can flow into, and return them as a list.
        // List must be ordered in REVERSE priority since we'll push them onto a stack.

        // Uncomment the block below to print around a particular row.
/*
          int yToPrint = 1;
          if (this.getY() == yToPrint) {
              reservoir.printReservoir(this.getY(), 10, this);
        }
*/
        // If the cell below is empty, flow down but also push the CURRENT cell
        // onto the stack to process later.
        if (reservoir.isEmpty(cellBelow())) {

            if (DEBUG_PRINT_CELL_COORDS) {
                System.out.println(this.toString()); // print the coordinates of the cell we're processing.
            }

            nextFlowCells.add(this);
            nextFlowCells.add(cellBelow()); // this one will be first off the stack.

            // If we arrived here by spilling over to the left, scan back to the right
            // and see if we need to flow there first.
            XYPair nextCellRight = cellRight();
            if (reservoir.isWater(nextCellRight)) {
                while (reservoir.isWater(nextCellRight) // If the next cell on the right is water.
                        && (reservoir.isWall(nextCellRight.cellBelow()) || reservoir.isSolidWater(nextCellRight.cellBelow()))) {    // And is sitting on something solid (wall or solid water)
                    nextCellRight = nextCellRight.cellRight(); // Keep stepping to the right.
                }

                // When we get here, either nextCellRight is EMPTY or is FLOWING water.
                if (reservoir.isEmpty(nextCellRight)) {
                    // If it's empty (and exists), flow there.
                    nextFlowCells.add(nextCellRight); // this one will be first off the stack, even before cellBelow.
                }
                // Otherwise, it's flowing water, so do nothing.
            }
            return nextFlowCells;
        }

        // If we're here, we can't simply flow down. Check the cell below to see if we should flow to the sides.

        if (reservoir.isWall(cellBelow())    // If the below cell is a wall,
                || reservoir.isSolidWater(cellBelow())) {   // or is solid water.
            // definitely try to flow out the sides.
            if (reservoir.isEmpty(cellRight()))
                nextFlowCells.add(cellRight());

            if (reservoir.isEmpty(cellLeft()))
                nextFlowCells.add(cellLeft()); // Left will come off the stack before right.
        }
        // Else the cell below is not "solid" water or a wall, so flow into it; not out the sides.
        // Flowing into it means "do nothing", but then we need to go back up the stack again and
        // find the next down-flowing empty space.

        return nextFlowCells;
    }

    public XYPair cellAbove() {
        return new XYPair(getX(), getY() - 1);
    }

    public XYPair cellBelow() {
        return new XYPair(getX(), getY() + 1);
    }

    public XYPair cellLeft() {
        return new XYPair(getX() - 1, getY());
    }

    public XYPair cellRight() {
        return new XYPair(getX() + 1, getY());
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

    @Override
    public String toString() {
        return ("(" + this.getX() + "," + getY() + ")");
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof XYPair
                && (((XYPair) o).getX().equals(x))
                && (((XYPair) o).getY().equals(y))
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

