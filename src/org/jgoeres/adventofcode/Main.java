package org.jgoeres.adventofcode;

import org.jgoeres.adventofcode.Day01.RunDay1;
import org.jgoeres.adventofcode.Day02.RunDay2;
import org.jgoeres.adventofcode.Day03.RunDay3;
import org.jgoeres.adventofcode.Day04.RunDay4;
import org.jgoeres.adventofcode.Day05.RunDay5;
import org.jgoeres.adventofcode.Day06.RunDay6;
import org.jgoeres.adventofcode.Day07.RunDay7;
import org.jgoeres.adventofcode.Day08.RunDay8;
import org.jgoeres.adventofcode.Day09.RunDay9;
import org.jgoeres.adventofcode.Day10.RunDay10;
import org.jgoeres.adventofcode.Day11.RunDay11;
import org.jgoeres.adventofcode.Day12.RunDay12;
import org.jgoeres.adventofcode.Day13.RunDay13;
import org.jgoeres.adventofcode.Day14.RunDay14;
import org.jgoeres.adventofcode.Day15.RunDay15;
import org.jgoeres.adventofcode.Day16.RunDay16;
import org.jgoeres.adventofcode.Day17.RunDay17;
import org.jgoeres.adventofcode.Day18.RunDay18;
import org.jgoeres.adventofcode.Day19.RunDay19;
import org.jgoeres.adventofcode.Day20.RunDay20;
import org.jgoeres.adventofcode.Day21.RunDay21;
import org.jgoeres.adventofcode.Day22.RunDay22;
import org.jgoeres.adventofcode.Day23.RunDay23;
import org.jgoeres.adventofcode.Day24.RunDay24;
import org.jgoeres.adventofcode.Day25.RunDay25;

public class Main {

    static final boolean RUN_ALL = false;

    static final boolean RUN_DAY_1 = false;
    static final boolean RUN_DAY_2 = false;
    static final boolean RUN_DAY_3 = false;
    static final boolean RUN_DAY_4 = false;
    static final boolean RUN_DAY_5 = false;
    static final boolean RUN_DAY_6 = false;
    static final boolean RUN_DAY_7 = false;
    static final boolean RUN_DAY_8 = false;
    static final boolean RUN_DAY_9 = false;
    static final boolean RUN_DAY_10 = false;
    static final boolean RUN_DAY_11 = false;
    static final boolean RUN_DAY_12 = false;
    static final boolean RUN_DAY_13 = false;
    static final boolean RUN_DAY_14 = false;
    static final boolean RUN_DAY_15 = false;
    static final boolean RUN_DAY_16 = false;
    static final boolean RUN_DAY_17 = false;
    static final boolean RUN_DAY_18 = false;
    static final boolean RUN_DAY_19 = false;
    static final boolean RUN_DAY_20 = false;
    static final boolean RUN_DAY_21 = false;
    static final boolean RUN_DAY_22 = false;
    static final boolean RUN_DAY_23 = false;
    static final boolean RUN_DAY_24 = false;
    static final boolean RUN_DAY_25 = true;

    private static long startTime = 0L;

    public static void main(String[] args) {
        //https://adventofcode.com/2018/

        if (RUN_DAY_1 || RUN_ALL) {
//             Day 1A
//             Starting with a frequency of zero, what is the resulting frequency
//             after all of the changes in frequency have been applied?
            setStartTime();
            RunDay1.problem1A();
            printElapsedTime();

            blankLine();

            //             Day 1B
//             You notice that the device repeats the same frequency change list over and over.
//             To calibrate the device, you need to find the first frequency it reaches twice.
            setStartTime();
            RunDay1.problem1B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_2 || RUN_ALL) {
//            --- Day 2: Inventory Management System ---

//            DAY 2A
//            To make sure you didn't miss any, you scan the likely candidate boxes again, counting the number that have an ID containing exactly two of any letter and then separately counting those with exactly three of any letter. You can multiply those two counts together to get a rudimentary checksum and compare it to what your device predicts.
//
//            For example, if you see the following box IDs:
//
//            abcdef contains no letters that appear exactly two or three times.
//                    bababc contains two a and three b, so it counts for both.
//                    abbcde contains two b, but no letter appears exactly three times.
//            abcccd contains three c, but no letter appears exactly two times.
//            aabcdd contains two a and two d, but it only counts once.
//            abcdee contains two e.
//            ababab contains three a and three b, but it only counts once.
//            Of these box IDs, four of them contain a letter which appears exactly twice, and three of them contain a letter which appears exactly three times. Multiplying these together produces a checksum of 4 * 3 = 12.
//
//            What is the checksum for your list of box IDs?
            setStartTime();
            RunDay2.problem2A();
            printElapsedTime();

            blankLine();

//            DAY 2B
//            The IDs abcde and axcye are close, but they differ by two characters (the second and fourth).
//            However, the IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.
//
//            What letters are common between the two correct box IDs?
            setStartTime();
            RunDay2.problem2B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_3 || RUN_ALL) {
            setStartTime();
            RunDay3.problem3A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay3.problem3B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_4 || RUN_ALL) {
            setStartTime();
            RunDay4.problem4A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay4.problem4B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_5 || RUN_ALL) {
            setStartTime();
            RunDay5.problem5A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay5.problem5B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_6 || RUN_ALL) {
            setStartTime();
            RunDay6.problem6A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay6.problem6B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_7 || RUN_ALL) {
            setStartTime();
            RunDay7.problem7A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay7.problem7B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_8 || RUN_ALL) {
            setStartTime();
            RunDay8.problem8A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay8.problem8B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_9 || RUN_ALL) {
            setStartTime();
            RunDay9.problem9A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay9.problem9B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_10 || RUN_ALL) {
            setStartTime();
            RunDay10.problem10Aand10B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_11 || RUN_ALL) {
            setStartTime();
            RunDay11.problem11A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay11.problem11B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_12 || RUN_ALL) {
            setStartTime();
            RunDay12.problem12A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay12.problem12B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_13 || RUN_ALL) {
            setStartTime();
            RunDay13.problem13A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay13.problem13B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_14 || RUN_ALL) {
            setStartTime();
            RunDay14.problem14A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay14.problem14B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_15 || RUN_ALL) {
            setStartTime();
            RunDay15.problem15A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay15.problem15B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_16 || RUN_ALL) {
            setStartTime();
            RunDay16.problem16A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay16.problem16B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_17 || RUN_ALL) {
            setStartTime();
            RunDay17.problem17A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay17.problem17B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_18 || RUN_ALL) {
            setStartTime();
            RunDay18.problem18A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay18.problem18B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_19 || RUN_ALL) {
            setStartTime();
            RunDay19.problem19A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay19.problem19B();
            printElapsedTime();

            blankLine();
        }
        
        if (RUN_DAY_20 || RUN_ALL) {
            setStartTime();
            RunDay20.problem20A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay20.problem20B();
            printElapsedTime();

            blankLine();
        }
        
        if (RUN_DAY_21 || RUN_ALL) {
            setStartTime();
            RunDay21.problem21A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay21.problem21B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_22 || RUN_ALL) {
            setStartTime();
            RunDay22.problem22A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay22.problem22B();
            printElapsedTime();

            blankLine();
        }
        
        if (RUN_DAY_23 || RUN_ALL) {
            setStartTime();
            RunDay23.problem23A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay23.problem23B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_24 || RUN_ALL) {
            setStartTime();
            RunDay24.problem24A();
            printElapsedTime();

            blankLine();

            setStartTime();
            RunDay24.problem24B();
            printElapsedTime();

            blankLine();
        }

        if (RUN_DAY_25 || RUN_ALL) {
            setStartTime();
            RunDay25.problem25A();
            printElapsedTime();

            blankLine();
        }
    }

    private static void blankLine() {
        System.out.println();
    }

    private static void setStartTime() {
        startTime = System.currentTimeMillis();
    }

    private static void printElapsedTime() {
        long endTime = System.currentTimeMillis();

        System.out.println("Time elapsed:\t" + (endTime - startTime) + " ms");
    }
}

