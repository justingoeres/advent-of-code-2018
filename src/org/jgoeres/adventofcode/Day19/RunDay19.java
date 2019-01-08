package org.jgoeres.adventofcode.Day19;

import static org.jgoeres.adventofcode.Day19.Register.REGISTER_0;

public class RunDay19 {
    static final String DEFAULT_PATH_TO_INPUTS = "day19/input.txt";
    static Program program;
    static CPU cpu;

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = true;

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
            String output = null;

            boolean print = false;
            if (DEBUG_PART_A_PRINT_PROGRESS || cpu.ip == 7) {
                output = i + ":\t" + cpu.ip + "\t" + cpu.getMemory().toString() + "\t";
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
            String output = null;

            if (DEBUG_PART_B_PRINT_PROGRESS) {
                output = i + ":\t" + cpu.ip + "\t" + cpu.getMemory().toString() + "\t";
            }

            cpu.execute(op);

            if (DEBUG_PART_B_PRINT_PROGRESS) {
                output += op.toString() + "\t" + cpu.getMemory().toString();
                System.out.println(output);
            }

            i++;

        }
        System.out.println(i);
        int haltValue = cpu.getMemory().getRegisterValue(REGISTER_0);
        System.out.println("Value in register 0 at HALT:\t" + haltValue);

        return haltValue;
        // Answer:
        // 10,982,400

        /* EXPLANATION:

           The input code, reinterpreted to pseudocode, looks like this:

                // After setup finishes
                b = 983 (part a)
                b = 10551383 (part b)

                // Then
                for (c = 1 ; c < b ; c++) {
                    for (e = 1 ; e < b ; e++) {
                        if (c * e == b) {
                            a += c
                        }
                    }
                }

            The output of this code is to produce the SUM OF ALL THE PRIME FACTORS OF B (including 1).

            For Part A:
                983 = 983 * 1
            So 983 + 1 = 984

            For Part B:
                10551383 = 43 * 59 * 4159
            Which gives 10551383    = 1 * 10,551,383
                                    = 43 * 245,381
                                    = 59 * 178,837
                                    = 4,159 * 2,537
            So 1 + 43 + 59 + 4159 + 2537 + 178837 + 245381 + 10551383 = 10,982,400

         */
    }
}