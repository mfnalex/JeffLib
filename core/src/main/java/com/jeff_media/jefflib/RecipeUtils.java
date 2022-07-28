package com.jeff_media.jefflib;

import com.google.common.base.Enums;
import com.jeff_media.jefflib.exceptions.InvalidRecipeException;
import com.jeff_media.jefflib.internal.cherokee.NotImplementedException;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.*;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * Crafting recipe related methods
 */
@UtilityClass
public class RecipeUtils {

    /**
     * Parses a crafting recipe from a configuration section (see examples/RecipeUtils_getRecipe.yml)
     *
     * @param section ConfigurationSection
     * @param key     NamespacedKey
     * @param result  Result
     * @return Crafting recipe
     */
    public static Recipe getRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        if (!section.isString("type") || section.getString("type").isEmpty()) {
            //System.out.println("TEST: " + section.getString("type"));
            throw new InvalidRecipeException("No recipe type defined");
        }

        final String type = section.getString("type", "unknown").toLowerCase(Locale.ROOT);

        if (!section.isList("ingredients") && !section.isConfigurationSection("ingredients")) {
            if (!requiresExactlyOneIngredient(type)) {
                throw new InvalidRecipeException("No recipe ingredients defined");
            }
            if (requiresExactlyOneIngredient(type) && !section.isSet("ingredient")) {
                throw new InvalidRecipeException("No recipe ingredient defined");
            }
        }

        switch (type) {
            case "shapeless":
                return getShapelessRecipe(section, key, result);
            case "shaped":
                return getShapedRecipe(section, key, result);
            case "blasting":
                return getBlastingRecipe(section, key, result);
            case "campfire":
                return getCampfireRecipe(section, key, result);
            case "furnace":
                return getFurnaceRecipe(section, key, result);
            case "merchant":
                return getMerchantRecipe(section, result);
            case "smithing":
                return getSmithingRecipe(section, key, result);
            case "smoking":
                return getSmokingRecipe(section, key, result);
            case "stonecutting":
                return getStonecuttingRecipe(section, key, result);
            default:
                throw new InvalidRecipeException("Invalid recipe type: " + section.getString("type", "unknown"));
        }
    }

    private static boolean requiresExactlyOneIngredient(final String type) {
        switch (type) {
            case "blasting":
            case "campfire":
            case "furnace":
            case "smoking":
            case "stonecutting":
                return true;
            default:
                return false;
        }
    }

    private static ShapelessRecipe getShapelessRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        final Collection<RecipeChoice> ingredients;

        if (section.isConfigurationSection("ingredients")) {
            ingredients = getRecipeChoices(Objects.requireNonNull(section.getConfigurationSection("ingredients"))).values();
        } else if (section.isList("ingredients")) {
            ingredients = getRecipeChoices(Objects.requireNonNull(section.getList("ingredients")));
        } else {
            throw new InvalidRecipeException("Could not parse recipe ingredients: neither List nor Map");
        }

        final ShapelessRecipe recipe = new ShapelessRecipe(key, result);
        ingredients.forEach(recipe::addIngredient);
        return recipe;
    }

    private static Recipe getShapedRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        if (!section.isConfigurationSection("ingredients")) {
            throw new InvalidRecipeException("'ingredients' must be a Map for shaped recipes");
        }
        if (!section.isList("shape")) {
            throw new InvalidRecipeException("No shape defined");
        }

        final List<String> shape = section.getStringList("shape");
        if (shape.size() > 3) {
            throw new InvalidRecipeException("'shape' cannot be longer than 3 lines");
        }
        shape.forEach((line) -> {
            if (line.length() > 3) {
                throw new InvalidRecipeException("Each line in 'shape' must not be longer than 3 characters");
            }
        });

        final ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(shape.toArray(new String[0])); // shape has to be called before the ingredients
        final Map<Character, RecipeChoice> ingredients = getRecipeChoices(Objects.requireNonNull(section.getConfigurationSection("ingredients")));
        ingredients.forEach(recipe::setIngredient);
        return recipe;
    }

    private static BlastingRecipe getBlastingRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        final AbstractFurnaceRecipe recipe = new AbstractFurnaceRecipe(section);
        return new BlastingRecipe(key, result, recipe.recipeChoice, recipe.experience, recipe.cookingTime);
    }

    private static CampfireRecipe getCampfireRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        final AbstractFurnaceRecipe recipe = new AbstractFurnaceRecipe(section);
        return new CampfireRecipe(key, result, recipe.recipeChoice, recipe.experience, recipe.cookingTime);
    }

    private static FurnaceRecipe getFurnaceRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        final AbstractFurnaceRecipe recipe = new AbstractFurnaceRecipe(section);
        return new FurnaceRecipe(key, result, recipe.recipeChoice, recipe.experience, recipe.cookingTime);
    }

    private static MerchantRecipe getMerchantRecipe(final ConfigurationSection section, final ItemStack result) {

        if (true) {
            // TODO: Implement. Can take exactly one or two ingredients.
            throw new NotImplementedException("Merchant trades are not implemented yet.");
        }

        final int maxUses;
        int uses;
        final boolean experienceReward;
        int villagerExperience = 0;
        float priceMultiplier = 0;

        if (!section.contains("max-uses")) {
            throw new InvalidRecipeException("'max-uses' is not defined");
        }
        if (!section.isInt("max-uses")) {
            throw new InvalidRecipeException("'max-uses' must be an integer");
        }
        maxUses = section.getInt("max-uses");
        uses = 0;
        if (section.contains("uses")) {
            if (section.isInt("uses")) {
                uses = section.getInt("uses");
            } else {
                throw new InvalidRecipeException("'uses' must be an integer when defined");
            }
        }
        if (section.isSet("experience-reward") && section.getBoolean("experience-reward")) {
            experienceReward = true;

            if (!section.isSet("villager-experience")) {
                throw new InvalidRecipeException("'villager-experience' must be defined when 'experience-reward' is true");
            }
            if (!section.isInt("villager-experience")) {
                throw new InvalidRecipeException("'villager-experience' must be an integer");
            }
            villagerExperience = section.getInt("villager-experience");

            if (!section.isSet("price-multiplier")) {
                throw new InvalidRecipeException("'price-multiplier' must be defined when 'experience-reward' is true");
            }
            if (!section.isDouble("price-multiplier") && !section.isInt("price-multiplier")) {
                throw new InvalidRecipeException("'price-multiplier' must be a float or integer");
            }
            priceMultiplier = (float) section.getDouble("price-multiplier");
        } else {
            experienceReward = false;
        }

        if (experienceReward) {
            return new MerchantRecipe(result, uses, maxUses, true, villagerExperience, priceMultiplier);
        } else {
            return new MerchantRecipe(result, uses, maxUses, false);
        }
    }

    private static SmithingRecipe getSmithingRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {

        final RecipeChoice base;
        final RecipeChoice addition;
        List<RecipeChoice> choices = null;

        if (section.isList("ingredients")) {
            choices = getRecipeChoices(Objects.requireNonNull(section.getList("ingredients")));
        } else if (section.isConfigurationSection("ingredients")) {
            choices = new ArrayList<>(getRecipeChoices(Objects.requireNonNull(section.getConfigurationSection("ingredients"))).values());
        }

        if (choices.size() != 2) {
            throw new InvalidRecipeException("'ingredients' must contain exactly two ingredients, found " + choices.size());
        }

        base = choices.get(0);
        addition = choices.get(1);

        return new SmithingRecipe(key, result, base, addition);
    }

    private static SmokingRecipe getSmokingRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        final AbstractFurnaceRecipe recipe = new AbstractFurnaceRecipe(section);
        return new SmokingRecipe(key, result, recipe.recipeChoice, recipe.experience, recipe.cookingTime);
    }

    private static StonecuttingRecipe getStonecuttingRecipe(final ConfigurationSection section, final NamespacedKey key, final ItemStack result) {
        return new StonecuttingRecipe(key, result, getRecipeChoice(section));
    }

    @Nonnull
    private static Map<Character, RecipeChoice> getRecipeChoices(final ConfigurationSection section) {
        final Map<Character, RecipeChoice> map = new HashMap<>();
        for (final String key : section.getKeys(false)) {
            if (key.length() != 1) {
                throw new InvalidRecipeException("Ingredient keys must be exactly one character long, found '" + key + "' instead");
            }
            final Character aChar = key.toCharArray()[0];
            if (map.containsKey(aChar)) {
                throw new InvalidRecipeException("Ingredient key '" + aChar + "' defined more than once");
            }
            if (section.isItemStack(key)) {
                map.put(aChar, new RecipeChoice.ExactChoice(section.getItemStack(key)));
            } else if (section.isString(key)) {
                final Material mat = Enums.getIfPresent(Material.class, section.getString(key).toUpperCase(Locale.ROOT)).orNull();
                if (mat == null) {
                    throw new InvalidRecipeException("Invalid material defined for ingredient key '" + aChar + "': " + section.getString(key));
                }
                map.put(aChar, new RecipeChoice.MaterialChoice(mat));
            } else {
                throw new InvalidRecipeException("Invalid ingredient defined for key '" + aChar + "': " + section.get(key));
            }
        }
        return map;
    }

    @Nonnull
    private static List<RecipeChoice> getRecipeChoices(final List<?> ingredients) {
        final List<RecipeChoice> list = new ArrayList<>();

        for (final Object item : ingredients) {
            if (item instanceof ItemStack) {
                list.add(new RecipeChoice.ExactChoice((ItemStack) item));
            } else if (item instanceof String) {
                final Material mat = Enums.getIfPresent(Material.class, ((String) item).toUpperCase()).orNull();
                if (mat == null) {
                    throw new InvalidRecipeException("Invalid ingredient: " + item);
                }
                list.add(new RecipeChoice.MaterialChoice(mat));
            } else {
                throw new InvalidRecipeException("Invalid ingredient: " + item);
            }
        }
        return list;
    }

    @Nonnull
    private static RecipeChoice getRecipeChoice(final ConfigurationSection section) {
        if (section.isSet("ingredients")) {
            if (section.isConfigurationSection("ingredients")) {
                final Map<Character, RecipeChoice> map = getRecipeChoices(section.getConfigurationSection("ingredients"));
                if (map.size() != 1) {
                    throw new InvalidRecipeException("'ingredients' must contain exactly one item, found " + map.size());
                }
                return map.values().stream().findFirst().get();
            } else if (section.isList("ingredients")) {
                final List<RecipeChoice> list = getRecipeChoices(section.getList("ingredients"));
                if (list.size() != 1) {
                    throw new InvalidRecipeException("'ingredients' must contain exactly one item, found " + list.size());
                }
            } else {
                throw new InvalidRecipeException("For this recipe type, 'ingredients' must either be a List or Map containing exactly one item");
            }
        } else if (section.isSet("ingredient")) {
            if (section.isItemStack("ingredient")) {
                return new RecipeChoice.ExactChoice(section.getItemStack("ingredient"));
            } else {
                final Material mat = Enums.getIfPresent(Material.class, section.getString("ingredient")).orNull();
                if (mat == null) {
                    throw new InvalidRecipeException("Invalid ingredient: " + section.getString("ingredient"));
                }
                return new RecipeChoice.MaterialChoice(mat);
            }
        }
        throw new InvalidRecipeException("No ingredient defined");
    }

    private static class AbstractFurnaceRecipe {
        private final RecipeChoice recipeChoice;
        private final float experience;
        private final int cookingTime;

        public AbstractFurnaceRecipe(final ConfigurationSection section) {
            if (!section.isSet("experience")) {
                experience = 0;
            } else {
                if (!section.isDouble("experience") && !section.isInt("experience")) {
                    throw new InvalidRecipeException("'experience' must be a float or integer");
                } else {
                    experience = (float) section.getDouble("experience");
                }
            }
            if (!section.isSet("cooking-time")) {
                throw new InvalidRecipeException("'cooking-time' is not defined");
            }
            if (!section.isInt("cooking-time")) {
                throw new InvalidRecipeException("'cooking-time' must be an integer");
            }
            cookingTime = section.getInt("cooking-time");
            recipeChoice = RecipeUtils.getRecipeChoice(section);
        }
    }
}
