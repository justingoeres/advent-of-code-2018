package org.jgoeres.adventofcode.Day04;

import org.jgoeres.adventofcode.Day03.FabricService;

import java.lang.reflect.Array;
import java.util.*;

public class RunDay4 {
    static String pathToInputs = "day04/input.txt";
    static GuardService guardService = new GuardService(pathToInputs);

    public static void problem4A() {
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

        // Find the guard with the most minutes sleeping.
        Integer maxMinutes = 0;
        String maxGuardId = null;
        for (Map.Entry<String, ArrayList<GuardDutyInterval>> guard
                : guardService.getSchedulesByGuard().entrySet()) {
            Integer totalMinutes = 0;
            for (GuardDutyInterval interval : guard.getValue()) {
                totalMinutes += interval.getDuration();
            }
//            System.out.println("Guard #" + guard.getKey() + "\t\t" + totalMinutes + "\tminutes");
            if (totalMinutes > maxMinutes) {
                maxMinutes = totalMinutes;
                maxGuardId = guard.getKey();
            }
        }

        // Find how long the guard spent asleep at each minute of the hour.
        ArrayList<GuardDutyInterval> guardSchedule = guardService.getSchedulesByGuard().get(maxGuardId);
        HashMap<Integer, Integer> minutesOfHour = new HashMap<>();
        for (GuardDutyInterval interval : guardSchedule) {
            // Figure out all the individual minutes the guard is sleeping,
            // and count them up.
            Integer startMinute = interval.getAsleep().getMinutes();
            Integer endMinute = startMinute + interval.getDuration();

            for (Integer minute = startMinute; minute < endMinute; minute++) {
                Integer count = minutesOfHour.getOrDefault(minute, 0);
                // Increment the count for this minute.
                minutesOfHour.put(minute, count + 1);
            }

        }

        // Now find out *which minute* that guard was most often asleep.
        Integer maxMinute = null;
        Integer maxCount = 0;
        for (Map.Entry<Integer, Integer> minute : minutesOfHour.entrySet()) {
//            System.out.println(min.getKey() + ": " + min.getValue());
            if (minute.getValue() > maxCount) {
                maxCount = minute.getValue();
                maxMinute = minute.getKey();
            }
        }

        System.out.println("Sleepiest guard: #" + maxGuardId);
        System.out.println("Time Asleep: " + maxCount + " minutes at time " + maxMinute);
        System.out.println("Answer: " + maxGuardId + " * " + maxMinute + " = " + (Integer.parseInt(maxGuardId) * maxMinute));

//        Answer:
//        Sleepiest guard: #3323
//        Time Asleep: 15 minutes at time 33
//        Answer: 3323 * 33 = 109659
    }

    public static void problem4B() {
        System.out.println("=== DAY 4B ===");
        System.out.println("TODO");
    }
}
