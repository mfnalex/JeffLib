package de.jeff_media.jefflib;

import com.google.common.base.Enums;
import de.jeff_media.jefflib.exceptions.InvalidRecipeException;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.*;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;

public class RecipeUtils {

    public static Recipe getRecipe(ConfigurationSection section, NamespacedKey key, ItemStack result) {
        if(!section.isString("type") || section.getString("type").isEmpty()) {
            throw new InvalidRecipeException("No recipe type defined");
        }

        if(!section.isList("ingredients") && section.isConfigurationSection("ingredients")) {
            throw new InvalidRecipeException("No recipe ingredients defined");
        }

        switch(section.getString("type","unknown").toLowerCase(Locale.ROOT)) {
            case "shapeless":
                return getShapelessRecipe(section, key, result);
            case "shaped":
                return getShapedRecipe(section, key, result);
            default:
                throw new InvalidRecipeException("Invalid recipe type: " + section.getString("type","unknown"));
        }
    }

    private static Recipe getShapedRecipe(ConfigurationSection section, NamespacedKey key, ItemStack result) {
        if(!section.isConfigurationSection("ingredients")) {
            throw new InvalidRecipeException("'ingredients' must be a Map for shaped recipes");
        }
        Map<Character,RecipeChoice> ingredients = getRecipeChoices(Objects.requireNonNull(section.getConfigurationSection("ingredients")));
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        ingredients.forEach(recipe::setIngredient);
        return recipe;
    }

    private static ShapelessRecipe getShapelessRecipe(ConfigurationSection section, NamespacedKey key, ItemStack result) {
        Collection<RecipeChoice> ingredients = null;

        if(section.isConfigurationSection("ingredients")) {
            ingredients = getRecipeChoices(Objects.requireNonNull(section.getConfigurationSection("ingredients"))).values();
        } else if(section.isList("ingredients")) {
            ingredients = getRecipeChoices(Objects.requireNonNull(section.getList("ingredients")));
        } else {
            throw new InvalidRecipeException("Could not parse recipe ingredients: neither List nor Map");
        }

        ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        ingredients.forEach(recipe::addIngredient);
        return recipe;
    }

    @NotNull private static Map<Character,RecipeChoice> getRecipeChoices(ConfigurationSection section) {
        HashMap<Character,RecipeChoice> list = new HashMap<>();
        for(String key : section.getKeys(false)) {
            if(key.length()!=1) {
                throw new InvalidRecipeException("Ingredient keys must be exactly one character long, found '" + key + "' instead");
            }
            Character aChar = key.toCharArray()[0];
            if(list.containsKey(aChar)) {
                throw new InvalidRecipeException("Ingredient key '" + aChar +"' defined more than once");
            }
            if(section.isItemStack(key)) {
                list.put(aChar,new RecipeChoice.ExactChoice(section.getItemStack(key)));
            } else if(section.isString(key)) {
                Material mat = Enums.getIfPresent(Material.class, section.getString(key).toUpperCase(Locale.ROOT)).orNull();
                if(mat == null) {
                    throw new InvalidRecipeException("Invalid material defined for ingredient key '"+aChar+"': " + section.getString(key));
                }
                list.put(aChar, new RecipeChoice.MaterialChoice(mat));
            } else {
                throw new InvalidRecipeException("Invalid ingredient defined for key '" + aChar+"': " + section.get(key));
            }
        }
        return list;
    }

    @NotNull private static List<RecipeChoice> getRecipeChoices(List<?> ingredients) {
        List<RecipeChoice> list = new ArrayList<>();

        for(Object item : ingredients) {
            if(item instanceof ItemStack) {
                list.add(new RecipeChoice.ExactChoice((ItemStack) item));
            } else if(item instanceof String) {
                Material mat = Enums.getIfPresent(Material.class, ((String) item).toUpperCase()).orNull();
                if(mat == null) {
                    throw new InvalidRecipeException("Invalid ingredient: " + item);
                }
                list.add(new RecipeChoice.MaterialChoice(mat));
            } else {
                throw new InvalidRecipeException("Invalid ingredient: " + item);
            }
        }

        return list;
    }

}
