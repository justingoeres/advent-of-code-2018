package org.jgoeres.adventofcode.Day19.tests;

import org.jgoeres.adventofcode.Day19.RunDay19;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day19Test {

    @Test
    public void testExample19A() {
        String pathToInputs = "day19/example.txt";
        int expectedResult = 6;   // value in register 0 at HALT.
        int result = RunDay19.problem19A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testJustin19A() {
        String pathToInputs = "day19/input.txt";
        int expectedResult = 984;   // value in register 0 at HALT.
        int result = RunDay19.problem19A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }
}
