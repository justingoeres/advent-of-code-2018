package org.jgoeres.adventofcode.Day16;

public class CPU {
    Memory memory = new Memory();

    public void setMemory(Memory memory) {
        this.memory = memory;
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
        int result = (A > memory.getRegisterValue(B) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void gtri(int A, int B, int C) {
        /*
        gtri (greater-than register/immediate) sets register C to 1 if register A is greater than value B. Otherwise, register C is set to 0.
         */
        int result = (memory.getRegisterValue(A) > B ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void gtrr(int A, int B, int C) {
        /*
        gtrr (greater-than register/register) sets register C to 1 if register A is greater than register B. Otherwise, register C is set to 0.
         */
        int result = (memory.getRegisterValue(A) > memory.getRegisterValue(B) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void eqir(int A, int B, int C) {
        /*
        eqir (equal immediate/register) sets register C to 1 if value A is equal to register B. Otherwise, register C is set to 0.
         */
        int result = (A == memory.getRegisterValue(B) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void eqri(int A, int B, int C) {
        /*
        eqri (equal register/immediate) sets register C to 1 if register A is equal to value B. Otherwise, register C is set to 0.
         */
        int result = (memory.getRegisterValue(A) == B ? 1 : 0);
        memory.setRegisterValue(C, result);
    }

    public void eqrr(int A, int B, int C) {
        /*
        eqrr (equal register/register) sets register C to 1 if register A is equal to register B. Otherwise, register C is set to 0.
         */
        int result = (memory.getRegisterValue(A) == memory.getRegisterValue(B) ? 1 : 0);
        memory.setRegisterValue(C, result);
    }



}
