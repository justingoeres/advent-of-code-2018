package org.jgoeres.adventofcode.Day10;

public class Extents {
    Integer xMin;
    Integer xMax;
    Integer yMin;
    Integer yMax;

    public Extents(Integer xMin, Integer xMax, Integer yMin, Integer yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public Integer width() {
        return Math.abs(xMax - xMin);
    }

    public Integer height() {
        return Math.abs(yMax - yMin);
    }

    public Integer getxMin() {
        return xMin;
    }

    public Integer getxMax() {
        return xMax;
    }

    public Integer getyMin() {
        return yMin;
    }

    public Integer getyMax() {
        return yMax;
    }

}
