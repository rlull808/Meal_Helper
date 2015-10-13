package com.lull.mealhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnClickListener, OnItemClickListener {

    private MealHelperDB db;
    private Context context;
    private Button recipeViewButton;
    private ListView recipeListView;
    private TextView recipeNameLabel;
    private TextView recipeIDHidden;
    ArrayList<Recipe> recipes;
    private Button editRecipeButton;
    private RecipeAdapter adapter;
    private TextView recipeTimerAmtHidden;


    //***************************************************
    //STANDARD ACTIVITY STARTERS / RESUMERS / SETUP
    //***************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        db = new MealHelperDB(context);
        recipes = new ArrayList<>();
        recipes = db.getRecipes();

        //get references and set recipeListView adapter
        recipeListView = (ListView) findViewById(R.id.recipeListView);
        recipeViewButton = (Button) findViewById(R.id.recipeViewButton);
        recipeNameLabel = (TextView) findViewById(R.id.recipeNameLabel);
        recipeIDHidden = (TextView) findViewById(R.id.recipeIdHidden);
        editRecipeButton = (Button) findViewById(R.id.editRecipeButton);
        recipeTimerAmtHidden = (TextView) findViewById(R.id.recipeTimerAmtHidden);
        adapter = new RecipeAdapter(this, recipes);
        recipeListView.setAdapter(adapter);

        //set listeners
        recipeViewButton.setOnClickListener(this);
        recipeListView.setOnItemClickListener(this);
        editRecipeButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Recipe> replaceRecipes = new ArrayList<>();
        replaceRecipes = db.getRecipes();
        refreshRecipeListView(replaceRecipes);
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
        switch (id) {
            case R.id.action_new_recipe:
                startNewRecipeDialog();
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //*******************************************************
    // CLICK HANDLING / CLICK EVENT METHODS
    //*******************************************************

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.recipeViewButton:
                lookAtRecipe();
                break;
            case R.id.editRecipeButton:
                editRecipe();
                break;
            default:
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Recipe recipe = recipes.get(position);
        recipeIDHidden.setText(String.valueOf(recipe.getRecipeID()));
        recipeNameLabel.setText(recipe.getRecipeName());
        recipeTimerAmtHidden.setText(String.valueOf(recipe.getTimerAmount()));
    }

    public void lookAtRecipe(){
        if(recipeIDHidden.getText().toString().equals("")){
            return;
        }
        else {
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra("mealID", Integer.parseInt(recipeIDHidden.getText().toString()));
            intent.putExtra("mealName", recipeNameLabel.getText().toString());
            intent.putExtra("timerAmt", Long.parseLong(recipeTimerAmtHidden.getText().toString()));
            startActivity(intent);
        }
    }

    public void editRecipe(){
        Intent intent = new Intent(this, RecipeAddEditActivity.class);
        intent.putExtra("mealID", Integer.parseInt(recipeIDHidden.getText().toString()));
        intent.putExtra("mealName", recipeNameLabel.getText().toString());
        intent.putExtra("timerAmt", Long.parseLong(recipeTimerAmtHidden.getText().toString()));
        startActivity(intent);
    }

    public void startNewRecipeDialog(){
        AlertDialog.Builder newRecipeDialog = new AlertDialog.Builder(MainActivity.this);
        newRecipeDialog.setTitle("New Recipe");
        newRecipeDialog.setMessage("Enter a Recipe Name");
        final EditText input = new EditText(MainActivity.this);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        newRecipeDialog.setView(input);
        newRecipeDialog.setIcon(R.drawable.new_recipe);

        newRecipeDialog.setPositiveButton("Create",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (input.getText().toString().equals("")) {
                            return;
                        }
                        else {
                            String recipeName = input.getText().toString();
                            Recipe newRecipe = new Recipe(recipeName);
                            long mealID = db.addNewRecipeToDB(newRecipe);
                            newRecipe.setRecipeID(Integer.parseInt(String.valueOf(mealID)));
                            recipes.add(newRecipe);
                            ArrayList<Recipe> newList = new ArrayList<>();
                            newList.addAll(recipes);
                            refreshRecipeListView(newList);

                            dialog.cancel();
                        }
                    }
                });
                newRecipeDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        newRecipeDialog.show();
    }

    public void refreshRecipeListView(ArrayList<Recipe> newList){

        recipes.clear();
        recipes.addAll(newList);
        adapter.notifyDataSetChanged();
    }
}