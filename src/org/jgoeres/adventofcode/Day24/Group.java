package org.jgoeres.adventofcode.Day24;

import java.util.ArrayList;

public class Group {
    int number;
    GroupType type;
    int unitCount;
    int hitPoints;
    AttackType attackType;
    int attackPower;
    int effectivePower;
    int initiative;
    ArrayList<AttackType> weaknesses;
    ArrayList<AttackType> immunities;

    public Group(GroupType type, int unitCount, int hitPoints, int attackPower, int initiative, int number) {
        this.type = type;
        this.unitCount = unitCount;
        this.hitPoints = hitPoints;
        this.attackPower = attackPower;
        this.initiative = initiative;
        this.number = number;
        this.effectivePower = calculateEffectivePower();

        // initialize the weakness & immunities as empty, we'll fill them later.
        weaknesses = new ArrayList<>();
        immunities = new ArrayList<>();
    }

    public int calculateDamage(Group defendingGroup) {
        Group attackingGroup = this;
        int damage;

        // The damage an attacking group deals to a defending group depends on the attacking
        // group's attack type and the defending group's immunities and weaknesses.
        // By default, an attacking group would deal damage equal to its effective power
        // to the defending group. However, if the defending group is immune to the attacking
        // group's attack type, the defending group instead takes no damage; if the defending
        // group is weak to the attacking group's attack type, the defending group instead takes double damage

        // Are the defenders immune?
        if (defendingGroup.immunities.contains(attackingGroup.attackType)) {
            // no damage done.
            return 0;
        }

        // By default, do effectivePower in damage
        damage = attackingGroup.effectivePower;

        // Are the defenders weak?
        if (defendingGroup.weaknesses.contains(attackingGroup.attackType)) {
            // double damage
            damage *= 2;
        }

        return damage;
        // Would this be enough damage to actually kill anyone?
//        if ((damage / defendingGroup.hitPoints) == 0) {
//            // Not enough power to kill anybody, so return 0 (and thereby don't attack)
//            return 0;
//        } else {
//            return damage;
//        }
    }

    public int takeDamage(int damageDealt) {
        // The defending group only loses whole units from damage; damage is always dealt in such a
        // way that it kills the most units possible, and any remaining damage to a unit that does
        // not immediately kill it is ignored. For example, if a defending group contains 10 units
        // with 10 hit points each and receives 75 damage, it loses exactly 7 units and is left with
        // 3 units at full health.

        int unitsToLose = Math.min(damageDealt / hitPoints, unitCount);  // whole number of total units killed by this damage, but not more than the total number of live units.

        unitCount -= unitsToLose;   // Kill the units

        this.effectivePower = calculateEffectivePower();  // Update the group's effective power after taking damage

        return unitsToLose; // return number killed
    }

    public boolean canHurtEnemy(Group enemy) {
        return ((this.effectivePower / enemy.hitPoints) > 0);   // Can only hurt the enemy if we can kill at least one unit.
    }

    public boolean isDead() {
        // Group is dead if they have zero or fewer units.
        return (unitCount <= 0);
    }

    public void boost(int boost) {
        this.attackPower += boost;
        this.effectivePower = calculateEffectivePower();    // Update the group's effective power after a boost.
    }

    private int calculateEffectivePower() {
        // Each group also has an effective power:
        // the number of units in that group multiplied by their attack damage.
        int effectivePower = unitCount * attackPower;
        return effectivePower;
    }
}

