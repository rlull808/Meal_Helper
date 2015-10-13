package com.lull.mealhelper;

/**
 * Created by Bob Lull on 10/2/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecipeLayout extends RelativeLayout {

    private TextView recipeNameTextView;
    private TextView recipeIDTextView;
    private Recipe recipe;
    private Context context;

    public RecipeLayout(Context rContext) {
        super(rContext);

        this.context = rContext;

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recipe_row, this, true);

        // get widget references
        recipeNameTextView = (TextView) findViewById(R.id.recipeNameTextView);
        recipeIDTextView = (TextView) findViewById(R.id.recipeIDTextView);

    }
    public void setRecipeText(Recipe r){
        recipe = r;

        recipeNameTextView.setText(recipe.getRecipeName());
        recipeIDTextView.setText(String.valueOf(recipe.getRecipeID()));
    }
}
