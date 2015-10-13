package com.lull.mealhelper;

import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Bob Lull on 9/26/2015.
 */
public class RecipeStep {
    private int stepID;
    private int stepMealID;
    private int stepNumber;
    private String stepInstruction;

    public RecipeStep(){
        this.stepID = -1;
    }

    public RecipeStep(int stepID, int stepMealID, int stepNum, String stepInst){
        this.stepID = stepID;
        this.stepMealID = stepMealID;
        this.stepNumber = stepNum;
        this.stepInstruction = stepInst;

    }

    public int getStepMealID() {
        return stepMealID;
    }

    public void setStepMealID(int stepMealID) {
        this.stepMealID = stepMealID;
    }

    public int getStepID() {return stepID;}

    public void setStepID(int stepId) {this.stepID = stepId;}

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getStepInstruction() {
        return stepInstruction;
    }

    public void setStepInstruction(String stepInstruction) {
        this.stepInstruction = stepInstruction;
    }
}

