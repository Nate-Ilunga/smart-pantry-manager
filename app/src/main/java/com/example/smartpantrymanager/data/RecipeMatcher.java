package com.example.smartpantrymanager.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The strict-matching rule — the core business logic of the app.
 *
 * A recipe is "suggested" only if EVERY ingredient it requires is present
 * in the pantry, in at least the required quantity. Missing even one
 * ingredient, or having too little of one, excludes the recipe.
 *
 * Matching is done in memory: the pantry and all recipe ingredients are
 * loaded once, then compared, rather than hitting the database per recipe.
 */
public class RecipeMatcher {

    /**
     * Returns the recipes the user can make right now, given their pantry.
     *
     * @param allRecipes      every recipe in the collection
     * @param allIngredients  every recipe-ingredient row, across all recipes
     * @param pantryItems     what the user currently has
     * @return the recipes whose every ingredient is satisfied by the pantry
     */
    public static List<Recipe> findMakeableRecipes(
            List<Recipe> allRecipes,
            List<RecipeIngredient> allIngredients,
            List<PantryItem> pantryItems) {

        // 1. Build a fast lookup of what the pantry holds:
        //    canonical ingredient name -> total quantity available.
        Map<String, Double> pantry = buildPantryMap(pantryItems);

        // 2. Group each recipe's required ingredients by recipe id,
        //    so we can check one recipe at a time.
        Map<Integer, List<RecipeIngredient>> byRecipe = groupByRecipe(allIngredients);

        // 3. Keep only the recipes where every ingredient is satisfied.
        List<Recipe> makeable = new ArrayList<>();
        for (Recipe recipe : allRecipes) {
            List<RecipeIngredient> required = byRecipe.get(recipe.id);

            // A recipe with no ingredient rows can't be meaningfully matched; skip it.
            if (required == null || required.isEmpty()) {
                continue;
            }

            if (pantrySatisfies(pantry, required)) {
                makeable.add(recipe);
            }
        }

        return makeable;
    }

    /**
     * Checks whether the pantry satisfies EVERY required ingredient.
     * Returns false as soon as one ingredient is missing or short —
     * this early exit is what makes the rule "strict".
     */
    private static boolean pantrySatisfies(
            Map<String, Double> pantry,
            List<RecipeIngredient> required) {

        for (RecipeIngredient ingredient : required) {
            String key = IngredientNormaliser.canonical(ingredient.name);
            Double available = pantry.get(key);

            // Missing entirely -> recipe fails.
            if (available == null) {
                return false;
            }

            // Present but not enough -> recipe fails.
            // (We compare quantities directly; both sides use the recipe's
            //  own units, which for this collection are consistent per ingredient.)
            if (available < ingredient.quantity) {
                return false;
            }
        }

        // Every ingredient was present in sufficient quantity.
        return true;
    }

    /**
     * Builds the pantry lookup. If the same ingredient appears twice
     * (e.g. two "milk" entries), their quantities are added together.
     */
    private static Map<String, Double> buildPantryMap(List<PantryItem> pantryItems) {
        Map<String, Double> map = new HashMap<>();
        for (PantryItem item : pantryItems) {
            String key = IngredientNormaliser.canonical(item.name);
            Double existing = map.get(key);
            map.put(key, (existing == null ? 0.0 : existing) + item.quantity);
        }
        return map;
    }

    /**
     * Groups all recipe-ingredient rows by their recipe id.
     */
    private static Map<Integer, List<RecipeIngredient>> groupByRecipe(
            List<RecipeIngredient> allIngredients) {
        Map<Integer, List<RecipeIngredient>> map = new HashMap<>();
        for (RecipeIngredient ingredient : allIngredients) {
            List<RecipeIngredient> list = map.get(ingredient.recipeId);
            if (list == null) {
                list = new ArrayList<>();
                map.put(ingredient.recipeId, list);
            }
            list.add(ingredient);
        }
        return map;
    }

    private RecipeMatcher() {
        // Utility class — not meant to be instantiated.
    }
}