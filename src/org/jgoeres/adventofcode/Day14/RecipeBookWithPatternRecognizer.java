package org.jgoeres.adventofcode.Day14;

import java.util.ArrayList;

public class RecipeBookWithPatternRecognizer extends CircularLinkedList {
    private String patternBuffer = "";
    private String patternToFind = "";
    private ArrayList<Integer> patternToFindQueue = new ArrayList<>();
    private Node patternScannerNode = null;

    private boolean patternFound = false;

    public RecipeBookWithPatternRecognizer(Integer rootNodeValue, String patternToFind) {
        super(rootNodeValue);
        this.patternToFind = patternToFind;

        // Load up the pattern queue (in reverse because we'll search backwards).
        String patternReversed = new StringBuilder(patternToFind).reverse().toString();
        for (int i = 0; i < patternReversed.length(); i++) {
            Integer patternItem = Integer.parseInt(patternReversed.substring(i, i + 1));
            patternToFindQueue.add(patternItem);
        }

        resetPatternScanner();
    }

    @Override
    public void addOnRightEnd(Integer newNodeValue) {
        super.addOnRightEnd(newNodeValue);
        checkPatternFound();
    }

    private void resetPatternScanner() {
        // Set the scanner to the root node.
        patternScannerNode = getRootNode();

    }

    private void scannerPrev() {
        Node prevNode = patternScannerNode.getPrev();
        setScannerCurrentNode(prevNode);
    }

    private void setScannerCurrentNode(Node node) {
        patternScannerNode = node;
    }

    private void checkPatternFound() {
        // Start at the END of the book and work backward,
        // while moving forward through the patternToFindQueue.

        String output = "";
        boolean found = true; // start assuming the pattern is matched.
        resetPatternScanner(); // Make sure we're scanning from the root.
        for (Integer patternItem : patternToFindQueue) {
            scannerPrev(); // Scan back one position.
            // check if this element matches the pattern.
            found &= (patternScannerNode.getValue().equals(patternItem));

            if (!found) {
                // If we broke the pattern.
                break; // give up and stop looking.
            } else {
//                System.out.println("Found " + patternItem);
                output += patternItem;
                // do nothing
            }
        }
        if (output.length() > 4) {
            System.out.println(getSize() + ":\t" + output);
        }
        patternFound = found;
    }

    public boolean isPatternFound() {
        return patternFound;
    }
}
