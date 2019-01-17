package org.jgoeres.adventofcode.Day24;

import java.util.Comparator;

public class GroupComparator implements Comparator<Group> {
    // During the target selection phase, each group attempts to choose one target.
    // In decreasing order of effective power, groups choose their targets;
    // in a tie, the group with the higher initiative chooses first.

    @Override
    public int compare(Group o1, Group o2) {
        int o1ep = o1.effectivePower;
        int o1i = o1.initiative;

        int o2ep = o2.effectivePower;
        int o2i = o2.initiative;

        // First, compare each group's effective power. Higher goes first.
        if (o1ep != o2ep) {
            return Integer.compare(o2ep, o1ep); //  order reversed to sort in decreasing order
        }

        // If both are the same effective power, compare initiatives. Higher goes first
        return Integer.compare(o2i, o1i);   //  order reversed to sort in decreasing order
    }
}
