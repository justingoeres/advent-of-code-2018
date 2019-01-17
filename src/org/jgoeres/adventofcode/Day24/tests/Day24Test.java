package org.jgoeres.adventofcode.Day24.tests;

import org.jgoeres.adventofcode.Day24.RunDay24;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day24Test {
    @Test
    public void testExample24A() {
        String pathToInputs = "day24/example.txt";
        int expectedResult = 5216;   // Bots in range of strongest signal nanobot
        int result = RunDay24.problem24A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }
}
