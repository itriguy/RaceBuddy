package com.xmatters.android.racebuddy.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Simple DAO for Race Data.
 */
public class RaceDAO {
    public static String TAG = RaceDAO.class.getName();
    private DbHelper dbHelper;

    public RaceDAO(Context context) {
        dbHelper = new DbHelper(context);
        Log.i(TAG, "Initialized data");
    }

    public void close() { //
        dbHelper.close();
    }

    public void saveRace(Race race){
        ContentValues values = new ContentValues();
        if(race.getId() != null) values.put(Race.FIELD.ID.toString(), race.getId());
        if(race.getName() != null) values.put(Race.FIELD.NAME.toString(), race.getName());
        if(race.getActualTime() != null) values.put(Race.FIELD.ACTUAL_TIME.toString(), race.getActualTime());
        if(race.getEstimatedTime() != null) values.put(Race.FIELD.ESTIMATED_TIME.toString(), race.getEstimatedTime());
        if(race.getDate() != null) values.put(Race.FIELD.DATE.toString(), race.getDate());
        if(race.getCreated() != null) values.put(Race.FIELD.CREATED.toString(), race.getCreated().getTime());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            if(race.getId() == null) {
                db.insertWithOnConflict(Race.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            } else {
                String[] where = {race.getId().toString()};
                db.updateWithOnConflict(Race.TABLE_NAME, values, Race.FIELD.ID + " = ?", where, SQLiteDatabase.CONFLICT_IGNORE);
            }
        } finally {
            close();
        }
        Log.i(TAG, "Saved " + race.getName());
    }

    /**
     * @return Cursor where the columns are _id, created_at, user, txt
     */
    public Cursor getRaces() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        return db.query(Race.TABLE_NAME, null, null, null, null, null, Race.FIELD.CREATED + " desc");
    }

    /**
     * @return Timestamp of the latest status we ahve it the database
     */
    public long getRaceCount() {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        try {
            String[] count = {"count(*)"};
            Cursor cursor = db.query(Race.TABLE_NAME, count, null, null, null, null, null);
            try {
                return cursor.moveToNext() ? cursor.getLong(0) : Long.MIN_VALUE;
            }
            finally {
                cursor.close();
            }
        }
        finally {
            db.close();
        }
    }

    /**
     * @param id of the status we are looking for
     * @return Text of the status
     */
    public String getRaceDetailsById(long id) {
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        try {
            String[] columns = Race.FIELD.getStringValues();
            Cursor cursor = db.query(Race.TABLE_NAME, columns, Race.FIELD.ID + "=" + id, null,
                                     null, null, null);
            try {
                return cursor.moveToNext() ? cursor.getString(0) : null;
            }
            finally {
                cursor.close();
            }
        }
        finally {
            db.close();
        }
    }


}
