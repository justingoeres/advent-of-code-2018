package org.jgoeres.adventofcode.Day22;

public class CaveSystem {
    int targetX;
    int targetY;
    int depth;
    Region[][] regions;

    public CaveSystem(int targetX, int targetY, int depth) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.depth = depth;

        regions = new Region[targetX + 1][targetY + 1];

        for (int y = 0; y <= targetY; y++) { // rows
            for (int x = 0; x <= targetX; x++) { // columns
                regions[x][y] = createNewRegion(x, y);
            }
        }
    }

    private Region createNewRegion(int x, int y) {
        // Calculate the geologic index.
        int geologicIndex = calculateGeologicIndex(x, y);

        // Calculate the erosion level.
        int erosionLevel = calculateErosionLevel(geologicIndex);

        // Calculate the type
        RegionType regionType = calculateRegionType(erosionLevel);

        // Create the region
        Region newRegion = new Region(regionType, erosionLevel);

        return newRegion;
    }

    public int calculateGeologicIndex(int x, int y) {
        // Calculate this region's geologic index from its x & y coordinates, and the puzzle input.

        // The region at the coordinates of the target has a geologic index of 0
        if (x == targetX && y == targetY) {
            return 0;
        }

        // If the region's X coordinate is 0, the geologic index is its Y coordinate times 48271
        if (x == 0) {
            return (y * 48271);
        }

        // If the region's Y coordinate is 0, the geologic index is its X coordinate times 16807.
        if (y == 0) {
            return (x * 16807);
        }

        // Otherwise, the region's geologic index is the result of
        // multiplying the erosion levels of the regions at X-1,Y and X,Y-1.
        int leftErosionLevel = regions[x - 1][y].erosionLevel;
        int aboveErosionLevel = regions[x][y - 1].erosionLevel;

        return leftErosionLevel * aboveErosionLevel;
    }

    public int calculateErosionLevel(int geologicIndex) {
        // A region's erosion level is its geologic index plus the
        // cave system's depth, all modulo 20183.
        int erosionLevel = (geologicIndex + depth) % 20183;
        return erosionLevel;
    }

    public RegionType calculateRegionType(int erosionLevel) {
        // If the erosion level modulo 3 is 0, the region's type is rocky.
        // If the erosion level modulo 3 is 1, the region's type is wet.
        // If the erosion level modulo 3 is 2, the region's type is narrow.
        RegionType regionType = RegionType.values()[erosionLevel % 3];

        return regionType;
    }

    public int calculateAreaRiskLevel(int minX, int maxX, int minY, int maxY) {
        int totalRiskLevel = 0;
        for (int y = 0; y <= targetY; y++) { // rows
            for (int x = 0; x <= targetX; x++) { // columns
                int riskLevel = regions[x][y].type.ordinal();
                totalRiskLevel += riskLevel;
            }
        }
        return totalRiskLevel;
    }

    public void printCaveSystem() {
        for (int y = 0; y <= targetY; y++) { // rows
            String output = "";
            for (int x = 0; x <= targetX; x++) { // columns
                if (x == 0 && y == 0) {
                    // mouth
                    output += "M";
                } else if (x == targetX && y == targetY) {
                    // target
                    output += "T";
                } else {
                    output += regions[x][y].type.asChar();
                }
            }
            System.out.println(y + ":\t" + output);
        }
    }
}
