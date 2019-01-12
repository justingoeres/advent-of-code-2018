package org.jgoeres.adventofcode.Day22.tests;

import org.jgoeres.adventofcode.Day22.RunDay22;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day22Test {
    @Test
    public void testExample22A() {
        int expectedResult = 114 ;   // total risk level of target area.
        int result = RunDay22.problem22A(10,10,510); // run using puzzle values in the class.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testJustin22A() {
        int expectedResult = 11462 ;   // total risk level of target area.
        int result = RunDay22.problem22A(); // run using puzzle values in the class.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testExample22B() {
        int expectedResult = 45 ;   // minutes to reach the target.
        int result = RunDay22.problem22B(10,10,510); // run using puzzle values in the class.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

}
