package org.jgoeres.adventofcode.Day04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuardService {
    private TreeMap<Date,String> guardSchedule = new TreeMap<>();

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
                guardSchedule.put(dateTime,eventString);

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
}
