package com.lull.mealhelper;

/**
 * Created by Bob Lull on 10/4/2015.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.view.View.OnFocusChangeListener;
import android.widget.Toast;
import java.util.ArrayList;


public class IngredientAddEditFragment extends Fragment implements OnClickListener, OnItemClickListener,
        OnFocusChangeListener{
    private MealHelperDB db;
    private TextView statusTextView;
    private EditText ingredientEditText;
    private EditText ingredientDescriptionEditText;
    private Button addIngredientButton;
    private Button saveEditIngredientButton;
    private Button clearTextButton;
    private ListView ingredientListView;
    private int mealID;
    private String recipeName;
    private IngredientAdapter ingredientAdapter;
    private ArrayList<Ingredient> ingredients;
    private TextView ingredientNumTextView;
    private Ingredient editIngredient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.ingredient_add_edit_fragment,
                container, false);

        //set up db and get bundle data
        db = new MealHelperDB(getActivity());
        Bundle data = getActivity().getIntent().getExtras();
        mealID = data.getInt("mealID", 0);
        recipeName = data.getString("mealName", "");
        ingredients = db.getRecipeIngredients(mealID);

        //register widgets
        statusTextView = (TextView) rootView.findViewById(R.id.statusTextView);
        ingredientNumTextView = (TextView) rootView.findViewById(R.id.ingredientNumTextView);
        ingredientEditText = (EditText) rootView.findViewById(R.id.ingredientEditText);
        ingredientDescriptionEditText = (EditText) rootView.findViewById(R.id.ingredientDescriptionEditText);
        addIngredientButton = (Button) rootView.findViewById(R.id.addIngredientButton);
        saveEditIngredientButton = (Button) rootView.findViewById(R.id.saveEditIngredientButton);
        clearTextButton = (Button) rootView.findViewById(R.id.clearTextButton);
        ingredientListView = (ListView) rootView.findViewById(R.id.ingredientListView);

        //set up listview adapter
        ingredientAdapter = new IngredientAdapter(getActivity(), ingredients);
        ingredientListView.setAdapter(ingredientAdapter);

        //set up listeners
        addIngredientButton.setOnClickListener(this);
        saveEditIngredientButton.setOnClickListener(this);
        clearTextButton.setOnClickListener(this);
        ingredientListView.setOnItemClickListener(this);
        ingredientListView.setOnFocusChangeListener(this);
        ingredientEditText.setOnFocusChangeListener(this);
        ingredientDescriptionEditText.setOnFocusChangeListener(this);


        statusTextView.setText("Editing: " + recipeName);

        setButtons();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addIngredientButton:
                addIngredient();
                break;
            case R.id.saveEditIngredientButton:
                saveEditIngredient();
                break;
            case R.id.clearTextButton:
                clearText();
                break;
            default:
        }
        setButtons();
    }

    public void addIngredient() {
        ingredientEditText.setText(ingredientEditText.getText().toString().trim());
        ingredientDescriptionEditText.setText(ingredientDescriptionEditText.getText().toString().trim());

        if (!ingredientEditText.getText().toString().equals("")) {
            editIngredient = new Ingredient();
            editIngredient.setMealID(mealID);
            editIngredient.setIngredientTitle(ingredientEditText.getText().toString().trim());
            editIngredient.setIngredientDesc(ingredientDescriptionEditText.getText().toString().trim());
            editIngredient.setIngredientNum(ingredients.size() + 1);
            ingredients.add(editIngredient);

            saveIngredients();
            refreshData();
            clearText();
        }
        else{
            clearText();
            Toast.makeText(getActivity(), "Please enter step instructions first.",
                    Toast.LENGTH_LONG).show();
        }
        setButtons();
    }
    public void saveEditIngredient(){
        if(!ingredientEditText.getText().toString().equals("")){
            editIngredient.setIngredientTitle(ingredientEditText.getText().toString().trim());
            editIngredient.setIngredientDesc(ingredientDescriptionEditText.getText().toString().trim());
            editIngredient.setIngredientNum(Integer.parseInt(ingredientNumTextView.getText().toString()));

            ingredients.set(editIngredient.getIngredientNum() - 1, editIngredient);

            saveIngredients();

            refreshData();
            clearText();
            setButtons();
        }

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        setButtons();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        editIngredient = ingredients.get(position);
        ingredientEditText.setText(editIngredient.getIngredientTitle());
        ingredientDescriptionEditText.setText(editIngredient.getIngredientDesc());
        ingredientNumTextView.setText(String.valueOf(editIngredient.getIngredientNum()));

        setButtons();
    }

    public void setButtons() {
        if (ingredientNumTextView.getText().toString().trim().equals("")) {
            saveEditIngredientButton.setEnabled(false);
            addIngredientButton.setEnabled(true);
        } else {
            saveEditIngredientButton.setEnabled(true);
            addIngredientButton.setEnabled(false);
        }
    }
    public void clearText(){
        ingredientNumTextView.setText("");
        ingredientEditText.setText("");
        ingredientDescriptionEditText.setText("");
        setButtons();
    }

    public void saveIngredients(){
        db.addRecipeIngredientsToDB(ingredients);
        Context context = getActivity();
        Toast.makeText(context, "Recipe Ingredients Saved.", Toast.LENGTH_LONG).show();
        refreshData();
    }

    public void refreshData(){
        ArrayList<Ingredient> newIngredients = new ArrayList<>();
        newIngredients = db.getRecipeIngredients(mealID);
        ingredients.clear();
        ingredients.addAll(newIngredients);
        ingredientAdapter.notifyDataSetChanged();
    }

}
