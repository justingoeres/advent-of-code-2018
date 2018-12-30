package org.jgoeres.adventofcode.Day15.tests;

import static org.junit.Assert.assertEquals;

import org.jgoeres.adventofcode.Day15.RunDay15;
import org.junit.Test;

public class Day15Test {
    @Test
    public void testShaneMcC() {
        String pathToInputs = "day15/examples/aoc-2018/15/input.txt";
        int expectedResult = 197025;
        /*
        Outcome: 197025
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testBattle1() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/battle1/input.txt";
        int expectedResult = 27730;
        /*
        Part 1: 27730 (590 x 47)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testBattle2() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/battle2/input.txt";
        int expectedResult = 36334;
        /*
        Part 1: 36334 (982 x 37)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testBattle3() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/battle3/input.txt";
        int expectedResult = 39514;
        /*
        Part 1: 39514 (859 x 46)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testBattle4() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/battle4/input.txt";
        int expectedResult = 27755;
        /*
        Part 1: 27755 (793 x 35)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testBattle5() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/battle5/input.txt";
        int expectedResult = 28944;
        /*
        Part 1: 28944 (536 x 54)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testBattle6() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/battle6/input.txt";
        int expectedResult = 18740;
        /*
        Part 1: 18740 (937 x 20)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testMoveLeft() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/moveLeft/input.txt";
        int expectedResult = 10030;
        /*
        Part 1: 10030 (295 x 34)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testMovement() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/movement/input.txt";
        int expectedResult = 27828;
        /*
        Part 1: 27828 (1546 x 18)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testMoveRight() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/moveRight/input.txt";
        int expectedResult = 10234;
        /*
        Part 1: 10234 (301 x 34)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit1() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit1/input.txt";
        int expectedResult = 13400;
        /*
        Outcome:    13400
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit2() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit2/input.txt";
        int expectedResult = 13987;
        /*
        Outcome: Part 1: 13987 (197 x 71)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit3() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit3/input.txt";
        int expectedResult = 10325;
        /*
        Outcome: Part 1: 10325 (295 x 35)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit4() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit4/input.txt";
        int expectedResult = 10804;
        /*
        Outcome: Part 1: 10804 (292 x 37)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit5() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit5/input.txt";
        int expectedResult = 10620;
        /*
        Outcome: Part 1: 10620 (295 x 36)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit6() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit6/input.txt";
        int expectedResult = 16932;
        /*
        Outcome: Part 1: 16932 (498 x 34)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit7() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit7/input.txt";
        int expectedResult = 10234;
        /*
        Outcome: Part 1: 10234 (301 x 34)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit8() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit8/input.txt";
        int expectedResult = 10430;
        /*
        Outcome: Part 1: 10430 (298 x 35)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit9() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit9/input.txt";
        int expectedResult = 12744;
        /*
        Outcome: Part 1: 12744 (531 x 24)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testReddit10() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/reddit10/input.txt";
        int expectedResult = 14740;
        /*
        Outcome: Part 1: 14740 (737 x 20)
         */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }

    @Test
    public void testWall() {
        String pathToInputs = "day15/examples/aoc-2018/15/tests/wall/input.txt";
        int expectedResult = 18468;
        /*
        Outcome: Part 1: 18468 (486 x 38)
        */
        int result = RunDay15.problem15A(pathToInputs);
        assertEquals("Outcome is "+result,expectedResult,result);
    }
}
