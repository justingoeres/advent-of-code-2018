package org.jgoeres.adventofcode.Day16;

public class Memory {
    private int[] registers = new int[Register.values().length];

    public int getRegisterValue(Register register){
        int value = registers[register.asInt()];
        return value;
    }

    public void setRegisterValue(Register register, int value) {
        registers[register.asInt()] = value;
    }
}
