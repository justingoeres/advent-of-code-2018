package org.jgoeres.adventofcode.Day16;

public class CodeSample {
    private Memory memoryBefore;
    private CodeOperation codeOperation;
    private Memory memoryAfter;

    public CodeSample(Memory memoryBefore, CodeOperation codeOperation, Memory memoryAfter) {
        this.memoryBefore = memoryBefore;
        this.codeOperation = codeOperation;
        this.memoryAfter = memoryAfter;
    }

    public Memory getMemoryBefore() {
        return memoryBefore;
    }

    public CodeOperation getCodeOperation() {
        return codeOperation;
    }

    public Memory getMemoryAfter() {
        return memoryAfter;
    }
}
