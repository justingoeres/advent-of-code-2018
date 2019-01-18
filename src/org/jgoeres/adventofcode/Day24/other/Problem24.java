package org.jgoeres.adventofcode.Day24.other;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//import com.adventofcode.GlobalFunctions;

public class Problem24 {

    private ArrayList<Unit> immune, infection;
    private int boost;
    private int killsLastRound;

    public Problem24(ArrayList<String> lines, int boost) {
        this.boost = boost;
        immuneCount = 1;
        infectionCount = 1;
        immune = new ArrayList<Unit>();
        infection = new ArrayList<Unit>();
        boolean immuneUnit = true;
        for (String line : lines) {
            line = line.trim();
            if (line.equals("Immune System:")) {
                immuneUnit = true;
            } else if (line.equals("Infection:")) {
                immuneUnit = false;
            } else if (line.equals("")) {
                //Do nothing on the middle blank line
            } else {//Having eliminated all other types of line, unit line
                Unit unit = new Unit(line, immuneUnit);
                if (immuneUnit) {
                    unit.damage += boost;
                    immune.add(unit);
                } else infection.add(unit);
            }
        }
    }

    public boolean simulate() {
        PowerSorter power = new PowerSorter();
        InitiativeSorter init = new InitiativeSorter();
        ArrayList<Unit> units = new ArrayList<Unit>();
        units.addAll(infection);
        units.addAll(immune);
        Collections.sort(units, init);
        killsLastRound = 1;//Just to get us into the first loop
        while (!immune.isEmpty() && !infection.isEmpty() && killsLastRound > 0) {
            killsLastRound = 0;
            //Phase 1 - Target selection
            for (Unit unit : immune) {
                unit.target = null;
            }
            for (Unit unit : infection) {
                unit.target = null;
            }
            ArrayList<Unit> targets = new ArrayList<Unit>();
            targets.addAll(immune);
            Collections.sort(infection, init); //Initiative breaks ties
            Collections.sort(infection, power);
            for (Unit unit : infection) {
                if (targets.isEmpty())
                    break;//No point in trying to find an enemy to attack!
                Unit best = null;
                int bestDamage = -1;
                for (Unit target : targets) {
                    int damage = target.testDamage(unit.getEffectivePower(), unit.damageType);
                    //System.out.println(unit.name + " would deal " + target.name + " " + damage + " damage.");
                    boolean doIt = false;
                    if (damage > bestDamage) {
                        doIt = true;
                    } else if (damage == bestDamage) {
                        if (best.getEffectivePower() < target.getEffectivePower())
                            doIt = true;
                        else if (best.getEffectivePower() == target.getEffectivePower()) {
                            if (target.initiative > best.initiative)
                                doIt = true;
                        }
                    }
                    if (doIt) {
                        bestDamage = damage;
                        best = target;
                    }
                }
                targets.remove(best);
                unit.target = best;
            }
            targets.clear();
            targets.addAll(infection);
            Collections.sort(immune, init); //Initiative breaks ties
            Collections.sort(immune, power);
            for (Unit unit : immune) {
                if (targets.isEmpty())
                    break;//No point in trying to find an enemy to attack!
                Unit best = null;
                int bestDamage = -1;
                for (Unit target : targets) {
                    int damage = target.testDamage(unit.getEffectivePower(), unit.damageType);
                    //System.out.println(unit.name + " would deal " + target.name + " " + damage + " damage.");
                    boolean doIt = false;
                    if (damage > bestDamage) {
                        doIt = true;
                    } else if (damage == bestDamage) {
                        if (best.getEffectivePower() < target.getEffectivePower())
                            doIt = true;
                        else if (best.getEffectivePower() == target.getEffectivePower()) {
                            if (target.initiative > best.initiative)
                                doIt = true;
                        }
                    }
                    if (doIt) {
                        bestDamage = damage;
                        best = target;
                    }
                }
                targets.remove(best);
                unit.target = best;
            }
            //OK, all target selection done. Time to do attacks.
            ArrayList<Unit> dead = new ArrayList<Unit>();
            for (Unit unit : units) {
                //In initiative order
                Unit target = unit.attackIfAlive();
                if (target != null) {
                    if (target.membersAlive <= 0)
                        dead.add(target);
                }
            }
            for (Unit deceased : dead) {
                units.remove(deceased);
                immune.remove(deceased);
                infection.remove(deceased);
            }
        }
        //OK, one group has eliminated the other - grab remaining members alive
        int total = 0;
        for (Unit unit : units) {
            total += unit.membersAlive;
            System.out.println(unit);
        }
        System.out.println("At the end of the simulation there are " + total + " soldiers alive for boost " + boost);
        return (!infection.isEmpty());
    }

    private class PowerSorter implements Comparator<Unit> {
        @Override
        public int compare(Unit arg0, Unit arg1) {
            return arg1.getEffectivePower() - arg0.getEffectivePower();
        }
    }

    private class InitiativeSorter implements Comparator<Unit> {
        @Override
        public int compare(Unit o1, Unit o2) {
            return o2.initiative - o1.initiative;
        }
    }

    static int immuneCount = 1, infectionCount = 1;

    private class Unit {
        String name;
        private Unit target;
        private int individualHealth, membersAlive;
        //private boolean immuneSystem;
        private ArrayList<DamageType> weaknesses, immunities;
        private DamageType damageType;
        private int damage, initiative;

        public Unit(String line, boolean immune) {
            //this.immuneSystem = immune;
            int index = line.indexOf(' ');
            membersAlive = Integer.parseInt(line.substring(0, index));
            line = line.substring(line.indexOf("with ") + "with ".length());
            index = line.indexOf(' ');
            individualHealth = Integer.parseInt(line.substring(0, index));
            line = line.substring(line.indexOf("points ") + "points ".length());
            immunities = new ArrayList<DamageType>();
            weaknesses = new ArrayList<DamageType>();
            if (line.startsWith("(")) {
                String[] types = line.substring(1, line.indexOf(')')).split("[;,] ?");
                boolean weak = true;
                for (String type : types) {
                    if (type.startsWith("weak to ")) {
                        type = type.substring("weak to ".length());
                        weak = true;
                    }
                    if (type.startsWith("immune to ")) {
                        type = type.substring("immune to ".length());
                        weak = false;
                    }
                    DamageType dType = DamageType.valueOf(DamageType.class, type.toUpperCase());
                    if (weak)
                        weaknesses.add(dType);
                    else immunities.add(dType);
                }
            }
            line = line.substring(line.indexOf("does ") + "does ".length());
            index = line.indexOf(' ');
            damage = Integer.parseInt(line.substring(0, index));
            line = line.substring(index + 1);
            index = line.indexOf(' ');
            damageType = DamageType.valueOf(DamageType.class, line.substring(0, index).toUpperCase());
            line = line.substring(line.lastIndexOf(' ') + 1);
            initiative = Integer.parseInt(line);
            target = null;
            name = (immune ? "Immune" + immuneCount++ : "Infection" + infectionCount++);
        }

        public String toString() {
            return name + " with " + membersAlive + " soldiers left.";
        }

        public Unit attackIfAlive() {
            if (target == null || membersAlive <= 0)
                return null;
            int damage = target.testDamage(getEffectivePower(), damageType);
            int killing = damage / target.individualHealth;//Partial damage is ignored
            target.membersAlive -= killing;
            killsLastRound += killing;
            //System.out.println(name + " attacks " + target.name + ", killing " + killing + " soldiers");
            return target;
        }

        public int testDamage(int effectivePower, DamageType type) {
            if (immunities.contains(type))
                return 0;
            if (weaknesses.contains(type))
                return 2 * effectivePower;
            return effectivePower;
        }

        public int getEffectivePower() {
            return membersAlive * damage;
        }
    }

    private enum DamageType {
        COLD, BLUDGEONING, FIRE, RADIATION, SLASHING
    }

    public static final void main(String[] args) {
        BufferedReader br = null;
        ArrayList<String> lines = new ArrayList<String>();
        lines.add("Immune System:");
        lines.add("17 units each with 5390 hit points (weak to radiation, bludgeoning) with an attack that does 4507 fire damage at initiative 2");
        lines.add("989 units each with 1274 hit points (immune to fire; weak to bludgeoning, slashing) with an attack that does 25 slashing damage at initiative 3");
        lines.add("");
        lines.add("Infection:");
        lines.add("801 units each with 4706 hit points (weak to radiation) with an attack that does 116 bludgeoning damage at initiative 1");
        lines.add("4485 units each with 2961 hit points (immune to radiation; weak to fire, cold) with an attack that does 12 slashing damage at initiative 4");
        Problem24 example = new Problem24(lines, 0);
        example.simulate();
        boolean infectionWins = true;
        int boost = 0;
        while (infectionWins) {
            ++boost;
            example = new Problem24(lines, boost);
            infectionWins = example.simulate();//simulate returns true if all immune system units died
        }
        System.out.println("Final boost was " + boost);
        //System.exit(0);
        lines.clear();
        try {
            br = new BufferedReader(new FileReader("/Users/justingoeres/Projects/advent-of-code-2018/day24/reddit.txt"));
            while (br.ready()) {
                lines.add(br.readLine());
            }
            Problem24 problem = new Problem24(lines, 0);
            problem.simulate();
            infectionWins = true;
            boost = 0;
            while (infectionWins) {
                ++boost;
                problem = new Problem24(lines, boost);
                infectionWins = problem.simulate();//simulate returns true if all immune system units died
            }
            System.out.println("Final boost was " + boost);
        } catch (IOException ioe) {
            System.out.println("Error is " + ioe.getMessage());
        } finally {
            if (br != null)
                try {
                    br.close();
                } catch (IOException e) {
                }
        }
    }
}