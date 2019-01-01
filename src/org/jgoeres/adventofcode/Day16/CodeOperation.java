package org.jgoeres.adventofcode.Day16;

public class CodeOperation {
    private int opCode;
    private int inputA;
    private int inputB;
    private int outputC;

    public CodeOperation(int opCode, int inputA, int inputB, int outputC) {
        this.opCode = opCode;
        this.inputA = inputA;
        this.inputB = inputB;
        this.outputC = outputC;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getInputA() {
        return inputA;
    }

    public int getInputB() {
        return inputB;
    }

    public int getOutputC() {
        return outputC;
    }
}
