package org.jgoeres.adventofcode.Day16;

public class Memory {
    private int[] registers = new int[Register.values().length];

    public int getRegisterValue(Register register) {
        int value = registers[register.asInt()];
        return value;
    }

    public int getRegisterValue(int registerAsInt) {
        int value = registers[registerAsInt];
        return value;
    }


    public void setRegisterValue(Register register, int value) {
        registers[register.asInt()] = value;
    }

    public void setRegisterValue(int registerAsInt, int value) {
        registers[registerAsInt] = value;
    }

}
