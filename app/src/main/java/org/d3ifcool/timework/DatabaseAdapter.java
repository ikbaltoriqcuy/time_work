package org.d3ifcool.timework;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cool on 2/24/2018.
 */

public class DatabaseAdapter extends SQLiteOpenHelper{

    private Context mContext;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "timeWorks.db";

    // All table name
    private static final String TABLE_SCHEDULE = "schedule";
    private static final String TABLE_QUOTES = "quotes";
    private static final String TABLE_ACCOUNT = "account";
    private static final String TABLE_DELAY = "delay";


    // schedule Table Columns names
    private static final String KEY_ID = "id_schedule";
    private static final String KEY_NAME = "name_schedule";
    private static final String KEY_DAY = "day";
    private static final String KEY_START_TIME = "start_time";
    private static final String KEY_END_TIME = "end_time";
    private static final String KEY_ACTIVE = "active";


    // quotes Table Columns names
    private static final String KEY_ID_QUOTE = "id_quote";
    private static final String KEY_NAME_QUOTE = "name_quote";

    //account table Columns names
    private static final String KEY_USERNAME = "username";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_IS_LOGIN = "is_login";
    private static final String KEY_MY_QUOTE = "my_quote";

    //current delay table Columns names
    private static final String KEY_DELAY = "delay";



    private static final String CREATE_QUOTES_TABLE = "CREATE TABLE " + TABLE_QUOTES + "("
            + KEY_NAME_QUOTE + " TEXT  )";

    private static final String CREATE_SCHEDULING_TABLE = "CREATE TABLE " + TABLE_SCHEDULE + "("
            + KEY_ID + " TEXT," + KEY_NAME + " TEXT," +KEY_DAY + " TEXT,"
            + KEY_START_TIME + " TEXT," + KEY_END_TIME  + " TEXT," + KEY_ACTIVE + " INTEGER)";

    private static final String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "("
            + KEY_USERNAME + " TEXT PRIMARY KEY," + KEY_IMAGE + " INTEGER," +KEY_IS_LOGIN + " INTEGER, "+
            KEY_MY_QUOTE +" TEXT)";

    private static final String CREATE_DELAY = "CREATE TABLE " + TABLE_DELAY+ "("
            + KEY_DELAY + " INTEGER)";







    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_SCHEDULING_TABLE);
            db.execSQL(CREATE_QUOTES_TABLE);
            db.execSQL(CREATE_ACCOUNT_TABLE);
            db.execSQL(CREATE_DELAY);

        }catch(Exception e) {
           Log.e("Create Database", "Error to Create");
        }

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DELAY);

        // Create tables again
        onCreate(db);
    }

    public long addQuote (String quotes){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME_QUOTE ,quotes);
        // Inserting Row
        long i = db.insert(TABLE_QUOTES, null, values);
        db.close(); // Closing database connection

        Log.i("data",quotes);

        return i;
    }


    public long addSchedule(Schedule schedule) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID , String.valueOf(schedule.getIdSchedule()) );
        values.put(KEY_NAME, String.valueOf(schedule.getNameSchedule())); // Contact Name
        values.put(KEY_DAY,String.valueOf(schedule.getDay()));
        values.put(KEY_START_TIME, String.valueOf(schedule.getStartTime())); // Contact Phone Number
        values.put(KEY_END_TIME, String.valueOf(schedule.getEndTime()));
        values.put(KEY_ACTIVE,schedule.getActive());

        // Inserting Row
        long i = db.insert(TABLE_SCHEDULE, null, values);
        db.close(); // Closing database connection
        return i;
    }

    public long addAccount (Account account){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME ,String.valueOf(account.getmUsername()));
        values.put(KEY_IMAGE ,account.getmImage());
        values.put(KEY_IS_LOGIN ,account.getmIsLogin());
        values.put(KEY_MY_QUOTE,String.valueOf(account.getQuote()));
        // Inserting Row
        long i = db.insert(TABLE_ACCOUNT, null, values);
        db.close(); // Closing database connection


        return i;
    }


    public long addDelay (Integer delay){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DELAY ,delay);
        // Inserting Row
        long i = db.insert(TABLE_DELAY, null, values);
        db.close(); // Closing database connection


        return i;
    }




    // Getting single contact
    public Schedule getSchedule(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SCHEDULE, new String[] { KEY_ID,
                        KEY_NAME, KEY_START_TIME,KEY_END_TIME }, KEY_ID + "=?",
                new String[] { id }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Schedule schedule = new Schedule(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3),
                cursor.getString(4),cursor.getInt(5));
        // return contact
        return schedule;
    }

    public ArrayList<Schedule> getAllSchedule() {
        ArrayList<Schedule> schedulestList = new ArrayList<Schedule>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCHEDULE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule(cursor.getString(0),
                        cursor.getString(1), cursor.getString(2),cursor.getString(3),
                        cursor.getString(4),cursor.getInt(5));
                // Adding contact to list
                schedulestList.add(schedule);
            } while (cursor.moveToNext());
        }

        // return contact list
        return schedulestList;
    }

    public Quotes getAllQuote(){
        Quotes quotes = new Quotes(mContext);
        String selectQuery = "SELECT  * FROM " + TABLE_QUOTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                String data = cursor.getString(0);
                quotes.setQuote(data);

            } while (cursor.moveToNext());
        }

        return  quotes;
    }


    public Account getAccount() {
        Account account = null;
        String selectQuery = "SELECT  * FROM " + TABLE_ACCOUNT;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                account = new Account(cursor.getString(0),
                        cursor.getInt(1), cursor.getInt(2),
                        cursor.getString(3));

            } while (cursor.moveToNext());
        }

        return account;

    }

    public int getDelay() {
        int delay = 0 ;
        String selectQuery = "SELECT  * FROM " + TABLE_DELAY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                delay = cursor.getInt(0);

            } while (cursor.moveToNext());

        }

        return delay;
    }



    // Updating single scheduling
    public int updateSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, String.valueOf(schedule.getNameSchedule())); // Contact Name
        values.put(KEY_DAY,String.valueOf(schedule.getDay()));
        values.put(KEY_START_TIME, String.valueOf(schedule.getStartTime())); // Contact Phone Number
        values.put(KEY_END_TIME, String.valueOf(schedule.getEndTime()));
        values.put(KEY_ACTIVE, schedule.getActive());
        // updating row
        return db.update(TABLE_SCHEDULE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(schedule.getIdSchedule()) });
    }

    // Updating single contact
    public int updateQuote(String newQuote , String currentQuote ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME_QUOTE , newQuote);

        // updating row
        return db.update(TABLE_QUOTES, values, KEY_NAME_QUOTE + " = ?",
                new String[] { currentQuote });
    }

    public int updateAccount(Account account, String curremtUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USERNAME ,account.getmUsername());
        values.put(KEY_IMAGE ,account.getmImage());
        values.put(KEY_IS_LOGIN ,account.getmIsLogin());
        values.put(KEY_MY_QUOTE,account.getQuote());
        // updating row
        return db.update(TABLE_ACCOUNT, values, KEY_USERNAME + " = ?",
                new String[] { String.valueOf(curremtUsername) });
    }

    public int updateDelay(int delay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_DELAY ,delay);


        return db.update(TABLE_DELAY, values, KEY_DELAY + " = ?",
                new String[] {String.valueOf(delay)});

    }



    public void deleteSchedule(Schedule schedule) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SCHEDULE, KEY_ID + " = ?",
                new String[] { String.valueOf(schedule.getIdSchedule()) });
        db.close();
    }


    public void deleteQuote(String quote) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUOTES, KEY_NAME_QUOTE + " = ?",
                new String[] { String.valueOf(quote) });
        db.close();
    }

}

