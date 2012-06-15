package com.xmatters.android.racebuddy;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.xmatters.android.racebuddy.twitter.Tweeter;

/**
 * Extension of the application framework that can share functionality across the app
 */
public class RaceBuddyApplication extends Application implements SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String TAG = RaceBuddyApplication.class.getSimpleName();
    public Tweeter tweeter;
    private SharedPreferences prefs;
    private boolean serviceRunning;

    @Override
    public void onCreate() {
        super.onCreate();
        this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.prefs.registerOnSharedPreferenceChangeListener(this);
        Log.i(TAG, "onCreated");
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.i(TAG, "onTerminated");
    }

    public synchronized Tweeter getTweeter() {
        if(this.tweeter == null) {
            //TODO Could do stuff here so tweeter could be a shared synchronized resource
            this.tweeter = new Tweeter();
        }
        return this.tweeter;
    }

    public synchronized void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        this.tweeter = null;
    }

    public boolean isServiceRunning() {
        return serviceRunning;
    }

    public void setServiceRunning(boolean serviceRunning) {
        this.serviceRunning = serviceRunning;
    }

}