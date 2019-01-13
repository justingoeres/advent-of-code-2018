package org.jgoeres.adventofcode.Day22;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.jgoeres.adventofcode.Day22.Direction.*;
import static org.jgoeres.adventofcode.Day22.Tool.CLIMBING_GEAR;
import static org.jgoeres.adventofcode.Day22.Tool.NOTHING;
import static org.jgoeres.adventofcode.Day22.Tool.TORCH;

public class CaveSystem {
    int targetX;
    int targetY;
    int depth;
    Region[][] regions;
    boolean[][] rockyMap, wetMap, narrowMap;
    boolean[][] nothingMap, climbingGearMap, torchMap;
    HashMap<String, CaveStep> caveSteps = new HashMap<>();

    ArrayList<CaveStep> currentEdges = new ArrayList<>();
    ArrayList<CaveStep> newEdges = new ArrayList<>();
    int maxDistance = Integer.MIN_VALUE;
    int unreachedCaveSteps = 0;


    public CaveSystem(int targetX, int targetY, int depth) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.depth = depth;

        regions = new Region[targetX + 1][targetY + 1];
        rockyMap = new boolean[targetX + 1][targetY + 1];
        wetMap = new boolean[targetX + 1][targetY + 1];
        narrowMap = new boolean[targetX + 1][targetY + 1];
        nothingMap = new boolean[targetX + 1][targetY + 1];
        climbingGearMap = new boolean[targetX + 1][targetY + 1];
        torchMap = new boolean[targetX + 1][targetY + 1];

        for (int y = 0; y <= targetY; y++) { // rows
            for (int x = 0; x <= targetX; x++) { // columns
                Region newRegion = createNewRegion(x, y);
                regions[x][y] = newRegion;

                rockyMap[x][y] = (newRegion.type.equals(RegionType.ROCKY));
                wetMap[x][y] = (newRegion.type.equals(RegionType.WET));
                narrowMap[x][y] = (newRegion.type.equals(RegionType.NARROW));

                //In rocky regions, you can use the climbing gear or the torch. You cannot use neither (you'll likely slip and fall).
                //In wet regions, you can use the climbing gear or neither tool. You cannot use the torch (if it gets wet, you won't have a light source).
                //In narrow regions, you can use the torch or neither tool. You cannot use the climbing gear (it's too bulky to fit).
                nothingMap[x][y] = wetMap[x][y] | narrowMap[x][y];
                climbingGearMap[x][y] = rockyMap[x][y] | wetMap[x][y];
                torchMap[x][y] = rockyMap[x][y] | narrowMap[x][y];

                // Build up the hashmap of x,y caves and tools that can go there.
                if (isNothingOK(newRegion.type)) {
                    CaveStep newCaveStep = new CaveStep(x, y, NOTHING);
                    caveSteps.put(newCaveStep.toString(), newCaveStep);
                }

                if (isTorchOK(newRegion.type)) {
                    CaveStep newCaveStep = new CaveStep(x, y, TORCH);
                    caveSteps.put(newCaveStep.toString(), newCaveStep);
                }

                if (isClimbingGearOK(newRegion.type)) {
                    CaveStep newCaveStep = new CaveStep(x, y, Tool.CLIMBING_GEAR);
                    caveSteps.put(newCaveStep.toString(), newCaveStep);
                }

            }
        }
        // Now connect everything up.
        for (Map.Entry<String, CaveStep> caveStepEntry : caveSteps.entrySet()) {
            CaveStep caveStep = caveStepEntry.getValue();

            // above
            if ((caveStep.getCaveAbove() == null) && caveSteps.containsKey(relativeKey(caveStep, ABOVE))) {
                // If there's not already a cave assigned above us, but one exists.
                // Wire it up.
                CaveStep caveAbove = caveSteps.get(relativeKey(caveStep, ABOVE));
                caveStep.connectToAbove(caveAbove);
            }
            // right
            if ((caveStep.getCaveRight() == null) && caveSteps.containsKey(relativeKey(caveStep, RIGHT))) {
                // If there's not already a cave assigned right us, but one exists.
                // Wire it up.
                CaveStep caveRight = caveSteps.get(relativeKey(caveStep, RIGHT));
                caveStep.connectToRight(caveRight);
            }
            // left
            if ((caveStep.getCaveLeft() == null) && caveSteps.containsKey(relativeKey(caveStep, LEFT))) {
                // If there's not already a cave assigned left us, but one exists.
                // Wire it up.
                CaveStep caveLeft = caveSteps.get(relativeKey(caveStep, LEFT));
                caveStep.connectToLeft(caveLeft);
            }
            // below
            if ((caveStep.getCaveBelow() == null) && caveSteps.containsKey(relativeKey(caveStep, BELOW))) {
                // If there's not already a cave assigned below us, but one exists.
                // Wire it up.
                CaveStep caveBelow = caveSteps.get(relativeKey(caveStep, BELOW));
                caveStep.connectToBelow(caveBelow);
            }

            // nothing
            if (!(caveStep.getTool().equals(NOTHING))
                    && (caveStep.getToNothing() == null)
                    && caveSteps.containsKey(relativeKey(caveStep, TO_NOTHING))) {
                // If this is NOT a nothing cave, and
                // If there's not already a nothing cave assigned to us, but one exists.
                // Wire it up.
                CaveStep caveNothing = caveSteps.get(relativeKey(caveStep, TO_NOTHING));
                caveStep.connectToNothing(caveNothing);
            }

            // torch
            if (!(caveStep.getTool().equals(TORCH))
                    && (caveStep.getToTorch() == null)
                    && caveSteps.containsKey(relativeKey(caveStep, TO_TORCH))) {
                // If this is NOT a torch cave, and
                // If there's not already a torch cave assigned to us, but one exists.
                // Wire it up.
                CaveStep caveTorch = caveSteps.get(relativeKey(caveStep, TO_TORCH));
                caveStep.connectToTorch(caveTorch);
            }

            // climbing gear
            if (!(caveStep.getTool().equals(CLIMBING_GEAR))
                    && (caveStep.getToClimbingGear() == null)
                    && caveSteps.containsKey(relativeKey(caveStep, TO_CLIMBING_GEAR))) {
                // If this is NOT a climbingGear cave, and
                // If there's not already a climbingGear cave assigned to us, but one exists.
                // Wire it up.
                CaveStep caveClimbingGear = caveSteps.get(relativeKey(caveStep, TO_CLIMBING_GEAR));
                caveStep.connectToClimbingGear(caveClimbingGear);
            }

        }
    }

    public boolean doTick() {
        boolean done = false;
        int sameToolCost = 1;
        int differentToolCost = 7;

        newEdges.clear();   // Reset the list of new edges.
        // Iterate through all the "edge" cavesteps.
        for (CaveStep caveStep : currentEdges) {
            int sameToolDistance = caveStep.distance + sameToolCost;
            int diffToolDistance = caveStep.distance + differentToolCost;

            CaveStep edgeCave = null;
            // For each one:
            //      - Get all the adjacent cavesteps.
            //      - Calculate the "distance" value of moving to them (+1 for same tool, +7 for different tool)
            //      - If the "distance" value of moving to them is < their current distance value,
            //          set their distance to that value,
            //          and add them to the "new edges"

            // above
            if ((caveStep.hasCave(ABOVE)) // if there's a cave above
                    && ((sameToolDistance) < caveStep.getCaveAbove().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getCaveAbove();
                edgeCave.distance = sameToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

            // below
            if ((caveStep.hasCave(BELOW)) // if there's a cave below
                    && ((sameToolDistance) < caveStep.getCaveBelow().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getCaveBelow();
                edgeCave.distance = sameToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

            // right
            if ((caveStep.hasCave(RIGHT)) // if there's a cave right
                    && ((sameToolDistance) < caveStep.getCaveRight().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getCaveRight();
                edgeCave.distance = sameToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

            // left
            if ((caveStep.hasCave(LEFT)) // if there's a cave left
                    && ((sameToolDistance) < caveStep.getCaveLeft().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getCaveLeft();
                edgeCave.distance = sameToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

            // to_torch
            if ((caveStep.hasCave(TO_TORCH)) // if there's a cave to_torch
                    && ((diffToolDistance) < caveStep.getToTorch().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getToTorch();
                edgeCave.distance = diffToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

            // to_nothing
            if ((caveStep.hasCave(TO_NOTHING)) // if there's a cave to_nothing
                    && ((diffToolDistance) < caveStep.getToNothing().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getToNothing();
                edgeCave.distance = diffToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

            // to_climbingGear
            if ((caveStep.hasCave(TO_CLIMBING_GEAR)) // if there's a cave to_climbingGear
                    && ((diffToolDistance) < caveStep.getToClimbingGear().distance)) { // And the new distance there would be less than its current
                // Set its distance
                edgeCave = caveStep.getToClimbingGear();
                edgeCave.distance = diffToolDistance;
                // Add it to the "new edges"
                newEdges.add(edgeCave);
            }

        }

        for (CaveStep caveStep : newEdges) {
            if (caveStep.distance > maxDistance) maxDistance = caveStep.distance;
        }

        unreachedCaveSteps = 0;
        for (Map.Entry<String, CaveStep> caveStepEntry : caveSteps.entrySet()) {
            CaveStep caveStep = caveStepEntry.getValue();
            if (caveStep.distance.equals(Integer.MAX_VALUE)) unreachedCaveSteps++;
        }

        // We're done when nothing has been added to newEdges!
        done = newEdges.isEmpty();

        // Swap the edge lists
        ArrayList<CaveStep> temp = null;
        temp = currentEdges;
        currentEdges = newEdges;
        newEdges = temp;

        return done;
    }

    public void initializeMapping() {
        CaveStep originCaveStep = caveSteps.get(XYToolToKey(0, 0, TORCH));
        // Set the "distance" for the 0,0 cavestep(torch) to 0.
        originCaveStep.distance = 0;
        // Add it to the current edges.
        currentEdges.add(originCaveStep);
    }

    public CaveStep getTargetCaveStep() {
        CaveStep target = caveSteps.get(XYToolToKey(targetX, targetY, TORCH));
        return target;
    }

    private String XYToolToKey(int x, int y, Tool tool) {
        String key = new CaveStep(x, y, tool).toString();
        return key;
    }

    private String relativeKey(CaveStep caveStep, Direction direction) {
        int x = caveStep.getX();
        int y = caveStep.getY();
        Tool tool = caveStep.getTool();
        switch (direction) {
            case ABOVE:
                y--;
                break;
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
            case BELOW:
                y++;
                break;
            case TO_TORCH:
                tool = TORCH;
                break;
            case TO_NOTHING:
                tool = NOTHING;
                break;
            case TO_CLIMBING_GEAR:
                tool = Tool.CLIMBING_GEAR;
                break;
        }
        String key = XYToolToKey(x, y, tool);
        return key;
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

    private int calculateGeologicIndex(int x, int y) {
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

    private int calculateErosionLevel(int geologicIndex) {
        // A region's erosion level is its geologic index plus the
        // cave system's depth, all modulo 20183.
        int erosionLevel = (geologicIndex + depth) % 20183;
        return erosionLevel;
    }

    private RegionType calculateRegionType(int erosionLevel) {
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

    private boolean isNothingOK(RegionType type) {
        return (type.equals(RegionType.WET) || type.equals(RegionType.NARROW));
    }

    private boolean isTorchOK(RegionType type) {
        return (type.equals(RegionType.ROCKY) || type.equals(RegionType.NARROW));
    }

    private boolean isClimbingGearOK(RegionType type) {
        return (type.equals(RegionType.WET) || type.equals(RegionType.ROCKY));
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

    public void printToolMap(Tool tool) {
        boolean[][] toolMap = null;
        switch (tool) {
            case NOTHING:
                toolMap = nothingMap;
                break;
            case TORCH:
                toolMap = torchMap;
                break;
            case CLIMBING_GEAR:
                toolMap = climbingGearMap;
                break;
        }
        for (int y = 0; y <= targetY; y++) { // rows
            String output = "";
            for (int x = 0; x <= targetX; x++) { // columns
                output += (toolMap[x][y] ? "X" : " ");
            }
            System.out.println(y + ":\t" + output);
        }
    }
}
