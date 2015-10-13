package com.lull.mealhelper;

/**
 * Created by Bob Lull on 10/1/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

public class IngredientFragment extends Fragment {

    private MealHelperDB db;
    private ArrayList<Ingredient> ingredients;
    private ListView ingredientListView;
    private String recipeName;
    private TextView statusTextView;
    private int mealID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ingredient_fragment,
                container, false);

        //gather data needed for layout
        db = new MealHelperDB(getActivity());
        Bundle data = getActivity().getIntent().getExtras();
        recipeName = data.getString("mealName", "");
        mealID = data.getInt("mealID", 0);
        ingredients = db.getRecipeIngredients(mealID);


        //register necessary widgets
        ingredientListView = (ListView) rootView.findViewById(R.id.ingredientListView);
        statusTextView = (TextView) rootView.findViewById(R.id.statusTextView);

        //populate widgets and set listView adapter
        statusTextView.setText("Ingredients List - " + recipeName);
        IngredientAdapter ingredAdapter = new IngredientAdapter(getActivity(), ingredients);
        ingredientListView.setAdapter(ingredAdapter);

        return rootView;
    }
}
