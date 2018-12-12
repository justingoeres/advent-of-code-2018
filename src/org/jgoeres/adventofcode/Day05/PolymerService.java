package org.jgoeres.adventofcode.Day05;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public abstract class PolymerService {

    public static void reducePolymers(ArrayList<Character> polymers) {
        for (int i = 0; i < polymers.size() - 1; i++) {
            Character current = polymers.get(i);  // get the current polymer
            Character next = polymers.get(i + 1); // get the next polymer.

            if (oppositeCase(current, next)) {
                // If this polymer and the next one are equal but opposite (e.g. 'd' and 'D')
                // Remove them both.
                polymers.remove(i + 1);
                polymers.remove(i);

                // And back up the pointer, but not so far that i++ will leave us with a negative index.
                i = Math.max(i - 2, -1);
            }
        }
    }

    public static boolean oppositeCase(Character c1, Character c2) {
        final int OPPOSITE_CASE_DISTANCE = 32;
        final int distance = Math.abs(c1 - c2);

        return (distance == OPPOSITE_CASE_DISTANCE);
    }
}
