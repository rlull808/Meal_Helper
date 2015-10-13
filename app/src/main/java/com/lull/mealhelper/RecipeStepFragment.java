package com.lull.mealhelper;

/**
 * Created by Bob Lull on 10/1/2015.
 */

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.view.View.OnClickListener;

public class RecipeStepFragment extends Fragment implements OnClickListener{

    private MealHelperDB db;
    private ListView stepsListView;
    private ArrayList<RecipeStep> steps;
    private String recipeName;
    private TextView statusTextView;
    private Long timerAmount;
    private TextView timerAmountTextView;
    private Boolean isTimerActivated;
    private Timer timer;
    private SharedPreferences savedValues;
    private long timerTicks;
    private int mealID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.steps_fragment, container, false);
        db = new MealHelperDB(getActivity());
        Bundle data = getActivity().getIntent().getExtras();
        mealID = data.getInt("mealID", 0);
        steps = db.getRecipeSteps(mealID);
        recipeName = data.getString("mealName", "");
        timerAmount = data.getLong("timerAmt", 0);

        isTimerActivated = false;
        timerTicks = 0;

        //register widgets
        stepsListView = (ListView) rootView.findViewById(R.id.stepsListView);
        statusTextView = (TextView) rootView.findViewById(R.id.statusTextView);
        timerAmountTextView = (TextView) rootView.findViewById(R.id.timerAmountTextView);

        //set text and adapter
        statusTextView.setText("Recipe Steps - " + recipeName);
        String timerStr = convertTimerMillisToTimeUnits(timerAmount);
        timerAmountTextView.setText(timerStr);
        StepAdapter stepAdapter = new StepAdapter(getActivity(), steps);
        stepsListView.setAdapter(stepAdapter);

        //set timerAmountTextView listener
        timerAmountTextView.setOnClickListener(this);

        //set up sharedPreferences file and get object
        savedValues = this.getActivity().getSharedPreferences("SavedValues", Context.MODE_PRIVATE);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isTimerActivated) {
            //get variables back
            //   timerAmount = savedValues.getLong("timerAmount", 0);
            long timeAtPause = savedValues.getLong("timeAtPause", 0);
            long currentTime = System.currentTimeMillis();
            timerTicks += ((currentTime - timeAtPause) / 1000);
            //do calculations to get the amount of time that's passed
            handleTimer();
        }
    }

    @Override
    public void onPause() {
        Editor editor = savedValues.edit();
        //editor.putLong("timerAmount", timerAmount);
        long timeAtPause = System.currentTimeMillis();
        editor.putLong("timeAtPause", timeAtPause);
        editor.commit();
        isTimerActivated = false;
        super.onPause();
    }

    public void updateView(final long timerSubtract){
        timerAmountTextView.post(new Runnable() {
            @Override
            public void run() {
                long millis = timerAmount + timerSubtract;
                if (millis < 0) {
                    timer.cancel();
                    isTimerActivated = false;
                    timerTicks = 0;
                }
                String timerText = convertTimerMillisToTimeUnits(millis);
                timerAmountTextView.setText(timerText);
            }
        });
    }

    public String convertTimerMillisToTimeUnits(long millis) {
        String timerAmountStr = "00:00:00";
        if (timerAmount > 0 && millis > 0) {
            long timerSeconds = millis / 1000 % 60;
            long timerMinutes = (millis - (timerSeconds * 1000)) / 1000 / 60 % 60;
            long timerHours = (millis - ((timerSeconds * 1000) + (timerMinutes * 1000 * 60))) / 1000
                    / 60 / 60;
            timerAmountStr = (timerHours + ":" + timerMinutes + ":" + timerSeconds);
        }
        return timerAmountStr;
    }

    public long convertTimeUnitsToTimerMillis(){

            long timerHoursInMillis = 0;
            long timerMinutesInMillis = 0;
            long timerSecondsInMillis = 0;

            String[] timerAmountArray = timerAmountTextView.getText().toString().split(":");
            for (int i = 0; i < timerAmountArray.length; i++) {
                String amt = timerAmountArray[i];
                if (i == 0) {
                    if (amt.equals("0") || amt.equals("00"))
                        timerHoursInMillis = 0;
                    else {
                        long timerAmtTemp = Long.parseLong(amt);
                        timerHoursInMillis = timerAmtTemp * 1000 * 60 * 60;
                    }
                }
                if (i == 1) {
                    if (amt.equals("0") || amt.equals("00"))
                        timerMinutesInMillis = 0;
                    else {
                        long timerAmtTemp = Long.parseLong(amt);
                        timerMinutesInMillis = timerAmtTemp * 1000 * 60;
                    }
                }
                if (i == 2) {
                    if (amt.equals("0") || amt.equals("00"))
                        timerSecondsInMillis = 0;
                    else {
                        long timerAmtTemp = Long.parseLong(amt);
                        timerSecondsInMillis = timerAmtTemp * 1000;
                    }
                }

            }
        return timerHoursInMillis + timerMinutesInMillis + timerSecondsInMillis;
    }

    @Override
    public void onClick(View v) {
        handleTimer();
    }

    public void handleTimer(){
        Intent serviceIntent = new Intent(getActivity(), TimerService.class);
        if (!isTimerActivated){
            isTimerActivated = true;
            if (timerAmount != 0) {
                serviceIntent.putExtra("mealName", recipeName);
                long timerServiceAmount = convertTimeUnitsToTimerMillis();
                serviceIntent.putExtra("timerAmt", timerServiceAmount);
                serviceIntent.putExtra("mealID", mealID);
                getActivity().startService(serviceIntent);
            }
            timer = new Timer(true);
            TimerTask timerTask = new TimerTask(){
                @Override
                public void run(){
                    timerTicks++;
                    long timerSubtract = 0 - (timerTicks *1000);
                    updateView(timerSubtract);
                }
            };
            timer.schedule(timerTask, 0, 1000);
        }
        else{
            isTimerActivated = false;

            if(isMyServiceRunning(TimerService.class)) {
                getActivity().stopService(serviceIntent);
            }
            if(timer != null)
            timer.cancel();
        }

    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}


