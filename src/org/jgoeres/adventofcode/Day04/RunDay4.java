package org.jgoeres.adventofcode.Day04;

import org.jgoeres.adventofcode.Day03.FabricService;

import java.util.Map;
import java.util.TreeMap;

public class RunDay4 {
    static String pathToInputs = "day04/input.txt";
    static GuardService guardService = new GuardService(pathToInputs);

    public static void problem4A(){
        System.out.println("=== DAY 4A ===");

//        Find the guard that has the most minutes asleep.
//        What minute does that guard spend asleep the most?
//
//        In the example above, Guard #10 spent the most minutes asleep,
//        a total of 50 minutes (20+25+5), while Guard #99 only slept for
//        a total of 30 minutes (10+10+10). Guard #10 was asleep most during minute 24
//        (on two days, whereas any other minute the guard was asleep was only seen on one day).
//
//        What is the ID of the guard you chose multiplied by the minute you chose?
//        (In the above example, the answer would be 10 * 24 = 240.)

        guardService.buildGuardSchedules();
    }

    public static void problem4B(){
        System.out.println("=== DAY 4B ===");
        System.out.println("TODO");
    }
}
