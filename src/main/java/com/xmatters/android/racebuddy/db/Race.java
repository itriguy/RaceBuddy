package com.xmatters.android.racebuddy.db;

import java.util.Date;

import android.provider.BaseColumns;

/**
 * Helper class for the Race table.
 */
public class Race {
    //SQL Table
    public static final String TABLE_NAME = "RACE";
    //SQL fields
    public static enum FIELD {
        ID(BaseColumns._ID), NAME, DATE("RACE_DATE"), ESTIMATED_TIME, ACTUAL_TIME, CREATED;
        private String value;
        FIELD(){
            value = this.name();
        }
        FIELD(String value){
            this.value = value;
        }
        @Override
        public String toString(){
            return value;
        }
        public static String[] getStringValues(){
            String[] stringValues = new String[values().length];
            for(int i = 0; i < values().length; i++){
                stringValues[i] = values()[i].toString();
            }
            return stringValues;
        }
    }
    //Queries
    public static String createTableSql() {
        return "create table " + TABLE_NAME + " (" + FIELD.ID + " int primary key, " +
                                FIELD.NAME + " text, " + FIELD.DATE + " text, " + FIELD.ESTIMATED_TIME + " text, " +
                                FIELD.ACTUAL_TIME + " text, " + FIELD.CREATED + " int)";
    }
    public static String dropTableSql() {
        return "drop table if exists " + TABLE_NAME;
    }

    private Integer id;
    private String name;
    private String date;
    private String estimatedTime;
    private String actualTime;
    private Date created;

    public Race(){
        this.created = new Date();
    }

    public Race(String name, String date, String estimatedTime, String actualTime, Date created) {
        this.name = name;
        this.date = date;
        this.estimatedTime = estimatedTime;
        this.actualTime = actualTime;
        this.created = created;
    }

    public Race(Integer id, String name, String date, String estimatedTime, String actualTime, Date created) {
        this(name, date, estimatedTime, actualTime, created);
        this.id = id;
    }

    /**
     * Convert race to tweet
     * @return String
     */
    public String toTweet(){
        StringBuilder builder = new StringBuilder();
        boolean completed = (actualTime == null || actualTime.isEmpty()) ? false : true;
        builder.append(completed ? "Raced " : "Racing ");
        builder.append(name);
        builder.append(" on " + date);
        builder.append(completed ? " finishing in " : " expecting ");
        builder.append(completed ? actualTime : estimatedTime);
        builder.append(" - via RaceBuddy");
        return builder.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getActualTime() {
        return actualTime;
    }

    public void setActualTime(String actualTime) {
        this.actualTime = actualTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
