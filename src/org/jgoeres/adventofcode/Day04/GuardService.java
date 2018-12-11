package org.jgoeres.adventofcode.Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardService {
    private TreeMap<Date, String> rawSchedule = new TreeMap<>();
    private HashMap<String, ArrayList<GuardDutyInterval>> schedulesByGuard = new HashMap<>();

    public GuardService(String pathToFile) {
        loadGuardSchedule(pathToFile);
        buildGuardSchedules();
    }

    private void loadGuardSchedule(String pathToFile) {

//      Match lines like
//      [1518-07-14 00:48] wakes up
//      [1518-09-16 00:04] Guard #3323 begins shift
//      [1518-10-07 00:34] falls asleep

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Pattern p = Pattern.compile("\\[(.*)\\] (.*)");

        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.

                Matcher m = p.matcher(line);
                m.matches();

                String dateTimeString = m.group(1);
                String eventString = m.group(2);

                Date dateTime = dateTimeFormat.parse(dateTimeString);
//                System.out.println(dateTime);

                // Put this event in the schedule.
                rawSchedule.put(dateTime, eventString);
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

//        HOW TO NAVIGATE THE SCHEDULE ENTRIES BY DATE
//        for(Map.Entry<Date,String> entry : rawSchedule.entrySet()) {
//            Date key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println(key + " => " + value);
//        }
    }

    public HashMap<String, ArrayList<GuardDutyInterval>> buildGuardSchedules() {
        String currentGuardID = null;
        Pattern patternGuardId = Pattern.compile("Guard #(\\d+).*");
        Pattern patternFallsAsleep = Pattern.compile("falls asleep");
        Pattern patternWakesUp = Pattern.compile("wakes up");

        ArrayList<GuardDutyInterval> currentIntervals = null;
        GuardDutyInterval currentGuardInterval = null;

        // Clear the existing schedule, if for some reason one would already exist.
        schedulesByGuard.clear();

        for (Map.Entry<Date, String> entry : rawSchedule.entrySet()) {
            String entryValue = entry.getValue();

            Matcher matchGuardId = patternGuardId.matcher(entryValue);
            Matcher matchFallsAsleep = patternFallsAsleep.matcher(entryValue);
            Matcher matchWakesUp = patternWakesUp.matcher(entryValue);

            if (matchGuardId.matches()) {
                currentGuardID = matchGuardId.group(1);
//                System.out.println("Found Guard #" + matchGuardId.group(1));

                // Either get the existing list of intervals for this guard,
                // or start a new list of intervals for this guard's shift.
                if (schedulesByGuard.containsKey(currentGuardID)) {
                    currentIntervals = schedulesByGuard.get(currentGuardID);
                } else {
                    currentIntervals = new ArrayList<>();
                    schedulesByGuard.put(currentGuardID, currentIntervals);
                }
            } else if (matchFallsAsleep.matches()) {
                // Start a new sleep/wake interval for this guard.
                currentGuardInterval = new GuardDutyInterval();
                currentGuardInterval.setAsleep(entry.getKey()); // set the current time as the asleep time for this interval
            } else if (matchWakesUp.matches()) {
                currentGuardInterval.setAwaken(entry.getKey()); // set the current time as the time the guard wakes up again.

                // Add the completed interval to the list for this guard.
                //               schedulesByGuard.get(currentGuardID).add(currentGuardInterval);
                currentIntervals.add(currentGuardInterval);
            }
        }
        return null;
    }

    public TreeMap<Date, String> getRawSchedule() {
        return rawSchedule;
    }

    public HashMap<String, ArrayList<GuardDutyInterval>> getSchedulesByGuard() {
        return schedulesByGuard;
    }
}
