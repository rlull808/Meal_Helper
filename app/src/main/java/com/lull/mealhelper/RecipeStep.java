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
    private Boolean stepNeedsTimer;
    private long stepTimerMillis;

    public RecipeStep(){
        this.stepNeedsTimer = false;
        this.stepTimerMillis = 0;

    }

    public RecipeStep(int stepID, int stepMealID, int stepNum, String stepInst, Boolean needsTimer, long timerMillis){
        this.stepID = stepID;
        this.stepMealID = stepMealID;
        this.stepNumber = stepNum;
        this.stepInstruction = stepInst;
        this.stepNeedsTimer = needsTimer;
        this.stepTimerMillis = timerMillis;
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

    public Boolean getStepNeedsTimer() {
        return stepNeedsTimer;
    }

    public void setStepNeedsTimer(Boolean needsTimer) {
        this.stepNeedsTimer = needsTimer;
    }

    public long getStepTimerMillis() {
        return stepTimerMillis;
    }

    public void setStepTimerMillis(long timerMillis){
        this.stepTimerMillis = timerMillis;
    }
}

