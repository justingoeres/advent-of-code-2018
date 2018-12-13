package org.jgoeres.adventofcode.Day07;

import java.util.ArrayList;

public class Step {
    private ArrayList<Step> parents = new ArrayList<>();


    public ArrayList<Step> getParents() {
        return parents;
    }

    public void setParents(ArrayList<Step> parents) {
        this.parents = parents;
    }
}
