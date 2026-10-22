package com.example.smartpantrymanager.data;

/**
 * Normalises ingredient names so matching is not broken by trivial
 * real-world differences: capitalisation, extra spaces, and simple
 * singular/plural forms ("tomato" vs "tomatoes").
 *
 * This is the "reasonably robust" matching the brief asks for — not a
 * full language solution, but enough that obvious mismatches don't slip through.
 */
public class IngredientNormaliser {

    /**
     * Reduces a name to a canonical form for comparison:
     * lowercased, trimmed, and de-pluralised.
     */
    public static String canonical(String raw) {
        if (raw == null) return "";

        String name = raw.trim().toLowerCase();
        if (name.isEmpty()) return "";

        // Collapse any run of spaces into one, so "baked   beans" == "baked beans".
        name = name.replaceAll("\\s+", " ");

        return singularise(name);
    }

    /**
     * Converts a simple English plural to its singular form.
     * Handles the common cases our recipes actually use; it does not
     * try to cover every irregular word in the language.
     */
    private static String singularise(String word) {
        // Irregulars worth handling explicitly.
        switch (word) {
            case "leaves": return "leaf";
            case "potatoes": return "potato";
            case "tomatoes": return "tomato";
            default: break;
        }

        // "cherries" -> "cherry", "berries" -> "berry"
        if (word.endsWith("ies") && word.length() > 3) {
            return word.substring(0, word.length() - 3) + "y";
        }

        // "boxes", "dishes" -> drop the "es"
        if (word.endsWith("es") && word.length() > 2
                && (word.endsWith("ches") || word.endsWith("shes")
                || word.endsWith("xes") || word.endsWith("ses"))) {
            return word.substring(0, word.length() - 2);
        }

        // General plural: "eggs" -> "egg", "onions" -> "onion".
        // Guard against short words and words ending in "ss" ("glass").
        if (word.endsWith("s") && !word.endsWith("ss") && word.length() > 3) {
            return word.substring(0, word.length() - 1);
        }

        return word;
    }

    private IngredientNormaliser() {
        // Utility class — not meant to be instantiated.
    }
}