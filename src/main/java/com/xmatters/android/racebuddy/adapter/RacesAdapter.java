package com.xmatters.android.racebuddy.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.xmatters.android.racebuddy.R;

import static com.xmatters.android.racebuddy.db.Race.FIELD;

/**
 * Adapter to support race list
 */
public class RacesAdapter extends SimpleCursorAdapter {
    private final static String TAG = "RacesAdapter";

    private static final String[] FROM = {FIELD.NAME.toString(), FIELD.DATE.toString()};
    private static final int[] TO = {R.id.txtRaceName, R.id.txtRaceDate};

    // Constructor
    public RacesAdapter(Context context, Cursor cursor) {
        super(context, R.layout.racerow, cursor, FROM, TO);
    }

    // This is where the actual binding of a cursor to view happens
    @Override
    public void bindView(View row, Context context, Cursor cursor) {
        super.bindView(row, context, cursor);

        // Manually bind created at timestamp to its view
        TextView txtRaceName = (TextView) row.findViewById(R.id.txtRaceName);
        txtRaceName.setText(cursor.getString(cursor.getColumnIndex(FIELD.NAME.toString())));

        TextView txtRaceDate = (TextView) row.findViewById(R.id.txtRaceDate);
        txtRaceDate.setText(cursor.getString(cursor.getColumnIndex(FIELD.DATE.toString())));
    }
}
