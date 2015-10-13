package com.lull.mealhelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

/**
 * Created by Bob Lull on 9/27/2015.
 */
public class IngredientAdapter extends ArrayAdapter<Ingredient>{

    private Context context;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingreds){
        super(context, 0, ingreds);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ingredient ingredient = getItem(position);
        IngredientLayout ingredientLayout;
        if (convertView == null) {
            ingredientLayout = new IngredientLayout(context);
        }
        else {
            ingredientLayout = (IngredientLayout) convertView;

        }

        ingredientLayout.setIngredientText(ingredient);
        return ingredientLayout;
    }
}
