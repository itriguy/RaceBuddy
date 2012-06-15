package com.xmatters.android.racebuddy.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xmatters.android.racebuddy.R;
import com.xmatters.android.racebuddy.twitter.Tweeter;
import com.xmatters.android.racebuddy.utility.DateUtility;

/**
 * Race Activity - Main Screen for tracking races, checklists, ...
 */
public class RaceActivity extends Activity {
    private static final String TAG = "RaceActivity";
    private static final int DATE_DIALOG_ID = 0;

    private EditText txtName;
    private EditText txtDetails;
    private EditText txtTimeActual;
    private EditText txtTimeEstimate;
    private TextView txtDate;
    private Button btnSave;
    private Button btnTweet;
    private Button btnDatePicker;

    private int year;
    private int month;
    private int day;

    private Tweeter tweeter;

    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            updateDisplay();
        }
    };


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.race);

        // capture our View elements
        txtName = (EditText) findViewById(R.id.raceName);
//        txtDetails = (EditText) findViewById(R.id.raceDetails);
        txtTimeActual = (EditText) findViewById(R.id.raceTimeActual);
        txtTimeEstimate = (EditText) findViewById(R.id.raceTimeEstimate);
        txtDate = (TextView) findViewById(R.id.raceDate);
        btnSave = (Button) findViewById(R.id.raceSave);
        btnTweet = (Button) findViewById(R.id.raceTweet);

        btnDatePicker = (Button) findViewById(R.id.raceDatePicker);

        //--- NOTE Need to handle this differently for existing race - don't overwrite
        year = DateUtility.getCurrentYear();
        month = DateUtility.getCurrentMonth();
        day = DateUtility.getCurrentDay();

        // add a click listener for date picker
        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "Saving content....");
            }
        });

        btnTweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(TAG, "Tweeting content....");
                new PostToTwitter().execute(getTweetMessage());
            }
        });

        updateDisplay();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();   //
      inflater.inflate(R.menu.menu, menu);         //
      return true; //
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {                              //
      case R.id.itemPrefs:
        startActivity(new Intent(this, PrefsActivity.class));  //
      break;
      }
      return true;  //
    }

    private void updateDisplay() {
        txtDate.setText(DateUtility.convertToString(year, month, day));
    }

    /**
     * Handle showDialog should it be called.
     *
     * @param id
     * @return Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch(id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, year, month, day);
        }
        return null;
    }

    protected String getTweetMessage(){
        StringBuilder builder = new StringBuilder();
        boolean completed = (txtTimeActual.getText() == null || txtTimeActual.getText().toString().isEmpty()) ? false : true;
        builder.append(completed ? "Raced " : "Racing ");
        builder.append(txtName.getText());
        builder.append(" on " + txtDate.getText());
        builder.append(completed ? " finishing in " : " expecting ");
        builder.append(completed ? txtTimeActual.getText() : txtTimeEstimate.getText());
        builder.append(" - via RaceBuddy");
        return builder.toString();
    }

    // Asynchronously posts to twitter
    class PostToTwitter extends AsyncTask<String, Integer, String> { //

        // Called to initiate the background activity
        @Override
        protected String doInBackground(String... statuses) { //
            tweeter = new Tweeter();
            String msg = tweeter.tweet(statuses[0]);
            Log.i(TAG, msg);
            return msg;
        }

        // Called when there's a status to be updated
        @Override
        protected void onProgressUpdate(Integer... values) { //
            super.onProgressUpdate(values);
            // Not used in this case
        }

        // Called once the background activity has completed
        @Override
        protected void onPostExecute(String result) { //
            Toast.makeText(RaceActivity.this, result, Toast.LENGTH_LONG).show();
        }
    }

}
