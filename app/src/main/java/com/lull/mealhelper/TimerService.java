package com.lull.mealhelper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.app.PendingIntent;
import android.app.NotificationManager;
import android.app.Notification;

import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {
    private long timerAmount;
    private String mealName;
    private long timerTicks;
    private int mealID;
    private Timer timer;
    private PendingIntent pendingIntent;
    private Intent notificationIntent;
    private Notification note;

    public TimerService() {
    }

    @Override
    public void onCreate() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timerAmount = intent.getLongExtra("timerAmt", 0);
        mealName =  intent.getStringExtra("mealName");
        mealID = intent.getIntExtra("mealID", 0);

        Log.d("TIMER SERVICE", "TIMER AMOUNT: " + timerAmount, null);

        //set up the notification and intents
        notificationIntent = new Intent(this, RecipeActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra("mealName", mealName);
        notificationIntent.putExtra("mealID", mealID);
        int flag = PendingIntent.FLAG_UPDATE_CURRENT;
        pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, flag);
        int icon = R.mipmap.ic_steak_icon;
        CharSequence tickerText = "Your " + mealName + " is finished!";
        CharSequence contentTitle = getText(R.string.app_name);
        CharSequence contentText = "Click here to return to MealHelper.";
        note = new NotificationCompat.Builder(this).setSmallIcon(icon).
                setTicker(tickerText).setContentTitle(contentTitle).setContentText(contentText).
                setContentIntent(pendingIntent).setAutoCancel(true).build();

        startTimer();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    public void startTimer(){
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                long elapsedMillis = 0;
                timerTicks ++;
                elapsedMillis = timerTicks * 1000;
                if ((timerAmount - elapsedMillis) <= 0 ){
                    Log.d("TIMER SERVICE", " TIMER FOR " + mealName + "HAS COMPLETED", null);
                    NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    final int NOTIFICATION_ID = 1;
                    nm.notify(NOTIFICATION_ID, note);

                    stopSelf();
                }
            }
        };
        timer = new Timer(true);
        timer.schedule(timerTask, 0, 1000);
    }
    public void stopTimer(){
        Log.d("TIMER SERVICE", "TIMER THREAD CANCELED", null);
        if(timer != null)
            timer.cancel();
    }

    @Override
    public void onDestroy() {
        Log.d("TIMER SERVICE", "TIMER HAS EXPIRED", null);
        stopTimer();
    }
}
