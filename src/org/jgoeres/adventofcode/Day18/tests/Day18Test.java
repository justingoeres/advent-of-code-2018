package org.jgoeres.adventofcode.Day18.tests;

import org.jgoeres.adventofcode.Day18.RunDay18;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day18Test {
    @Test
    public void testExample18A() {
        String pathToInputs = "day18/example.txt";
        int expectedResult = 1147;   // 37 * 31 = 1147
        int result = RunDay18.problem18A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testJustin18A() {
        String pathToInputs = "day18/input.txt";
        int expectedResult = 360720;   // 835 * 432 = 360720
        int result = RunDay18.problem18A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

}
