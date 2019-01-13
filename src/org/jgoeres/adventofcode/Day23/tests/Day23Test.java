package org.jgoeres.adventofcode.Day23.tests;

import org.jgoeres.adventofcode.Day23.RunDay23;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day23Test {
    @Test
    public void testExample23A() {
        String pathToInputs = "day23/example.txt";
        int expectedResult = 7;   // value in register 0 at HALT.
        int result = RunDay23.problem23A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testJustin23A() {
        String pathToInputs = "day23/input.txt";
        int expectedResult = 588;   // value in register 0 at HALT.
        int result = RunDay23.problem23A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }
}
