package org.jgoeres.adventofcode.Day02;

public abstract class RunDay2 {
    static String pathToInputs = "day02/input.txt";
    static BoxChecksumService boxChecksumService = new BoxChecksumService(pathToInputs);

    public static void problem2A() {
//            DAY 02A
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
        System.out.println("=== DAY 2A ===");

        // Process each box ID character by character
        // Build a hash map, where
        //      the keys are the characters
        //      the values are the number of times that character appears.
        // Then check the hashmap:
        //      Does it contain the value 2?
        //      Does it contain the value 3?

        Integer numContainsTwo = 0;
        Integer numContainsThree = 0;

        System.out.println("Number of boxes to process: " + boxChecksumService.getBoxesList().size());

        for (String boxID : boxChecksumService.getBoxesList()) {
            BoxChecksum thisBoxCheckSum = boxChecksumService.calculateBoxChecksum(boxID);

            if (thisBoxCheckSum.containsTwo()) {
                numContainsTwo += 1;
            }
            if (thisBoxCheckSum.containsThree()) {
                numContainsThree += 1;
            }
        }
        System.out.println("Boxes containing 2: " + numContainsTwo);
        System.out.println("Boxes containing 3: " + numContainsThree);
        System.out.println("Result: " + numContainsTwo + " * " + numContainsThree + " = " + (numContainsTwo * numContainsThree));

//        Answer:
//        Number of boxes to process: 250
//        Boxes containing 2: 246
//        Boxes containing 3: 23
//        Result: 246 * 23 = 5658
    }
}
