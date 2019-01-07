package org.jgoeres.adventofcode.Day19;

public class CPU {
    int ip = 0; // instruction pointer starts at zero.
    int ipBoundRegister;
    Memory memory = new Memory();

    private IOpCode[] opCodes = opCodeFunctor(this);

    public CPU(int ipBoundRegister) {
        this.ipBoundRegister = ipBoundRegister;
    }

    public void reset(){
        memory.clear(); // clear the memory
        ip = 0; // reset the instruction pointer.
    }
    public void copyToMemory(Memory sourceMemory) {
        for (int i = 0; i < memory.getRegisters().length; i++) {
            memory.setRegisterValue(i,sourceMemory.getRegisterValue(i));
        }
    }

    public void execute(CodeOperation op){
        /*
        When the instruction pointer is bound to a register,
            1) its value is written to that register just before each instruction is executed,
            2) and the value of that register is written back to the instruction
                pointer immediately after each instruction finishes execution.
            3) Afterward, move to the next instruction by adding one to the
               instruction pointer, even if the value in the instruction pointer
               was just updated by an instruction.
        (Because of this, instructions
        must effectively set the instruction pointer to the instruction
        before the one they want executed next.)
         */

        // Write ip value to bound register.
        memory.setRegisterValue(ipBoundRegister,ip);

        // Execute the next instruction.
        opCodes[op.getOpCode().ordinal()].execute(op.getInputA(),    // execute the operation
                op.getInputB(),
                op.getOutputC());

        // Write bound register back to ip.
        setIp(memory.getRegisterValue(ipBoundRegister));

        // Finally, increment the ip
        incrementIp();
    }

    public boolean ipIsValid(Program program){
        // Does the ip point to something inside the program?
        return (ip < program.getProgramCode().size());
    }

    public void setIp(int ip) {
        this.ip = ip;
    }

    public void incrementIp(int amount){
        ip+=amount;
    }

    public void incrementIp(){
        incrementIp(1);
    }

    public Memory getMemory() {
        return memory;
    }

    public void addr(int A, int B, int C) {
        /*
        addr (add register) stores into register C the result of adding register A and register B.
         */
        int result = memory.getRegisterValue(A) + memory.getRegisterValue(B);
        memory.setRegisterValue(C, result);
    }

    public void addi(int A, int B, int C) {
        /*
        addi (add immediate) stores into register C the result of adding register A and value B.
         */
        int result = memory.getRegisterValue(A) + B;
        memory.setRegisterValue(C, result);
    }

    public void mulr(int A, int B, int C) {
        /*
        mulr (multiply register) stores into register C the result of multiplying register A and register B.
         */
        int result = memory.getRegisterValue(A) * memory.getRegisterValue(B);
        memory.setRegisterValue(C, result);
    }

    public void muli(int A, int B, int C) {
        /*
        muli (multiply immediate) stores into register C the result of multiplying register A and value B.
         */
        int result = memory.getRegisterValue(A) * B;
        memory.setRegisterValue(C, result);
    }

    public void banr(int A, int B, int C) {
        /*
        banr (bitwise AND register) stores into register C the result of bitwise AND register A and register B.
         */
        int result = memory.getRegisterValue(A) & memory.getRegisterValue(B);
        memory.setRegisterValue(C, result);
    }

    public void bani(int A, int B, int C) {
        /*
        bani (bitwise AND immediate) stores into register C the result of bitwise AND register A and value B.
         */
        int result = memory.getRegisterValue(A) & B;
        memory.setRegisterValue(C, result);
    }

    public void borr(int A, int B, int C) {
        /*
        borr (bitwise OR register) stores into register C the result of bitwise OR register A and register B.
         */
        int result = memory.getRegisterValue(A) | memory.getRegisterValue(B);
        memory.setRegisterValue(C, result);
    }

    public void bori(int A, int B, int C) {
        /*
        bori (bitwise OR immediate) stores into register C the result of bitwise OR register A and value B.
         */
        int result = memory.getRegisterValue(A) | B;
        memory.setRegisterValue(C, result);
    }

    public void setr(int A, int B, int C) { // B is ignored
        /*
        setr (set register) copies the contents of register A into register C. (Input B is ignored.)
         */
        memory.setRegisterValue(C, memory.getRegisterValue(A));
    }

    public void seti(int A, int B, int C) { // B is ignored
        /*
        seti (set immediate) stores value A into register C. (Input B is ignored.)
         */
        memory.setRegisterValue(C, A);
    }

    public void gtir(int A, int B, int C) {
        /*
        gtir (greater-than immediate/register) sets register C to 1 if value A is greater than register B. Otherwise, register C is set to 0.
         */
        int result = ((A > memory.getRegisterValue(B)) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void gtri(int A, int B, int C) {
        /*
        gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
         */
        int result = ((memory.getRegisterValue(A) > B) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void gtrr(int A, int B, int C) {
        /*
        gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
         */
        int result = ((memory.getRegisterValue(A) > memory.getRegisterValue(B)) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void eqir(int A, int B, int C) {
        /*
        eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
         */
        int result = ((A == memory.getRegisterValue(B)) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void eqri(int A, int B, int C) {
        /*
        eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
         */
        int result = ((memory.getRegisterValue(A) == B) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void eqrr(int A, int B, int C) {
        /*
        eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
         */
        int result = ((memory.getRegisterValue(A) == memory.getRegisterValue(B)) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }


    private static IOpCode[] opCodeFunctor(CPU cpu) {
        return new IOpCode[]{
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
    }


}
