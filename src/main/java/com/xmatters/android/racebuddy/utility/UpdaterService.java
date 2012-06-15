package com.xmatters.android.racebuddy.utility;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

import com.xmatters.android.racebuddy.RaceBuddyApplication;
import com.xmatters.android.racebuddy.db.DbHelper;

/**
 * Service meant to run
 */
public class UpdaterService extends Service {
    private static final String TAG = "UpdaterService";

    static final int DELAY = 60000; // wait a minute
    private boolean runFlag = false;
    private Updater updater;
    private RaceBuddyApplication application;

    DbHelper dbHelper;
    SQLiteDatabase db;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        getApplication();
        application = (RaceBuddyApplication) getApplication();
        this.updater = new Updater();

        dbHelper = new DbHelper(this);

        Log.d(TAG, "onCreated");
    }

    @Override
    public int onStartCommand(Intent intent, int flag, int startId) {
        if(! runFlag) {
            this.runFlag = true;
            this.updater.start();
            application.setServiceRunning(true);

            Log.d(TAG, "onStarted");
        }
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.runFlag = false;
        this.updater.interrupt();
        this.updater = null;
        application.setServiceRunning(false);

        Log.d(TAG, "onDestroyed");
    }

    /**
     * Thread that performs background service task so that it doesn't slow down the app's UI
     */
    private class Updater extends Thread {

        public Updater() {
            super("UpdaterService-Updater");
        }

        @Override
        public void run() {
            UpdaterService updaterService = UpdaterService.this;
            while(updaterService.runFlag) {
                Log.d(TAG, "Updater running");
                try {
                    runFlag = false;
                    Log.d(TAG, "Updater ran");
                    Thread.sleep(DELAY);
                }
                catch(InterruptedException e) {
                    updaterService.runFlag = false;
                }
            }
        }
    }

}