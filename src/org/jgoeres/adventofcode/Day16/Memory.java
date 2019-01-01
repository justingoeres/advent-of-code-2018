package org.jgoeres.adventofcode.Day16;

import java.util.Arrays;

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

    public int[] getRegisters() {
        return registers;
    }

    public void setRegisterValue(Register register, int value) {
        registers[register.asInt()] = value;
    }

    public void setRegisterValue(int registerAsInt, int value) {
        registers[registerAsInt] = value;
    }

    @Override
    public String toString() {
        String registerValues = "";
        for (int registerValue : registers) {
            registerValues += registerValue + " ";
        }
        return "Memory{" + registerValues + '}';
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;

        // check if the argument is of the
        // type Memory by comparing the classes
        // of the passed argument and this object.
        // if(!(obj instanceof Memory)) return false; ---> avoid.
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        // type casting of the argument.
        Memory memory = (Memory) obj;

        // comparing the state of argument with
        // the state of 'this' Object.

        boolean result = Arrays.equals(this.registers, memory.registers);
        return result;
    }
}
