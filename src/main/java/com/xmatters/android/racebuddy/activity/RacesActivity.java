package com.xmatters.android.racebuddy.activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xmatters.android.racebuddy.R;
import com.xmatters.android.racebuddy.adapter.RacesAdapter;
import com.xmatters.android.racebuddy.db.RaceDAO;

import static com.xmatters.android.racebuddy.db.Race.FIELD;

/**
 * Activity to handle race lists
 */
public class RacesActivity extends BaseActivity { //
    private static final String TAG = "RacesActivity";
    private static final String[] FROM = {FIELD.NAME.toString(), FIELD.DATE.toString()};
    private static final int[] TO = {R.id.txtRaceName, R.id.txtRaceDate};

    private Cursor cursor;
    private ListView listRaces;
    private RacesAdapter adapter;
    private RaceDAO raceDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.racelist);

        // Init DAO
        raceDAO = new RaceDAO(application.getApplicationContext());

        // Find your views
        listRaces = (ListView) findViewById(R.id.listRaces);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Setup List
        this.setupList();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Close the database
        raceDAO.close();
    }

    // Responsible for fetching data and setting up the list and the adapter
    private void setupList() {
        // Get the data
        cursor = raceDAO.getRaces();
        startManagingCursor(cursor);
        // Setup Adapter
        adapter = new RacesAdapter(application.getApplicationContext(), cursor);
        listRaces.setAdapter(adapter);
        listRaces.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                Log.i(TAG, cursor.getString(cursor.getColumnIndex(FIELD.NAME.toString())));
            }
        });
    }

}