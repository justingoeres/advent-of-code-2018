package org.jgoeres.adventofcode.Day17;

import java.util.ArrayList;

public class XYPair {
    private Integer x;
    private Integer y;

    public XYPair(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<XYPair> nextFlowCells(Reservoir reservoir) {
        ArrayList<XYPair> nextFlowCells = new ArrayList<>();
        // Identify which cells this cell can flow into, and return them as a list.
        // List must be ordered in REVERSE priority since we'll push them onto a stack.
        int yToPrint = 1;
        // If the cell below is empty, flow down but also push the CURRENT cell
        // onto the stack to process later.
//        if (this.getY() == yToPrint) {
//            reservoir.printReservoir(this.getY(), 10, this);
//        }

        if (reservoir.isEmpty(cellBelow())) {

//            System.out.println(this.toString());

            nextFlowCells.add(this);  // Maybe don't add to the stack when we're spilling over, i.e. cellRight or cellLeft is water
            nextFlowCells.add(cellBelow()); // this one will be first off the stack.

            XYPair testCell = this;
            XYPair nextCellRight = cellRight();
            // If we arrived here by spilling over to the left, scan back to the right
            // and see if we need to flow there first.
            if (reservoir.isWater(nextCellRight)) {
                while (reservoir.isWater(nextCellRight) // If the next cell on the right is water.
                        && (reservoir.isWall(nextCellRight.cellBelow()) || reservoir.isSolidWater(nextCellRight.cellBelow()))) {    // And is sitting on something solid (wall or solid water)
                    testCell = nextCellRight;
                    nextCellRight = nextCellRight.cellRight(); // Keep stepping to the right.
                }
                // When we get here, either nextCellRight is EMPTY or is FLOWING water.

                if (reservoir.isEmpty(nextCellRight) && reservoir.isInBounds(nextCellRight)) {
                    // If it's empty (and exists), flow there.
                    nextFlowCells.add(nextCellRight); // this one will be first off the stack, even before cellBelow.
                }
                // Otherwise, it's flowing water, so do nothing.

//                if (reservoir.isEmpty(testCell.cellBelow())) {
//                    nextFlowCells.add(testCell.cellBelow());
//                } else if (reservoir.isEmpty(nextCellRight) && reservoir.isInBounds(nextCellRight)) {
//                    // If it's empty (and exists), flow there.
//                    nextFlowCells.add(nextCellRight); // this one will be first off the stack, even before cellBelow.
//                }
            }
            return nextFlowCells;
        }

        // If we're here, we can't simply flow down. Check the cell below to see if we should flow to the sides.

        if (reservoir.isWall(cellBelow())) {
            // If the below cell is a wall, definitely try to flow out the sides.
            if ((reservoir.isEmpty(cellRight())) && reservoir.isInBounds(cellRight()))
                nextFlowCells.add(cellRight());

            if ((reservoir.isEmpty(cellLeft())) && reservoir.isInBounds(cellLeft()))
                nextFlowCells.add(cellLeft()); // Left will come off the stack before right.
        }

        if (reservoir.isWater(cellBelow())) {
            if (reservoir.isSolidWater(cellBelow())) {
                // If the cell below is "solid" water (i.e. it's contained on both edges.
                // Then flow out the sides.
                if (reservoir.isEmpty(cellRight()) && reservoir.isInBounds(cellRight()))
                    nextFlowCells.add(cellRight());

                if (reservoir.isEmpty(cellLeft()) && reservoir.isInBounds(cellRight()))
                    nextFlowCells.add(cellLeft()); // Left will come off the stack before right.
            } else {
                // The cell below is not "solid" water, so flow into it; not out the sides.
                // Flowing into it means "do nothing", but then we need to go back up the stack again and
                // find the next down-flowing empty space.
            }
        }
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

