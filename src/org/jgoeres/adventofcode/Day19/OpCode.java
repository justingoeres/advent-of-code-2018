package org.jgoeres.adventofcode.Day19;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

enum OpCode {
    addr("addr"),
    addi("addi"),
    mulr("mulr"),
    muli("muli"),
    banr("banr"),
    bani("bani"),
    borr("borr"),
    bori("bori"),
    setr("setr"),
    seti("seti"),
    gtir("gtir"),
    gtri("gtri"),
    gtrr("gtrr"),
    eqir("eqir"),
    eqri("eqri"),
    eqrr("eqrr");


    private String name;

    private static final Map<String, OpCode> ENUM_MAP;

    OpCode(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

// Build an immutable map of String name to enum pairs.
// Any Map impl can be used.

    static {
        Map<String, OpCode> map = new ConcurrentHashMap<String, OpCode>();
        for (OpCode instance : OpCode.values()) {
            map.put(instance.getName(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static OpCode get(String name) {
        return ENUM_MAP.get(name);
    }
}