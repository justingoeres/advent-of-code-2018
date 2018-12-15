package org.jgoeres.adventofcode.Day09;

import java.util.ArrayList;

public class CircularArrayList<E> extends ArrayList<E> {
    public E get(int index) {
        return super.get(index % size());
    }
}
