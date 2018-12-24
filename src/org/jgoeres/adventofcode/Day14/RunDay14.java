package org.jgoeres.adventofcode.Day14;

import java.util.ArrayList;

import static org.jgoeres.adventofcode.Day14.CircularLinkedList.Elf.ELF1;
import static org.jgoeres.adventofcode.Day14.CircularLinkedList.Elf.ELF2;

public class RunDay14 {
    static final Integer puzzleInput = 765071; // << From problem webpage.
    static final boolean PRINT_LIST = false;

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

        int NUMBER_OF_RECIPES = puzzleInput;
        int NUMBER_TO_EXTRACT = 10;

        while (recipeBook.getSize() < (NUMBER_OF_RECIPES + NUMBER_TO_EXTRACT)) { // Keep going until we have ten recipes PAST our target number.
            // Calculate new recipes
            Integer currentSum = RecipeBookService.getElfsSum(recipeBook);
            ArrayList<Integer> newRecipeValues = RecipeBookService.calculateNewRecipeValues(currentSum);
            recipeBook.addRecipesToBook(newRecipeValues);

            // Move the elves (both by ONE *PLUS* their current value.
            recipeBook.nextByN(ELF1, (1 + recipeBook.getElfCurrentValue(ELF1)));
            recipeBook.nextByN(ELF2, (1 + recipeBook.getElfCurrentValue(ELF2)));

//            if (PRINT_LIST) {
//                System.out.println(recipeBook.printList());
//            }
        }

        // Output the results.
        // Hijack Elf #1 to retrieve the recipe values we want.
        recipeBook.resetElfToRoot(ELF1);
        // fast-forward Elf #1 to the section we're interested it.
        recipeBook.nextByN(ELF1, NUMBER_OF_RECIPES);

        String result = "";
        // Extract the values of the next N recipes
        for (int i = 0; i < NUMBER_TO_EXTRACT; i++) {
            result += recipeBook.getElfCurrentValue(ELF1).toString();
            recipeBook.next(ELF1);
        }

        System.out.println("After creating " + NUMBER_OF_RECIPES
                + " recipes, the next " + NUMBER_TO_EXTRACT
                + " will be " + result);

        // Examples:
        // After creating 9 recipes, the next 10 will be 5158916779
        // After creating 5 recipes, the next 10 will be 0124515891
        // After creating 18 recipes, the next 10 will be 9251071085
        // After creating 2018 recipes, the next 10 will be 5941429882

        // Answer:
        // After creating 765071 recipes, the next 10 will be 3171123923
    }

    public static void problem14B() {
        /*
        As it turns out, you got the Elves' plan backwards.
        They actually want to know how many recipes appear on the scoreboard
        to the left of the first recipes whose scores are the digits from your puzzle input.

            51589 first appears after 9 recipes.
            01245 first appears after 5 recipes.
            92510 first appears after 18 recipes.
            59414 first appears after 2018 recipes.

        How many recipes appear on the scoreboard to the left of the score
        sequence in your puzzle input?
         */

        System.out.println("=== DAY 14B ===");

//        String patternToFind = "51589"; // 9 recipes
//        String patternToFind = "01245"; // 5 recipes
//        String patternToFind = "92510"; // 18 recipes
//        String patternToFind = "59414"; // 2018 recipes
        String patternToFind = puzzleInput.toString();

        RecipeBookWithPatternRecognizer recipeBook = new RecipeBookWithPatternRecognizer(3, patternToFind); // Start with a 3...
        recipeBook.addOnRightEnd(7); // ...and a 7.

        // Move Elf2 to the new node
        recipeBook.next(ELF2);

        while (true) { // Keep going until we break below
            Integer currentSum = RecipeBookService.getElfsSum(recipeBook);
            ArrayList<Integer> newRecipeValues = RecipeBookService.calculateNewRecipeValues(currentSum);

            recipeBook.addRecipesToBook(newRecipeValues);

            // Move the elves (both by ONE *PLUS* their current value.
            recipeBook.nextByN(ELF1, (1 + recipeBook.getElfCurrentValue(ELF1)));
            recipeBook.nextByN(ELF2, (1 + recipeBook.getElfCurrentValue(ELF2)));

            if (PRINT_LIST) {
                System.out.println(recipeBook.printList());
            }

            if (recipeBook.isPatternFound()) break;
        }

        System.out.println("Pattern " + patternToFind + " found after "
                + (recipeBook.getSize() - patternToFind.length()) + " recipes");

        // Examples:
        // Pattern 765071 found after 9 recipes
        // Pattern 01245 found after 5 recipes
        // Pattern 92510 found after 18 recipes
        // Pattern 59414 found after 2018 recipes

        // Answer:
        // Pattern 765071 found after 20353748 recipes
    }

}
