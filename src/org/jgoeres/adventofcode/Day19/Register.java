package org.jgoeres.adventofcode.Day19;

public enum Register {
    REGISTER_0,
    REGISTER_1,
    REGISTER_2,
    REGISTER_3;

    public int asInt(){
        return this.ordinal();
    }

    public static Register intToEnum(int intValue){
        return Register.values()[intValue];
    }
}
