package org.jgoeres.adventofcode.Day09;

public class CircularDoubleLinkedList {
    private Node rootNode;
    private Node currentNode;

    public CircularDoubleLinkedList(Integer rootNodeValue) {
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

    public void nextByN(Integer distance){
        for (int i = 0; i <  distance; i++) {
            currentNode = currentNode.getNext();
        }
    }

    public void prev() {
        Node prevNode = getCurrentNode().getPrev();
        setCurrentNode(prevNode);
    }

    public void prevByN(Integer distance){
        for (int i = 0; i <  distance; i++) {
            currentNode = currentNode.getPrev();
        }
    }

    public insertToRight(Integer newNodeValue) {
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
    }

    public removeCurrentNode(){
        // The "next" of "previous" should now point to "next" of "current"
        Node currentNext = currentNode.getNext();
        Node currentPrev = currentNode.getPrev();

        currentNext.setPrev(currentPrev);
        currentPrev.setNext(currentNext);
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
