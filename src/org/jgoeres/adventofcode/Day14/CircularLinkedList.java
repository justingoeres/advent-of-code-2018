package org.jgoeres.adventofcode.Day14;


public class CircularLinkedList {
    private Node rootNode;
    private Node elf1CurrentNode;
    private Node elf2CurrentNode;

    enum Elf {ELF1, ELF2}


    public CircularLinkedList(Integer rootNodeValue) {
        // Create the list with one node (the root).
        // Create the root node
        Node newRootNode = new Node(rootNodeValue);

        // Set it to point to itself (circularly)
        newRootNode.setNext(newRootNode);
        newRootNode.setPrev(newRootNode);

        setRootNode(newRootNode);
        setElfCurrentNode(Elf.ELF1, getRootNode()); // Set the "current" node to point at the root.
        setElfCurrentNode(Elf.ELF2, getRootNode()); // Set the "current" node to point at the root.
    }

    public void next(Elf elf) {
        Node nextNode = getElfCurrentNode(elf).getNext();
        setElfCurrentNode(elf, nextNode);
    }

    public void nextByN(Elf elf, Integer distance) {
        for (int i = 0; i < distance; i++) {
            next(elf);
        }
    }

    public void prev(Elf elf) {
        Node currentNode = getElfCurrentNode(elf);
        Node prevNode = currentNode.getPrev();
        setElfCurrentNode(elf, prevNode);
    }

    public void prevByN(Elf elf, Integer distance) {
        for (int i = 0; i < distance; i++) {
            prev(elf);
        }
    }

    public void addOnRightEnd(Integer newNodeValue) {
        // Create a new node.
        Node newNode = new Node(newNodeValue);

        Node currentEnd = rootNode.getPrev();

        // For new node:
        // "Next" points around to the root.
        newNode.setNext(rootNode);
        // "Previous" points to the current.
        newNode.setPrev(currentEnd);

        // For current node:
        // "Next" points to the new node.
        currentEnd.setNext(newNode);

        // For the original "next" node, "previous" points to the new node.
        rootNode.setPrev(newNode);
    }

//    public Integer removeCurrentNode() {
//        // We'll return the value of this node after removing.
//        Integer returnValue = currentNode.getValue();
//
//        // The "next" of "previous" should now point to "next" of "current"
//        Node currentNext = currentNode.getNext();
//        Node currentPrev = currentNode.getPrev();
//
//        currentNext.setPrev(currentPrev);
//        currentPrev.setNext(currentNext);
//
//        // Set the "current" node to the node to the right of the one we removed.
//        currentNode = currentNext;
//
//        return returnValue;
//    }

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

    private boolean isElfCurrentNode(Elf elf, Node node) {
        boolean isCurrentNode = (getElfCurrentNode(elf) == node);
        return isCurrentNode;
    }

    private String printNode(Node node) {
        String SPACE = " ";
        String FIRST_CHAR = (isElfCurrentNode(Elf.ELF1, node)) ? "(" : (isElfCurrentNode(Elf.ELF2, node)) ? "[" : SPACE;
        String LAST_CHAR = (isElfCurrentNode(Elf.ELF1, node)) ? ")" : (isElfCurrentNode(Elf.ELF2, node)) ? "]" : SPACE;

        String output = FIRST_CHAR + node.getValue() + LAST_CHAR;

        return output;
    }

    public Node getRootNode() {
        return rootNode;
    }

    private void setRootNode(Node rootNode) {
        this.rootNode = rootNode;
    }

    private void setElfCurrentNode(Elf elf, Node currentNode) {
        switch (elf) {
            case ELF1:
                this.elf1CurrentNode = currentNode;
                break;
            case ELF2:
                this.elf2CurrentNode = currentNode;
                break;
        }
    }

    private Node getElfCurrentNode(Elf elf) {
        switch (elf) {
            case ELF1:
                return elf1CurrentNode;
            case ELF2:
                return elf2CurrentNode;
        }
        return null;
    }

    public Integer getElfCurrentValue(Elf elf) {
        return getElfCurrentNode(elf).getValue();
    }
}
