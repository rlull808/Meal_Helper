package com.lull.mealhelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by Bob Lull on 9/27/2015.
 */
public class StepAdapter extends ArrayAdapter<RecipeStep>{
    Context context;

    public StepAdapter(Context context, ArrayList<RecipeStep> steps){
       super(context, 0, steps);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecipeStep step = getItem(position);
        StepLayout stepLayout;
        if (convertView == null) {
            stepLayout = new StepLayout(context/*, step*/);
        }
        else {
            stepLayout = (StepLayout) convertView;
        }
        stepLayout.setStepText(step);
        return stepLayout;
    }
}