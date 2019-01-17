package org.jgoeres.adventofcode.Day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jgoeres.adventofcode.Day24.GroupType.IMMUNE;
import static org.jgoeres.adventofcode.Day24.GroupType.INFECTION;

public class Battle {
    TreeSet<Group> immuneSystem = new TreeSet<>(new GroupComparator());
    TreeSet<Group> infection = new TreeSet<>(new GroupComparator());

    TreeSet<Group> allUnits = new TreeSet<>(new GroupComparator());

    TreeMap<Group, Group> attackPairs = new TreeMap<>(new InitiativeComparator());

    final boolean DEBUG_PRINT_PROGRESS = false;

    public Battle(String pathToFile) {
        loadBattle(pathToFile);
    }

    public void loadBattle(String pathToFile) {
        /*
        File looks like:
        Immune System:
        1432 units each with 7061 hit points (immune to cold; weak to bludgeoning) with an attack that does 41 slashing damage at initiative 17
        3387 units each with 9488 hit points (weak to bludgeoning) with an attack that does 27 slashing damage at initiative 20
        ...
        Infection:
        290 units each with 29776 hit points (weak to cold, radiation) with an attack that does 204 bludgeoning damage at initiative 16
        7268 units each with 14114 hit points (immune to radiation; weak to bludgeoning) with an attack that does 3 bludgeoning damage at initiative 19
         */

        GroupType currentGroupType = null;
        Group currentGroup = null;
        try (BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
            String line;
            int groupNum = 1;
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                Matcher mGroupType = Pattern.compile("^(Immune|Infection).*").matcher(line);

                // See if this is the header for "Immune" or "Infection"
                if (mGroupType.find()) {
                    currentGroupType = (mGroupType.group(1).equals("Immune") ? IMMUNE : INFECTION);
                    groupNum = 1;   // Reset the group number
                    continue;   // Loop immediately, there's nothing else on this line
                }

                // Else this is a unit definition, so parse it.
                Matcher mUnitStats = Pattern.compile("(\\d+)\\D+(\\d+)\\D+(\\d+)\\D+(\\d+).*").matcher(line);
                if (mUnitStats.find()) {
                    currentGroup = new Group(currentGroupType,
                            Integer.parseInt(mUnitStats.group(1)),  // unit count
                            Integer.parseInt(mUnitStats.group(2)),  // hit points
                            Integer.parseInt(mUnitStats.group(3)),  // attack power
                            Integer.parseInt(mUnitStats.group(4)),  // initiative
                            groupNum); // group number

                    // Continue parsing the attack/immunity/weakness info
                    Matcher mImmunitiesAndWeaknesses = Pattern.compile(".*\\((.*)\\).*").matcher(line);
                    if (mImmunitiesAndWeaknesses.find()) {
                        String immunitiesAndWeaknesses = mImmunitiesAndWeaknesses.group(1);

                        // Find each type of thing – immunities, and weaknesses
                        String[] parts = immunitiesAndWeaknesses.split(";");
                        for (String part : parts) { // parse each half, if present.
                            Matcher mImmunityOrWeakness = Pattern.compile(".*(immune|weak) to (.*)").matcher(part);
                            if (mImmunityOrWeakness.find()) {
                                // parse the list of types first.
                                String[] types = mImmunityOrWeakness.group(2).split(", ");
                                for (String type : types) {
                                    // Parse out the individual attack types from this immunity or weakness,
                                    // and add them to the corresponding list in this group.
                                    AttackType thisType = AttackType.fromString(type);

                                    if (mImmunityOrWeakness.group(1).equals("weak")) {
                                        currentGroup.weaknesses.add(thisType);
                                    } else {
                                        currentGroup.immunities.add(thisType);
                                    }
                                }
                            }
                        }
                    }

                    // Finally, parse the attack type
                    Matcher mAttackType = Pattern.compile(".*attack that does \\d+ " + attackTypeMatchString() + " damage.*").matcher(line);
                    if (mAttackType.find()) {
                        AttackType attackType = AttackType.fromString(mAttackType.group(1));
                        currentGroup.attackType = attackType;
                    }

                    // Done parsing this group, so add it to the correct side.
                    if (currentGroupType.equals(IMMUNE)) {
                        immuneSystem.add(currentGroup);
                    } else {
                        infection.add(currentGroup);
                    }
                    // Also add it to the "all units" list
                    allUnits.add(currentGroup);
                    groupNum++; // increment the group number.
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

    }

    public TreeSet<Group> doTimerTick() {

        ///////////////// Phase 1: Target selection /////////////////////////////////////////////

        // During the target selection phase, each group attempts to choose one target.
        // In decreasing order of effective power, groups choose their targets; 
        // in a tie, the group with the higher initiative chooses first.

        if (DEBUG_PRINT_PROGRESS) {
            // Print group status for each army.
            int i = 1;
            System.out.println("Immune System:");
            for (Group group : immuneSystem) {
                System.out.println("Group " + group.number + " contains " + group.unitCount + " units");
                i++;
            }
            i = 1;
            System.out.println("Infection:");
            for (Group group : infection) {
                System.out.println("Group " + group.number + " contains " + group.unitCount + " units");
                i++;
            }
            System.out.println();   // blank line
        }

        attackPairs.clear();    // Clear the attackPairs set because we start over every turn.

        for (Group group : allUnits) {
            // Process every group in priority (TreeSet) order.

            // The attacking group chooses to target the group in the enemy army to
            // which it would deal the most damage (after accounting for weaknesses and immunities,
            // but not accounting for whether the defending group has enough units
            // to actually receive all of that damage)

            // Get the enemies list based on this group's type.
            TreeSet<Group> enemyUnits = ((group.type == IMMUNE) ? infection : immuneSystem);

            // Evaluate all enemies to see who we should attack.
            int maxDamage = 0;
            Group candidateEnemy = null;
            for (Group enemyGroup : enemyUnits) {
                // Find who would get the most damage
                int damage = group.calculateDamage(enemyGroup);

                if (damage == 0)
                    continue;  // If this group can't damage this enemy, there won't be any target selection anyway.
                if (DEBUG_PRINT_PROGRESS && damage > 0) {
                    // Print details of the proposed attack.
                    System.out.println(group.type + " group " + group.number + " would deal defending group " +
//                            candidateEnemy.number + " " + maxDamage + " damage");
                            enemyGroup.number + " " + damage + " damage");

                }

                if ((damage > maxDamage) // If we'd do the most damage to this enemy
                        && (!attackPairs.containsValue(enemyGroup)))   // AND if no one else is already attacking this enemy.
                {
                    // If this is the most damage we've found
                    maxDamage = damage;
                    candidateEnemy = enemyGroup;    // Keep track of this enemy, it's the best candidate so far
                } else if (damage == maxDamage) { // If we have a tie
                    // If an attacking group is considering two defending groups to which it would deal equal damage,
                    // it chooses to target the defending group with the largest effective power;
                    // if there is still a tie, it chooses the defending group with the highest initiative.
                    if (enemyGroup.effectivePower > candidateEnemy.effectivePower) {
                        // Choose the enemy with the larger effective power.
                        maxDamage = damage;
                        candidateEnemy = enemyGroup;    // Keep track of this enemy, it's the best candidate so far
                    } else if (enemyGroup.effectivePower == candidateEnemy.effectivePower) {
                        // If there's still a tie
                        if (enemyGroup.initiative > candidateEnemy.initiative) {
                            // Take the enemy with the larger initiative
                            maxDamage = damage;
                            candidateEnemy = enemyGroup;    // Keep track of this enemy, it's the best candidate so far
                        }
                    }
                }
            }

            // If we found an available candidateEnemy, claim it
            if (candidateEnemy != null) {
                attackPairs.put(group, candidateEnemy);

            }
        }
        if (DEBUG_PRINT_PROGRESS) System.out.println();   // blank line

        // When we arrive here, everybody has a target to attack (or they've chosen not to attack)


        ///////////////// Phase 2: Attacking ////////////////////////////////////////////////////////////

        // During the attacking phase, each group deals damage to the target it selected, if any.
        // Groups attack in decreasing order of initiative, regardless of whether they are part of
        // the infection or the immune system. (If a group contains no units, it cannot attack.)

        for (Map.Entry<Group, Group> attackPair : attackPairs.entrySet()) {
            Group attacker = attackPair.getKey();
            Group defender = attackPair.getValue();

            if (!attacker.isDead()) {   // Only living units get to attack
                // Calculate the damage to be done.
                int damageDealt = attacker.calculateDamage(defender);

                // The defending group only loses whole units from damage; damage is always dealt in such a
                // way that it kills the most units possible, and any remaining damage to a unit that does
                // not immediately kill it is ignored. For example, if a defending group contains 10 units
                // with 10 hit points each and receives 75 damage, it loses exactly 7 units and is left with
                // 3 units at full health.
                int numberKilled = defender.takeDamage(damageDealt);
                if (DEBUG_PRINT_PROGRESS) {
                    System.out.println(attacker.type + " group " + attacker.number + " attacks defending group "
                            + defender.number + ", killing " + numberKilled + " units");
                }
            }
        }

        if (DEBUG_PRINT_PROGRESS) {
            System.out.println();   // A couple linefeeds when we're done.
            System.out.println();   // A couple linefeeds when we're done.
        }

        // Clean up the dead by removing them from the unit lists.
        immuneSystem.clear();
        infection.clear();
        for (Group group : allUnits) {
            // Rebuild the unit lists
            if (!group.isDead()) {
                switch (group.type) {
                    case IMMUNE:
                        immuneSystem.add(group);
                        break;
                    case INFECTION:
                        infection.add(group);
                        break;
                }
            }
        }

        // Is either army completely dead?
        if (immuneSystem.size() == 0) return infection; // Infection wins!

        if (infection.size() == 0) return immuneSystem; // Infection wins!

        // Everybody still has somebody living, so continue the fight.
        // Now having cleaned up the individual armies, rebuild the allUnits list from the living.
        allUnits.clear();
        for (Group liveGroup : immuneSystem) {
            allUnits.add(liveGroup);
        }
        for (Group liveGroup : infection) {
            allUnits.add(liveGroup);
        }

        return null;    // No winner yet.
    }

    public int totalUnitCount(TreeSet<Group> groups) {
        int totalUnitCount = 0;
        for (Group group : groups) {
            totalUnitCount += group.unitCount;
        }
        return totalUnitCount;
    }

    private static String attackTypeMatchString() {
        String matchString = "(";
        boolean first = true;
        for (AttackType attackType : AttackType.values()) {
            if (first) {
                first = false;
            } else {
                matchString += "|";
            }
            matchString += attackType.asString();
        }
        matchString += ")";
        return matchString;
    }
}
