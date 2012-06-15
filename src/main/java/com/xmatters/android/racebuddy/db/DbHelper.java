package com.xmatters.android.racebuddy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Database helper
 */
public class DbHelper extends SQLiteOpenHelper {
    static final String TAG = "DbHelper";
    static final String DB_NAME = "racebuddy.db";
    static final int DB_VERSION = 1;
    Context context;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    /**
     * Called only once, first time the DB is created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Race.createTableSql());
        Log.d(TAG, "Created race data table");
    }

    /**
     * Called whenever newVersion != oldVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { //
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='races'";
        // TODO if exists update, else create. Temporarily dropping and recreating.
        db.execSQL(Race.dropTableSql()); // drops the old table
        onCreate(db);
    }
}