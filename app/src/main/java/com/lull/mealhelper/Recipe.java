package com.lull.mealhelper;

import java.util.ArrayList;

/**
 * Created by Bob Lull on 9/26/2015.
 */
public class Recipe {
    private int RecipeID;
    private String RecipeName;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<RecipeStep> recipeSteps;

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<RecipeStep> getRecipeSteps() {
        return recipeSteps;
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps) {
        this.recipeSteps = recipeSteps;
    }

    public int getRecipeID() {
        return RecipeID;
    }

    public void setRecipeID(int recipeID) {
        RecipeID = recipeID;
    }

    public String getRecipeName() {
        return RecipeName;
    }

    public void setRecipeName(String recipeName) {
        RecipeName = recipeName;
    }
}
