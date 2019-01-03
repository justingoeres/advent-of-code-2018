package org.jgoeres.adventofcode.Day17;

import java.util.ArrayList;

public class WaterCell extends XYPair {
    private WaterCell sourceWater;

    public WaterCell(Integer x, Integer y, WaterCell sourceWater) {
        super(x, y);
        this.sourceWater = sourceWater;
    }

    public ArrayList<WaterCell> nextFlowCells(Reservoir reservoir) {
        ArrayList<WaterCell> nextFlowCells = new ArrayList<>();
        // Identify which cells this cell can flow into, and return them as a list.
        // List must be ordered in REVERSE priority since we'll push them onto a stack.

        // If the cell below is empty, flow down but also push the CURRENT cell
        // onto the stack to process later.
        if (reservoir.isEmpty(cellBelow())) {
            nextFlowCells.add(this);
            nextFlowCells.add(cellBelow()); // this one will be first off the stack.

            if (reservoir.isWater(cellRight())) {
                // If we arrived here by spilling over to the left, scan back to the right
                // and see if we need to flow there first.
                //TODO: WORKING HERE.
            }
            return nextFlowCells;
        }

        // If we're here, we can't flow down. So flow to the sides.
        if (reservoir.isEmpty(cellRight())) nextFlowCells.add(cellRight());

        // If we're here, we can't flow down. So flow to the sides.
        if (reservoir.isEmpty(cellLeft())) nextFlowCells.add(cellLeft()); // Left will come off the stack before right.

        return nextFlowCells;
    }

    public void doNextFlow(Reservoir reservoir) {
        // Add this cell to the reservoir's list of cells we've flowed through.
        reservoir.waterCells.add(this);
        System.out.println(this.getY());
        // Recursively calculate the next flow step from this cell.

        if (cellBelow().getY() > reservoir.BottomRight.getY()) {
            // If the next cell would be off the bottom end of the scan.
            return; // then WE'RE DONE!
        }

//        reservoir.printReservoir();


        // If this cell isn't surrounded by anything, flowing down is easy!
        if (reservoir.isEmpty(cellBelow())
                && reservoir.isEmpty(cellLeft())
                && reservoir.isEmpty(cellRight())) {
            WaterCell cellBelow = cellBelow();
            cellBelow.setSourceWater(this);
            cellBelow.doNextFlow(reservoir);
        }

        // If there's water on both sides of us, step backward to the previous y-row and flow from there
        WaterCell nextSource;
        if (reservoir.isWater(cellLeft())
                && reservoir.isWater(cellRight())) {
//            nextSource = sourceWater;
//            while (true) {
//                if (this.getY().equals(nextSource.getY())) {  // go back until we're up one row.
//                    nextSource = nextSource.sourceWater; // step backward.
//                } else {
//                    break;
//                }
//            }
//            // Once we get here, nextSource will be one y-row up. Flow from there.
//            nextSource.doNextFlow(reservoir);
            return; // all the way back to a cell where there ISN'T water on both sides
        }


        // Can't easily flow down – do a more detailed check to see if flowing down is still OK.
        if (reservoir.isWall(cellBelow())
                || reservoir.isWater(cellBelow())) {
            // If the cell below us is a wall, or water.
            // Try to flow left first.
            if (reservoir.isEmpty(cellLeft())) {    // Either this or the next IF should be true, since we just checked and made sure there ISN'T water on both sides.
                WaterCell cellLeft = cellLeft();
                cellLeft.setSourceWater(this);
                cellLeft.doNextFlow(reservoir);
            }

            if (reservoir.isEmpty(cellRight())) {                // Then try to flow right.
                WaterCell cellRight = cellRight();
                cellRight.setSourceWater(this);
                cellRight.doNextFlow(reservoir);
            }

//            // If we can't flow left any further, go backwards up the list of sourceWater to see if we can flow right.
//            // Check only in our current y row.
//            nextSource = sourceWater;
//            while (this.getY().equals(nextSource.getSourceWater().getY())) { // as long as we're still in this row.
//                nextSource = nextSource.sourceWater; // step backward.
//            }
//            // Flow (right) from here.
//            nextSource.doNextFlow(reservoir);
        }


        // Then try to flow down!?
        // JJG 2019-01-03 this doesn't quite work – need to make sure we can't flow right before going down.
        // But we can do that without recursing up the stack – we need to keep this "end" of the water live.
        if (reservoir.isEmpty(cellBelow())) {

            if (reservoir.isWall(cellLeft())
                    || reservoir.isWall(cellRight())) {
                // If there's a wall immediately on either side of us, then we're already flowing down (having spilled over somewhere)
                // So flow down.
                WaterCell cellBelow = cellBelow();
                cellBelow.setSourceWater(this);
                cellBelow.doNextFlow(reservoir);
            }
            // Before we flow down, go backwards up the list of sourceWater to see if we can flow right.
            // Check only in our current y row.
            nextSource = sourceWater;
            while (this.getY().equals(nextSource.getSourceWater().getY())) { // as long as we're still in this row.
                nextSource = nextSource.sourceWater; // step backward.
            }
            // Flow (right) from here.
            nextSource.doNextFlow(reservoir);
            // After the (rightward) flow ends, it will return here and flow down.

            WaterCell cellBelow = cellBelow();
            cellBelow.setSourceWater(this);
            cellBelow.doNextFlow(reservoir);
        }

        return;

    }

    public WaterCell getSourceWater() {
        return sourceWater;
    }

    private void setSourceWater(WaterCell sourceWater) {
        this.sourceWater = sourceWater;
    }

    public WaterCell cellBelow() {
        return new WaterCell(getX(), getY() + 1, this);
    }

    public WaterCell cellLeft() {
        return new WaterCell(getX() - 1, getY(), this);
    }

    public WaterCell cellRight() {
        return new WaterCell(getX() + 1, getY(), this);
    }

    public boolean canFlowDown() {
        //
        return false;
    }

//    @Override
//    public String toString() {
//        return ("(" + this.getX() + "," + getY() + ")");
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o instanceof WaterCell
//                && (((WaterCell) o).getX().equals(this.getX()))
//                && (((WaterCell) o).getY().equals(this.getY()))
//                ) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    @Override
//    public int hashCode() {
//        String hashString = this.toString();
//        int hashCode = hashString.hashCode();
//        return hashCode; // Make the hashCode equivalent for WaterCells at the same coords.
//    }
}
