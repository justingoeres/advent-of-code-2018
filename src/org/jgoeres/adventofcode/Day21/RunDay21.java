package org.jgoeres.adventofcode.Day21;

import org.jgoeres.adventofcode.Day19.CPU;
import org.jgoeres.adventofcode.Day19.CodeOperation;
import org.jgoeres.adventofcode.Day19.Program;

import static org.jgoeres.adventofcode.Day19.Register.REGISTER_0;

public class RunDay21 {
    static final String DEFAULT_PATH_TO_INPUTS = "day21/input.txt";
    static Program program;
    static CPU cpu;

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = true;

    public static void problem21A() {
        problem21A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem21A(String pathToInputs) {
        /*
        What is the lowest non-negative integer value for 
        register 0 that causes the program to halt after 
        executing the fewest instructions? (Executing the 
        same instruction multiple times counts as multiple 
        instructions executed.)
        */
        System.out.println("=== DAY 21A ===");

        program = new Program(pathToInputs);
        cpu = new CPU(program.getIp()); // Create a CPU with the instruction pointer binding of our program.

        int i = 0;
        while (cpu.ipIsValid(program)) {
            //for (CodeOperation op : program.getProgramCode()) {     // Run the whole program
            CodeOperation op = program.getProgramCode().get(cpu.getIp());
            String output = null;

            boolean print = false;
            if (DEBUG_PART_A_PRINT_PROGRESS || cpu.getIp() == 7) {
                output = i + ":\t" + cpu.getIp() + "\t" + cpu.getMemory().toString() + "\t";
                print = true;
            }

            cpu.execute(op);

            if (DEBUG_PART_A_PRINT_PROGRESS || print) {
                output += op.toString() + "\t" + cpu.getMemory().toString();
                System.out.println(output);
//            System.out.println(i+":\t"+cpu.getMemory().toString());
            }
            i++;

        }

        int haltValue = cpu.getMemory().getRegisterValue(REGISTER_0);
        System.out.println("Value in register 0 at HALT:\t" + haltValue);

        return haltValue;
        // Answer:
        // Value in register 0 at HALT:	984    }
    }

    public static void problem21B() {
        problem21B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem21B(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 21B ===");
        return 0;
    }
}