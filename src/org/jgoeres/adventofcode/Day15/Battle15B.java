package org.jgoeres.adventofcode.Day15;

public class Battle15B extends Battle {
    // Extend the Battle class to add aborting of battle as soon as the elves lose a unit.
    int elvesOriginalSize;

    public Battle15B(String pathToFile) {
        super(pathToFile);

        elvesOriginalSize = super.elves.size();
    }

    @Override
    public boolean isOver() {
        // Battle ends as soon as the elves lose somebody.
        return (elvesLostSomeone() || super.isOver());
    }

    public boolean elvesLostSomeone() {
        boolean elvesLostSomeone = (super.elves.size() < elvesOriginalSize);
        return elvesLostSomeone;
    }
}
