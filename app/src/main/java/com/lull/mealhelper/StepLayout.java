package com.lull.mealhelper;
/**
 * Created by Bob Lull on 10/1/2015.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class StepLayout extends RelativeLayout{

    private CheckBox checkboxComplete;
    private TextView stepNumTextView;
    private TextView stepTextView;

    private RecipeStep step;
    private MealHelperDB db;
    private Context context;


    public StepLayout(Context context){
        super(context);

        // set context and get db object
        this.context = context;
        db = new MealHelperDB(context);

        // inflate layout
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.step_row, this, true);

        //widget references
        checkboxComplete = (CheckBox) findViewById(R.id.checkboxComplete);
        stepNumTextView = (TextView) findViewById(R.id.stepNumTextView);
        stepTextView = (TextView) findViewById(R.id.stepTextView);

    }
    public void setStepText(RecipeStep rs){
        step = rs;

        stepNumTextView.setText(String.valueOf(step.getStepNumber()));
        stepTextView.setText(step.getStepInstruction());
    }
}
