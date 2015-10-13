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
    private long timerAmount;

    public Recipe(){
        this.setTimerAmount(0);
    }
    public Recipe(String recipeName){
        this.setRecipeName(recipeName);
        this.setTimerAmount(0);
    }
    public Recipe(int recipeID, String recipeName, ArrayList<Ingredient> ingredients,
                  ArrayList<RecipeStep> steps){
        this.setRecipeID(recipeID);
        this.setRecipeName(recipeName);
        this.setIngredients(ingredients);
        this.setRecipeSteps(steps);
        this.setTimerAmount(0);
    }
    public Recipe(int recipeID, String recipeName, ArrayList<Ingredient> ingredients,
                  ArrayList<RecipeStep> steps, long timerAmount){
        this.setRecipeID(recipeID);
        this.setRecipeName(recipeName);
        this.setIngredients(ingredients);
        this.setRecipeSteps(steps);
        this.setTimerAmount(timerAmount);
    }
    public long getTimerAmount() {
        return timerAmount;
    }

    public void setTimerAmount(long timerAmount) {
        this.timerAmount = timerAmount;
    }

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
