package org.jgoeres.adventofcode.Day05;

public class RunDay5 {
    static String pathToInputs = "day05/input.txt";
    static PolymerList polymers = new PolymerList(pathToInputs);

    static final boolean DISPLAY_FINAL_POLYMERS = false;

    public static void problem5A() {
/*
        The polymer is formed by smaller units which, when triggered,
        react with each other such that two adjacent units of the same type
        and opposite polarity are destroyed. Units' types are represented
        by letters; units' polarity is represented by capitalization.

        For instance, r and R are units with the same type but opposite polarity,
        whereas r and s are entirely different types and do not react.

        For example:

        In aA, a and A react, leaving nothing behind.
        In abBA, bB destroys itself, leaving aA. As above, this then destroys itself, leaving nothing.
        In abAB, no two adjacent units are of the same type, and so nothing happens.
        In aabAAB, even though aa and AA are of the same type, their polarities match, and so nothing happens.

        Now, consider a larger example, dabAcCaCBAcCcaDA:

        dabAcCaCBAcCcaDA  The first 'cC' is removed.
        dabAaCBAcCcaDA    This creates 'Aa', which is removed.
        dabCBAcCcaDA      Either 'cC' or 'Cc' are removed (the result is the same).
        dabCBAcaDA        No further actions can be taken.
        After all possible reactions, the resulting polymer contains 10 units.

        How many units remain after fully reacting the polymer you scanned?
 */
        System.out.println("=== DAY 5A ===");

//        ArrayList<Character> polymers = polymerService.getPolymersList();
//        for (int i = 0; i < polymers.size() - 1; i++) {
//            Character current = polymers.get(i);  // get the current polymer
//            Character next = polymers.get(i + 1); // get the next polymer.
//
//            if (polymerService.oppositeCase(current, next)) {
//                // If this polymer and the next one are equal but opposite (e.g. 'd' and 'D')
//                // Remove them both.
//                polymers.remove(i + 1);
//                polymers.remove(i);
//
//                // And back up the pointer, but not so far that i++ will leave us with a negative index.
//                i = Math.max(i - 2, -1);
//            }
//        }
        PolymerService.reducePolymers(polymers);

        // When we're done, we should have the minimum set of non-matched polymer pairs.
        if (DISPLAY_FINAL_POLYMERS) {
            System.out.println("Remaining polymer codes:");
            for (int i = 0; i < polymers.size(); i++) {
                System.out.print(polymers.get(i));
            }
            System.out.println();
        }

        System.out.println("Number remaining: " + polymers.size());
        // Answer:
        // Number remaining: 10132
    }

    public static void problem5B() {
        /*
        One of the unit types is causing problems; it's preventing
        the polymer from collapsing as much as it should. Your goal
        is to figure out which unit type is causing the most problems,
        remove all instances of it (regardless of polarity), fully
        react the remaining polymer, and measure its length.

        What is the length of the shortest polymer you can produce by
        removing all units of exactly one type and fully reacting
        the result?
        */
        System.out.println("=== DAY 5B ===");

        // We can start with the original result of 5A.
        // That way we don't have to reduce it over and over.

        Integer minLength = Integer.MAX_VALUE; // Really big.
        Character minChar = null;
        // Iterate through all of a-z (and A-Z)
        for (Character c = 'a'; c <= 'z'; c++) {
            PolymerList toReduce = (PolymerList) polymers.clone();

            // Remove the target base
            PolymerService.removeOneBase(toReduce, c);
            // Reduce the remaining list.
            PolymerService.reducePolymers(toReduce);

//            System.out.println("Removed: " + c + "\t\tNew Reduced Length: " + toReduce.size());

            if (toReduce.size() < minLength) {
                minLength = toReduce.size();
                minChar = c;
            }
        }

        System.out.println("Minimum length: " + minLength + " after removing " + Character.toUpperCase(minChar));

        // Answer:
        // Minimum length: 4572 after removing M
    }
}
