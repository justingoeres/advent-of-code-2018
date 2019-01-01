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

        Samples samples = new Samples(pathToInputs);
        // TODO: Write comparisons to test each sample against every opcode type and count the number of matches.

        CPU cpu = new CPU();
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
        CodeSample sample = samples.getCodeSamples().get(0);
        for (IOpCode opCode : OpCodes) {
            cpu.setMemory(sample.getMemoryBefore());

            // Execute this opCode
            opCode.execute(sample.getCodeOperation().getInputA(),
                    sample.getCodeOperation().getInputB(),
                    sample.getCodeOperation().getOutputC());

            // Test the resulting memory to see if it matches the expected "After"
            boolean matches = cpu.getMemory().equals(sample.getMemoryAfter());

            System.out.println("CPU:\t"+cpu.getMemory().toString()+"\tExpected:\t"+sample.getMemoryAfter().toString());
            if (matches) {
                System.out.println("Result matches!");
            }
        }


        System.out.println(samples);
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
