package org.jgoeres.adventofcode.Day19;

public class RunDay19 {
    static final String DEFAULT_PATH_TO_INPUTS = "day19/input.txt";
    static Program program;

    public static void problem19A() {
        problem19A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem19A(String pathToInputs) {
        /*
        What value is left in register 0 when the
        background process halts?

        */
        System.out.println("=== DAY 19A ===");

        program = new Program(pathToInputs);
        CPU cpu = new CPU(program.ip); // Create a CPU with the instruction pointer binding of our program.

        while (cpu.ipIsValid(program)) {
            //for (CodeOperation op : program.getProgramCode()) {     // Run the whole program
            CodeOperation op = program.getProgramCode().get(cpu.ip);
            cpu.execute(op);
        }

        int haltValue = cpu.getMemory().getRegisterValue(Register.REGISTER_0);
        System.out.println("Value in register 0 at HALT:\t" + haltValue);

        return haltValue;
        // Answer:
        // Value in register 0 at HALT:	984
    }

    public static void problem19B() {
        problem19B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem19B(String pathToInputs) {
        /*
        Problem description
        */
        System.out.println("=== DAY 19B ===");
        return 0;
    }
}