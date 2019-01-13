package org.jgoeres.adventofcode.Day22.tests;

import org.jgoeres.adventofcode.Day22.RunDay22;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Day22Test {
    @Test
    public void testExample22A() {
        int expectedResult = 114;   // total risk level of target area.
        int result = RunDay22.problem22A(10, 10, 510); // run using puzzle values in the class.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testExample22B() {
        int expectedResult = 45;   // minutes to reach the target.
        int result = RunDay22.problem22B(10, 10, 510, 16, 16); // run using puzzle values in the class.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testJustin22A() {
        int expectedResult = 11462;   // total risk level of target area.
        int result = RunDay22.problem22A(); // run using puzzle values in the class.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testJustin22B() {
        /* Justin's Puzzle Inputs */
        final int DEPTH = 7863;
        final int TARGET_X = 14;
        final int TARGET_Y = 760;

        int expectedResult = 1054;   // minutes to reach the target.
        // run using puzzle values in the class.
        int result = RunDay22.problem22B(TARGET_X, TARGET_Y, DEPTH, TARGET_X + 24, TARGET_Y + 1); // Add a bit in X for extra pathing space.
        assertEquals("Outcome is " + result, expectedResult, result);
    }

}
