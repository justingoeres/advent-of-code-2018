package org.jgoeres.adventofcode.Day19;

public class CodeOperation {
    private OpCode opCode;
    private int inputA;
    private int inputB;
    private int outputC;

    public CodeOperation(OpCode opCode, int inputA, int inputB, int outputC) {
        this.opCode = opCode;
        this.inputA = inputA;
        this.inputB = inputB;
        this.outputC = outputC;
    }

    public OpCode getOpCode() {
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
