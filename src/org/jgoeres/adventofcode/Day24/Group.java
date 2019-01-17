package org.jgoeres.adventofcode.Day24;

import java.util.ArrayList;

public class Group {
    GroupType type;
    int unitCount;
    AttackType attackType;
    int attackPower;
    int initiative;
    ArrayList<AttackType> weaknesses;
    ArrayList<AttackType> immunities;

    public int getEffectivePower() {
        // Each group also has an effective power:
        // the number of units in that group multiplied by their attack damage.
        int effectivePower = unitCount * attackPower;
        return effectivePower;
    }
}

