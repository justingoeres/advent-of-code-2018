package org.jgoeres.adventofcode.Day05;

import java.util.ArrayList;

public class RunDay5 {
    static String pathToInputs = "day05/input.txt";
    static PolymerService polymerService = new PolymerService(pathToInputs);

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

        ArrayList<Character> polymers = polymerService.getPolymersList();

        for (int i = 0; i < polymers.size() - 1; i++) {
            Character current = polymers.get(i);  // get the current polymer
            Character next = polymers.get(i + 1); // get the next polymer.

            if (polymerService.oppositeCase(current, next)) {
                // If this polymer and the next one are equal but opposite (e.g. 'd' and 'D')
                // Remove them both.
                polymers.remove(i + 1);
                polymers.remove(i);

                // And back up the pointer, but not so far that i++ will leave us with a negative index.
                i = Math.max(i - 2, -1);
            }
        }

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
        System.out.println("=== DAY 5B ===");
    }
}
