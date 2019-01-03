package org.jgoeres.adventofcode.Day17;

public class WaterCell extends XYPair {
    private WaterCell sourceWater;

    public WaterCell(Integer x, Integer y, WaterCell sourceWater) {
        super(x, y);
        this.sourceWater = sourceWater;
    }


    public void doNextFlow(Reservoir reservoir) {
        // Add this cell to the reservoir's list of cells we've flowed through.
        reservoir.waterCells.add(this);

        // Recursively calculate the next flow step from this cell.

        if (cellBelow().getY() > reservoir.BottomRight.getY()){
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
            nextSource = sourceWater;
            while (true) {
                if (this.getY().equals(nextSource.getY())) {  // go back until we're up one row.
                    nextSource = nextSource.sourceWater; // step backward.
                } else {
                    break;
                }
            }
            // Once we get here, nextSource will be one y-row up. Flow from there.
            nextSource.doNextFlow(reservoir);
        }


        // Can't easily flow down – do a more detailed check to see if flowing down is still OK.
        if (reservoir.isWall(cellBelow())
                || reservoir.isWater(cellBelow())) {
            // If the cell below us is a wall.
            // Try to flow left first.
            if (reservoir.isEmpty(cellLeft())) {
                WaterCell cellLeft = cellLeft();
                cellLeft.setSourceWater(this);
                cellLeft.doNextFlow(reservoir);
                return;
            }

            // Then try to flow right.
            if (reservoir.isEmpty(cellRight())) {
                WaterCell cellRight = cellRight();
                cellRight.setSourceWater(this);
                cellRight.doNextFlow(reservoir);
                return;
            }

            // If we can't flow left any further, go backwards up the list of sourceWater to see if we can flow right.
            // Check only in our current y row.
            nextSource = sourceWater;
            while (this.getY().equals(nextSource.getSourceWater().getY())) { // as long as we're still in this row.
                nextSource = nextSource.sourceWater; // step backward.
            }
            // Flow (right) from here.
            nextSource.doNextFlow(reservoir);
        }

        // Then try to flow down!?
        if (reservoir.isEmpty(cellBelow())) {
            WaterCell cellBelow = cellBelow();
            cellBelow.setSourceWater(this);
            cellBelow.doNextFlow(reservoir);
            return;
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
