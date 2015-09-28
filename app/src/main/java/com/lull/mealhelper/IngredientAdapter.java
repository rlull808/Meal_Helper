package com.lull.mealhelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Bob Lull on 9/27/2015.
 */
public class IngredientAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Ingredient> ingredients;

    public IngredientAdapter(Context context, ArrayList<Ingredient> ingreds){
        this.context = context;
        this.ingredients = ingreds;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        IngredientLayout ingredientLayout = null;
        Ingredient ingredient = ingredients.get(position);

        if (convertView == null) {
            ingredientLayout = new IngredientLayout(context, ingredient);
        }
        else {
            ingredientLayout = (IngredientLayout) convertView;
            ingredientLayout.setIngredientText(ingredient);
        }
        return ingredientLayout;
    }
}
