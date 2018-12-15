package org.jgoeres.adventofcode.Day08;

import java.io.File;
import java.util.Scanner;

public class License {
    private Node rootNode = null;

    public License(String pathToFile) {
        loadLicenseInfo(pathToFile);
    }

    private void loadLicenseInfo(String pathToFile) {
     /* The file looks like
        9 11 7 2 4 3 3 7 1 9 0 8 8 8 2 8 8 6 1 7 2 1 2

        where a node consists of:

        - A header, which is always exactly two numbers:
            - The quantity of child nodes.
            - The quantity of metadata entries.
        - Zero or more child nodes (as specified in the header).
        - One or more metadata entries (as specified in the header).

    Each child node is itself a node that has its own header, child nodes, and metadata.
    For example:

    2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2
    A----------------------------------
        B----------- C-----------
                         D-----
    In this example, each node of the tree is also marked with an underline starting with
    a letter for easier identification. In it, there are four nodes:

        A, which has 2 child nodes (B, C) and 3 metadata entries (1, 1, 2).
        B, which has 0 child nodes and 3 metadata entries (10, 11, 12).
        C, which has 1 child node (D) and 1 metadata entry (2).
        D, which has 0 child nodes and 1 metadata entry (99).
     */

        try (Scanner sc = new Scanner(new File(pathToFile))) {
            rootNode = new Node(sc);
//            System.out.println(nodeTree);
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    public Node getRootNode() {
        return rootNode;
    }
}
