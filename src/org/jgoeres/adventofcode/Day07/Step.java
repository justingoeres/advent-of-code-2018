package org.jgoeres.adventofcode.Day07;

import java.util.HashMap;

public class Step {
    private Character Id = null;
    private HashMap<Character,Step> parents = new HashMap<>();

    public Step(Character id) {
        Id = id;
    }

    public Character getId() {
        return Id;
    }

    public HashMap<Character,Step> getParents() {
        return parents;
    }

    public void setParents(HashMap<Character,Step> parents) {
        this.parents = parents;
    }
}
