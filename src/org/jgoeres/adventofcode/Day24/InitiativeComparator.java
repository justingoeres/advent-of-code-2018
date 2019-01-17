package org.jgoeres.adventofcode.Day24;

import java.util.Comparator;

public class InitiativeComparator implements Comparator<Group> {
    //During the attacking phase, each group deals damage to the target it selected, if any.
    // Groups attack in decreasing order of initiative, regardless of whether they are
    // part of the infection or the immune system.

    @Override
    public int compare(Group o1, Group o2) {
        int o1i = o1.initiative;

        int o2i = o2.initiative;

        // Compare initiatives. Higher goes first
        return Integer.compare(o2i, o1i);   //  order reversed to sort in decreasing order
    }
}
