package org.jgoeres.adventofcode.Day08;

import java.util.ArrayList;
import java.util.Scanner;

import static org.jgoeres.adventofcode.Day08.RunDay8.PRINT_NODETREE_DEBUG;

public class Node {
    ArrayList<Node> childNodes = new ArrayList<>();
    ArrayList<Integer> metaDataEntries = new ArrayList<>();

    public Node(Scanner sc) {
    /*
    A node consists of:

    - A header, which is always exactly two numbers:
        - The quantity of child nodes.
        - The quantity of metadata entries.
    - Zero or more child nodes (as specified in the header).
    - One or more metadata entries (as specified in the header).
     */

        // Read the header:
        Integer numChildNodes = sc.nextInt();
        Integer numMetadataEntries = sc.nextInt();
        if (PRINT_NODETREE_DEBUG) {
            System.out.println(numChildNodes + "\t" + numMetadataEntries);
        }

        // Process the child nodes
        for (int i = 0; i < numChildNodes; i++) {
            // Construct a (child node) from the scanner
            Node newChild = new Node(sc);
            // Add it to our child nodes
            childNodes.add(newChild);
        }

        // Process the metadata
        for (int i = 0; i < numMetadataEntries; i++) {
            // Read the metadata value
            Integer newMetadata = sc.nextInt();
            if (PRINT_NODETREE_DEBUG) {
                System.out.println("\t\t" + newMetadata);
            }
            // Add it to our list of metadata for this node.
            metaDataEntries.add(newMetadata);
        }
    }

    public Integer sumAllMetaData() {
        // Calculate the running sum of all metadata, recursively.
        Integer currentSum = 0;

        // Add up all the metadata in this node
        for (Integer metaData : metaDataEntries) {
            currentSum += metaData;
        }

        // Add to it all the metadata in all the children.
        for (Node childNode : childNodes) {
            currentSum += childNode.sumAllMetaData();
        }
        return currentSum;
    }
}
