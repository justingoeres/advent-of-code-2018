package org.jgoeres.adventofcode.Day19;

import static org.jgoeres.adventofcode.Day19.Register.REGISTER_0;

public class RunDay19 {
    static final String DEFAULT_PATH_TO_INPUTS = "day19/input.txt";
    static Program program;
    static CPU cpu;

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
        cpu = new CPU(program.ip); // Create a CPU with the instruction pointer binding of our program.

        int i = 0;
        while (cpu.ipIsValid(program)) {
            //for (CodeOperation op : program.getProgramCode()) {     // Run the whole program
            CodeOperation op = program.getProgramCode().get(cpu.ip);
            String output = i + ":\t" + cpu.ip + "\t" + cpu.getMemory().toString() + "\t";

            cpu.execute(op);
            output += op.toString() + "\t" + cpu.getMemory().toString();

            System.out.println(output);
//            System.out.println(i+":\t"+cpu.getMemory().toString());
            i++;

        }

        int haltValue = cpu.getMemory().getRegisterValue(REGISTER_0);
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
        A new background process immediately spins up in its place.
        It appears identical, but on closer inspection, you notice
        that this time, register 0 started with the value 1.

        What value is left in register 0 when this new background
        process halts?
        */
        System.out.println("=== DAY 19B ===");

        // We can reuse the program we loaded in Part A.
        cpu.reset();    // Reset the CPU.

        // Set register 0 to 1.
        cpu.getMemory().setRegisterValue(REGISTER_0, 1);
        int i = 0;
        while (cpu.ipIsValid(program)) {
            //for (CodeOperation op : program.getProgramCode()) {     // Run the whole program
            CodeOperation op = program.getProgramCode().get(cpu.ip);
            String output = i + ":\t" + cpu.ip + "\t" + cpu.getMemory().toString() + "\t";

            cpu.execute(op);
            output += op.toString() + "\t" + cpu.getMemory().toString();

            System.out.println(output);
            i++;

        }
        System.out.println(i);
        int haltValue = cpu.getMemory().getRegisterValue(REGISTER_0);
        System.out.println("Value in register 0 at HALT:\t" + haltValue);

        return haltValue;
    }
}