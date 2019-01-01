package org.jgoeres.adventofcode.Day16;

public class RunDay16 {
    static final String DEFAULT_PATH_TO_INPUTS = "day16/input.txt";

    public static void problem16A() {
        problem16A(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem16A(String pathToInputs) {
        /*
        Ignoring the opcode numbers, how many codeSamples in your puzzle input behave like three or more opcodes?
        */
        System.out.println("=== DAY 16A ===");

        final boolean DEBUG_PRINT_MATCH_INFO = false;

        Samples samples = new Samples(pathToInputs);

        CPU cpu = new CPU();

        String[] opCodeNames = {"addr", "addi", "mulr", "muli",
                "banr", "bani", "borr", "bori", "setr", "seti",
                "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr"};
        IOpCode[] OpCodes = new IOpCode[]{
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.addr(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.addi(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.mulr(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.muli(A, B, C);
                    }
                },


                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.banr(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.bani(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.borr(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.bori(A, B, C);
                    }
                },


                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.setr(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.seti(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.gtir(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.gtri(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.gtrr(A, B, C);
                    }
                },


                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.eqir(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.eqri(A, B, C);
                    }
                },
                new IOpCode() {
                    @Override
                    public void execute(int A, int B, int C) {
                        cpu.eqrr(A, B, C);
                    }
                },
        };
        int totalMatchSamples = 0; // tally how many samples match at least 3 opcodes.
        int sampleNum = 0;
        for (CodeSample sample : samples.getCodeSamples()) { // check every sample we have
            int numMatches = 0; // Count how many opcodes this matches.
            int opCodeNum = 0;
            for (IOpCode opCode : OpCodes) {
                cpu.copyToMemory(sample.getMemoryBefore());

                // Execute this opCode
                opCode.execute(sample.getCodeOperation().getInputA(),
                        sample.getCodeOperation().getInputB(),
                        sample.getCodeOperation().getOutputC());

                // Test the resulting memory to see if it matches the expected "After"
                boolean matches = cpu.getMemory().equals(sample.getMemoryAfter());

                if (matches) {
                    numMatches++; // increment our counter.

                    if (DEBUG_PRINT_MATCH_INFO) {
                        String output = sampleNum+": Before:\t" + sample.getMemoryBefore().toString();
                        output += "\tOperation:\t" + opCodeNames[opCodeNum] + "\t"
                                + sample.getCodeOperation().getInputA() + " "
                                + sample.getCodeOperation().getInputB() + " "
                                + sample.getCodeOperation().getOutputC() + "\t"
                                + "After:\t" + cpu.getMemory().toString();
                        System.out.println(output);
                    }


                    if (numMatches >= 3) { // If we've matched three opcodes...
                        // Increment our total count and stop looking!
                        if(DEBUG_PRINT_MATCH_INFO){
                            System.out.println("^^^^^^^^ THREE MATCHES ^^^^^^^^");
                        }
                        totalMatchSamples++;
                        break;
                    }
                }
                opCodeNum++;
            }
            sampleNum++;
        }

        System.out.println("Number of Code Samples matching 3 or more opcodes:\t" + totalMatchSamples
                + " out of " + samples.getCodeSamples().size());

        // Answer: 547
        return 0;
    }


    public static void problem16B() {
        problem16B(DEFAULT_PATH_TO_INPUTS);
    }

    public static int problem16B(String pathToInputs) {
        /*
        Problem Description
        */
        System.out.println("=== DAY 16B ===");

        return 0;
    }
}
