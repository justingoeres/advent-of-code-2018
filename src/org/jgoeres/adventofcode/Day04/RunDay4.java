package org.jgoeres.adventofcode.Day04;

import java.util.*;

public class RunDay4 {
    static String pathToInputs = "day04/input.txt";
    static GuardService guardService = new GuardService(pathToInputs);

    static Integer maxMinute = null;

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
        HashMap<Integer, Integer> minutesOfHour = guardService.calculateMinutesHistogram(maxGuardId);

        // Now find out *which minute* that guard was most often asleep.
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
//        Of all guards, which guard is most frequently asleep on the same minute?
//
//        In the example above, Guard #99 spent minute 45 asleep more than
//        any other guard or minute - three times in total.
//        (In all other cases, any guard spent any minute asleep at most twice.)
//
//        What is the ID of the guard you chose multiplied by the minute you chose?
//        (In the above example, the answer would be 99 * 45 = 4455.)

        Integer maxSleep = 0;
        maxMinute = 0;
        String maxGuardId = null;
        for (String guardId : guardService.getSchedulesByGuard().keySet()) {
            // For each guard, find how many times they were asleep on maxMinute.

            for (Map.Entry<Integer,Integer> minute : guardService.calculateMinutesHistogram(guardId).entrySet()) {
                // For each minute of the hour
                // minute will be null for any guards who never slept.
                if (minute != null && minute.getValue() > maxSleep) {
                    // Check if this is the most minutes anyone has slept on any minute.
                    maxMinute = minute.getKey();
                    maxSleep = minute.getValue();
                    maxGuardId = guardId;
                }
            }
        }
        System.out.println("Guard #" + maxGuardId + " slept " + maxSleep + " times on minute " + maxMinute);
        System.out.println("Answer: " + maxGuardId + " * " + maxMinute + " = " + (Integer.parseInt(maxGuardId) * maxMinute));

        // Answer:
//        Looking for sleepiest guard on minute 33
//        Guard #983 slept 18 times on minute 37
//        Answer: 983 * 37 = 36371

    }
}
