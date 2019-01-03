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

        // If this cell isn't surrounded by anything, flowing down is easy!
        if (reservoir.isEmpty(cellBelow())
                && reservoir.isEmpty(cellLeft())
                && reservoir.isEmpty(cellRight())) {
            WaterCell cellBelow = (WaterCell) this.cellBelow();
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
