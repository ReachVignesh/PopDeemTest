/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package jsoft.projects.photoclick.library;

/*import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DatabaseHandlerAddress extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "test";

    // Login table name
    private static final String TABLE_ADDRESS = "testtable";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESSNAME = "add_name";
    private static final String KEY_REGIONNAME = "region_name";
    private static final String KEY_CITYNAME = "city_name";
    private static final String KEY_STREETNAME = "street_name";
    private static final String KEY_HOUSENUMBER = "house_number";
    private static final String KEY_APARTNUMBER = "apart_number";
    private static final String KEY_DELIVERYCHECKED = "check";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public DatabaseHandlerAddress(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADDRESS_TABLE = "CREATE TABLE " + TABLE_ADDRESS + "("
                + KEY_ID + " INTEGER PRIMARY KEY ,"
                + KEY_ADDRESSNAME + " TEXT ,"
                + KEY_REGIONNAME + " TEXT ,"
                + KEY_CITYNAME + " TEXT ,"
                + KEY_STREETNAME + " TEXT ,"
                + KEY_HOUSENUMBER + " TEXT ,"
                + KEY_APARTNUMBER + " TEXT ,"
                + KEY_DELIVERYCHECKED + " TEXT ,"
                + KEY_UID + " TEXT ,"
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_ADDRESS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);

        // Create tables again
        onCreate(db);
    }

    *//**
     * Storing user details in database
     * *//*
    public void addAddress(String name,String region,String city,String street,String house,String apart,String key, String uid, String created_at)
//    ,String region,String city,String street,String house,String apart,String key,
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESSNAME, name); // Name
        values.put(KEY_REGIONNAME, region); // Email
        values.put(KEY_CITYNAME, city); // Email
        values.put(KEY_STREETNAME, street); // Email
        values.put(KEY_HOUSENUMBER, house); // Email
        values.put(KEY_APARTNUMBER, apart); // Email
        values.put(KEY_DELIVERYCHECKED, key); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        db.insert(TABLE_ADDRESS, null, values);
        db.close(); // Closing database connection
    }

    *//**
     * Getting user data from database
     * *//*
    public HashMap<String, String> getAddressDetails(){
        HashMap<String,String> addressfree = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_ADDRESS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            addressfree.put("add_name", cursor.getString(1));
            addressfree.put("region_name", cursor.getString(2));
            addressfree.put("city_name", cursor.getString(3));
            addressfree.put("street_name", cursor.getString(4));
            addressfree.put("house_number", cursor.getString(5));
            addressfree.put("apart_number", cursor.getString(6));
            addressfree.put("check", cursor.getString(7));
            addressfree.put("uid", cursor.getString(8));
            addressfree.put("created_at", cursor.getString(9));
        }
        cursor.close();
        db.close();
        // return user
        return addressfree;
    }

    *//**
     * Getting user login status
     * return true if rows are there in table
     * *//*
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ADDRESS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    *//**
     * Re create database
     * Delete all tables and create them again
     * *//*
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ADDRESS, null, null);
        db.close();
    }

}*/
