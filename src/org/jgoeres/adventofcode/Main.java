package org.jgoeres.adventofcode;

import org.jgoeres.adventofcode.Day01.RunDay1;

public class Main {

    static final boolean RUN_ALL = false;

    static final boolean RUN_DAY_1 = true;
    static final boolean RUN_DAY_2 = false;

    public static void main(String[] args) {
        //https://adventofcode.com/2018/

        if (RUN_DAY_1 || RUN_ALL) {
            // Day 01A
            // Starting with a frequency of zero, what is the resulting frequency
            // after all of the changes in frequency have been applied?
            RunDay1.problem1A();

            // Day 01B
            // You notice that the device repeats the same frequency change list over and over.
            // To calibrate the device, you need to find the first frequency it reaches twice.
            RunDay1.problem1B();
        }

        if (RUN_DAY_2 || RUN_ALL) {
            System.out.println("Day 2!");
        }

    }
}
