package com.xmatters.android.racebuddy.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.xmatters.android.racebuddy.R;

/**
 * Preferences - Controller
 */
public class PrefsActivity extends PreferenceActivity { //

  @Override
  protected void onCreate(Bundle savedInstanceState) { //
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.prefs); //
  }

}
