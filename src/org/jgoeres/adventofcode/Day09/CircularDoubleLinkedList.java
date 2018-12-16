package org.jgoeres.adventofcode.Day09;

public class CircularDoubleLinkedList {
    private Node rootNode;
    private Node currentNode;

    public CircularDoubleLinkedList(Integer rootNodeValue) {
        // Create the list with one node (the root).
        // Create the root node
        Node newRootNode = new Node(rootNodeValue);

        // Set it to point to itself (circularly)
        newRootNode.setNext(newRootNode);
        newRootNode.setPrev(newRootNode);

        setRootNode(newRootNode);
        setCurrentNode(getRootNode()); // Set the "current" node to point at the root.
    }

    public void next() {
        Node nextNode = getCurrentNode().getNext();
        setCurrentNode(nextNode);
    }

    public void nextByN(Integer distance) {
        for (int i = 0; i < distance; i++) {
            currentNode = currentNode.getNext();
        }
    }

    public void prev() {
        Node prevNode = getCurrentNode().getPrev();
        setCurrentNode(prevNode);
    }

    public void prevByN(Integer distance) {
        for (int i = 0; i < distance; i++) {
            currentNode = currentNode.getPrev();
        }
    }

    public void insertToRight(Integer newNodeValue) {
        // Create a new node.
        Node newNode = new Node(newNodeValue);

        Node currentNext = currentNode.getNext();

        // For new node:
        // "Next" points to the current next.
        newNode.setNext(currentNext);
        // "Previous" points to the current.
        newNode.setPrev(currentNode);

        // For current node:
        // "Next" points to the new node.
        currentNode.setNext(newNode);

        // For the original "next" node, "previous" points to the new node.
        currentNext.setPrev(newNode);

        // Set current node to point at the one we inserted.
        currentNode = newNode;
    }

    public Integer removeCurrentNode() {
        // We'll return the value of this node after removing.
        Integer returnValue = currentNode.getValue();

        // The "next" of "previous" should now point to "next" of "current"
        Node currentNext = currentNode.getNext();
        Node currentPrev = currentNode.getPrev();

        currentNext.setPrev(currentPrev);
        currentPrev.setNext(currentNext);

        // Set the "current" node to the node to the right of the one we removed.
        currentNode = currentNext;

        return returnValue;
    }

    public String printList() {
        // Start at the rootNode and iterate through the whole list printing all the values.
        // Highlight the currentNode.
        String output = "";

        // Start at the root node.
        Node nodeToPrint = rootNode;
        // Print the root node first.
        output += printNode(nodeToPrint);
        // Index forward.
        nodeToPrint = nodeToPrint.getNext();

        // Iterate through the list printing everything, but stop when we reach the root node again.
        while (!(nodeToPrint == rootNode)) {
            output += printNode(nodeToPrint);
            nodeToPrint = nodeToPrint.getNext();
        }
        return output;
    }

    private boolean isCurrentNode(Node node) {
        boolean isCurrentNode = (node == currentNode);
        return isCurrentNode;
    }

    private String printNode(Node node) {
        String output = "";

        String SPACE = " ";
        String FIRST_CHAR = (isCurrentNode(node)) ? "(" : SPACE;
        String LAST_CHAR = (isCurrentNode(node)) ? ")" : SPACE;

        output = FIRST_CHAR + node.getValue() + LAST_CHAR;

        return output;
    }

    public Node getRootNode() {
        return rootNode;
    }

    private void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    public Node getCurrentNode() {
        return currentNode;
    }

    private void setCurrentNode(Node currentNode) {
        this.currentNode = currentNode;
    }
}
