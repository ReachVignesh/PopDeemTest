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

public class DatabaseHandlerAddressPay extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "freeman4";

    // Login table name
    private static final String TABLE_ADDRESSPAY = "addresspay";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_ADDRESSNAME = "add_name";
    private static final String KEY_REGIONNAME = "region_name";
    private static final String KEY_CITYNAME = "city_name";
    private static final String KEY_STREETNAME = "street_name";
    private static final String KEY_HOUSENUMBER = "house_number";
    private static final String KEY_APARTNUMBER = "apart_number";
    private static final String KEY_PAYABLE = "payable_amt";
    private static final String KEY_COMMENT = "comment_back";
    private static final String KEY_DELIVERYCHECKED = "check";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";

    public DatabaseHandlerAddressPay(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ADDRESSPAY_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_ADDRESSPAY + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ADDRESSNAME + " TEXT NOT NULL, "
                + KEY_REGIONNAME + " TEXT ,"
                + KEY_CITYNAME + " TEXT ,"
                + KEY_STREETNAME + " TEXT ,"
                + KEY_HOUSENUMBER + " TEXT ,"
                + KEY_APARTNUMBER + " TEXT ,"
                + KEY_DELIVERYCHECKED + " TEXT ,"
                + KEY_PAYABLE + " TEXT ,"
                + KEY_COMMENT + " TEXT ,"
                + KEY_UID + " TEXT, "
                + KEY_CREATED_AT + " TEXT" + ")";
        db.execSQL(CREATE_ADDRESSPAY_TABLE);
    }

//    String createStatement =
//            String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY
//                    AUTOINCREMENT, %s TEXT NOT NULL,
//            %s TEXT NOT NULL);",
//    DATABASE_TABLE,
//    KEY_ITEMID,
//    KEY_ITEM,
//    KEY_QTY);

//    db.execSQL("
//    CREATE TABLE " + DATABASE_TABLE + "(" +
//    KEY_ITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//    KEY_ITEM + " TEXT NOT NULL, " +
//    KEY_QTY + " TEXT NOT NULL );"
//            );

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESSPAY);

        // Create tables again
        onCreate(db);
    }

    *//**
     * Storing user details in database
     * *//*
    public void addAddressPay(String name,String region,String city,String street,String house,String apart,String key,String payable,String comment,String uid, String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ADDRESSNAME, name); // Name
        values.put(KEY_REGIONNAME, region); // Email
        values.put(KEY_CITYNAME, city); // Email
        values.put(KEY_STREETNAME, street); // Email
        values.put(KEY_HOUSENUMBER, house); // Email
        values.put(KEY_APARTNUMBER, apart); // Email
        values.put(KEY_DELIVERYCHECKED, key); // Email
        values.put(KEY_PAYABLE, payable); // Email
        values.put(KEY_COMMENT, comment); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At

        // Inserting Row
        db.insert(TABLE_ADDRESSPAY, null, values);
        db.close(); // Closing database connection
    }

    *//**
     * Getting user data from database
     * *//*
    public HashMap<String, String> getAddressPayDetails(){
        HashMap<String,String> address = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_ADDRESSPAY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            address.put("add_name", cursor.getString(1));
            address.put("region_name", cursor.getString(2));
            address.put("city_name", cursor.getString(3));
            address.put("street_name", cursor.getString(4));
            address.put("house_number", cursor.getString(5));
            address.put("apart_number", cursor.getString(6));
            address.put("check", cursor.getString(7));
            address.put("payable_amt", cursor.getString(8));
            address.put("comment_back", cursor.getString(9));
            address.put("uid", cursor.getString(10));
            address.put("created_at", cursor.getString(11));
        }
        cursor.close();
        db.close();
        // return user
        return address;
    }

    *//**
     * Getting user login status
     * return true if rows are there in table
     * *//*
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ADDRESSPAY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();

        // return row count
        return rowCount;
    }

    *//**
     * Re crate database
     * Delete all tables and create them again
     * *//*
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ADDRESSPAY, null, null);
        db.close();
    }

}*/
