package com.example.javasqlitedb.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.javasqlitedb.Model.Model;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db에 create table

        db.execSQL(Constants.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }

    public long insertData(String foodName, String foodImage, String foodPrice, String foodDescrption,
                           String addTimeStamp, String updateTimeStamp){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Constants.C_NAME,foodName);
        values.put(Constants.C_IMAGE,foodImage);
        values.put(Constants.C_PRICE,foodPrice);
        values.put(Constants.C_DESCRIPTION,foodDescrption);
        values.put(Constants.C_ADD_STAMP,addTimeStamp);
        values.put(Constants.C_UPDATE_STAMP,updateTimeStamp);

        long id = db.insert(Constants.TABLE_NAME,null,values);

        db.close();

        return id;


    }



    //update

    public void update(String id,String name,String price,String description, String image,String addTimeStamp,String updateTimeStamp){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.C_NAME,name);
        values.put(Constants.C_PRICE,price);
        values.put(Constants.C_DESCRIPTION,description);
        values.put(Constants.C_IMAGE,image);
        values.put(Constants.C_ADD_STAMP,addTimeStamp);
        values.put(Constants.C_UPDATE_STAMP,updateTimeStamp);

        database.update(Constants.TABLE_NAME,values,Constants.C_ID + "= ?",new String[]{id});
        database.close();
    }

    //delete

    public void delete(String id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Constants.TABLE_NAME,Constants.C_ID + " =? " ,new String[]{id});
        database.close();
    }

    //get all data
    public ArrayList<Model> getAllData(String orderBy) {

        ArrayList<Model> dataList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + " ORDER BY " + orderBy;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Model model = new Model(
                        ""+ cursor.getInt(cursor.getColumnIndex(Constants.C_ID)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constants.C_NAME)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constants.C_IMAGE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constants.C_PRICE)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constants.C_DESCRIPTION)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constants.C_ADD_STAMP)),
                        ""+ cursor.getString(cursor.getColumnIndex(Constants.C_UPDATE_STAMP))
                );

                dataList.add(model);

            } while (cursor.moveToNext());
        }
        db.close();
        return dataList;



    }

}
