package org.jgoeres.adventofcode.Day24;

import java.util.ArrayList;

public class Group {
    GroupType type;
    int unitCount;
    int hitPoints;
    AttackType attackType;
    int attackPower;
    int initiative;
    ArrayList<AttackType> weaknesses;
    ArrayList<AttackType> immunities;

    public Group(GroupType type, int unitCount,int hitPoints, int attackPower, int initiative) {
        this.type = type;
        this.unitCount = unitCount;
        this.hitPoints = hitPoints;
        this.attackPower = attackPower;
        this.initiative = initiative;

        // initialize the weakness & immunities as empty, we'll fill them later.
        weaknesses = new ArrayList<>();
        immunities = new ArrayList<>();
    }

    public int getEffectivePower() {
        // Each group also has an effective power:
        // the number of units in that group multiplied by their attack damage.
        int effectivePower = unitCount * attackPower;
        return effectivePower;
    }
}

