package org.jgoeres.adventofcode.Day07;

import java.util.HashMap;

public class Step {
    private HashMap<Character,Step> parents = new HashMap<>();

    public HashMap<Character,Step> getParents() {
        return parents;
    }

    public void setParents(HashMap<Character,Step> parents) {
        this.parents = parents;
    }
}
