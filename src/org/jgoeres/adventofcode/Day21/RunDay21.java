package org.jgoeres.adventofcode.Day21;

import org.jgoeres.adventofcode.Day19.CPU;
import org.jgoeres.adventofcode.Day19.CodeOperation;
import org.jgoeres.adventofcode.Day19.Program;

import java.util.HashSet;
import java.util.TreeSet;

import static org.jgoeres.adventofcode.Day19.Register.REGISTER_0;
import static org.jgoeres.adventofcode.Day19.Register.REGISTER_3;

public class RunDay21 {
    static final String DEFAULT_PATH_TO_INPUTS = "day21/input.txt";
    static Program program;
    static CPU cpu;

    static final boolean DEBUG_PART_A_PRINT_PROGRESS = false;
    static final boolean DEBUG_PART_B_PRINT_PROGRESS = false;

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

        final int HALT_VALUE = 6132825;
        cpu.getMemory().setRegisterValue(REGISTER_0, HALT_VALUE);

        int i = 0;
        while (cpu.ipIsValid(program)) {
            //for (CodeOperation op : program.getProgramCode()) {     // Run the whole program
            CodeOperation op = program.getProgramCode().get(cpu.getIp());
            String output = null;

            if (DEBUG_PART_A_PRINT_PROGRESS) {
                output = i + ":\t" + cpu.getIp() + "\t" + cpu.getMemory().toString() + "\t";
            }

            cpu.execute(op);

            if (DEBUG_PART_A_PRINT_PROGRESS) {
                output += op.toString() + "\t" + cpu.getMemory().toString();
                System.out.println(output);
            }
            i++;
        }

        int haltValue = cpu.getMemory().getRegisterValue(REGISTER_0);
        System.out.println("Register 0 value of " + HALT_VALUE + " halts after " + i + " instructions.");

        return haltValue;
        // Answer:
        // Register 0 value of 6132825 halts after 1848 instructions.

        /*
        Trace of registers & program execution leading up to the final check of (A==D) on line 1847.
        You can see that the value of D is 6132825, so that's what we set A to at the start.
        1835:	23	[ 6132825	1	256	8322634	1	22	 ]	seti	25	8	5	[ 6132825	1	256	8322634	1	25	 ]
        1836:	26	[ 6132825	1	256	8322634	1	25	 ]	setr	1	4	2	[ 6132825	1	1	8322634	1	26	 ]
        1837:	27	[ 6132825	1	1	8322634	1	26	 ]	seti	7	5	5	[ 6132825	1	1	8322634	1	7	 ]
        1838:	8	[ 6132825	1	1	8322634	1	7	 ]	bani	2	255	1	[ 6132825	1	1	8322634	1	8	 ]
        1839:	9	[ 6132825	1	1	8322634	1	8	 ]	addr	3	1	3	[ 6132825	1	1	8322635	1	9	 ]
        1840:	10	[ 6132825	1	1	8322635	1	9	 ]	bani	3	16777215	3	[ 6132825	1	1	8322635	1	10	 ]
        1841:	11	[ 6132825	1	1	8322635	1	10	 ]	muli	3	65899	3	[ 6132825	1	1	-1302490023	1	11	 ]
        1842:	12	[ 6132825	1	1	-1302490023	1	11	 ]	bani	3	16777215	3	[ 6132825	1	1	6132825	1	12	 ]
        1843:	13	[ 6132825	1	1	6132825	1	12	 ]	gtir	256	2	1	[ 6132825	1	1	6132825	1	13	 ]
        1844:	14	[ 6132825	1	1	6132825	1	13	 ]	addr	1	5	5	[ 6132825	1	1	6132825	1	15	 ]
        1845:	16	[ 6132825	1	1	6132825	1	15	 ]	seti	27	8	5	[ 6132825	1	1	6132825	1	27	 ]
        1846:	28	[ 6132825	1	1	6132825	1	27	 ]	eqrr	3	0	1	[ 6132825	1	1	6132825	1	28	 ]
        1847:	29	[ 6132825	1	1	6132825	1	28	 ]	addr	1	5	5	[ 6132825	1	1	6132825	1	30	 ]
         */
    }

    public static void problem21B() {
        problem21B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem21B(String pathToInputs) {
        /*
        What is the lowest non-negative integer value for register 0 that causes
        the program to halt after executing the most instructions?
        (The program must actually halt; running forever does not count as halting.)
        */
        System.out.println("=== DAY 21B ===");
        cpu.reset();

//        final int HALT_VALUE = 3453754;
//        cpu.getMemory().setRegisterValue(REGISTER_0, HALT_VALUE);

        TreeSet<Integer> haltValues = new TreeSet<>();
//        haltValues.add(HALT_VALUE);
        int i = 0;
        int prevHaltValue = 0;
        while (cpu.ipIsValid(program)) {
            CodeOperation op = program.getProgramCode().get(cpu.getIp());
            String output = null;

            boolean print = false;
            if (DEBUG_PART_B_PRINT_PROGRESS && cpu.getIp() == 29) {
                output = i + ":\t" + cpu.getIp() + "\t" + cpu.getMemory().toString() + "\t";
                print = true;
            }

            cpu.execute(op);

            if (DEBUG_PART_B_PRINT_PROGRESS && print) {
                output += op.toString() + "\t" + cpu.getMemory().toString();
                System.out.println(output);
                print = false;
            }

            if (cpu.getIp() == 29) { // If we're at the halt check instruction (which is #28)
                int haltValue = cpu.getMemory().getRegisterValue(REGISTER_3);
                if (haltValues.contains(haltValue)) {
                    System.out.println(i + "\t" + haltValue + " << REPEAT");
                    break;
                } else {
                    haltValues.add(haltValue);
                    prevHaltValue = haltValue;
                }
            }
            i++;
        }
        System.out.println("Halt value immediately before the repeated value:\t"+ prevHaltValue);
        return prevHaltValue;
        // Answer:
        // Halt value immediately before the repeated value: 8307757
    }
}