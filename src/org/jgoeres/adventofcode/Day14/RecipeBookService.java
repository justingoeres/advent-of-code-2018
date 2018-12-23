package org.jgoeres.adventofcode.Day14;

import java.util.ArrayList;

import static org.jgoeres.adventofcode.Day14.CircularLinkedList.Elf.ELF1;
import static org.jgoeres.adventofcode.Day14.CircularLinkedList.Elf.ELF2;

public class RecipeBookService {
    public static Integer getElfsSum(CircularLinkedList recipeBook) {
        Integer elf1Score = recipeBook.getElfCurrentValue(ELF1);
        Integer elf2Score = recipeBook.getElfCurrentValue(ELF2);

        return (elf1Score + elf2Score);
    }

    public static ArrayList<Integer> calculateNewRecipeValues(Integer elfsSum) {
        /*
        To create new recipes, the two Elves combine their current recipes. This creates new
        recipes from the digits of the sum of the current recipes' scores. With the current recipes'
        scores of 3 and 7, their sum is 10, and so two new recipes would be created: the first
        with score 1 and the second with score 0. If the current recipes' scores were 2 and 3,
        the sum, 5, would only create one recipe (with a score of 5) with its single digit.
         */
        ArrayList<Integer> recipeValues = new ArrayList<>();

        if (elfsSum < 10) { // sum is less than 10, create only one recipe.
            recipeValues.add(elfsSum);
        } else {    // sum is greater than 10, create two new recipes
            recipeValues.add(1);    // the first recipe will always be the "1" in e.g. "14"
            recipeValues.add(elfsSum - 10); // the second recipe is the ones digit
        }
        return recipeValues;
    }

    public static boolean addRecipesToBook(CircularLinkedList recipeBook, ArrayList<Integer> recipeValues) {
        boolean patternFound = false;

        for (Integer recipeValue : recipeValues) {
            patternFound |= recipeBook.addOnRightEnd(recipeValue);
        }
        return patternFound;
    }
}
