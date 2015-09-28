package com.lull.mealhelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MealHelperDB db;
    private Context context;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        db = new MealHelperDB(context);

        ArrayList<String> recipeNames = db.getRecipeNames();

        for (String name : recipeNames){
            Log.d("Meal Helper DB: ", " Recipe: " + name, null);
        }
        Log.d("Meal Helper DB: ", "___________________PANCAKE INGREDIENTS__________________", null);

        ArrayList<Ingredient> ingredients = db.getRecipeIngredients(1);

        for (Ingredient ingred : ingredients){
            Log.d("Meal Helper DB: ", "Ingredient# " + ingred.getIngredientNum() + ", " +
                    ingred.getIngredientTitle(), null);
        }

        ArrayList<RecipeStep> steps = db.getRecipeSteps(1);

        for (RecipeStep step : steps){
            Log.d("Meal Helper DB: ",  "Step # " + step.getStepNumber() + ", " +
                    step.getStepInstruction(), null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
