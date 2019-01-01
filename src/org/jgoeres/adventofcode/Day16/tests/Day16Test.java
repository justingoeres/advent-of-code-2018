package org.jgoeres.adventofcode.Day16.tests;

import org.jgoeres.adventofcode.Day16.RunDay16;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day16Test {
    @Test
    public void testJustin16A() {
        String pathToInputs = "day16/input.txt";
        int expectedResult = 547;
        int result = RunDay16.problem16A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testJustin16B() {
        String pathToInputs = "day16/input.txt";
        int expectedResult = 582; // TBD
        int result = RunDay16.problem16B(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

}
