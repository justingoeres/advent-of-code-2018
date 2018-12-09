package org.jgoeres.adventofcode;

import org.jgoeres.adventofcode.Day01.RunDay1;
import org.jgoeres.adventofcode.Day02.RunDay2;

public class Main {

    static final boolean RUN_ALL = false;

    static final boolean RUN_DAY_1 = false;
    static final boolean RUN_DAY_2 = true;

    public static void main(String[] args) {
        //https://adventofcode.com/2018/

        if (RUN_DAY_1 || RUN_ALL) {
//             Day 1A
//             Starting with a frequency of zero, what is the resulting frequency
//             after all of the changes in frequency have been applied?
            RunDay1.problem1A();

//             Day 1B
//             You notice that the device repeats the same frequency change list over and over.
//             To calibrate the device, you need to find the first frequency it reaches twice.
            RunDay1.problem1B();
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
            RunDay2.problem2A();

//            DAY 2B
//            The IDs abcde and axcye are close, but they differ by two characters (the second and fourth).
//            However, the IDs fghij and fguij differ by exactly one character, the third (h and u). Those must be the correct boxes.
//
//            What letters are common between the two correct box IDs?
            RunDay2.problem2B();
        }

    }
}
