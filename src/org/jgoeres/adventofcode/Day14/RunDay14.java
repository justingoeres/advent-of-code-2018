package org.jgoeres.adventofcode.Day14;

import java.util.ArrayList;

import static org.jgoeres.adventofcode.Day14.CircularLinkedList.Elf.ELF1;
import static org.jgoeres.adventofcode.Day14.CircularLinkedList.Elf.ELF2;

public class RunDay14 {
    static final Integer puzzleInput = 765071; // << From problem webpage.
    static final boolean PRINT_LIST = true;

    public static void problem14A() {
        /*
        Only two recipes are on the board: the first recipe got a score of 3, the second, 7.
        Each of the two Elves has a current recipe: the first Elf starts with the first recipe,
        and the second Elf starts with the second recipe.

        To create new recipes, the two Elves combine their current recipes. This creates new
        recipes from the digits of the sum of the current recipes' scores. With the current recipes'
        scores of 3 and 7, their sum is 10, and so two new recipes would be created: the first
        with score 1 and the second with score 0. If the current recipes' scores were 2 and 3,
        the sum, 5, would only create one recipe (with a score of 5) with its single digit.

        The new recipes are added to the end of the scoreboard in the order they are created.
        So, after the first round, the scoreboard is 3, 7, 1, 0.

        After all new recipes are added to the scoreboard, each Elf picks a new current recipe.
        To do this, the Elf steps forward through the scoreboard a number of recipes equal to
        1 plus the score of their current recipe. So, after the first round, the first Elf moves
        forward 1 + 3 = 4 times, while the second Elf moves forward 1 + 7 = 8 times. If they run
        out of recipes, they loop back around to the beginning. After the first round, both Elves
        happen to loop around until they land on the same recipe that they had in the beginning;
        in general, they will move to different recipes.
        */

        System.out.println("=== DAY 14A ===");

        CircularLinkedList recipeBook = new CircularLinkedList(3); // Start with a 3...
        recipeBook.addOnRightEnd(7); // ...and a 7.
        // Move Elf2 to the new node
        recipeBook.next(ELF2);

        int round = 0;
        int NUMBER_OF_ROUNDS = 10;

        while (round < NUMBER_OF_ROUNDS) {
            // Calculate new recipes
            Integer currentSum = RecipeBookService.getElfsSum(recipeBook);
            ArrayList<Integer> newRecipeValues = RecipeBookService.calculateNewRecipeValues(currentSum);
            RecipeBookService.addRecipesToBook(recipeBook, newRecipeValues);

            // Move the elves (both by ONE *PLUS* their current value.
            recipeBook.nextByN(ELF1, (1 + recipeBook.getElfCurrentValue(ELF1)));
            recipeBook.nextByN(ELF2, (1 + recipeBook.getElfCurrentValue(ELF2)));

            if (PRINT_LIST) {
                System.out.println(recipeBook.printList());
            }
            round++;
        }
    }


    public static void problem14B() {
        /*
        Problem Description
         */

        System.out.println("=== DAY 1BA ===");

    }

}
