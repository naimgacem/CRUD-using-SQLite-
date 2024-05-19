package com.example.miniprojet;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DB_NAME = "Database";
    public static final int DB_VERSION = 1;
    public static final String TABLE_NAME = "M2Users";
    public static final String ID_COL = "id";

    public static final String NAME_COL = "username";

    public static final String Password_COL = "Password";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + Password_COL + " TEXT)";


        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public void addNewUser(String Username, String Password) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(NAME_COL, Username);
        values.put(Password_COL, Password);


        db.insert(TABLE_NAME, null, values);


        db.close();
    }public boolean isValidUser(String user, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_COL};
        String selection = NAME_COL + " = ?" + " AND " + Password_COL + " = ?";
        String[] selectionArgs = {user, pass};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean isValid = cursor.moveToFirst();
        cursor.close();
        db.close();
        return isValid;
    }
    public boolean isUserExists(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {ID_COL};
        String selection = NAME_COL + " = ?";
        String[] selectionArgs = {user};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean userExists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return userExists;
    }
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, NAME_COL + " = ?", new String[]{username});
        db.close();
    }

    public void deleteLastItem() {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE " + ID_COL + " = (SELECT MAX(" + ID_COL + ") FROM " + TABLE_NAME + ")";
        Log.d("DatabaseHelper", "Executing query: " + deleteQuery);

        db.execSQL(deleteQuery);
        Log.d("DatabaseHelper", "Deleted last item");
        db.close();
    }

    public boolean isValidPassword(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {Password_COL};
        String selection = NAME_COL + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        boolean isValid = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int passwordIndex = cursor.getColumnIndex(Password_COL);
                if (passwordIndex != -1) {
                    String storedPassword = cursor.getString(passwordIndex);
                    isValid = password.equals(storedPassword);
                } else {

                }
            }
            cursor.close();
        }
        db.close();
        return isValid;
    }


    public void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Password_COL, newPassword);
        String selection = NAME_COL + " = ?";
        String[] selectionArgs = {username};
        db.update(TABLE_NAME, values, selection, selectionArgs);
        db.close();
    }




    public void fillListview(ArrayList<Post> list, CustomAdapter adapter) {

        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // Define a query to retrieve all data
        String query = "SELECT * FROM " + TABLE_NAME;

        // Execute the query
        Cursor cursor = db.rawQuery(query, null);

        // Process the data from the cursor
        if (cursor != null) {
            while (cursor.moveToNext()) {


                String Username = cursor.getString(cursor.getColumnIndexOrThrow(NAME_COL));
                String Password = cursor.getString(cursor.getColumnIndexOrThrow(Password_COL));



                // Create an item object and add it to the list
                Post newItem = new Post( Username, Password);
                list.add(newItem);
            }
            // Close the cursor
            cursor.close();
        }

        // Notify the adapter that the data set has changed
        adapter.notifyDataSetChanged();

        // Close the database
        db.close();
    }

}