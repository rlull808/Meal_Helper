package com.lull.mealhelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Bob Lull on 9/27/2015.
 */
public class IngredientLayout extends RelativeLayout {

    private CheckBox checkboxComplete;
    private TextView ingredientTextView;
    private TextView ingredientDescription;

    private Ingredient ingredient;
    private MealHelperDB db;
    private Context context;

    public IngredientLayout(Context Icontext /*, Ingredient ingred*/) {
        super(Icontext);

        // set context and get db object
        this.context = Icontext;
        db = new MealHelperDB(context);

        //inflate layout
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.ingredient_row, this, true);

        //widget references
        checkboxComplete = (CheckBox) findViewById(R.id.checkboxComplete);
        ingredientTextView = (TextView) findViewById(R.id.ingredientTextView);
        ingredientDescription = (TextView) findViewById(R.id.ingredientDescription);

    }

    public void setIngredientText(Ingredient ing){
        ingredient = ing;

        ingredientTextView.setText(ingredient.getIngredientTitle());
        if (ingredient.getIngredientDesc().equals(""))
        {
            ingredientDescription.setText("");
        }
        else {
            ingredientDescription.setText(ingredient.getIngredientDesc());
        }
    }
}

