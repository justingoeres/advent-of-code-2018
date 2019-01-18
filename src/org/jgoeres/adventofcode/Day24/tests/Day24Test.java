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

    @Test
    public void testJustin24A() {
        String pathToInputs = "day24/input.txt";
        int expectedResult = 10723;   // Bots in range of strongest signal nanobot
        int result = RunDay24.problem24A(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }

    @Test
    public void testExample24B() {
        String pathToInputs = "day24/example.txt";
        int expectedResult = 51;   // Bots in range of strongest signal nanobot
        int result = RunDay24.problem24B(pathToInputs);
        assertEquals("Outcome is " + result, expectedResult, result);
    }

//    @Test
//    public void testExample224B() {
//        String pathToInputs = "day24/example2.txt";
//        int expectedResult = 51;   // Bots in range of strongest signal nanobot
//        int result = RunDay24.problem24B(pathToInputs);
//        assertEquals("Outcome is " + result, expectedResult, result);
//    }

}
