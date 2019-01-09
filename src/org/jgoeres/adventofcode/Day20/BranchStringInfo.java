package org.jgoeres.adventofcode.Day20;

public class BranchStringInfo {
    int openParenIndex;
    int pipeIndex;
    int closeParenIndex;

    public BranchStringInfo() {
    }

    public BranchStringInfo(int openParenIndex, int pipeIndex, int closeParenIndex) {
        this.openParenIndex = openParenIndex;
        this.pipeIndex = pipeIndex;
        this.closeParenIndex = closeParenIndex;
    }

}
