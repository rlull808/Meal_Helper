package com.lull.mealhelper;

/**
 * Created by Bob Lull on 10/2/2015.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import android.widget.TextView;

import java.util.ArrayList;
public class RecipeAdapter extends ArrayAdapter<Recipe>{

    private Context context;

    public RecipeAdapter(Context context, ArrayList<Recipe> recipes) {
        super(context, 0, recipes);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Recipe recipe = getItem(position);
        RecipeLayout recipeLayout;
        if (convertView == null) {
            recipeLayout = new RecipeLayout(context);
        }
        else{
            recipeLayout = (RecipeLayout) convertView;
        }
        recipeLayout.setRecipeText(recipe);
        return recipeLayout;
    }
}
