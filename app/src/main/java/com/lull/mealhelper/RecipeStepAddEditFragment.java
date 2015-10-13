package com.lull.mealhelper;

/**
 * Created by Bob Lull on 10/3/2015.
 */
import android.content.Context;
import android.os.AsyncTask;
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

public class RecipeStepAddEditFragment extends Fragment implements OnClickListener, OnItemClickListener,
            OnFocusChangeListener{
    private MealHelperDB db;
    private ListView recipeStepsListView;
    private ArrayList<RecipeStep> steps;
    private String recipeName;
    private TextView statusTextView;
    private EditText recipeStepEditText;
    private Button addStepButton;
    private Button editStepButton;
    private Button clearTextButton;
    private Button addTimerButton;
    private TextView recipeStepNumTextView;
    private RecipeStep editStep;
    private int mealID;
    private long timerAmount;
    private StepAdapter stepAdapter;
    private EditText timerHoursEditText;
    private EditText timerMinutesEditText;
    private EditText timerSecondsEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recipe_step_add_edit_fragment,
                container, false);

        // set up database object and get intent extras
        db = new MealHelperDB(getActivity());
        Bundle data = getActivity().getIntent().getExtras();
        mealID = data.getInt("mealID", 0);
        timerAmount = data.getLong("timerAmt", 0);
        steps = db.getRecipeSteps(mealID);

        recipeName = data.getString("mealName", "");

        // register widgets
        recipeStepsListView = (ListView) rootView.findViewById(R.id.recipeStepsListView);
        statusTextView = (TextView) rootView.findViewById(R.id.statusTextView);
        recipeStepEditText = (EditText) rootView.findViewById(R.id.recipeStepEditText);
        addStepButton = (Button) rootView.findViewById(R.id.addStepButton);
        editStepButton = (Button) rootView.findViewById(R.id.editStepButton);
        clearTextButton = (Button) rootView.findViewById(R.id.clearTextButton);
        recipeStepNumTextView = (TextView) rootView.findViewById(R.id.recipeStepNumTextView);
        timerHoursEditText = (EditText) rootView.findViewById(R.id.timerHoursEditText);
        timerMinutesEditText = (EditText) rootView.findViewById(R.id.timerMinutesEditText);
        timerSecondsEditText = (EditText) rootView.findViewById(R.id.timerSecondsEditText);
        addTimerButton = (Button) rootView.findViewById(R.id.addTimerButton);

        // set up adapter for recipe step listview
        stepAdapter = new StepAdapter(getActivity(), steps);
        recipeStepsListView.setAdapter(stepAdapter);

        // set listeners
        addStepButton.setOnClickListener(this);
        editStepButton.setOnClickListener(this);
        clearTextButton.setOnClickListener(this);
        addTimerButton.setOnClickListener(this);
        recipeStepsListView.setOnItemClickListener(this);
        timerHoursEditText.setOnFocusChangeListener(this);
        timerMinutesEditText.setOnFocusChangeListener(this);
        timerSecondsEditText.setOnFocusChangeListener(this);

        //final UI setup
        statusTextView.setText("Editing: " + recipeName);
        convertTimerMillistoTimeUnits();
        setButtons();
        return rootView;
    }

    //*****************************************
    //EVENT HANDLERS
    //*****************************************
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addStepButton:
                addStep();
                break;
            case R.id.editStepButton:
                saveEdit();
                break;
            case R.id.clearTextButton:
                clearText();
                break;
            case R.id.addTimerButton:
                setButtons();
                long timerLong = convertTimeUnitsToTimerMillis();
                db.addTimerToRecipe(timerLong, mealID);
            default:
        }
        setButtons();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        editStep = steps.get(position);
        recipeStepNumTextView.setText(String.valueOf(editStep.getStepNumber()));
        recipeStepEditText.setText(editStep.getStepInstruction());

        setButtons();
    }

    //make sure timer fields have correct amounts, also check if user is
    //adding a new step, or updating an existing step
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        correctTimerText();
        setButtons();
    }

    //***********************************************
    //EDIT TEXT / BUTTON MANIPULATING METHODS
    //***********************************************

    public void setButtons(){
        if (recipeStepNumTextView.getText().toString().trim().equals("")){
            editStepButton.setEnabled(false);
            addStepButton.setEnabled(true);
        }
        else{
            editStepButton.setEnabled(true);
            addStepButton.setEnabled(false);
        }
    }

    public void clearText(){
        recipeStepEditText.setText("");
        recipeStepNumTextView.setText("");
    }

    public void correctTimerText(){
        String hours = (timerHoursEditText.getText().toString());
        if(!hours.equals("")) {
            int timerHours = Integer.parseInt(hours);
            if (timerHours > 23) {
                timerHoursEditText.setText("23");
            }
        }
        String minutes = (timerMinutesEditText.getText().toString());
        if(!minutes.equals("")) {
            int timerMinutes = Integer.parseInt(minutes);
            if (timerMinutes > 59) {
                timerMinutesEditText.setText("59");
            }
        }
        String seconds = (timerSecondsEditText.getText().toString());
        if(!seconds.equals("")) {
            int timerSeconds = Integer.parseInt(timerSecondsEditText.getText().toString());
            if (timerSeconds > 59) {
                timerSecondsEditText.setText("59");
            }
        }
    }

    public void convertTimerMillistoTimeUnits(){
        if (timerAmount > 0) {
            long timerSeconds = timerAmount / 1000 % 60;
            long timerMinutes = (timerAmount - (timerSeconds * 1000)) / 1000 / 60 % 60;
            long timerHours = (timerAmount - ((timerSeconds * 1000) + (timerMinutes * 1000 * 60))) / 1000
                    / 60 / 60;
            timerHoursEditText.setText(String.valueOf(timerHours));
            timerMinutesEditText.setText(String.valueOf(timerMinutes));
            timerSecondsEditText.setText(String.valueOf(timerSeconds));
        }
    }

    //included here just to pair it up with the other milli converter
    public long convertTimeUnitsToTimerMillis(){
        if (timerHoursEditText.getText().toString() != "" &&
            timerMinutesEditText.getText().toString() != "" &&
            timerSecondsEditText.getText().toString() != ""){
            long timerHoursInMillis = 0;
            long timerMinutesInMillis = 0;
            long timerSecondsInMillis = 0;
            long timerAmountInMillis = 0;
            if(!timerHoursEditText.getText().toString().equals("")) {
                timerHoursInMillis = Long.parseLong(timerHoursEditText.getText().toString());
                timerHoursInMillis = timerHoursInMillis * 1000 * 60 * 60;
                }
            if(!timerMinutesEditText.getText().toString().equals("")) {
                timerMinutesInMillis = Long.parseLong(timerMinutesEditText.getText().toString());
                timerMinutesInMillis = timerMinutesInMillis * 1000 * 60;
            }
            if(!timerSecondsEditText.getText().toString().equals("")) {
                timerSecondsInMillis = Long.parseLong(timerSecondsEditText.getText().toString());
                timerSecondsInMillis = timerSecondsInMillis * 1000;
            }
            timerAmountInMillis = timerHoursInMillis + timerMinutesInMillis + timerSecondsInMillis;
                return timerAmountInMillis;
        }
        return 0;
    }

    //****************************************************
    //DATABASE-USING METHODS
    //****************************************************

    public void addStep(){
        recipeStepEditText.setText(recipeStepEditText.getText().toString().trim());
        if (!recipeStepEditText.getText().toString().equals("")) {
            editStep = new RecipeStep();
            editStep.setStepMealID(mealID);
            editStep.setStepInstruction(recipeStepEditText.getText().toString());
            editStep.setStepNumber(steps.size() + 1);
            steps.add(editStep);
            saveRecipeSteps();
           // new SaveRecipeSteps().execute();
            refreshData();
        //    stepAdapter.notifyDataSetChanged();
            clearText();
        }
        else{
            clearText();
            Toast.makeText(getActivity(), "Please enter step instructions first.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void saveEdit(){
        recipeStepEditText.setText(recipeStepEditText.getText().toString().trim());
        if(!(recipeStepEditText.getText().toString().equals("") ||
                recipeStepNumTextView.getText().toString().equals(""))){

            editStep.setStepInstruction(recipeStepEditText.getText().toString().trim());
            editStep.setStepNumber(Integer.parseInt(recipeStepNumTextView.getText().toString()));

            steps.set(editStep.getStepNumber() - 1, editStep);

            saveRecipeSteps();

            refreshData();
            clearText();
            setButtons();
        }
        else{
            Toast.makeText(getActivity(), "Please select a step from the list first.",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void saveRecipeSteps(){
        Context context = getActivity();
        db.addRecipeStepsToDB(steps);
        Toast.makeText(context, "Recipe Steps saved.", Toast.LENGTH_LONG).show();
    }

    public void refreshData(){
        ArrayList<RecipeStep> newRecipeSteps = new ArrayList<>();
        newRecipeSteps = db.getRecipeSteps(mealID);
        steps.clear();
        steps.addAll(newRecipeSteps);
        stepAdapter.notifyDataSetChanged();
    }
}
