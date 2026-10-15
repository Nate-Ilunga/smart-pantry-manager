package com.example.smartpantrymanager.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Populates the recipe collection the first time the app runs.
 *
 * Seeding is skipped if recipes already exist, so reopening the app
 * never creates duplicates. Must be called from a background thread —
 * Room blocks database access on the main thread.
 */
public class DatabaseSeeder {

    /**
     * Inserts the starter recipe collection if the database is empty.
     */
    public static void seedIfEmpty(AppDatabase db) {
        RecipeDao recipeDao = db.recipeDao();

        // Already seeded — nothing to do.
        if (recipeDao.recipeCount() > 0) {
            return;
        }

        // --- South African recipes ---

        addRecipe(recipeDao,
                new Recipe(
                        "Chakalaka",
                        "Spicy South African vegetable relish",
                        "1. Heat the oil in a pan over medium heat.\n" +
                                "2. Fry the onion until soft and translucent.\n" +
                                "3. Add the curry powder and chilli, stir for one minute.\n" +
                                "4. Add the grated carrot and chopped pepper, cook for five minutes.\n" +
                                "5. Stir in the tinned tomato and baked beans.\n" +
                                "6. Simmer for ten minutes, season with salt, and serve warm or cold.",
                        25),
                Arrays.asList(
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"carrot", "2", "pcs"},
                        new String[]{"green pepper", "1", "pcs"},
                        new String[]{"tomato", "400", "g"},
                        new String[]{"baked beans", "400", "g"},
                        new String[]{"curry powder", "2", "tsp"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe(
                        "Bunny Chow",
                        "Durban curry served in a hollowed loaf",
                        "1. Fry the onion in oil until golden.\n" +
                                "2. Add the garlic, ginger and curry powder, cook for two minutes.\n" +
                                "3. Add the chicken pieces and brown on all sides.\n" +
                                "4. Stir in the tomato and potato with a cup of water.\n" +
                                "5. Simmer covered for thirty minutes until the potato is tender.\n" +
                                "6. Hollow out the bread, spoon the curry inside, and top with the bread lid.",
                        50),
                Arrays.asList(
                        new String[]{"bread", "1", "pcs"},
                        new String[]{"chicken", "500", "g"},
                        new String[]{"potato", "2", "pcs"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"tomato", "2", "pcs"},
                        new String[]{"garlic", "3", "pcs"},
                        new String[]{"ginger", "1", "tsp"},
                        new String[]{"curry powder", "3", "tsp"},
                        new String[]{"cooking oil", "30", "ml"}
                ));

        addRecipe(recipeDao,
                new Recipe(
                        "Vetkoek with Mince",
                        "Fried dough buns filled with savoury mince",
                        "1. Mix the flour, yeast, sugar and salt in a bowl.\n" +
                                "2. Add warm water gradually and knead into a soft dough.\n" +
                                "3. Cover and leave to rise for one hour.\n" +
                                "4. Fry the onion, then brown the mince with the curry powder.\n" +
                                "5. Add the tomato and simmer for fifteen minutes.\n" +
                                "6. Shape the dough into balls and deep-fry until golden on both sides.\n" +
                                "7. Split each vetkoek and fill with the mince.",
                        90),
                Arrays.asList(
                        new String[]{"flour", "500", "g"},
                        new String[]{"yeast", "10", "g"},
                        new String[]{"sugar", "1", "tsp"},
                        new String[]{"salt", "1", "tsp"},
                        new String[]{"beef mince", "500", "g"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"tomato", "2", "pcs"},
                        new String[]{"curry powder", "2", "tsp"},
                        new String[]{"cooking oil", "500", "ml"}
                ));

        addRecipe(recipeDao,
                new Recipe(
                        "Boerewors Roll",
                        "Grilled farmer's sausage in a roll with onion relish",
                        "1. Grill the boerewors over medium heat for fifteen minutes, turning often.\n" +
                                "2. Slice the onion and fry slowly until soft and caramelised.\n" +
                                "3. Add the chopped tomato to the onion and cook for five minutes.\n" +
                                "4. Slice the rolls open and warm them briefly on the grill.\n" +
                                "5. Place the boerewors in each roll and top with the relish.",
                        25),
                Arrays.asList(
                        new String[]{"boerewors", "500", "g"},
                        new String[]{"bread roll", "4", "pcs"},
                        new String[]{"onion", "2", "pcs"},
                        new String[]{"tomato", "2", "pcs"},
                        new String[]{"cooking oil", "15", "ml"}
                ));

        // --- Breakfast ---

        addRecipe(recipeDao,
                new Recipe("Scrambled Eggs", "Soft creamy eggs on toast",
                        "1. Crack the eggs into a bowl and whisk with the milk.\n" +
                                "2. Melt the butter in a pan over low heat.\n" +
                                "3. Pour in the eggs and stir slowly as they set.\n" +
                                "4. Remove from the heat while still slightly wet.\n" +
                                "5. Season with salt and serve on toasted bread.", 10),
                Arrays.asList(
                        new String[]{"egg", "3", "pcs"},
                        new String[]{"milk", "30", "ml"},
                        new String[]{"butter", "15", "g"},
                        new String[]{"bread", "2", "pcs"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("French Toast", "Egg-soaked bread fried until golden",
                        "1. Whisk the eggs, milk, sugar and cinnamon in a shallow dish.\n" +
                                "2. Soak each slice of bread for about twenty seconds per side.\n" +
                                "3. Melt the butter in a pan over medium heat.\n" +
                                "4. Fry each slice until golden on both sides.\n" +
                                "5. Serve warm.", 15),
                Arrays.asList(
                        new String[]{"bread", "4", "pcs"},
                        new String[]{"egg", "2", "pcs"},
                        new String[]{"milk", "120", "ml"},
                        new String[]{"sugar", "1", "tsp"},
                        new String[]{"cinnamon", "1", "tsp"},
                        new String[]{"butter", "20", "g"}
                ));

        addRecipe(recipeDao,
                new Recipe("Oatmeal", "Warm oats cooked in milk",
                        "1. Bring the milk to a gentle simmer in a small pot.\n" +
                                "2. Stir in the oats and a pinch of salt.\n" +
                                "3. Cook for five minutes, stirring often.\n" +
                                "4. Sweeten with sugar and serve hot.", 10),
                Arrays.asList(
                        new String[]{"oats", "80", "g"},
                        new String[]{"milk", "300", "ml"},
                        new String[]{"sugar", "1", "tsp"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Cheese Omelette", "Folded eggs with melted cheese",
                        "1. Whisk the eggs with a pinch of salt.\n" +
                                "2. Melt the butter in a non-stick pan over medium heat.\n" +
                                "3. Pour in the eggs and let the base set.\n" +
                                "4. Scatter the grated cheese over one half.\n" +
                                "5. Fold the omelette over and slide onto a plate.", 10),
                Arrays.asList(
                        new String[]{"egg", "3", "pcs"},
                        new String[]{"cheese", "50", "g"},
                        new String[]{"butter", "15", "g"},
                        new String[]{"salt", "1", "tsp"}
                ));

        // --- Pasta ---

        addRecipe(recipeDao,
                new Recipe("Spaghetti Bolognese", "Slow-simmered beef and tomato sauce",
                        "1. Boil the spaghetti in salted water until just tender.\n" +
                                "2. Fry the onion and garlic in oil until soft.\n" +
                                "3. Add the beef mince and brown it thoroughly.\n" +
                                "4. Stir in the tinned tomato and simmer for twenty minutes.\n" +
                                "5. Season with salt and serve the sauce over the drained pasta.", 40),
                Arrays.asList(
                        new String[]{"spaghetti", "400", "g"},
                        new String[]{"beef mince", "500", "g"},
                        new String[]{"tomato", "400", "g"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"garlic", "2", "pcs"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Pasta Carbonara", "Creamy egg and bacon pasta",
                        "1. Boil the pasta in salted water until just tender.\n" +
                                "2. Fry the bacon until crisp.\n" +
                                "3. Whisk the eggs with the grated cheese and black pepper.\n" +
                                "4. Drain the pasta and return it to the warm pot off the heat.\n" +
                                "5. Stir through the egg mixture quickly so it thickens without scrambling.\n" +
                                "6. Fold in the bacon and serve immediately.", 25),
                Arrays.asList(
                        new String[]{"spaghetti", "400", "g"},
                        new String[]{"bacon", "200", "g"},
                        new String[]{"egg", "3", "pcs"},
                        new String[]{"cheese", "80", "g"},
                        new String[]{"black pepper", "1", "tsp"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Tomato Pasta", "Simple garlic and tomato pasta",
                        "1. Boil the pasta in salted water until tender.\n" +
                                "2. Gently fry the garlic in olive oil without browning it.\n" +
                                "3. Add the tinned tomato and simmer for fifteen minutes.\n" +
                                "4. Season with salt and toss the drained pasta through the sauce.", 25),
                Arrays.asList(
                        new String[]{"pasta", "400", "g"},
                        new String[]{"tomato", "400", "g"},
                        new String[]{"garlic", "3", "pcs"},
                        new String[]{"olive oil", "30", "ml"},
                        new String[]{"salt", "1", "tsp"}
                ));

        // --- One-pot mains ---

        addRecipe(recipeDao,
                new Recipe("Chicken Curry", "Mild tomato-based chicken curry",
                        "1. Fry the onion in oil until golden.\n" +
                                "2. Add the garlic and curry powder and cook for two minutes.\n" +
                                "3. Brown the chicken pieces in the spice mixture.\n" +
                                "4. Add the tinned tomato and simmer covered for thirty minutes.\n" +
                                "5. Season with salt and serve with rice.", 45),
                Arrays.asList(
                        new String[]{"chicken", "600", "g"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"garlic", "3", "pcs"},
                        new String[]{"tomato", "400", "g"},
                        new String[]{"curry powder", "3", "tsp"},
                        new String[]{"rice", "300", "g"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Vegetable Stir-Fry", "Quick mixed vegetables with soy sauce",
                        "1. Heat the oil in a wok or wide pan over high heat.\n" +
                                "2. Add the garlic and stir for thirty seconds.\n" +
                                "3. Add the carrot, pepper and cabbage and stir-fry for five minutes.\n" +
                                "4. Pour in the soy sauce and toss to coat.\n" +
                                "5. Serve over rice.", 20),
                Arrays.asList(
                        new String[]{"carrot", "2", "pcs"},
                        new String[]{"green pepper", "1", "pcs"},
                        new String[]{"cabbage", "200", "g"},
                        new String[]{"garlic", "2", "pcs"},
                        new String[]{"soy sauce", "30", "ml"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"rice", "300", "g"}
                ));

        addRecipe(recipeDao,
                new Recipe("Chilli con Carne", "Beef and bean chilli",
                        "1. Fry the onion and garlic in oil until soft.\n" +
                                "2. Add the beef mince and brown it.\n" +
                                "3. Stir in the chilli powder and cook for one minute.\n" +
                                "4. Add the tinned tomato and kidney beans.\n" +
                                "5. Simmer for twenty-five minutes and season with salt.", 40),
                Arrays.asList(
                        new String[]{"beef mince", "500", "g"},
                        new String[]{"kidney beans", "400", "g"},
                        new String[]{"tomato", "400", "g"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"garlic", "2", "pcs"},
                        new String[]{"chilli powder", "2", "tsp"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Lentil Soup", "Hearty lentil and vegetable soup",
                        "1. Fry the onion and carrot in oil for five minutes.\n" +
                                "2. Add the garlic and cook for one more minute.\n" +
                                "3. Stir in the lentils and tinned tomato.\n" +
                                "4. Pour in the stock and bring to a boil.\n" +
                                "5. Simmer for thirty minutes until the lentils are soft.\n" +
                                "6. Season with salt and serve.", 45),
                Arrays.asList(
                        new String[]{"lentils", "250", "g"},
                        new String[]{"carrot", "2", "pcs"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"garlic", "2", "pcs"},
                        new String[]{"tomato", "400", "g"},
                        new String[]{"stock cube", "1", "pcs"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"salt", "1", "tsp"}
                ));

        // --- Comfort food ---

        addRecipe(recipeDao,
                new Recipe("Macaroni and Cheese", "Baked pasta in a cheese sauce",
                        "1. Boil the macaroni until just tender and drain.\n" +
                                "2. Melt the butter in a pot and stir in the flour to form a paste.\n" +
                                "3. Add the milk gradually, whisking until the sauce thickens.\n" +
                                "4. Stir in most of the grated cheese and season with salt.\n" +
                                "5. Combine with the macaroni, top with the remaining cheese.\n" +
                                "6. Bake at 180C for twenty minutes until golden.", 45),
                Arrays.asList(
                        new String[]{"macaroni", "400", "g"},
                        new String[]{"cheese", "200", "g"},
                        new String[]{"milk", "500", "ml"},
                        new String[]{"butter", "50", "g"},
                        new String[]{"flour", "50", "g"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Tomato Soup", "Smooth roasted tomato soup",
                        "1. Fry the onion and garlic in oil until soft.\n" +
                                "2. Add the tinned tomato and the stock.\n" +
                                "3. Simmer for twenty minutes.\n" +
                                "4. Blend until smooth.\n" +
                                "5. Season with salt and serve with bread.", 30),
                Arrays.asList(
                        new String[]{"tomato", "800", "g"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"garlic", "2", "pcs"},
                        new String[]{"stock cube", "1", "pcs"},
                        new String[]{"cooking oil", "30", "ml"},
                        new String[]{"bread", "2", "pcs"},
                        new String[]{"salt", "1", "tsp"}
                ));

        addRecipe(recipeDao,
                new Recipe("Pancakes", "Thin pan-fried pancakes",
                        "1. Whisk the flour, eggs and milk into a smooth batter.\n" +
                                "2. Stir in the sugar and a pinch of salt.\n" +
                                "3. Heat a little butter in a non-stick pan.\n" +
                                "4. Pour in a thin layer of batter and swirl to coat the pan.\n" +
                                "5. Cook until the edges lift, then flip and cook the other side.\n" +
                                "6. Repeat with the remaining batter.", 25),
                Arrays.asList(
                        new String[]{"flour", "250", "g"},
                        new String[]{"egg", "2", "pcs"},
                        new String[]{"milk", "500", "ml"},
                        new String[]{"sugar", "2", "tsp"},
                        new String[]{"butter", "30", "g"},
                        new String[]{"salt", "1", "tsp"}
                ));

        // --- Quick meals ---

        addRecipe(recipeDao,
                new Recipe("Grilled Cheese Sandwich", "Toasted sandwich with melted cheese",
                        "1. Butter one side of each slice of bread.\n" +
                                "2. Place the cheese between the unbuttered sides.\n" +
                                "3. Fry in a dry pan over medium heat until golden.\n" +
                                "4. Flip and toast the other side until the cheese melts.", 10),
                Arrays.asList(
                        new String[]{"bread", "2", "pcs"},
                        new String[]{"cheese", "60", "g"},
                        new String[]{"butter", "20", "g"}
                ));

        addRecipe(recipeDao,
                new Recipe("Cheese Quesadilla", "Folded tortilla with melted cheese and onion",
                        "1. Finely slice the onion and fry until soft.\n" +
                                "2. Scatter the cheese and onion over half of the tortilla.\n" +
                                "3. Fold the tortilla over the filling.\n" +
                                "4. Toast in a dry pan for two minutes per side.\n" +
                                "5. Cut into wedges and serve.", 15),
                Arrays.asList(
                        new String[]{"tortilla", "2", "pcs"},
                        new String[]{"cheese", "100", "g"},
                        new String[]{"onion", "1", "pcs"},
                        new String[]{"cooking oil", "15", "ml"}
                ));
    }

    /**
     * Inserts one recipe plus its ingredient rows.
     *
     * The recipe is inserted first because its generated id is needed
     * as the foreign key on every ingredient row.
     *
     * Each ingredient arrives as {name, quantity, unit} and is normalised
     * before storage so matching is not broken by casing or spacing.
     */
    private static void addRecipe(RecipeDao dao, Recipe recipe, List<String[]> rawIngredients) {
        int recipeId = (int) dao.insertRecipe(recipe);

        List<RecipeIngredient> ingredients = new ArrayList<>();
        for (String[] raw : rawIngredients) {
            String name = raw[0].trim().toLowerCase();
            double quantity = Double.parseDouble(raw[1]);
            String unit = raw[2].trim().toLowerCase();
            ingredients.add(new RecipeIngredient(recipeId, name, quantity, unit));
        }

        dao.insertIngredients(ingredients);
    }
}