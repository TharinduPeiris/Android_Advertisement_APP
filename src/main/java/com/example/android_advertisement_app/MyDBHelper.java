package com.example.android_advertisement_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "advertisements.db";
    //ads table
    public static final String TABLE_NAME = "ADS";
    public static final String COLUMN_ID = "ad_id";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_CONTACTNO = "contactno";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_IMAGE = "image";
    private static final int DATABASE_VERSION = 3; //3

    //user table
    public static final String TABLE_USERS = "users";
    public static final String KEY_ID = "id";
    public static final String KEY_USER_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";


    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public MyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onConfigure(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_TABLE_USERS);

        db.execSQL(" CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID+ " INTEGER NOT NULL," +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_PRICE + " NUMBER NOT NULL, " +
                COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                COLUMN_CONTACTNO + " TEXT NOT NULL, " +
                COLUMN_LOCATION + " TEXT NOT NULL, " +
                COLUMN_IMAGE + " BLOB NOT NULL,"+
                " FOREIGN KEY ("+COLUMN_USER_ID+") REFERENCES users "+"("+KEY_ID+"));"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        this.onCreate(db);
    }

    /**
     * save new ad
     **/
    public void saveNewAD(Advertisment advertisment) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, advertisment.getUser_id());
        values.put(COLUMN_NAME, advertisment.getName());
        values.put(COLUMN_IMAGE, advertisment.getImage());
        values.put(COLUMN_PRICE, advertisment.getPrice());
        values.put(COLUMN_DESCRIPTION, advertisment.getDescription());
        values.put(COLUMN_LOCATION, advertisment.getLocation());
        values.put(COLUMN_CONTACTNO, advertisment.getContactNo());
        // insert table
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     *  filter results
     **/
    public List<Advertisment> peopleList(int filter) {

        String query;
        query = "SELECT  * FROM " + TABLE_NAME + " WHERE user_id="+ filter;

        List<Advertisment> advertismentLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Advertisment advertisment;

        if (cursor.moveToFirst()) {
            do {
                advertisment = new Advertisment();

                advertisment.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                advertisment.setUser_id(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                advertisment.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                advertisment.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                advertisment.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                advertisment.setContactNo(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACTNO)));
                advertisment.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                advertisment.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));

                advertismentLinkedList.add(advertisment);
            } while (cursor.moveToNext());
        }

        return advertismentLinkedList;
    }


    /**
     *  filter all results
     **/
    public List<Advertisment> allAdsList() {

        String query = "SELECT  * FROM " + TABLE_NAME ;

        List<Advertisment> advertismentLinkedList = new LinkedList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Advertisment advertisment;

        if (cursor.moveToFirst()) {
            do {
                advertisment = new Advertisment();

                advertisment.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                advertisment.setUser_id(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                advertisment.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                advertisment.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
                advertisment.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
                advertisment.setContactNo(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACTNO)));
                advertisment.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
                advertisment.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));

                advertismentLinkedList.add(advertisment);
            } while (cursor.moveToNext());
        }

        return advertismentLinkedList;
    }


    /**
     * Query only 1 record
     **/
    public Advertisment getAD(long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT  * FROM " + TABLE_NAME + " WHERE ad_id=" + id;
        Cursor cursor = db.rawQuery(query, null);

        Advertisment receivedAdvertisment = new Advertisment();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            receivedAdvertisment.setUser_id(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
            receivedAdvertisment.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
            receivedAdvertisment.setPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PRICE)));
            receivedAdvertisment.setDescription(cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)));
            receivedAdvertisment.setContactNo(cursor.getString(cursor.getColumnIndex(COLUMN_CONTACTNO)));
            receivedAdvertisment.setLocation(cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)));
            receivedAdvertisment.setImage(cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGE)));
        }


        return receivedAdvertisment;

    }

    /**
     * delete ads
     **/
    public void deleteADRecord(long id, Context context) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE ad_id='" + id + "'");
        Toast.makeText(context, "Deleted successfully.", Toast.LENGTH_SHORT).show();

    }

    /**
     * update ads
     **/
    public void updateADRecord(long adId, Context context, Advertisment advertisment) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_ID, advertisment.getUser_id());
        values.put(COLUMN_NAME, advertisment.getName());
        values.put(COLUMN_IMAGE, advertisment.getImage());
        values.put(COLUMN_PRICE, advertisment.getPrice());
        values.put(COLUMN_DESCRIPTION, advertisment.getDescription());
        values.put(COLUMN_LOCATION, advertisment.getLocation());
        values.put(COLUMN_CONTACTNO, advertisment.getContactNo());
        db.update(TABLE_NAME, values, "ad_id =" + adId, null);
        //you can use the constants above instead of typing the column names
        // db.execSQL("UPDATE  " + TABLE_NAME + " SET name ='" + updatedAd.getName() + "', price ='" + updatedAd.getPrice() + "', description ='" + updatedAd.getDescription() +"', contactno ='" + updatedAd.getContactNo() +"', location ='" + updatedAd.getLocation() + "', image ='" + updatedAd.getImage() + "'  WHERE ad_id='" + personId + "'");
        Toast.makeText(context, "Updated successfully.", Toast.LENGTH_SHORT).show();


    }


    public User Authenticate(User user) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cur != null && cur.moveToFirst()) {

            User user1 = new User(cur.getString(0), cur.getString(1), cur.getString(2), cur.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }


        return null;
    }

    /**
     * user email
     **/
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cur != null && cur.moveToFirst()) {

            return true;
        }

        //if email does not exist return false
        return false;
    }


    public void addUser(User user) {
        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_USER_NAME, user.userName);
        val.put(KEY_EMAIL, user.email);
        val.put(KEY_PASSWORD, user.password);
        long todo_id = db.insert(TABLE_USERS, null, val);
    }


}
