package org.jgoeres.adventofcode.Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardService {
    private TreeMap<Date, String> guardSchedule = new TreeMap<>();

    public GuardService(String pathToFile) {
        loadGuardSchedule(pathToFile);
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

//                    System.out.println(m.group(0));
//                    System.out.println(m.group(1));
//                    System.out.println(m.group(2));
                String dateTimeString = m.group(1);
                String eventString = m.group(2);

                Date dateTime = dateTimeFormat.parse(dateTimeString);
                System.out.println(dateTime);

                // Put this event in the schedule.
                guardSchedule.put(dateTime, eventString);

            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

//        HOW TO NAVIGATE THE SCHEDULE ENTRIES BY DATE
//        for(Map.Entry<Date,String> entry : guardSchedule.entrySet()) {
//            Date key = entry.getKey();
//            String value = entry.getValue();
//            System.out.println(key + " => " + value);
//        }
    }

    public HashMap<String,ArrayList<GuardDutyInterval>> buildGuardSchedules(){
        GuardDutyInterval currentInterval = null;
        String currentGuardID = null;
        Pattern patternGuardId = Pattern.compile("Guard #(\\d+).*");
        Pattern patternFallsAsleep = Pattern.compile("falls asleep");
        Pattern patternWakesUp = Pattern.compile("wakes up");

        ArrayList<GuardDutyInterval> currentIntervals = null;
        GuardDutyInterval currentGuardInterval = null;

        HashMap<String,ArrayList<GuardDutyInterval>> schedulesByGuard = new HashMap<>();

        for (Map.Entry<Date, String> entry : guardSchedule.entrySet()) {
            String entryValue = entry.getValue();

            Matcher matchGuardId = patternGuardId.matcher(entryValue);
            Matcher matchFallsAsleep = patternFallsAsleep.matcher(entryValue);
            Matcher matchWakesUp = patternWakesUp.matcher(entryValue);

            if (matchGuardId.matches()){
                // If we have any outstanding intervals, add them
                // to the overall map of guard schedules before processing
                // the new guard.
//                if (currentIntervals != null) {
//                    schedulesByGuard.put(currentGuardID,
//                            (schedulesByGuard.get(currentGuardID).addAll(currentIntervals))
//                    );
//                }

                currentGuardID = matchGuardId.group(1);
                System.out.println("Found Guard #" + matchGuardId.group(1));

                // Either get the existing list of intervals for this guard,
                // or start a new list of intervals for this guard's shift.
                if (schedulesByGuard.containsKey(currentGuardID)){
                    currentIntervals = schedulesByGuard.get(currentGuardID);
                } else {
                    currentIntervals = new ArrayList<>();
                    schedulesByGuard.put(currentGuardID,currentIntervals);
                }
//                currentIntervals = new ArrayList<>();
            } else if (matchFallsAsleep.matches()){
                // Start a new sleep/wake interval for this guard.
                currentGuardInterval = new GuardDutyInterval();
                currentGuardInterval.setAsleep(entry.getKey()); // set the current time as the asleep time for this interval
            } else if (matchWakesUp.matches()){
                currentGuardInterval.setAwaken(entry.getKey()); // set the current time as the time the guard wakes up again.

                // Add the completed interval to the list for this guard.
 //               schedulesByGuard.get(currentGuardID).add(currentGuardInterval);
                currentIntervals.add(currentGuardInterval);
            }

        }
        return null;
    }

    public TreeMap<Date, String> getGuardSchedule() {
        return guardSchedule;
    }
}
