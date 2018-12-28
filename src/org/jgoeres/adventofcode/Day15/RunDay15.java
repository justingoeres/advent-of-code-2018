package org.jgoeres.adventofcode.Day15;

public class RunDay15 {
    static final String pathToInputs = "day15/input.txt";
    static final boolean DEBUG_PRINT_EACH_TURN = true;

    public static void problem15A() {
        /*
        Problem Description
        */
        System.out.println("=== DAY 15A ===");

        Battle battle = new Battle(pathToInputs);

        int t = 0;
        while (!battle.isOver()) {
            System.out.println("\n============ TURN #"+t+" ============");
            battle.doTimerTick();
            if (DEBUG_PRINT_EACH_TURN){
                battle.printBattle();
                battle.printArmies();
            }
            t++;
        }
        System.out.println(battle);
    }

    public static void problem15B() {

        /*
        Problem Description
        */
        System.out.println("=== DAY 15B ===");

    }
}
