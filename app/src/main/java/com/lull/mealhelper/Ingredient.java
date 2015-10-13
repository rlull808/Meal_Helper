package com.lull.mealhelper;

/**
 * Created by Bob Lull on 9/27/2015.
 */
public class Ingredient {

    private int ingredientID;
    private int mealID;
    private int ingredientNum;
    private String ingredientTitle;
    private String ingredientDesc;

    public Ingredient(){
        ingredientID = -1;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }

    public int getIngredientNum() {
        return ingredientNum;
    }

    public void setIngredientNum(int ingredientNum) {
        this.ingredientNum = ingredientNum;
    }

    public String getIngredientTitle() {
        return ingredientTitle;
    }

    public void setIngredientTitle(String ingredientTitle) {
        this.ingredientTitle = ingredientTitle;
    }

    public String getIngredientDesc() {
        return ingredientDesc;
    }

    public void setIngredientDesc(String ingredientDesc) {
        this.ingredientDesc = ingredientDesc;
    }

}


