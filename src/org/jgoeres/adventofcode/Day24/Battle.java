package org.jgoeres.adventofcode.Day24;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.jgoeres.adventofcode.Day24.GroupType.IMMUNE;
import static org.jgoeres.adventofcode.Day24.GroupType.INFECTION;

public class Battle {
    ArrayList<Group> immuneSystem = new ArrayList<>();
    ArrayList<Group> infection = new ArrayList<>();

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
            while ((line = br.readLine()) != null) {    // Keep reading until the end of the file.
                Matcher mGroupType = Pattern.compile("^(Immune|Infection).*").matcher(line);

                // See if this is the header for "Immune" or "Infection"
                if (mGroupType.find()) {
                    currentGroupType = (mGroupType.group(1).equals("Immune") ? IMMUNE : INFECTION);
                    System.out.println("Found group:\t" + currentGroup);
                    continue;   // Loop immediately, there's nothing else on this line
                }

                // Else this is a unit definition, so parse it.
                Matcher mUnitStats = Pattern.compile("(\\d+)\\D+(\\d+)\\D+(\\d+)\\D+(\\d+).*").matcher(line);
                if (mUnitStats.find()) {
                    currentGroup = new Group(currentGroupType,
                            Integer.parseInt(mUnitStats.group(1)),  // unit count
                            Integer.parseInt(mUnitStats.group(2)),  // hit points
                            Integer.parseInt(mUnitStats.group(3)),  // attack power
                            Integer.parseInt(mUnitStats.group(4))); // initiative
                    System.out.println("Found group:\t" + currentGroup);

                    // Continue parsing the attack/immunity/weakness info
                    Matcher mImmunitiesAndWeaknesses = Pattern.compile(".*\\((.*)\\).*").matcher(line);
                    if (mImmunitiesAndWeaknesses.find()) {
                        String immunitiesAndWeaknesses = mImmunitiesAndWeaknesses.group(1);
                        System.out.println(mImmunitiesAndWeaknesses.group(1));  // e.g. "immune to cold; weak to bludgeoning"

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
                }
                System.out.println("Done!");

            }
        } catch (Exception e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }

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
