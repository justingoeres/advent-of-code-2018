package org.jgoeres.adventofcode.Day21.tests;

import org.jgoeres.adventofcode.Day21.RunDay21;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day21Test {

    @Test
    public void testJustin19A() {
        String pathToInputs = "day21/input.txt";
        int expectedResult = 6132825;   // value in register 0 at HALT.
        int result = RunDay21.problem21A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testJustin19B() {
        String pathToInputs = "day21/input.txt";
        int expectedResult = 8307757;   // value in register 0 at HALT.
        int result = RunDay21.problem21B(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }

}
